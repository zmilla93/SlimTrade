package com.zrmiller.slimtrade.panels;

import java.awt.Color;
import java.awt.FlowLayout;

import com.zrmiller.slimtrade.dialog.BasicDialog;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class StashHelperContainer extends BasicDialog{
	
	private static final long serialVersionUID = 1L;
	public static int height = StashHelper.height;
	private int offsetY = 10;
	private int spacingX = 5;
	
	public StashHelperContainer(){
		this.setBackground(Color.YELLOW);
		this.setBounds(0, 0, height, height);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, spacingX, 0));
	}
	
	public void updateBounds(){
		int posX = StashGridOverlay.getGridPos().x-spacingX;
		int posY = StashGridOverlay.getWinPos().y-height-offsetY;
		int width = StashGridOverlay.getGridSize().width+spacingX;
		this.setBounds(posX, posY, width, height);
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
