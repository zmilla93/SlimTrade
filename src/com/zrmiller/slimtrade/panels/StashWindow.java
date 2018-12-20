package com.zrmiller.slimtrade.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class StashWindow extends BasicMenuWindow{

	private static final long serialVersionUID = 1L;
	private static int gridWidth = 400;
	private static int gridHeight = 400;
	private static int adjustPanelThickness = 12;
	private int thinBorder = 5;
	private int defaultPosX = 100;
	private int defaultPosY = 100;
	private int lowerPanelHeight = 50;
	private int bufferX;
	private int bufferY;

	private GridPanel grid;
	
	//RESIZING
	private int snapSize = 4;
	private int startingX;
	private int startingY;
	private int startingSize;
	
	//TODO : Add saving

	public StashWindow(){
		super("Stash Overlay", gridWidth+adjustPanelThickness, gridHeight+adjustPanelThickness);
		this.setSnapSize(snapSize);
//		this.isOptimizedDrawingEnabled();
		container.setLayout(new BorderLayout());
		//TODO : Move clear background to BasicMenuWindow?
		container.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		container.setBounds(defaultPosX, defaultPosY, gridWidth, gridHeight+lowerPanelHeight);
		
		grid = new GridPanel(gridWidth, gridHeight);
		grid.setBackground(new Color(1.0f,1.0f,1.0f,0.2f));
//		grid.setSnapSize(snapSize);
		container.add(grid, BorderLayout.CENTER);
		
		BasicPanel topPanel = new BasicPanel(gridWidth+adjustPanelThickness+thinBorder, thinBorder);
		container.add(topPanel, BorderLayout.PAGE_START);
		
		BasicPanel leftPanel = new BasicPanel(thinBorder, gridHeight);
		container.add(leftPanel, BorderLayout.LINE_START);
		
		BasicPanel rightPanel = new BasicPanel(adjustPanelThickness, gridHeight);
		Border b = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		rightPanel.setBorder(b);
		rightPanel.setEnabled(true);
		container.add(rightPanel, BorderLayout.LINE_END);
		
		BasicPanel bottomPanel = new BasicPanel(gridWidth, adjustPanelThickness);
		bottomPanel.setBorder(b);
		rightPanel.setEnabled(true);
		container.add(bottomPanel, BorderLayout.PAGE_END);
		//Width Adjust
		rightPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
		    	startingX = e.getXOnScreen();
		    	startingSize = container.getWidth();
		    }
		});
		
		rightPanel.addMouseMotionListener(new java.awt.event.MouseAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent e) {
		    	int dis = startingX-e.getXOnScreen();
		    	adjustSize(startingSize-dis, 0);
		    }
		});
		
		//Height Adjust
		bottomPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
		    	startingY = e.getYOnScreen();
		    	startingSize = container.getHeight();
		    }
		});
		
		bottomPanel.addMouseMotionListener(new java.awt.event.MouseAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent e) {
		    	int dis = startingY-e.getYOnScreen();
		    	adjustSize(0, startingSize-dis);
		    }
		});
	}
	
	private void adjustSize(int width, int height){
		int w = width;
		int h = height;
		if((w==0 || w%snapSize==0) && (h == 0 || h%snapSize==0)){
			w = width == 0 ? this.getWidth() : width;
			h = height == 0 ? this.getHeight() : (height + this.titlebar.getHeight());
			int gridX = w-adjustPanelThickness-thinBorder;
			int gridY = h-adjustPanelThickness-thinBorder-this.titlebar.getHeight();
			this.resizeWindow(w, h);
			grid.resizeGrid(gridX, gridY);
		}
	}
}
