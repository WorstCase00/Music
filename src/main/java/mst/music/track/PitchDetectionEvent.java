package mst.music.track;

import mst.music.analysis.Pitch;

import java.util.Collections;
import java.util.Optional;

public class PitchDetectionEvent {

	private final float frequency;
	private final float probability;
	private final long timestamp;

	public PitchDetectionEvent(float frequency, float probability, long timestamp) {
		this.frequency = frequency;
		this.probability = probability;
		this.timestamp = timestamp;
	}

	public float getFrequency() {
		return frequency;
	}

	public float getProbability() {
		return probability;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Optional<Pitch> getNearestPitch() {
		if (frequency == -1) {
			return Optional.empty();
		}
		return Optional.of(Pitch.getNearestPitch(frequency));
	}


}
