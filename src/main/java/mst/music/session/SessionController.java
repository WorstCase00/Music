package mst.music.session;

import mst.music.scoring.ScoringController;
import mst.music.tracking.TrackingController;
import mst.music.tracking.TrackingListener;

public class SessionController implements TrackingListener {
	private final SessionModel sessionModel;
	private final ScoringController scoringController;
	private final TrackingController trackingController;

	public SessionController(SessionModel sessionModel, ScoringController scoringController, TrackingController trackingController) {
		this.sessionModel = sessionModel;
		this.scoringController = scoringController;
		this.trackingController = trackingController;
	}

	@Override
	public void onTrackStarted() {

	}

	@Override
	public void onTrackEnded() {

	}

	public void onRefresh() {
		this.scoringController.refresh();
		this.trackingController.refresh();
	}
}
