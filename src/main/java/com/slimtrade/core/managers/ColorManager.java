package main.java.com.slimtrade.core.managers;

import java.awt.Color;

import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.ColorThemeType;

public class ColorManager {
	
	// INTERNAL
	private static ColorThemeType currentColorTheme;
	
	// Mutual Colors
	public static Color PRIMARY = new Color(255,204,0); 						// MAIN COLORS
	public static Color GREEN_SALE = new Color(0, 100, 0); 			// 
	public static Color GREEN_APPROVE = new Color(0, 100, 0); 		// 
	public static Color RED_SALE = new Color(100, 0, 0);			// 
	public static Color RED_DENY = new Color(100, 0, 0);			// 
	public static Color ORANGE_SCANNER = new Color(250, 128, 0);	// 
	public static Color INTERACTIVE = Color.BLUE;					// Currently Unused
	
	// Exclusive Colors
	public static Color BACKGROUND;				// DARK/LIGHT THEME
	public static Color LOW_CONSTRAST;			// SUBTLE BACKGROUND	- SIDEBAR/FORUM FIELD
	public static Color HIGH_CONTRAST_1;		// DEFINE WHITE/BLACK 	- CAN BE BACKGROUND OF OPPO
	public static Color HIGH_CONTRAST_2;		// LESSER WHITE/BLACK 	- CAN BE LOW CONSTAST OF OPPO
	public static Color TEXT;	// DEFAULT TEXT
	public static Color DISABLED = Color.GRAY;	// DISABLED
	
	//Static Colors
	//TODO : Check uses of clear
	public static Color CLEAR = new Color(0, 0, 0, 0);
	public static Color CLEAR_CLICKABLE = new Color(1.0f, 1.0f, 1.0f, 0.002f);
	public static Color POE_TEXT_DARK = new Color(53, 28, 13);
	public static Color POE_TEXT_LIGHT = new Color(254, 192, 118);
		
	public static void setTheme(ColorThemeType theme) {
		if(currentColorTheme == theme){
			return;
		}
		switch (theme) {
		default:
		case DARK_THEME:
//			ColorManager.BACKGROUND = new Color(64, 64, 64);
			ColorManager.BACKGROUND = Color.BLACK;
			ColorManager.LOW_CONSTRAST = Color.DARK_GRAY;
			ColorManager.HIGH_CONTRAST_1 = Color.WHITE;
			ColorManager.HIGH_CONTRAST_2 = Color.LIGHT_GRAY;
			break;
		case LIGHT_THEME:
			ColorManager.BACKGROUND = Color.WHITE;
			ColorManager.LOW_CONSTRAST = Color.LIGHT_GRAY;
			ColorManager.HIGH_CONTRAST_1 = Color.BLACK;
			ColorManager.HIGH_CONTRAST_2 = Color.DARK_GRAY;
			break;
		}
		//MUTUAL
		TEXT = HIGH_CONTRAST_2;
	}
	
	//TODO : Revamp this?
	public static Color modify(Color c, int mod) {
		int min = 0;
		int max = 255;
		int r = TradeUtility.intWithinRange(c.getRed() + mod, min, max);
		int g = TradeUtility.intWithinRange(c.getGreen() + mod, min, max);
		int b = TradeUtility.intWithinRange(c.getBlue() + mod, min, max);
		return new Color(r, g, b);
	}

	public static ColorThemeType getTheme(){
		return currentColorTheme;
	}
	
}
