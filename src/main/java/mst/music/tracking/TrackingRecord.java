package mst.music.tracking;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import com.google.common.collect.Lists;

import java.util.List;

public class TrackingRecord {

	private final List<PitchDetectionResult> detectionResults = Lists.newArrayList();
	private final List<AudioEvent> audioEvents = Lists.newArrayList();

	public void add(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		detectionResults.add(pitchDetectionResult);
		audioEvents.add(audioEvent);
	}
}
