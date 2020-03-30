package com.slimtrade.gui.stash;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;

public class StashGridPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	Color lineColor = Color.YELLOW;
	private int gridCellCount = 12;
	
	public StashGridPanel(){
		this.setBackground(ColorManager.CLEAR);
		this.setBorder(BorderFactory.createLineBorder(lineColor));
//		this.setBackground(Color);
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
//		System.out.println(gridCellCount);
//		g.dispose();
		g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.2f));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		double gridCellWidth = this.getWidth()/(double)gridCellCount;
		double gridCellHeight = this.getHeight()/(double)gridCellCount;
//		System.out.println(gridCellWidth);
		//Vertical
		g.setColor(lineColor);
		for(int i = 0;i<gridCellCount;i++){
			g.drawLine((int)(i*gridCellWidth), 0, (int)(i*gridCellWidth), this.getHeight());
		}
		//Horizontal
		g.setColor(lineColor);
		for(int i = 0;i<gridCellCount;i++){
			g.drawLine(0, (int)(i*gridCellHeight), this.getWidth(), (int)(i*gridCellHeight));
		}
	}
	
	public int getGridWidth(){
		return this.getWidth();
	}
	
	public int getGridHeight(){
		return this.getHeight();
	}
	
	public Point getGridPosition(){
		return this.getLocation();
	}
	
	public void setGridCellCount(int count){
		this.gridCellCount = count;
	}
	
	public int getGridCellCount(){
		return this.gridCellCount;
	}
	
}
