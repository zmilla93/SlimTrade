package main.java.com.slimtrade.gui.options.customizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class PresetMacroRow extends JPanel {

	// TODO : Image
	private static final long serialVersionUID = 1L;
	protected final int BUFFER_WIDTH = 10;
	private final int COLUMN_SIZE = 40;
	private JLabel nameLabel = new JLabel("NAME");
	private JLabel tempLabel = new JLabel("TEMP");
	private JTextField textLMB = new JTextField(COLUMN_SIZE);
	private JTextField textRMB = new JTextField(COLUMN_SIZE);
	private IconButton exampleButton;
	GridBagConstraints gc = new GridBagConstraints();
	
	private Dimension size;

	PresetMacroRow(String name, String img, boolean... thin) {
		this.setBackground(Color.GRAY);
		this.setLayout(new GridBagLayout());
		if (thin.length > 0 && thin[0]) {
			this.setPreferredSize(new Dimension(500, 20));
		}else{
			this.setPreferredSize(new Dimension(500, 40));
		}
		System.out.println("CELL SIZE : " + textLMB.getPreferredSize());
		size = textLMB.getPreferredSize();
		gc.weightx = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		nameLabel.setText(name);
		nameLabel.setSize(50, 20);
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);
		gc.gridx++;
		this.add(nameLabel, gc);
		gc.gridx++;
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);
		gc.gridx += 2;// Label
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);
		gc.gridx += 2;// Action
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);

		if (thin.length > 0 && thin[0]) {
			gc.gridx = 2;
			gc.gridy = 0;
		} else {
			gc.gridx = 1;
			gc.gridy = 1;
			
		}
		exampleButton = new IconButton(img, 20);
		this.add(exampleButton, gc);
		gc.gridy = 0;
	}

	// TODO Make row look like editable
	public void addRow(String title, String text) {
		gc.gridx = 3;
		this.add(new JLabel(title), gc);
		gc.gridx = 5;
		JLabel textLabel = new JLabel(text);
		textLabel.setPreferredSize(size);
		this.add(textLabel, gc);
		gc.gridy++;
	}

	public void addEditLMB(String title, String preset) {
		gc.gridx = 3;
		this.add(new JLabel(title), gc);
		gc.gridx = 5;
		textLMB.setPreferredSize(size);
		textLMB.setText(preset);
		this.add(textLMB, gc);
		gc.gridy++;
	}

	public void addEditRMB(String title, String preset) {
		gc.gridx = 3;
		this.add(new JLabel(title), gc);
		gc.gridx = 5;
		// JTextField text = new JTextField(50);
//		textRMB.setPreferredSize(size);
		textRMB.setText(preset);
		// textRMB.setEditable(false);
		this.add(textRMB, gc);
		gc.gridy++;
	}

	public void addEditRow(String title, String preset) {
		gc.gridx = 3;
		this.add(new JLabel(title), gc);
		gc.gridx = 5;
		JTextField text = new JTextField(COLUMN_SIZE);
//		text.setPreferredSize(size);
		text.setText(preset);
		this.add(text, gc);
		gc.gridy++;
	}
}
