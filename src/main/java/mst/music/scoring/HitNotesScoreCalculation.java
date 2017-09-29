package mst.music.scoring;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import mst.music.analysis.Pitch;
import mst.music.track.TrackDefinition;
import mst.music.tracking.TrackingRecord;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class HitNotesScoreCalculation implements ScoreCalculation {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HitNotesScoreCalculation.class);

	private TrackingRecord trackingRecord;
	private TrackDefinition definition;
	private int beatsPerMinute;

	public HitNotesScoreCalculation(TrackDefinition trackDefinition, int bpm) {
		this.definition = trackDefinition;
		this.beatsPerMinute = bpm;
		this.trackingRecord = new TrackingRecord();
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

	@Override
	public void reset() {
		trackingRecord = new TrackingRecord();
	}

	@Override
	public void setDefinition(TrackDefinition definition) {
		this.definition = definition;
	}

	@Override
	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
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
