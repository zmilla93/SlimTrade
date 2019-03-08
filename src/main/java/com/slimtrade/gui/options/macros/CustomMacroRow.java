package main.java.com.slimtrade.gui.options.macros;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.enums.ButtonRow;
import main.java.com.slimtrade.gui.enums.PreloadedImageCustom;
import main.java.com.slimtrade.gui.options.RemovablePanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class CustomMacroRow extends RemovablePanel {

	private static final long serialVersionUID = 1L;
	private JLabel nameLabel = new JLabel("Custom");
	private JLabel m1Label = new JLabel("Left Mouse");
	private JLabel m2Label = new JLabel("Right Mouse");
	private JTextField m1Text = new JTextField(30);
	private JTextField m2Text = new JTextField(30);
	private JComboBox<ImageIcon> iconCombo;
	private JComboBox<ButtonRow> rowCombo;
	private IconButton deleteButton = new IconButton("/resources/icons/close.png", 20);

	private boolean unsaved = true;
	private boolean delete;

	public CustomMacroRow() {
		this.setBackground(Color.GRAY);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		// Image img = new
		// ImageIcon(this.getClass().getResource("/resources/icons/cart.png")).getImage().getScaledInstance(20,
		// 20, Image.SCALE_SMOOTH);
		// ImageIcon i = new ImageIcon(img);
		// ImageIcon[] icons = {i};
		iconCombo = new JComboBox<ImageIcon>();
		for (PreloadedImageCustom i : PreloadedImageCustom.values()) {
			ImageIcon icon = new ImageIcon(i.getImage());
			iconCombo.addItem(icon);
			// System.out.println(icon);
			// iconCombo.addItem(i);
		}

		iconCombo.setFocusable(false);
		iconCombo.removeAll();
		iconCombo.setLayout(new BorderLayout());
		iconCombo.setBorder(null);
//		Dimension iconSize = iconCombo.getPreferredSize();
//		iconSize.width = iconSize.width / 2;
//		iconCombo.setPreferredSize(iconSize);

		rowCombo = new JComboBox<ButtonRow>();
		for (ButtonRow row : ButtonRow.values()) {
			rowCombo.addItem(row);
		}
		// Dimension rowSize = rowCombo.getPreferredSize();
		// rowSize.height = 20;
		// rowCombo.setPreferredSize(rowSize);
		// rowCombo.addItem("Top");
		// rowCombo.addItem("Bottom");

		this.add(new BufferPanel(10, 0), gc);
		gc.gridx++;
		gc.gridheight = 2;
		this.add(rowCombo, gc);
		gc.gridheight = 1;
		gc.gridx++;
		this.add(new BufferPanel(10, 0), gc);
		gc.gridx++;
		this.add(m1Label, gc);
		gc.gridx++;
		this.add(new BufferPanel(10, 0), gc);
		gc.gridx++;
		this.add(m1Text, gc);
		gc.gridx++;
		gc.gridheight = 2;
		this.add(iconCombo, gc);
		gc.gridheight = 1;
		gc.gridx++;
		this.add(new BufferPanel(10, 0), gc);
		this.add(deleteButton, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		// this.add(rowCombo, gc);
		gc.gridx += 2;
		this.add(m2Label, gc);
		gc.gridx += 2;
		this.add(m2Text, gc);

		this.revalidate();
		this.repaint();
	}

	public JButton getDeleteButton() {
		return this.deleteButton;
	}

	public ButtonRow getButtonRow() {
		return (ButtonRow) rowCombo.getSelectedItem();
	}

	public PreloadedImageCustom getButtonImage() {
		return PreloadedImageCustom.values()[iconCombo.getSelectedIndex()];
		// return iconCombo.getSelectedItem().toString();
	}

	public String getTextLMB() {
		return m1Text.getText();
	}
	
	public String getTextRMB() {
		return m2Text.getText();
	}
	
	public void setButtonRow(ButtonRow row){
		rowCombo.setSelectedItem(row);
	}
	
	public void setButtonImage(PreloadedImageCustom img){
		int i = PreloadedImageCustom.valueOf(img.toString()).ordinal();
		iconCombo.setSelectedIndex(i);
	}

	public void setTextLMB(String text) {
		m1Text.setText(text);
	}

	public void setTextRMB(String text) {
		m2Text.setText(text);
	}

	

}
