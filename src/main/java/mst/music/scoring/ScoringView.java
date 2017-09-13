package mst.music.scoring;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.NumberFormat;

public class ScoringView extends JPanel {

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
		this.scoreBar.setBackground(new Color((1f - percentage), percentage, 0f));
		repaint();
	}

	public void updateCurrentScore(float currentScore) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		this.currentScoreLabel.setText(numberFormat.format(currentScore));
	}
}