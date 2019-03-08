package main.java.com.slimtrade.gui.stash.helper;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.windows.StashGridOverlay;

public class StashHelperContainer extends BasicDialog{
	//TODO : Should probably get spacing X directly from stash overlay left buffer
	private static final long serialVersionUID = 1L;
	public static int height = StashHelper.height;
	private int offsetY = 10;
	private int spacingX = 5;
	private int posX = 0;
	private int posY = 0;
	
	public StashHelperContainer(){
		this.setBackground(ColorManager.CLEAR);
//		this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED));
		this.setBounds(0, 0, height, height);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, spacingX, 0));
	}
	
	public void updateBounds(){
		posX = Main.saveManager.getInt("stashOverlay", "x");
		posY = Main.saveManager.getInt("stashOverlay", "y");
		int width = Main.saveManager.getInt("stashOverlay", "width");
		this.setBounds(posX+10, posY-15, width, height);
	}

	public void updateBounds(int posX, int posY, int width){
		int x = posX-spacingX;
		int y = posY-height-offsetY;
		int w = width+spacingX;
		this.setBounds(x, y, w, height);
	}
	
	public void updateCellSize(int cellWidth, int cellHeight){
		
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
