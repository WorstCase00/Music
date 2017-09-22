package mst.music.tracking;

import abc.notation.MusicElement;
import abc.notation.NoteAbstract;
import abc.notation.Tune;
import abc.parser.TuneParser;
import abc.ui.swing.JScoreComponent;
import abc.ui.swing.JScoreElement;
import com.google.common.collect.Lists;
import mst.music.track.TrackDefinition;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class TrackingView  extends JPanel {

	private final JScoreComponent scoreUI;
	private final JComboBox<String>  trackBox;
	private final TrackParser trackParser = new TrackParser();

	private List<JScoreElement> abc4jNotes;

	public TrackingView() {
		super(new GridLayout(2,1));
		setBorder(new TitledBorder("Tracking"));

		scoreUI =new JScoreComponent();
		Tune tune = new TuneParser().parse(trackParser.get(TrackDefinition.HANSL));
		abc4jNotes = (List<JScoreElement>) tune.getMusic().getFirstVoice().stream()
				.filter(e -> e instanceof NoteAbstract)
				.map(e -> scoreUI.getRenditionElementFor((MusicElement) e))
				.collect(Collectors.toList());
		scoreUI.setTune(tune);
		add(scoreUI);

		this.trackBox = new JComboBox<>(new Vector<>(Lists.newArrayList(
				TrackDefinition.HANSL.getTitle(),
				TrackDefinition.SUM.getTitle()
		)));
		add(trackBox);
	}

	public void addTrackSelectionListener(ItemListener listener) {
		trackBox.addItemListener(listener);
	}

	public void showTrack(TrackDefinition trackDefinition) {
		Tune tune = new TuneParser().parse(trackParser.get(trackDefinition));
		abc4jNotes = (List<JScoreElement>) tune.getMusic().getFirstVoice().stream()
				.filter(e -> e instanceof NoteAbstract)
				.map(e -> scoreUI.getRenditionElementFor((MusicElement) e))
				.collect(Collectors.toList());
		scoreUI.setTune(tune);
		refresh();
	}

	public void refresh() {
		scoreUI.setSelectedItems(Collections.emptyList());
		scoreUI.repaint();

	}

	public void showTempo(int beatsPerMinute) {
		// TODO
	}

	public void showCurrentNode(int index) {
		scoreUI.setSelectedItems(abc4jNotes.subList(0, index+1));
		scoreUI.repaint();

	}

	public void showTrackingDone() {
		// TODO
	}
}
