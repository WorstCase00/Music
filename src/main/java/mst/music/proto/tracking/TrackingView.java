package mst.music.proto.tracking;

import abc.notation.MusicElement;
import abc.notation.NoteAbstract;
import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;
import abc.ui.swing.JScoreElement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TrackingView  extends JPanel {

	private final JScoreComponent scoreUI;
	private final List<JScoreElement> abc4jNotes;

	public TrackingView() {
		super(new GridLayout(1,1));
		setBorder(new TitledBorder("Tracking"));

		String tuneAsString = "X:0\nT:A simple scale exercise\nM:4/4\nK:D\nL:1/4\n" +
				"G E E2|F D D2|C D E F|G G G2|G E E2|F D D2|C E G G|C4\n";
		Tune tune = new TuneParser().parse(tuneAsString);
		scoreUI =new JScoreComponent();
		scoreUI.setTune(tune);
		abc4jNotes = (List<JScoreElement>) tune.getMusic().getFirstVoice().stream()
				.filter(e -> e instanceof NoteAbstract)
				.map(e -> scoreUI.getRenditionElementFor((MusicElement) e))
				.collect(Collectors.toList());
		add(scoreUI);
	}

	public void showTrack(TrackDefinition trackDefinition) {

	}

	public void showTempo(int beatsPerMinute) {

	}

	public void showCurrentNode(int index) {
		scoreUI.setSelectedItems(abc4jNotes.subList(0, index));
		scoreUI.repaint();

	}

	public void showTrackingDone() {
	}
}
