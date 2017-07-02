package mst.music.proto.tracking;

import abc.notation.MusicElement;
import abc.notation.Note;
import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.stream.Collectors;

public class TrackingView  extends JPanel {

	private JScoreComponent scoreComponent;

	public TrackingView() {
		super(new GridLayout(1,1));
		setBorder(new TitledBorder("Tracking"));

		String tuneAsString = "X:0\nT:A simple scale exercise\nM:4/4\nK:D\nL:1/4\nG E E2|G A Bc|de fg-|gf ed|cB A G|FE DC\n";
		Tune tune = new TuneParser().parse(tuneAsString);
		java.util.List<Note> notes = (java.util.List<Note>) tune.getMusic().getFirstVoice().stream()
				.filter(e -> e instanceof Note)
				.map(e -> (Note) e)
				.collect(Collectors.toList());
		JScoreComponent scoreUI =new JScoreComponent();
		scoreUI.setTune(tune);
		scoreUI.setSelectedItem((MusicElement) tune.getMusic().getFirstVoice().get(2));//.getElementAtStreamPosition(3));
		add(scoreUI);
		scoreUI.colo
	}

	public void showTrack(TrackDefinition trackDefinition) {

	}

	public void showTempo(int beatsPerMinute) {

	}
}
