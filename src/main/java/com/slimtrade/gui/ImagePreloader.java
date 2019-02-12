package main.java.com.slimtrade.gui;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImagePreloader {
	
	public static Image rad;
	public static Image close;
	private int imageSize = 20;
	private final double IMAGE_SCALE = 0.94;
	
	public ImagePreloader(){
		resizeImages(imageSize);
	}
	
	public void resizeImages(int imgSize){
		this.imageSize = (int)(imgSize*IMAGE_SCALE);
		rad = new ImageIcon(this.getClass().getResource("/resources/icons/rad.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		close = new ImageIcon(this.getClass().getResource("/resources/icons/rad.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	}
	
}
