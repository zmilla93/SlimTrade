package main.java.com.slimtrade.gui.options.customizer;

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
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class CustomMacroRow extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel nameLabel = new JLabel("Custom");
	private JLabel m1Label = new JLabel("Left Mouse");
	private JLabel m2Label = new JLabel("Right Mouse");
	private JTextField m1Text = new JTextField(40);
	private JTextField m2Text = new JTextField(40);
	private JComboBox<ImageIcon> iconCombo;
	private IconButton deleteButton = new IconButton("/resources/icons/close.png", 20);
	
	
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
		for (Component component : iconCombo.getComponents())
		{
		    if (component instanceof JButton) {
//		    	iconCombo.remove(component);
		    }
		}
		Dimension iconSize = iconCombo.getPreferredSize();
		iconSize.height = 20;
		iconSize.width=(int)(iconSize.height*1.2);
//		iconSize.width = 20;
//		
		iconCombo.setPreferredSize(iconSize);
		
		
		this.add(new BufferPanel(10, 0), gc);
		gc.gridx++;
		this.add(nameLabel, gc);
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
		
		gc.gridx=3;
		gc.gridy=1;
		this.add(m2Label, gc);
		gc.gridx+=2;
		this.add(m2Text, gc);
		
		this.revalidate();
		this.repaint();
		
		
	}
	
}
