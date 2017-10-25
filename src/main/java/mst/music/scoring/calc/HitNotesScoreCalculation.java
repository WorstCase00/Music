package mst.music.scoring.calc;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.analysis.Pitch;
import mst.music.scoring.Score;
import mst.music.track.TrackDefinition;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class HitNotesScoreCalculation extends BaseScoreCalculation {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HitNotesScoreCalculation.class);

	public HitNotesScoreCalculation(TrackDefinition trackDefinition, int bpm) {
		super(trackDefinition, bpm);
	}

	@Override
	public Score add(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		trackingRecord.add(pitchDetectionResult, audioEvent);
		float scoreValue = calculateCurrentScore();
		float percentage = scoreValue / trackingRecord.getTimestamps().size();

		return new Score(
				scoreValue,
				percentage);
	}

	private float calculateCurrentScore() {
		List<Long> timestamps = trackingRecord.getTimestamps();
		float sum = 0;
		LOGGER.debug("timestamp count: {}", timestamps.size());
		for (int i = 0; i < timestamps.size(); i++) {
			Pitch expectedPitch = definition.calculatePitch(timestamps.get(i), beatsPerMinute);
			Optional<Pitch> actualPitch = trackingRecord.getResult(i).getNearestPitch();

			LOGGER.debug("got pitch for expected {}: {}", expectedPitch, actualPitch);
			if (expectedPitch.equals(actualPitch.orElse(null))) {
				sum += 1;
			}
		}
		LOGGER.debug("current score: {}", sum);
		return sum;
	}

}
