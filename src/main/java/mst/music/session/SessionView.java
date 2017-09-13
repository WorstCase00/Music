package mst.music.session;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SessionView extends JPanel {

	private final JLabel currentScoreLabel;
	final JButton refreshButton;

	public SessionView() {
		super(new GridLayout(2,1));
		setBorder(new TitledBorder("Session"));

		this.currentScoreLabel = new JLabel("Session");
		add(this.currentScoreLabel);
		this.refreshButton = new JButton("refresh");
		add(refreshButton);
	}

	public void addRefreshButtonListener(ActionListener actionListener) {
		refreshButton.addActionListener(actionListener);
	}
}
