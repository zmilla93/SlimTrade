package main.java.com.slimtrade.gui.options.general;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

public class AdvancedRow extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int HEIGHT = 22;
	private final int LABEL_WIDTH = 100;
	private final int PATH_WIDTH = 450;

	private boolean changed = false;
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

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			fileChooser = new JFileChooser();
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			fileChooser = new JFileChooser();

		}

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String filename = f.getName().toLowerCase();
					return filename.endsWith(".txt");
				}
			}

			public String getDescription() {
				return "Text Files (*.txt)";
			}
		});

		this.add(labelPanel, gc);
		gc.gridx++;
		this.add(pathPanel, gc);
		gc.gridx++;
		this.add(editButton, gc);

	}

	public String getText() {
		return pathLabel.getText();
	}

	public void setText(String text) {
		pathLabel.setText(text);
		changed = true;
	}

	public boolean isChanged() {
		return this.changed;
	}

	public void setChanged(boolean state) {
		this.changed = state;
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
