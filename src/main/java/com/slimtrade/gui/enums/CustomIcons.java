package com.slimtrade.gui.enums;

import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Icons for use by the user with custom macro buttons.
 */

public enum CustomIcons implements ICacheImage {

	CANCEL("icons/custom/cancelx48.png"),
	REPLY("icons/custom/replyx48.png"),
	BOOKMARK("icons/custom/bookmarkx48.png"),
	MAP("icons/custom/mapx64.png"),
	BEAKER("icons/custom/beakerx48.png"),
	FLOW2("icons/custom/flow-switchx48.png"),
	MAIL1("icons/custom/mailx64.png"),
	WATCH("icons/custom/watchx64.png"),
	CLOCK("icons/custom/clockx64.png"),

	CART("icons/default/cartx64.png"),  //
	HOME("icons/default/homex64.png"),  //
	INVITE("icons/default/invitex48.png"),  //
	LEAVE("icons/default/leavex64.png"),    //
	REFRESH("icons/default/refreshx64.png"),    //
	THUMB("icons/default/thumbx64.png"),    //
	WARP("icons/default/warpx64.png"),  //
	;
	
	private Image image;
	private BufferedImage bufferedImage;
	private int cachedSize = 0;
	private Color cachedColor;
	private final String path;
//	private boolean preCache = 0;

	
	CustomIcons(String path){
		this.path = path;
//		getImage(References.DEFAULT_IMAGE_SIZE);
//		getColorImage(ColorManager.TEXT);
	}

//	public Image getImage(){
//		return this.getImage(References.DEFAULT_IMAGE_SIZE);
//	}
	
	public Image getImage(int size){
		if(image == null || size != cachedSize) {
			image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
			cachedSize = size;
		}
		return image;
	}

	@Override
	public Image getColorImage(Color color) {
		if(image == null || References.DEFAULT_IMAGE_SIZE != cachedSize || true) {
			getImage(References.DEFAULT_IMAGE_SIZE);
		}
		if(bufferedImage == null || !ColorManager.matchingColors(color, cachedColor)) {
			System.out.println("buf" + bufferedImage);
			System.out.println("col" + color + " | " + cachedColor);
			System.out.println("colcheck" + ColorManager.matchingColors(color, cachedColor));
			System.out.println("GEN IMAGE");
			bufferedImage = new BufferedImage(cachedSize, cachedSize, BufferedImage.TYPE_INT_ARGB);
			Graphics2D bGr = bufferedImage.createGraphics();
			bGr.drawImage(image, 0, 0, null);
			bGr.dispose();
			bufferedImage = colorImage(bufferedImage);
			cachedColor = color;
		}
		return bufferedImage;
	}

	private static BufferedImage colorImage(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();

		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] = ColorManager.TEXT.getRed();
				pixels[1] = ColorManager.TEXT.getGreen();
				pixels[2] = ColorManager.TEXT.getBlue();
				raster.setPixel(xx, yy, pixels);
			}
		}
		return image;
	}

}
