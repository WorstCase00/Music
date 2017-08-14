package mst.music.scoring;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.track.TrackDefinition;
import mst.music.track.TrackingState;
import org.slf4j.LoggerFactory;

public class ScoringModel {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ScoringModel.class);

	private final ScoringView view;

	private TrackingState.State state;
	private TrackDefinition definition;
	private int beatsPerMinute;
	private double startTimestamp;
	private double endTimestamp;

	public ScoringModel(ScoringView scoringView) {
		this.view = scoringView;
		this.state = TrackingState.State.WAITING;
	}

	public void setTrackDefinitions(TrackDefinition trackDefinition) {
		this.definition = trackDefinition;
	}

	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
	}

	public void addPitchDetectionResult(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if (state.equals(TrackingState.State.WAITING)) {
			handleInWaiting(pitchDetectionResult, audioEvent);
		} else if (state.equals(TrackingState.State.TRACKING)) {
			handleInTracking(pitchDetectionResult, audioEvent);
		}
	}

	private void handleInTracking(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		LOGGER.debug("ts: {}", audioEvent.getTimeStamp());
		if (audioEvent.getEndTimeStamp() > endTimestamp) {
			LOGGER.debug("set state to done");
			state = TrackingState.State.DONE;
		} else {
			int currentIndex = definition.calculateCurrentNoteIndex((long) ((audioEvent.getTimeStamp() - startTimestamp) * 1000), beatsPerMinute);
			LOGGER.debug("set current note index to: {}", currentIndex);
			view.update();
		}
	}

	private void handleInWaiting(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if(pitchDetectionResult.getPitch() != -1) {
			LOGGER.debug("set state to tracking");
			state = TrackingState.State.TRACKING;
			startTimestamp = audioEvent.getTimeStamp();
			endTimestamp = audioEvent.getTimeStamp() + definition.getBeatCount() / beatsPerMinute * 60;
			LOGGER.debug("end timestamp: {}", endTimestamp);
		}
	}
}
