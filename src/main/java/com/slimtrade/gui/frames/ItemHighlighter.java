package main.java.com.slimtrade.gui.frames;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;

public class ItemHighlighter extends BasicDialog{

	private static final long serialVersionUID = 1L;
	//Static
	private static double gridX;
	private static double gridY;
	private static double cellWidth = 0;
	private static double cellHeight = 0;
	//Internal
	private int stashX;
	private int stashY;

	public ItemHighlighter(int stashX, int stashY, Color color){
		this.stashX = stashX;
		this.stashY = stashY;
		this.setVisible(false);
		this.setBackground(ColorManager.CLEAR);
		this.getRootPane().setBorder(BorderFactory.createLineBorder(color, 4, false));
		this.setBounds(0-(int)cellWidth*2, 0, (int)cellWidth, (int)cellHeight);
		this.setSize(new Dimension((int)cellWidth, (int)cellHeight));
		this.setVisible(false);
	}
	
	public static void saveGridInfo(double gridX, double gridY, double cellWidth, double cellHeight){
		ItemHighlighter.gridX = gridX;
		ItemHighlighter.gridY = gridY;
		ItemHighlighter.cellWidth = cellWidth;
		ItemHighlighter.cellHeight = cellHeight;
	}
	
	public void refresh(){
		this.setBounds((int)(gridX+((stashX-1)*cellWidth)), (int)(gridY+((stashY-1)*cellHeight)), (int)cellWidth, (int)cellHeight);
	}
	
	public void destroy(){
		this.dispose();
	}
	
}
