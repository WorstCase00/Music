package mst.music.scoring;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.track.TrackDefinition;

public interface ScoreCalculation {

	Score add(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent);

	void reset();

	void setDefinition(TrackDefinition definition);

	void setBeatsPerMinute(int beatsPerMinute);
}
