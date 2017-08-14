package mst.music.scoring;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;

public class ScoringView extends JPanel {

	private static final Random RANDOM = new Random(321l);

	private final JPanel scoreBar;

	public ScoringView() {
		super(new GridLayout(1,1));
		setBorder(new TitledBorder("Scoring"));
		this.scoreBar = new JPanel();
//		this.scoreBar.setBackground(Color.black);
//		this.scoreBar.setSize(10, 10);
		add(scoreBar);
		scoreBar.setVisible(true);

	}

	public void update() {

		setBackground(Color.getHSBColor(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat()));
//		scoreBar.invalidate();
//		Graphics graphics = scoreBar.getGraphics();
		repaint();
//		scoreBar.paint(graphics);
//		scoreBar.update(graphics);
////		scoreBar.repaint(graphics);
//		repaint();
//		update(graphics);
//		validate();
	}
}