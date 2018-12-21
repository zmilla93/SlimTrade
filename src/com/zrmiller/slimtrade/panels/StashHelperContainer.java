package com.zrmiller.slimtrade.panels;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;

public class StashHelperContainer extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public static int height = StashHelper.height;
	private int offsetY = 10;
	private int spacingX = 5;
	
	public StashHelperContainer(){
		this.setBackground(Color.RED);
		this.setBounds(0, 0, height, height);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, spacingX, 0));
	}
	
	public void updateBounds(){
//		Point stashPos = Overlay.stashWindow.LocationOnScreen();
		int posX = Overlay.stashWindow.grid.getLocationOnScreen().x-spacingX;
		int posY = Overlay.stashWindow.getLocation().y-height-offsetY;
		int width = Overlay.stashWindow.grid.getWidth()+spacingX;
		this.setBounds(posX, posY, width, height);
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
