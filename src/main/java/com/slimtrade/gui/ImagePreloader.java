package main.java.com.slimtrade.gui;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImagePreloader {
	
	public static Image callback;
	public static Image wait;
	public static Image refresh;
	public static Image close;
	public static Image warp;
	public static Image trade;
	public static Image thank;
	public static Image leave;
	public static Image home;
	public static Image invite;
	private int imageSize = 20;
	private final double IMAGE_SCALE = 0.94;
	
	public ImagePreloader(){
		resizeImages(imageSize);
	}
	
	public void resizeImages(int imgSize){
		this.imageSize = (int)(imgSize*IMAGE_SCALE);
//		rad = new ImageIcon(this.getClass().getResource("/resources/icons/rad.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		callback = new ImageIcon(this.getClass().getResource("/resources/icons/phone.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		wait = new ImageIcon(this.getClass().getResource("/resources/icons/clock1.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		warp = new ImageIcon(this.getClass().getResource("/resources/icons/warp.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		refresh = new ImageIcon(this.getClass().getResource("/resources/icons/refresh1.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		close = new ImageIcon(this.getClass().getResource("/resources/icons/rad.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		trade = new ImageIcon(this.getClass().getResource("/resources/icons/cart.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		thank = new ImageIcon(this.getClass().getResource("/resources/icons/thumb1.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		leave = new ImageIcon(this.getClass().getResource("/resources/icons/leave.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		home = new ImageIcon(this.getClass().getResource("/resources/icons/home.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		invite = new ImageIcon(this.getClass().getResource("/resources/icons/invite.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	}
	
}
