package com.zrmiller.slimtrade.panels;

import java.awt.Dimension;

import javax.swing.JPanel;

public class SpacerPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public SpacerPanel(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		//this.setBackground(color);
	}
	
}
