package com.zrmiller.slimtrade.panels;

import java.awt.Color;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;

public class StashOverlay extends JPanel{

	private int defaultWidth = 200;
	private int defaultHeight = 200;
	private int defaultPosX = 100;
	private int defaultPosY = 100;
	private int lowerPanelHeight = 50;
	private JPanel normalOverlay;
	private JPanel normalGrid;
	
	public StashOverlay(){
//		normalOverlay = new BasicPanel(defaultWidth, defaultHeight+lowerPanelHeight);
		this.setLayout(Overlay.flowCenter);
		this.setBounds(defaultPosX, defaultPosY, defaultWidth, defaultHeight+lowerPanelHeight);
		//normalOverlay.setBounds(defaultPosX, defaultPosY, defaultWidth, defaultHeight+lowerPanelHeight);
		normalGrid = new BasicPanel(defaultWidth, defaultHeight);
		normalGrid.setBackground(Color.green);
		this.add(normalGrid);
		
	}
}
