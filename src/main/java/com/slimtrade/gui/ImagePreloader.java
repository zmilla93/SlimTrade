package com.slimtrade.gui;

import java.awt.Image;
import java.net.URL;

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

	public static Image chaos;
	
	private int imageSize = 20;
	private final double IMAGE_SCALE = 0.94;
	
	public ImagePreloader(){
		resizeImages(imageSize);
	}
	
	public void resizeImages(int imgSize){
	    ClassLoader loader = getClass().getClassLoader();
	    URL resource = loader.getResource("icons/phone.png");
        System.out.println("RESOURCE : " + resource);

		this.imageSize = (int)(imgSize*IMAGE_SCALE);
//		rad = new ImageIcon(loader.getResource("icons/rad.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        System.out.println(loader);
		callback = new ImageIcon(loader.getResource("icons/phone.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		wait = new ImageIcon(loader.getResource("icons/clock1.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		warp = new ImageIcon(loader.getResource("icons/warp.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		refresh = new ImageIcon(loader.getResource("icons/refresh1.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		close = new ImageIcon(loader.getResource("icons/rad.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		trade = new ImageIcon(loader.getResource("icons/cart.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		thank = new ImageIcon(loader.getResource("icons/thumb1.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		leave = new ImageIcon(loader.getResource("icons/leave.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		home = new ImageIcon(loader.getResource("icons/home.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		invite = new ImageIcon(loader.getResource("icons/invite.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		chaos = new ImageIcon(loader.getResource("currency/chaos.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
//		invite = new ImageIcon(loader.getResource("icons/invite.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
//		invite = new ImageIcon(loader.getResource("icons/invite.png")).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	
	
	}
	
}
