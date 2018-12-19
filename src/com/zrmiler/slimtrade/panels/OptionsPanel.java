package com.zrmiler.slimtrade.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.zrmiler.slimtrade.Main;

public class OptionsPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private int minX = 400;
	private int minY = 800;
	private int rowHeight = 30;
	private JButton closeButton;
	
	//TODO : cleanup size variables for better resizing
	
	public OptionsPanel(){
		//TODO : FIX CENTERING
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBounds((Main.screenSize.width-minX)/2, (Main.screenSize.height/2)-(minY/4), minX, minX);
		this.setMinimumSize(new Dimension(minX, minY));
		this.setPreferredSize(new Dimension(minX, minY));
		
		JPanel titlebar = new JPanel();
		JLabel titlebarLabel = new JLabel("Options");
		Border titlebarBorder = BorderFactory.createRaisedSoftBevelBorder();
		titlebar.setPreferredSize(new Dimension(minX-rowHeight, rowHeight));
		titlebar.setBorder(titlebarBorder);
		titlebar.add(titlebarLabel);
		this.add(titlebar);
		
		closeButton = new JButton();
		closeButton.setLayout(null);
		closeButton.setPreferredSize(new Dimension(rowHeight, rowHeight));
		this.add(closeButton);
		
		JPanel toggleHeader = new JPanel();
		JLabel toggleLabel = new JLabel("Toggle");
		Border toggleBorder = BorderFactory.createRaisedSoftBevelBorder();
		toggleHeader.setPreferredSize(new Dimension((int)(minX*0.8), rowHeight));
		toggleHeader.setBorder(toggleBorder);
		toggleHeader.add(toggleLabel);
		this.add(toggleHeader);
		
		
	}
	
	public JButton getCloseButton(){
		return closeButton;
	}
	
}
