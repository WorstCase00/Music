package mst.music.proto.tracking;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;

public class TrackingController implements PitchDetectionHandler {

	private final TrackingModel model;

	public TrackingController(TrackingModel model) {
		this.model = model;
	}

	public void update(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		model.addPitchDetectionResult(pitchDetectionResult, audioEvent);
	}

	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		this.update(pitchDetectionResult, audioEvent);
	}

	public void setExcercise(TrackDefinition trackDefinition, int bpm) {
		model.setTrackDefinitions(trackDefinition);
		model.setBeatsPerMinute(bpm);
	}
}
