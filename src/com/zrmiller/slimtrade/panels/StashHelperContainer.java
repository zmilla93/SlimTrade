package com.zrmiller.slimtrade.panels;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.ColorManager;
import com.zrmiller.slimtrade.windows.StashWindow;

public class StashHelperContainer extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public static int height = StashHelper.height;
	private int offsetY = 10;
	private int spacingX = 5;
	
	public StashHelperContainer(){
		this.setBackground(ColorManager.CLEAR);
		this.setBounds(0, 0, height, height);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, spacingX, 0));
	}
	
	public void updateBounds(){
		int posX = StashWindow.getGridPos().x-spacingX;
		int posY = StashWindow.getWinPos().y-height-offsetY;
		int width = StashWindow.getGridSize().width+spacingX;
		this.setBounds(posX, posY, width, height);
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
