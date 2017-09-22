package mst.music.scoring;

import mst.music.track.TrackDefinition;

public class DefaultRunResult implements RunResult {
	private final TrackDefinition trackingRecord;
	private final int beatsPerMinute;
	private final float currentScore;

	public DefaultRunResult(TrackDefinition trackingRecord, int beatsPerMinute, float currentScore) {
		this.trackingRecord = trackingRecord;
		this.beatsPerMinute = beatsPerMinute;
		this.currentScore = currentScore;
	}

	@Override
	public String getTitle() {
		return "hans";
	}

	@Override
	public int getBpm() {
		return beatsPerMinute;
	}

	@Override
	public float getScore() {
		return currentScore;
	}
}
