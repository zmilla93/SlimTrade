package com.slimtrade.gui.enums;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum PreloadedImage {
	CART("/resources/icons/cartx64.png"),
	CLOSE("/resources/icons/closex64.png"),
	DISK("/resources/icons/disk.png"),
	INVITE("/resources/icons/invite1.png"),
	REFRESH("/resources/icons/refreshx64.png"),
	REPLY("/resources/icons/replyx48.png"),
	TAG("/resources/icons/tagx48.png"),
	;
	
	Image image;
	private final double IMAGE_SCALE = 0.94;
	private final int imageSize = (int)(20*IMAGE_SCALE);
	
	PreloadedImage(String path){
		this.image = new ImageIcon(this.getClass().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	}
	
	public Image getImage(){
		return this.image;
	}
	
}
