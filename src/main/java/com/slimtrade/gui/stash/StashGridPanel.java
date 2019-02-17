package main.java.com.slimtrade.gui.stash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import main.java.com.slimtrade.core.managers.ColorManager;

public class StashGridPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	Color lineColor = Color.GREEN;
	private int gridCellCount = 12;
	
	public StashGridPanel(){
		this.setBackground(ColorManager.CLEAR);
//		this.setBackground(Color);
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
//		System.out.println(gridCellCount);
//		g.dispose();
		g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.3f));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		double gridCellWidth = this.getWidth()/(double)gridCellCount;
		double gridCellHeight = this.getHeight()/(double)gridCellCount;
//		System.out.println(gridCellWidth);
		//Vertical
		g.setColor(lineColor);
		for(int i = 0;i<=gridCellCount;i++){
			if(i == gridCellCount){
				g.drawLine((int)(i*gridCellWidth-1), 0, (int)(i*gridCellWidth-1), this.getHeight());
			}else{
				g.drawLine((int)(i*gridCellWidth), 0, (int)(i*gridCellWidth), this.getHeight());
			}
			
		}
		//Horizontal
		g.setColor(lineColor);
		for(int i = 0;i<=gridCellCount;i++){
			
			if(i==gridCellCount){
				g.drawLine(0, (int)(i*gridCellHeight-1), this.getWidth(), (int)(i*gridCellHeight-1));
			}else{
				g.drawLine(0, (int)(i*gridCellHeight), this.getWidth(), (int)(i*gridCellHeight));
			}
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
