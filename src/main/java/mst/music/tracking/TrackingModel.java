package mst.music.tracking;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackingModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackingModel.class);

	private final TrackingView view;

	private int beatsPerMinute;
	private TrackDefinition definition;
	private TrackingState state;
	private TrackingRecord record;
	private double endTImestamp;
	private double startTimestamp;

	public TrackingModel(TrackingView view) {
		this.view = view;
		this.state = TrackingState.WAITING;
		this.beatsPerMinute = -1;
		this.definition = null;
	}

	public void setTrackDefinitions(TrackDefinition trackDefinition) {
		this.definition = trackDefinition;
		this.view.showTrack(trackDefinition);
	}

	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
		this.view.showTempo(beatsPerMinute);
	}

	public void addPitchDetectionResult(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if (state.equals(TrackingState.WAITING)) {
			handleInWaiting(pitchDetectionResult, audioEvent);
		} else if (state.equals(TrackingState.TRACKING)) {
			handleInTracking(pitchDetectionResult, audioEvent);
		}
	}

	private void handleInTracking(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		LOGGER.debug("ts: {}", audioEvent.getTimeStamp());
		record.add(pitchDetectionResult, audioEvent);
		if (audioEvent.getEndTimeStamp() > endTImestamp) {
			LOGGER.debug("set state to done");
			state = TrackingState.DONE;
			view.showTrackingDone();
		} else {
			int currentIndex = definition.calculateCurrentNoteIndex((long) ((audioEvent.getTimeStamp() - startTimestamp) * 1000), beatsPerMinute);
			LOGGER.debug("set current note index to: {}", currentIndex);
			view.showCurrentNode(currentIndex);
		}
	}

	private void handleInWaiting(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if(pitchDetectionResult.getPitch() != -1) {
			LOGGER.debug("set state to tracking");
			state = TrackingState.TRACKING;
			record = new TrackingRecord();
			startTimestamp = audioEvent.getTimeStamp();
			endTImestamp = audioEvent.getTimeStamp() + definition.getBeatCount() / beatsPerMinute * 60;
			LOGGER.debug("end timestamp: {}", endTImestamp);
		}
	}
}
