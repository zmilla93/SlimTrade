package com.slimtrade.core.managers;

import java.awt.Color;

import com.slimtrade.enums.ColorTheme;
import com.slimtrade.core.utility.TradeUtility;

import javax.swing.*;
import javax.swing.border.Border;

public class ColorManager {

    // INTERNAL
    private static ColorTheme currentColorTheme;

    // Mutual Colors
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
    public static Color LOW_CONTRAST_1;             // SUBTLE BACKGROUND	- SIDEBAR/FORUM FIELD
    public static Color LOW_CONTRAST_2;             // SUBTLE BACKGROUND	- SIDEBAR/FORUM FIELD
    public static Color HIGH_CONTRAST_1;            // DEFINE WHITE/BLACK 	- CAN BE BACKGROUND OF OPPO
    public static Color HIGH_CONTRAST_2;            // LESSER WHITE/BLACK 	- CAN BE LOW CONSTAST OF OPPO
    public static Color TEXT;                       // DEFAULT TEXT
    public static Color TEXT_EDIT_BACKGROUND;       // DEFAULT TEXT

    // OTHER
    public static Color PLAYER_JOINED_INCOMING = new Color(0, 160, 0);
    public static Color PLAYER_JOINED_OUTGOING = new Color(160, 0, 0);
    public static Color MESSAGE_NAME_BG;
    public static Color MESSAGE_ITEM_BG;
    public static Color BUTTON_SECONDARY_COLOR;

    // BORDERS
    public static Border BORDER_PRIMARY;
    public static Border BORDER_TEXT;
    public static Border BORDER_LOW_CONTRAST_1;


    // TODO : Remove clear as it causes massive drawing issues!
    //TODO : Check uses of clear
    public static Color CLEAR = new Color(0, 0, 0, 0);
    //    public static Color CLEAR_CLICKABLE = new Color(1.0f, 1.0f, 1.0f, 0.002f);
    public static Color POE_TEXT_DARK = new Color(53, 28, 13);
    public static Color POE_TEXT_LIGHT = new Color(254, 192, 118);


    // Internal
    private static final int OFFSET_COLOR_AMOUNT = 60;

    public static void setTheme(ColorTheme theme) {
        if (currentColorTheme == theme) {
            return;
        }
        switch (theme) {
            default:
            case LIGHT_THEME:
                PRIMARY = new Color(215, 206, 169); //Purp
                ColorManager.BACKGROUND = Color.WHITE;
                ColorManager.LOW_CONTRAST_1 = Color.LIGHT_GRAY;
                ColorManager.HIGH_CONTRAST_1 = Color.BLACK;
                ColorManager.HIGH_CONTRAST_2 = Color.DARK_GRAY;
                ColorManager.BACKGROUND = new Color(255, 245, 235);
//                ColorManager.BACKGROUND = new Color(255, 245, 235);
//                ColorManager.LOW_CONTRAST_1 = new Color(240, 235, 225);
                ColorManager.LOW_CONTRAST_1 = new Color(227, 222, 212);
//                ColorManager.LOW_CONTRAST_2 = new Color(197, 192, 182);
//                ColorManager.LOW_CONTRAST_2 = new Color(214, 209, 199);
                ColorManager.LOW_CONTRAST_2 = LOW_CONTRAST_1;
                ColorManager.HIGH_CONTRAST_1 = new Color(60, 60, 60);
                ColorManager.HIGH_CONTRAST_2 = new Color(102, 102, 102);

                ColorManager.MESSAGE_NAME_BG = new Color(227, 222, 212);
                ColorManager.MESSAGE_ITEM_BG = new Color(204, 199, 189);
                TEXT = ColorManager.HIGH_CONTRAST_1;
                BUTTON_SECONDARY_COLOR = ColorManager.BACKGROUND;
//                TEXT = Color.RED;
                // TODO : TESTING COLORS
//                ColorManager.LOW_CONTRAST_2 = Color.GREEN;
//                ColorManager.HIGH_CONTRAST_2 = Color.ORANGE;
                break;

            case DARK_THEME:
//			ColorManager.BACKGROUND = new Color(64, 64, 64);
                PRIMARY = new Color(107, 93, 145);
                ColorManager.BACKGROUND = new Color(43, 43, 43);
                ColorManager.LOW_CONTRAST_1 = new Color(80, 80, 80);
                ColorManager.LOW_CONTRAST_2 = new Color(112, 112, 112);
                ColorManager.HIGH_CONTRAST_1 = Color.WHITE;
                ColorManager.HIGH_CONTRAST_2 = Color.LIGHT_GRAY;
                BUTTON_SECONDARY_COLOR = new Color(140, 140, 140);
                TEXT = new Color(180, 180, 180);
//                TEXT = Color.YELLOW;
                break;

        }
        //MUTUAL
//        TEXT = HIGH_CONTRAST_1;
//        TEXT = HIGH_CONTRAST_1;
        TEXT_EDIT_BACKGROUND = LOW_CONTRAST_1;
//        TEXT_EDIT_BACKGROUND = ColorManager.LOW_CONTRAST_1;
        BORDER_PRIMARY = BorderFactory.createLineBorder(ColorManager.PRIMARY);
        BORDER_TEXT = BorderFactory.createLineBorder(ColorManager.TEXT);
        BORDER_LOW_CONTRAST_1 = BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_1);
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

    public static Color lighter(Color c) {
        return modify(c, OFFSET_COLOR_AMOUNT);
    }

    public static Color darker(Color c) {
        return modify(c, -OFFSET_COLOR_AMOUNT);
    }

    public static ColorTheme getTheme() {
        return currentColorTheme;
    }

}
