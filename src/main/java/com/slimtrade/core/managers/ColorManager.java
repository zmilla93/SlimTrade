package main.java.com.slimtrade.core.managers;

import java.awt.Color;

import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.ColorTheme;

public class ColorManager {

	// Mutual Colors
	public static Color PRMIARY = Color.ORANGE; 			// MAIN COLOR
	public static Color GREEN_SALE = new Color(0, 100, 0); 		// APPROVE
	public static Color GREEN_APPROVE = new Color(0, 100, 0); 		// APPROVE
	public static Color RED_SALE = new Color(100, 0, 0);			// DENY
	public static Color RED_DENY = new Color(100, 0, 0);			// DENY
	public static Color ORANGE = Color.ORANGE;				// ORANGE
	public static Color INTERACTIVE = Color.BLUE;			// INTERACTIVE - Unsed
	
	//TODO : Check uses of clear
	public static Color CLEAR = new Color(0, 0, 0, 0);
	public static Color CLEAR_CLICKABLE = new Color(1.0f, 1.0f, 1.0f, 0.002f);

	public static Color POE_TEXT_DARK = new Color(53, 28, 13);
	public static Color POE_TEXT_LIGHT = new Color(254, 192, 118);

	// public static Color PRMIARY; // MAIN COLOR
	// public static Color GREEN; // APPROVE
	// public static Color RED; // DENY
	// public static Color ORANGE; // ORANGE
	// public static Color INTERACTIVE; // INTERACTIVE
	// public static Color DISABLED; // DISABLED

	// Exclusive Colors
	public static Color BACKGROUND;				// DARK/LIGHT THEME
	public static Color LOW_CONSTRAST;			// SUBTLE BACKGROUND	- SIDEBAR/FORUM FIELD
	public static Color HIGH_CONSTRAST_1;		// DEFINE WHITE/BLACK 	- CAN BE BACKGROUND OF OPPO
	public static Color HIGH_CONSTRAST_2;		// LESSER WHITE/BLACK 	- CAN BE LOW CONSTAST OF OPPO
	public static Color DISABLED = Color.GRAY;	// DISABLED

	public static void setTheme(ColorTheme theme) {
		switch (theme) {
		default:
		case DARK_THEME:
			ColorManager.BACKGROUND = new Color(64, 64, 64);
			ColorManager.LOW_CONSTRAST = Color.DARK_GRAY;
			ColorManager.HIGH_CONSTRAST_1 = Color.WHITE;
			ColorManager.HIGH_CONSTRAST_2 = Color.LIGHT_GRAY;
			break;
		case LIGHT_THEME:
			ColorManager.BACKGROUND = Color.WHITE;
			ColorManager.LOW_CONSTRAST = Color.LIGHT_GRAY;
			ColorManager.HIGH_CONSTRAST_1 = Color.BLACK;
			ColorManager.HIGH_CONSTRAST_2 = Color.DARK_GRAY;
			break;
		}
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

}
