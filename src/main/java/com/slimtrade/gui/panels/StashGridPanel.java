package com.slimtrade.gui.panels;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;

public class StashGridPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private double gridCellWidth = 0;
	private double gridCellHeight = 0;
	private int gridCellCount = 12;
	private Color lineColor = Color.RED;
	
	public StashGridPanel(){
		this.setBackground(ColorManager.CLEAR);
	}
	
	public StashGridPanel(int width, int height){
		this.setBackground(ColorManager.CLEAR);
		this.width = width;
		this.height = height;
		this.gridCellWidth = (double)width/12;
		this.gridCellHeight = (double)height/12;
		this.setPreferredSize(new Dimension(width, height));
	}
	
	protected void paintComponent(Graphics g){
		g.setColor(lineColor);
		for(int i = 0;i<=gridCellCount;i++){
			g.drawLine((int)(i*gridCellWidth), 0, (int)(i*gridCellWidth), height);
		}
		for(int i = 0;i<=gridCellCount;i++){
			g.drawLine(0, (int)(i*gridCellHeight), width, (int)(i*gridCellHeight));
		}
		super.paintComponent(g);
	}
	
	public void setLineColor(Color color){
		this.lineColor = color;
	}
	
	public void resizeGrid(int width, int height){
		this.width = width;
		this.height = height;
		this.gridCellWidth = (double)width/12;
		this.gridCellHeight = (double)height/12;
		this.setPreferredSize(new Dimension(width, height));
		this.revalidate();
		this.repaint();
	}
}
