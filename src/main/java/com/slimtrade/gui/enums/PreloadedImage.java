package main.java.com.slimtrade.gui.enums;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum PreloadedImage {

	DISK("/resources/icons/disk.png"),
	
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
