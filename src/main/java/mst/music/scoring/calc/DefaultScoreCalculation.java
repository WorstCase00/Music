package mst.music.scoring.calc;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.analysis.Pitch;
import mst.music.scoring.Score;
import mst.music.track.PitchDetectionEvent;
import mst.music.track.TrackDefinition;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultScoreCalculation extends BaseScoreCalculation {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DefaultScoreCalculation.class);

	public DefaultScoreCalculation(TrackDefinition trackDefinition, int bpm) {
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
			PitchDetectionEvent result = trackingRecord.getResult(i);

			float expectedFrequency = expectedPitch.getFrequency();
			float actualFrequency = result.getFrequency();

			float delta = calculateDelta(expectedFrequency, actualFrequency);
			LOGGER.debug("delta for frequency tuple ({}, {}): {}", new Object[] {expectedFrequency, actualFrequency, delta});
			sum += delta;
		}
		LOGGER.debug("current score: {}", sum);
		return sum;
	}

	private static float calculateDelta(float expectedFrequency, float actualFrequency) {
		if (actualFrequency < 0) {
			return 0;
		}
		return 1 - (Math.abs(expectedFrequency - actualFrequency)) / (expectedFrequency + actualFrequency);
	}

}
