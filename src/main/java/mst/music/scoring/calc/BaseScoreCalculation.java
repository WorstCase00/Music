package mst.music.scoring.calc;

import mst.music.track.TrackDefinition;
import mst.music.tracking.TrackingRecord;

abstract class BaseScoreCalculation implements ScoreCalculation {
	protected TrackingRecord trackingRecord;
	protected TrackDefinition definition;
	protected int beatsPerMinute;

	BaseScoreCalculation(TrackDefinition trackDefinition, int bpm) {
		this.definition = trackDefinition;
		this.trackingRecord = new TrackingRecord();
		this.beatsPerMinute = bpm;
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
}
