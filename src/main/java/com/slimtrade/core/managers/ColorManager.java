package main.java.com.slimtrade.core.managers;

import java.awt.Color;

public class ColorManager {

	// Mutual Colors
	public static Color PRMIARY = Color.ORANGE; // MAIN COLOR
	public static Color GREEN = Color.GREEN; // APPROVE
	public static Color RED = Color.RED; // DENY
	public static Color ORANGE = Color.ORANGE; // ORANGE
	public static Color INTERACTIVE = Color.BLUE; // INTERACTIVE
	public static Color DISABLED = Color.GRAY; // DISABLED
	
//	public static Color PRMIARY; // MAIN COLOR
//	public static Color GREEN; // APPROVE
//	public static Color RED; // DENY
//	public static Color ORANGE; // ORANGE
//	public static Color INTERACTIVE; // INTERACTIVE
//	public static Color DISABLED; // DISABLED

	// Exclusive Colors
	public static Color BACKGROUND; // DARK/LIGHT THEME
	public static Color LOW_CONSTRAST; // SUBTLE BACKGROUND
	public static Color HIGH_CONSTRAST_1; // DEFINE WHITE/BLACK
	public static Color HIGH_CONSTRAST_2; // LESSER WHITE/BLACK - CAN BE
											// LIGHT/DARK THEME

	public ColorManager() {
//		NewColorManager.PRMIARY = Color.YELLOW;
//		NewColorManager.GREEN = Color.GREEN;
//		NewColorManager.RED = Color.RED;
//		NewColorManager.ORANGE = Color.ORANGE;
//		NewColorManager.INTERACTIVE = Color.BLUE;
//		NewColorManager.DISABLED = Color.GRAY;
	}

	public static void setTheme(ColorTheme theme) {
		switch (theme) {
		default:
		case DARK_THEME:
			ColorManager.BACKGROUND = Color.BLACK;
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

}
