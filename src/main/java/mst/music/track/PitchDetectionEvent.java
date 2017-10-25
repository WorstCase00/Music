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
		return Optional.of(getRealPitch());
	}

	private Pitch getRealPitch() {
		int index = Collections.binarySearch(Pitch.PITCH_FREQUENCIES, frequency);
		if (index >= 0) {
			return Pitch.LADDER.get(index);
		}
		int insertionPoint = (-index) - 1;
		if (insertionPoint == 0) {
			return Pitch.LADDER.get(0);
		} else if (insertionPoint == Pitch.LADDER.size()) {
			return Pitch.LADDER.get(insertionPoint - 1);
		}

		if (Math.abs(frequency - Pitch.LADDER.get(insertionPoint-1).getFrequency()) <=
				Math.abs(frequency - Pitch.LADDER.get(insertionPoint).getFrequency())) {
			return Pitch.LADDER.get(insertionPoint-1);
		}
		return Pitch.LADDER.get(insertionPoint);
	}
}
