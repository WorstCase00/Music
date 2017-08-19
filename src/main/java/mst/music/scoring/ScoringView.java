package mst.music.scoring;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;

public class ScoringView extends JPanel {

	private static final Random RANDOM = new Random(321l);

	private final JPanel scoreBar;
	private final JLabel currentScoreLabel;

	public ScoringView() {
		super(new GridLayout(2,1));
		setBorder(new TitledBorder("Scoring"));
		this.scoreBar = new JPanel();
		add(scoreBar);


		this.currentScoreLabel = new JLabel("0");
		add(currentScoreLabel);

	}

	public void update() {
		this.scoreBar.setBackground(Color.getHSBColor(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat()));
		repaint();
	}

	public void updateCurrentScore(float currentScore) {
		this.currentScoreLabel.setText(Float.toString(currentScore));
	}
}