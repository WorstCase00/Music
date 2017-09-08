package mst.music.track;

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
}
