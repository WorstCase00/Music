package mst.music.tracking;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import com.google.common.collect.Lists;
import mst.music.track.PitchDetectionEvent;

import java.util.List;
import java.util.stream.Collectors;

public class TrackingRecord {

	private final List<PitchDetectionEvent> detectionResults = Lists.newArrayList();

	public void add(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		detectionResults.add(new PitchDetectionEvent(
				pitchDetectionResult.getPitch(),
				pitchDetectionResult.getProbability(),
				(long) audioEvent.getTimeStamp() * 1000
		));
	}

	public List<Long> getTimestamps() {
		return detectionResults.stream().map(event -> event.getTimestamp()).collect(Collectors.toList());
	}


	public PitchDetectionEvent getResult(int resultIndex) {
		return detectionResults.get(resultIndex);
	}
}
