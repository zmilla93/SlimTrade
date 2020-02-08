package com.slimtrade.gui.options.general;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.basic.CustomTextField;
import com.slimtrade.gui.buttons.BasicButton;

public class AdvancedRow extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;

	private final int HEIGHT = 22;
	private final int LABEL_WIDTH = 100;

	private boolean changed = false;
	private JLabel label = new JLabel();
	private JTextField textField = new CustomTextField(30);
	private JLabel pathLabel;
	private JButton editButton;
	private JFileChooser fileChooser;

	public AdvancedRow(String labelText) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
 
		JPanel labelPanel = new JPanel(new GridBagLayout());
		labelPanel.setPreferredSize(new Dimension(LABEL_WIDTH, HEIGHT));
		label.setText(labelText);
		labelPanel.add(label);

		JPanel pathPanel = new JPanel(new GridBagLayout());
//		pathPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pathLabel = new JLabel("Unset");
		textField.setColumns(40);
		textField.setText("Unset");
        textField.setText(labelText);

        textField.setEditable(false);
        gc.fill = GridBagConstraints.HORIZONTAL;
		pathPanel.add(textField, gc);
		gc.fill = GridBagConstraints.NONE;

		editButton = new BasicButton("Edit");
		editButton.setFocusable(false);
		Dimension editSize = editButton.getPreferredSize();
		editSize.height = HEIGHT;
		editButton.setPreferredSize(editSize);
		
		this.setOpaque(false);
		labelPanel.setOpaque(false);
		pathPanel.setOpaque(false);

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
        gc.fill = GridBagConstraints.HORIZONTAL;
//        pathPanel.setPreferredSize(pathPanel.getPreferredSize());
		this.add(pathPanel, gc);
        gc.fill = GridBagConstraints.NONE;
		gc.gridx++;
		this.add(editButton, gc);
		

		App.eventManager.addColorListener(this);
		updateColor();

	}

	public String getText() {
		return textField.getText();
	}

	public void setText(String text) {
		textField.setText(text);
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

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
//		textField.setBackground(ColorManager.BACKGROUND);
		label.setForeground(ColorManager.TEXT);
		pathLabel.setForeground(ColorManager.TEXT);
//        textField.setBorder(BorderFactory);
	}

}
