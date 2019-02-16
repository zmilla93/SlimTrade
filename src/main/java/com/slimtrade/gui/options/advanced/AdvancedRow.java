package main.java.com.slimtrade.gui.options.advanced;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdvancedRow extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int HEIGHT = 22;
	private final int LABEL_WIDTH = 100;
	private final int PATH_WIDTH = 450;

	private JLabel pathLabel;
	private JButton editButton;
	private JFileChooser fileChooser;

	public AdvancedRow(String labelText) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		JPanel labelPanel = new JPanel(new GridBagLayout());
		labelPanel.setPreferredSize(new Dimension(LABEL_WIDTH, HEIGHT));
		JLabel label = new JLabel(labelText);
		labelPanel.add(label);

		// TODO : Set path
		JPanel pathPanel = new JPanel(new GridBagLayout());
		pathPanel.setPreferredSize(new Dimension(PATH_WIDTH, HEIGHT));
		pathPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pathLabel = new JLabel("Unset");
		pathPanel.add(pathLabel);

		editButton = new JButton("Edit");
		editButton.setFocusable(false);
		Dimension editSize = editButton.getPreferredSize();
		editSize.height = HEIGHT;
		editButton.setPreferredSize(editSize);

		fileChooser = new JFileChooser();

		this.add(labelPanel, gc);
		gc.gridx++;
		this.add(pathPanel, gc);
		gc.gridx++;
		this.add(editButton, gc);

	}

	public JLabel getPathLabel() {
		return this.pathLabel;
	}

	public JButton getEditButton() {
		return this.editButton;
	}

	public JFileChooser getFileChooser() {
		return this.fileChooser;
	}

}
