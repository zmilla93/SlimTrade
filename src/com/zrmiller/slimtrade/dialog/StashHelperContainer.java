package com.zrmiller.slimtrade.dialog;

import java.awt.Color;
import java.awt.FlowLayout;

import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class StashHelperContainer extends BasicDialog{
	//TODO : Should probably get spacing X directly from stash overlay left buffer
	private static final long serialVersionUID = 1L;
	public static int height = StashHelper.height;
	private int offsetY = 10;
	private int spacingX = 5;
	
	public StashHelperContainer(){
		this.getContentPane().setBackground(Color.YELLOW);
		this.setBounds(0, 0, height, height);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, spacingX, 0));
	}
	
	public void updateBounds(){
		int posX = StashGridOverlay.getGridPos().x-spacingX;
		int posY = StashGridOverlay.getWinPos().y-height-offsetY;
		int width = StashGridOverlay.getGridSize().width+spacingX;
		this.setBounds(posX, posY, width, height);
	}
	
	/**
	 * Position coordinates and width should the window pos/size of stash overlay
	 * @param posX
	 * @param posY
	 * @param width
	 */
	public void updateBounds(int posX, int posY, int width){
		int x = posX-spacingX;
		int y = posY-height-offsetY;
		int w = width+spacingX;
		this.setBounds(x, y, w, height);
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
