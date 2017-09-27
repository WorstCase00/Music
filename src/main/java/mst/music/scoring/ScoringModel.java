package mst.music.scoring;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.track.TrackDefinition;
import mst.music.track.TrackingState;
import org.slf4j.LoggerFactory;

public class ScoringModel {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ScoringModel.class);

	private final ScoringView view;
	private final ScoreCalculation scoreCalculation;

	private TrackingState.State state;
	private TrackDefinition definition;
	private int beatsPerMinute;
	private double startTimestamp;
	private double endTimestamp;
	private Score currentScore;

	public ScoringModel(ScoringView scoringView, ScoreCalculation scoreCalculation) {
		this.view = scoringView;
		this.scoreCalculation = scoreCalculation;
		this.state = TrackingState.State.WAITING;
	}

	public void refresh() {
		currentScore = Score.EMPTY;
		state = TrackingState.State.WAITING;
		view.updateScoreBar(0f);
		view.updateCurrentScore(0f);
	}

	public void setTrackDefinitions(TrackDefinition trackDefinition) {
		scoreCalculation.setDefinition(trackDefinition);
		this.definition = trackDefinition;
	}

	public void setBeatsPerMinute(int beatsPerMinute) {
		scoreCalculation.setBeatsPerMinute(beatsPerMinute);
		this.beatsPerMinute = beatsPerMinute;
	}

	public void addPitchDetectionResult(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if (state.equals(TrackingState.State.WAITING)) {
			handleInWaiting(pitchDetectionResult, audioEvent);
		} else if (state.equals(TrackingState.State.TRACKING)) {
			handleInTracking(pitchDetectionResult, audioEvent);
		}
	}

	float getCurrentScore() {
		return currentScore.getValue();
	}

	private void handleInTracking(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		LOGGER.debug("ts: {}", audioEvent.getTimeStamp());
		if (audioEvent.getEndTimeStamp() > endTimestamp) {
			LOGGER.debug("set state to done");
			state = TrackingState.State.DONE;
		} else {
			int currentIndex = definition.calculateCurrentNoteIndex((long) ((audioEvent.getTimeStamp() - startTimestamp) * 1000), beatsPerMinute);
			LOGGER.debug("set current note index to: {}", currentIndex);
			currentScore = scoreCalculation.add(pitchDetectionResult, audioEvent);
			view.updateScoreBar(currentScore.getPercentage());
			view.updateCurrentScore(currentScore.getValue());
		}
	}

	private void handleInWaiting(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if(pitchDetectionResult.getPitch() != -1) {
			LOGGER.debug("set state to tracking");
			state = TrackingState.State.TRACKING;
			startTimestamp = audioEvent.getTimeStamp();
			endTimestamp = audioEvent.getTimeStamp() + definition.getBeatCount() / beatsPerMinute * 60;
			scoreCalculation.reset();
			currentScore = scoreCalculation.add(pitchDetectionResult, audioEvent);
			LOGGER.debug("end timestamp: {}", endTimestamp);
		}
	}

	public RunResult getCurrentRunResults() {
		return new DefaultRunResult(
				definition,
				beatsPerMinute,
				currentScore.getValue()
		);
	}
}
