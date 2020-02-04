package com.slimtrade.gui.enums;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Preloaded icons used by the SlimTrade UI. These are intended for internal use only.
 * PreloadedImageCustom is for use by the user with custom custom buttons.
 */

public enum PreloadedImage {
	CART("icons/cartx64.png"),
	CLOSE("icons/closex64.png"),
	HOME("icons/home3.png"),
	INVITE("icons/invite1.png"),
    LEAVE("icons/leave.png"),
	REFRESH("icons/refreshx64.png"),
	REPLY("icons/replyx48.png"),
	THUMB("icons/thumb.png"),
	TAG("icons/tagx48.png"),
	WARP("icons/warp.png"),
	;
	
	Image image;
	ImageIcon imageIcon;
	private static final double IMAGE_SCALE = 0.94;
	private static final int imageSize = (int)(20*IMAGE_SCALE);
	
	PreloadedImage(String path){
		this.image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
	}
	
	public Image getImage(){
		return this.image;
	}
	
}
