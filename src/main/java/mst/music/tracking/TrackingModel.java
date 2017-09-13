package mst.music.tracking;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import com.google.common.collect.Lists;
import mst.music.track.TrackDefinition;
import mst.music.track.TrackingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TrackingModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackingModel.class);

	private final TrackingView view;

	private int beatsPerMinute;
	private TrackDefinition definition;
	private TrackingState.State state;
	private TrackingRecord record;
	private double endTimestamp;
	private double startTimestamp;

	private final List<TrackingListener> listeners;

	public TrackingModel(TrackingView view) {
		this.view = view;
		this.state = TrackingState.State.WAITING;
		this.beatsPerMinute = -1;
		this.definition = null;
		this.listeners = Lists.newArrayList();
	}

	public void setTrackDefinitions(TrackDefinition trackDefinition) {
		this.definition = trackDefinition;
		this.view.showTrack(trackDefinition);
	}

	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
		this.view.showTempo(beatsPerMinute);
	}

	public void addListener(TrackingListener listener) {
		this.listeners.add(listener);
	}

	public void addPitchDetectionResult(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if (state.equals(TrackingState.State.WAITING)) {
			handleInWaiting(pitchDetectionResult, audioEvent);
		} else if (state.equals(TrackingState.State.TRACKING)) {
			handleInTracking(pitchDetectionResult, audioEvent);
		}
	}

	private void handleInTracking(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		LOGGER.debug("ts: {}", audioEvent.getTimeStamp());
		record.add(pitchDetectionResult, audioEvent);
		if (audioEvent.getEndTimeStamp() > endTimestamp) {
			LOGGER.debug("set state to done");
			state = TrackingState.State.DONE;
			view.showTrackingDone();
			listeners.stream().forEach(listener -> listener.onTrackEnded());
		} else {
			int currentIndex = definition.calculateCurrentNoteIndex((long) ((audioEvent.getTimeStamp() - startTimestamp) * 1000), beatsPerMinute);
			LOGGER.debug("set current note index to: {}", currentIndex);
			view.showCurrentNode(currentIndex);
		}
	}

	private void handleInWaiting(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
		if(pitchDetectionResult.getPitch() != -1) {
			LOGGER.debug("set state to tracking");
			state = TrackingState.State.TRACKING;
			record = new TrackingRecord();
			startTimestamp = audioEvent.getTimeStamp();
			endTimestamp = audioEvent.getTimeStamp() + definition.getBeatCount() / beatsPerMinute * 60;
			LOGGER.debug("end timestamp: {}", endTimestamp);
			listeners.stream().forEach(listener -> listener.onTrackStarted());
		}
	}

	public void refresh() {
		this.state = TrackingState.State.WAITING;
		this.view.refresh();
	}
}
