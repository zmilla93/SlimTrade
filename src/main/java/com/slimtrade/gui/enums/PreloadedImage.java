package com.slimtrade.gui.enums;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum PreloadedImage {
	CART("icons/cartx64.png"),
	CLOSE("icons/closex64.png"),
	DISK("icons/disk.png"),
	INVITE("icons/invite1.png"),
	REFRESH("icons/refreshx64.png"),
	REPLY("icons/replyx48.png"),
	TAG("icons/tagx48.png"),
	;
	
	Image image;
	private final double IMAGE_SCALE = 0.94;
	private final int imageSize = (int)(20*IMAGE_SCALE);
	
	PreloadedImage(String path){
//        System.out.println(path);
		this.image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	}
	
	public Image getImage(){
		return this.image;
	}
	
}
