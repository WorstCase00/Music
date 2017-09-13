package mst.music.scoring;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.track.TrackDefinition;

public class ScoringController implements PitchDetectionHandler {

	private final ScoringModel model;

	public ScoringController(ScoringModel model) {
		this.model = model;
	}

	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		model.addPitchDetectionResult(pitchDetectionResult, audioEvent);
	}

	public void setExercise(TrackDefinition trackDefinition, int bpm) {
		model.setTrackDefinitions(trackDefinition);
		model.setBeatsPerMinute(bpm);
	}

	public void refresh() {
		model.refresh();
	}
}
