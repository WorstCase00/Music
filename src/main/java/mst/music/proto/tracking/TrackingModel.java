package mst.music.proto.tracking;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;

public class TrackingModel {

	private final TrackingView view;

	private int beatsPerMinute;
	private TrackDefinition definition;
	private TrackingState state;
	private TrackingRecord record;
	private double expectedEndTimeStamp;

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
		record.add(pitchDetectionResult, audioEvent);
		if (audioEvent.getEndTimeStamp() > expectedEndTimeStamp) {
			state = TrackingState.DONE;
		}
	}

	private void handleInWaiting(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if(pitchDetectionResult.getPitch() != -1) {
			state = TrackingState.TRACKING;
			record = new TrackingRecord();
			expectedEndTimeStamp = audioEvent.getTimeStamp() * 1000 +
					definition.getBeatCount() / beatsPerMinute * 60 * 1000;
		}
	}
}
