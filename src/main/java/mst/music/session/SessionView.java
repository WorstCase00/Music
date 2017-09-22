package mst.music.session;

import mst.music.scoring.RunResult;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class SessionView extends JPanel {

	private static final Object[] COLUMN_NAMES = new String[] {
			"Title", "BPM", "Score"
	};
	private final JLabel currentScoreLabel;
	final JButton refreshButton;
	final JButton saveButton;
	final JTable resultsTable;
	private final DefaultTableModel tableModel;

	public SessionView() {
		super(new GridLayout(2,1));
		setBorder(new TitledBorder("Session"));
		this.tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		this.resultsTable = new JTable(tableModel);
		add(resultsTable);
		this.currentScoreLabel = new JLabel("Session Stats");
		add(this.currentScoreLabel);
		this.refreshButton = new JButton("refresh");
		add(refreshButton);
		this.saveButton = new JButton("save");
		add(saveButton);
	}

	public void addRefreshButtonListener(ActionListener actionListener) {
		refreshButton.addActionListener(actionListener);
	}

	public void addSaveButtonListener(ActionListener actionListener) {
		saveButton.addActionListener(actionListener);
	}

	public void addRunResult(RunResult runResult) {
		tableModel.addRow(new Object[] {
				runResult.getTitle(),
				runResult.getBpm(),
				runResult.getScore()
		});
		repaint();
	}
}
