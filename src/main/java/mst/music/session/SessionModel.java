package mst.music.session;

import mst.music.scoring.RunResult;

public class SessionModel {
	private final SessionView view;

	public SessionModel(SessionView view) {
		this.view = view;
	}

	public void addRunResults(RunResult runResult) {
		view.addRunResult(runResult);
	}
}
