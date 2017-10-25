package mst.music.scoring.calc;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.scoring.Score;
import mst.music.track.TrackDefinition;

public class PauseRespectingScoreCalculation extends BaseScoreCalculation {

	public PauseRespectingScoreCalculation(TrackDefinition trackDefinition, int bpm) {
		super(trackDefinition, bpm);
	}

	@Override
	public Score add(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		return null;
	}

}
