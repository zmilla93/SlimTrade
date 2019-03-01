package main.java.com.slimtrade.gui.options.macros.datatypes;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum PreloadedImageCustom {

	REFRESH("/resources/icons/refresh3.png"),
	CLOCK("/resources/icons/clock1.png"),
	HAPPY("/resources/icons/happy.png"),
	
	;
	
	Image image;
	private final double IMAGE_SCALE = 0.94;
	private final int imageSize = (int)(20*IMAGE_SCALE);
	
	PreloadedImageCustom(String path){
		this.image = new ImageIcon(this.getClass().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	}
	
	public Image getImage(){
		return this.image;
	}
	
}
