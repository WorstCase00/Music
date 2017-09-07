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
		this.currentScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.currentScoreLabel.setFont(this.currentScoreLabel.getFont().deriveFont(100f));
		add(currentScoreLabel);

	}

	public void updateScoreBar(float percentage) {
		this.scoreBar.setBackground(new Color((1f - percentage), percentage, 0f));//Color.RGBtoHSB(0, 255f * percentage , 0)RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat()));
		repaint();
	}

	public void updateCurrentScore(float currentScore) {
		this.currentScoreLabel.setText(Float.toString(currentScore));
	}
}