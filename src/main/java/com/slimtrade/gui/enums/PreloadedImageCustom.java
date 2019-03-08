package main.java.com.slimtrade.gui.enums;

import java.awt.Image;

import javax.swing.ImageIcon;

public enum PreloadedImageCustom {

//	REFRESH("/resources/icons/refreshx48.png"),
	BEAKER("/resources/icons/beakerx48.png"),
	CANCEL("/resources/icons/cancelx48.png"),
	CLOCK("/resources/icons/clock1.png"),
	FLOW("/resources/icons/flowx48.png"),
	HAPPY("/resources/icons/happy.png"),
	KEY("/resources/icons/key1x48.png"),
//	KEY2("/resources/icons/key2x48.png"),
	MAP("/resources/icons/mapx48.png"),
//	SHUFFLE("/resources/icons/shufflex48.png"),
	
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
