package com.slimtrade.gui.enums;

import com.slimtrade.core.References;

import java.awt.Image;
import java.sql.Ref;
import javax.swing.ImageIcon;

/**
 * Preloaded icons for use by the user with custom macro buttons.
 */

public enum PreloadedImageCustom {

	CANCEL("icons/custom/cancelx48.png"),
	BOOKMARK("icons/custom/bookmarkx48.png"),
	MAP("icons/custom/mapx64.png"),
	BEAKER("icons/custom/beakerx48.png"),
	FLOW2("icons/custom/flow-switchx48.png"),
	CLOCK("icons/custom/clockx64.png"),
	WATCH("icons/custom/watchx64.png"),
	MAIL1("icons/custom/mailx64.png"),
	;
	
	private Image image;
	private int cachedSize = 0;
	private final String path;

	
	PreloadedImageCustom(String path){
		this.path = path;
	}

	public Image getImage(){
		return this.getImage(References.DEFAULT_IMAGE_SIZE);
	}
	
	public Image getImage(int size){
		if(image == null || size != cachedSize) {
			image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
			cachedSize = size;
		}
		return image;
	}
	
}
