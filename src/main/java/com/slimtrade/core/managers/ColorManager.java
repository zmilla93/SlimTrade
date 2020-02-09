package com.slimtrade.core.managers;

import java.awt.Color;

import com.slimtrade.enums.ColorTheme;
import com.slimtrade.core.utility.TradeUtility;

public class ColorManager {

    // INTERNAL
    private static ColorTheme currentColorTheme;

    // Mutual Colors
//	public static Color PRIMARY = new Color(255,204,0); 						// MAIN COLORS
//	public static Color PRIMARY = new Color(208, 255, 194); 						// MAIN COLORS
//	public static Color PRIMARY = new Color(224, 239, 255); 						// MAIN COLORS
//	public static Color PRIMARY = new Color(240, 235, 225); 						// MAIN COLORS
//    public static Color PRIMARY = new Color(224, 136, 57);                        // MAIN COLORS
//    public static Color PRIMARY = new Color(224, 136, 57);                        // MAIN COLORS
//    public static Color PRIMARY = new Color(227, 255, 212);                        // MAIN COLORS
//    public static Color PRIMARY = new Color(196, 183, 215); //Purp
    public static Color PRIMARY = new Color(215, 206, 169); //Tanish
//    public static Color PRIMARY = new Color(194, 162, 64); //Bronzish
    public static Color GREEN_SALE = new Color(0, 100, 0);            //
    //	public static Color GREEN_APPROVE = new Color(14, 179, 0); 		//
//    public static Color GREEN_APPROVE = new Color(190, 215, 0);        //
    public static Color GREEN_APPROVE = new Color(73, 156, 84);        //
    public static Color RED_SALE = new Color(100, 0, 0);            //
    //	public static Color RED_DENY = new Color(150, 0, 0);			//
    public static Color RED_DENY = new Color(199, 84, 80);            //
    public static Color SCANNER_BACKGROUND = new Color(200, 128, 0);    //
    public static Color INTERACTIVE = Color.BLUE;                    // Currently Unused

    // Exclusive Colors
    public static Color BACKGROUND;                 // DARK/LIGHT THEME
    public static Color LOW_CONSTRAST_1;            // SUBTLE BACKGROUND	- SIDEBAR/FORUM FIELD
    public static Color LOW_CONSTRAST_2;            // SUBTLE BACKGROUND	- SIDEBAR/FORUM FIELD
    public static Color HIGH_CONTRAST_1;            // DEFINE WHITE/BLACK 	- CAN BE BACKGROUND OF OPPO
    public static Color HIGH_CONTRAST_2;            // LESSER WHITE/BLACK 	- CAN BE LOW CONSTAST OF OPPO
    public static Color TEXT;    // DEFAULT TEXT
    public static Color DISABLED = Color.GRAY;    // DISABLED

    // OTHER
    public static Color PLAYER_JOINED_INCOMING = new Color(0, 160, 0);
    public static Color PLAYER_JOINED_OUTGOING = new Color(160, 0, 0);


    //Static Colors
    //TODO : Check uses of clear
    public static Color CLEAR = new Color(0, 0, 0, 0);
    public static Color CLEAR_CLICKABLE = new Color(1.0f, 1.0f, 1.0f, 0.002f);
    public static Color POE_TEXT_DARK = new Color(53, 28, 13);
    public static Color POE_TEXT_LIGHT = new Color(254, 192, 118);

    public static void setTheme(ColorTheme theme) {
        if (currentColorTheme == theme) {
            return;
        }
        switch (theme) {
            default:
            case LIGHT_THEME:
                ColorManager.BACKGROUND = Color.WHITE;
                ColorManager.LOW_CONSTRAST_1 = Color.LIGHT_GRAY;
                ColorManager.HIGH_CONTRAST_1 = Color.BLACK;
                ColorManager.HIGH_CONTRAST_2 = Color.DARK_GRAY;
                ColorManager.BACKGROUND = new Color(255, 245, 235);
                ColorManager.LOW_CONSTRAST_1 = new Color(240, 235, 225);
                ColorManager.LOW_CONSTRAST_2 = new Color(197, 192, 182);
                ColorManager.HIGH_CONTRAST_1 = new Color(60, 60, 60);
                ColorManager.HIGH_CONTRAST_2 = new Color(102, 102, 102);
                break;

            case DARK_THEME:
//			ColorManager.BACKGROUND = new Color(64, 64, 64);
                ColorManager.BACKGROUND = new Color(43, 43, 43);
                ColorManager.LOW_CONSTRAST_1 = Color.DARK_GRAY;
                ColorManager.HIGH_CONTRAST_1 = Color.WHITE;
                ColorManager.HIGH_CONTRAST_2 = Color.LIGHT_GRAY;
                break;

        }
        //MUTUAL
        TEXT = HIGH_CONTRAST_1;
//        TEXT = Color.ORANGE;
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

    public static ColorTheme getTheme() {
        return currentColorTheme;
    }

}
