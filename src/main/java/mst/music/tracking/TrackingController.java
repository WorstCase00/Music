package mst.music.tracking;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.track.TrackDefinition;

public class TrackingController implements PitchDetectionHandler {

	private final TrackingModel model;

	public TrackingController(TrackingModel model) {
		this.model = model;
	}

	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		model.addPitchDetectionResult(pitchDetectionResult, audioEvent);
	}

	public void setExercise(TrackDefinition trackDefinition, int bpm) {
		model.setTrackDefinition(trackDefinition);
		model.setBeatsPerMinute(bpm);
	}

	public void refresh() {
		model.refresh();
	}

	public void setExercise(TrackDefinition trackDefinition) {
		setExercise(trackDefinition, model.getBeatsPerMinute());
	}
}
