package mst.music.proto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ScoringPanel extends JPanel {

	public ScoringPanel() {
		super(new GridLayout(0,1));
		setBorder(new TitledBorder("Scoring"));
	}
}