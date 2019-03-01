package main.java.com.slimtrade.gui.options.macros.datatypes;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum PreloadedImage {

	CART("/resources/icons/cart.png"),
	CLOCK("/resources/icons/clock1.png"),
	THUMB("/resources/icons/thumb1.png"),
	LEAVE("/resources/icons/leave.png"),
	HAPPY("/resources/icons/happy.png"),
	REFRESH1("/resources/icons/refresh1.png"),
	REFRESH3("/resources/icons/refresh3.png"),
	
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
