package com.slimtrade.gui.enums;

import java.awt.Image;

import javax.swing.ImageIcon;

//TODO : WHAT IS THIS?
public enum PreloadedImageCustom {

//	REFRESH("icons/refreshx48.png"),
	BEAKER("icons/beakerx48.png"),
	CANCEL("icons/cancelx48.png"),
	CLOCK("icons/clock1.png"),
	FLOW("icons/flowx48.png"),
	HAPPY("icons/happy.png"),
	KEY("icons/key1x48.png"),
//	KEY2("icons/key2x48.png"),
	MAP("icons/mapx48.png"),
//	SHUFFLE("icons/shufflex48.png"),
	
	;
	
	Image image;
	private final double IMAGE_SCALE = 0.94;
	private final int imageSize = (int)(20*IMAGE_SCALE);
	
	PreloadedImageCustom(String path){
		this.image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	}
	
	public Image getImage(){
		return this.image;
	}
	
}
