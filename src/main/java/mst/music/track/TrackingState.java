package mst.music.track;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import org.slf4j.LoggerFactory;

public class TrackingState implements PitchDetectionHandler {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TrackingState.class);

	private State state;

	private int beatsPerMinute;
	private TrackDefinition definition;
	private double endTimestamp;
	private double startTimestamp;

	public void setTrackDefinitions(TrackDefinition trackDefinition) {
		this.definition = trackDefinition;
	}

	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
	}

	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if (state.equals(State.WAITING)) {
			handleInWaiting(pitchDetectionResult, audioEvent);
		} else if (state.equals(State.TRACKING)) {
			handleInTracking(pitchDetectionResult, audioEvent);
		}
	}

	private void handleInTracking(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		LOGGER.debug("ts: {}", audioEvent.getTimeStamp());
		if (audioEvent.getEndTimeStamp() > endTimestamp) {
			LOGGER.debug("set state to done");
			state = State.DONE;
		}
	}

	private void handleInWaiting(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if (pitchDetectionResult.getPitch() != -1) {
			LOGGER.debug("set state to tracking");
			state = State.TRACKING;
			startTimestamp = audioEvent.getTimeStamp();
			endTimestamp = audioEvent.getTimeStamp() + definition.getBeatCount() / beatsPerMinute * 60;
			LOGGER.debug("end timestamp: {}", endTimestamp);
		}
	}

	public enum State {
		WAITING,
		TRACKING,
		DONE,
	}
}
