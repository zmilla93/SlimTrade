package main.java.com.slimtrade.gui.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.gui.panels.BufferPanel;

public class BasicsPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private int bufferX = 30;
	private int bufferY = 5;

	BasicsPanel(int width, int height) {
		super(width, height);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		GridBagConstraints gcMain = new GridBagConstraints();
		GridBagConstraints gcRow = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		// gc.weightx = 0;
		// gc.weighty = 0;
		// System.out.println(gc.weightx);

		JLabel timeLabel = new JLabel("Date Format");

		JComboBox<String> timeCombo = new JComboBox<String>();
		timeCombo.addItem("time1");
		timeCombo.addItem("time2");
		// this.addPair(timeLabel, timeCombo, bufferX, bufferY, gc);
		// gc.gridy++;

		JLabel stashLabel = new JLabel("Stash Location");
		JLabel overlayLabel = new JLabel("Panel Location Cool");

		JButton stashButton = new JButton("Edit");
		JButton overlayButton = new JButton("Edit");

		gcMain.gridx = 0;
		gcMain.gridy = 0;
		gcRow.gridx = 0;
		gcRow.gridy = 0;

		JPanel p0 = new JPanel(new GridBagLayout());
		p0.add(new BufferPanel(10, 0), gcRow);
		gcRow.gridx++;
		p0.add(timeLabel, gcRow);
		gcRow.gridx++;
		p0.add(new BufferPanel(40, 0), gcRow);
		gcRow.gridx++;
		p0.add(timeCombo, gcRow);
		gcRow.gridx = 0;
		gcRow.gridy++;

		this.add(p0, gcMain);
		gcMain.gridy++;
		this.add(new BufferPanel(0, 20), gcMain);
		gcMain.gridy++;

		JPanel p1 = new JPanel(new GridBagLayout());
		p1.add(new BufferPanel(10, 0), gcRow);
		gcRow.gridx++;
		p1.add(stashLabel, gcRow);
		gcRow.gridx++;
		p1.add(new BufferPanel(40, 0), gcRow);
		gcRow.gridx++;
		p1.add(stashButton, gcRow);
		gcRow.gridx = 0;
		gcRow.gridy++;

		this.add(p1, gcMain);
		gcMain.gridy++;
		this.add(new BufferPanel(0, 20), gcMain);
		gcMain.gridy++;

		JPanel p2 = new JPanel(new GridBagLayout());
		p2.add(new BufferPanel(10, 0), gcRow);
		gcRow.gridx++;
		p2.add(overlayLabel, gcRow);
		gcRow.gridx++;
		p2.add(new BufferPanel(40, 0), gcRow);
		gcRow.gridx++;
		p2.add(overlayButton, gcRow);
		gcRow.gridx = 0;
		gcRow.gridy++;

		this.add(p2, gcMain);
		gcMain.gridy++;

		// this.addPairPanel(stashLabel, stashButton, bufferX, bufferY, gc);
		// this.addPair(stashLabel, stashButton, bufferX, bufferY, gc);
		// this.addPair(overlayLabel, overlayButton, bufferX, bufferY, gc);
		// this.addPairPanel(overlayLabel, overlayButton, bufferX, bufferY, gc);
		// this.add(GridBagFactory.createSpacedRow(10, stashLabel, stashButton),
		// gc);
		// gc.gridy++;
		// this.add(GridBagFactory.createSpacedRow(10, overlayLabel,
		// overlayButton), gc);

		this.autoResize();
		// this.setMaximumSize(this.getPreferredSize());

	}

}
