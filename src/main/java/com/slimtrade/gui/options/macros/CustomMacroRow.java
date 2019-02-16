package main.java.com.slimtrade.gui.options.macros;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.com.slimtrade.gui.buttons.IconButton;
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
	private JComboBox<String> rowCombo;
	private IconButton deleteButton = new IconButton("/resources/icons/close.png", 20);
	
	private boolean unsaved = true;
	private boolean delete;
	
	public CustomMacroRow(){
		this.setBackground(Color.GRAY);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		Image img = new ImageIcon(this.getClass().getResource("/resources/icons/cart.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon i = new ImageIcon(img);
		ImageIcon[] icons = {i};
		iconCombo = new JComboBox<ImageIcon>(icons);
		iconCombo.setFocusable(false);
		iconCombo.removeAll();
		iconCombo.setLayout(new BorderLayout());
		iconCombo.setBorder(null);
		Dimension iconSize = iconCombo.getPreferredSize();
//		iconSize.height = 30;
		iconSize.width = iconSize.width/2;
		iconCombo.setPreferredSize(iconSize);
		
		rowCombo = new JComboBox<String>();
//		Dimension rowSize = rowCombo.getPreferredSize();
//		rowSize.height = 20;
//		rowCombo.setPreferredSize(rowSize);
		rowCombo.addItem("Top");
		rowCombo.addItem("Bottom");
		
		this.add(new BufferPanel(10, 0), gc);
		gc.gridx++;
		gc.gridheight=2;
		this.add(rowCombo, gc);
		gc.gridheight=1;
		gc.gridx++;
		this.add(new BufferPanel(10, 0), gc);
		gc.gridx++;
		this.add(m1Label, gc);
		gc.gridx++;
		this.add(new BufferPanel(10, 0), gc);
		gc.gridx++;
		this.add(m1Text, gc);
		gc.gridx++;
		gc.gridheight=2;
		this.add(iconCombo, gc);
		gc.gridheight=1;
		gc.gridx++;
		this.add(new BufferPanel(10, 0), gc);
		this.add(deleteButton, gc);
		
		gc.gridx=1;
		gc.gridy=1;
//		this.add(rowCombo, gc);
		gc.gridx+=2;
		this.add(m2Label, gc);
		gc.gridx+=2;
		this.add(m2Text, gc);
		
		this.revalidate();
		this.repaint();
	}
	
	public void setTextLMB(String text){
		m1Text.setText(text);
	}
	
	public void setTextRMB(String text){
		m2Text.setText(text);
	}
	
	public String getTextLMB(){
		return m1Text.getText();
	}
	public String getTextRMB(){
		return m2Text.getText();
	}
	
	public JButton getDeleteButton(){
		return this.deleteButton;
	}
	
}
