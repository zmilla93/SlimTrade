package com.slimtrade.core.managers;

import java.awt.Color;

import com.slimtrade.enums.ColorTheme;
import com.slimtrade.core.utility.TradeUtility;

import javax.swing.*;
import javax.swing.border.Border;

public class ColorManager {

    // Mutual
    public static Color GREEN_SALE = new Color(0, 100, 0);
    public static Color GREEN_APPROVE = new Color(73, 156, 84);
    public static Color RED_SALE = new Color(100, 0, 0);
    public static Color RED_DENY = new Color(199, 84, 80);
    public static Color SCANNER_BACKGROUND = new Color(200, 128, 0);

    // General
    public static Color PRIMARY;
    public static Color BACKGROUND;
    public static Color LOW_CONTRAST_1;
    public static Color LOW_CONTRAST_2;
    public static Color HIGH_CONTRAST_1;
    public static Color HIGH_CONTRAST_2;
    public static Color TEXT;
    public static Color TEXT_EDIT_BACKGROUND;
    public static Color TEXT_SELECTION;

    // Specific
    public static Color PLAYER_JOINED_INCOMING;
    public static Color PLAYER_JOINED_OUTGOING;
    public static Color MESSAGE_BORDER;
    public static Color MESSAGE_NAME_BG;
    public static Color MESSAGE_ITEM_BG;
    public static Color MESSAGE_TIMER_BG;
    public static Color MESSAGE_PRICE_TEXT;
    public static Color MENUBAR_EXPAND_BUTTON;
    public static Color BUTTON_SECONDARY_COLOR;
    public static Color SCROLL_BAR;

    // BORDERS
    public static Border BORDER_PRIMARY;
    public static Border BORDER_TEXT;
    public static Border BORDER_LOW_CONTRAST_1;

    // TODO : Check uses of clear as it can cause drawing issues.
    public static Color CLEAR = new Color(0, 0, 0, 0);
    public static Color POE_TEXT_DARK = new Color(53, 28, 13);
    public static Color POE_TEXT_LIGHT = new Color(254, 192, 118);

    // Internal
    private static final int OFFSET_COLOR_AMOUNT = 60;
    private static ColorTheme currentColorTheme;
    private static boolean colorBlindMode;

    public static void setTheme(ColorTheme theme) {
//        if (currentColorTheme == theme) {
//            return;
//        }
        if (theme == null) {
            theme = ColorTheme.values()[0];
        }
        currentColorTheme = theme;

        // Some defaults
        GREEN_SALE = new Color(0, 130, 0);
        PLAYER_JOINED_INCOMING = new Color(0, 140, 0);
        GREEN_APPROVE = new Color(73, 156, 84);
        RED_SALE = new Color(130, 0, 0);
        PLAYER_JOINED_OUTGOING = RED_SALE;
        RED_DENY = new Color(199, 84, 80);
        SCANNER_BACKGROUND = new Color(200, 128, 0);

//        public static Color PLAYER_JOINED_INCOMING = new Color(0, 160, 0);
//        public static Color PLAYER_JOINED_OUTGOING = new Color(160, 0, 0);

        switch (theme) {

            // Light Theme
            case SOLARIZED_LIGHT:
                // General
                PRIMARY = new Color(215, 206, 169);
                BACKGROUND = new Color(255, 245, 235);
                LOW_CONTRAST_1 = new Color(227, 222, 212);
                LOW_CONTRAST_2 = new Color(191, 186, 176);
                HIGH_CONTRAST_1 = new Color(60, 60, 60);
                HIGH_CONTRAST_2 = new Color(102, 102, 102);
                // Specific
                TEXT = ColorManager.HIGH_CONTRAST_1;
                TEXT_SELECTION = PRIMARY;
                MESSAGE_BORDER = PRIMARY;
                MESSAGE_NAME_BG = new Color(227, 222, 212);
                MESSAGE_ITEM_BG = new Color(204, 199, 189);
                MESSAGE_TIMER_BG = LOW_CONTRAST_2;
                MESSAGE_PRICE_TEXT = PRIMARY;
                MENUBAR_EXPAND_BUTTON = PRIMARY;
                BUTTON_SECONDARY_COLOR = ColorManager.BACKGROUND;
                SCROLL_BAR = PRIMARY;
                break;

            // Light Theme
            case SOLARIZED_DARK:
                // General
                PRIMARY = new Color(0, 44, 57);
                BACKGROUND = new Color(3, 56, 70);
                LOW_CONTRAST_1 = new Color(0, 43, 54);
                LOW_CONTRAST_2 = new Color(59, 94, 118);
                HIGH_CONTRAST_1 = new Color(56, 116, 137);
                HIGH_CONTRAST_2 = new Color(102, 102, 102);
                // Specific
                TEXT = new Color(184, 188, 189);
                TEXT_SELECTION = TEXT;
                MESSAGE_BORDER = PRIMARY;
                MESSAGE_NAME_BG = LOW_CONTRAST_1;
                MESSAGE_ITEM_BG = BACKGROUND;
                MESSAGE_TIMER_BG = LOW_CONTRAST_2;
                MESSAGE_PRICE_TEXT = TEXT;
                MENUBAR_EXPAND_BUTTON = PRIMARY;
                BUTTON_SECONDARY_COLOR = ColorManager.BACKGROUND;
                SCROLL_BAR = PRIMARY;
                break;

            // Dark Theme
//            case DARK_THEME_1:
//                // General
//                PRIMARY = new Color(77, 77, 77);
//                BACKGROUND = new Color(43, 43, 43);
//                LOW_CONTRAST_1 = new Color(80, 80, 80);
//                LOW_CONTRAST_2 = new Color(112, 112, 112);
//                HIGH_CONTRAST_1 = Color.WHITE;
//                HIGH_CONTRAST_2 = Color.LIGHT_GRAY;
//                // Specific
//                TEXT = new Color(200, 185, 97);
//                TEXT_SELECTION = TEXT;
//                MESSAGE_BORDER = PRIMARY;
//                MESSAGE_NAME_BG = LOW_CONTRAST_1;
//                MESSAGE_ITEM_BG = BACKGROUND;
//                MESSAGE_TIMER_BG = LOW_CONTRAST_2;
//                MESSAGE_PRICE_TEXT = TEXT;
//                MENUBAR_EXPAND_BUTTON = PRIMARY;
//                BUTTON_SECONDARY_COLOR = new Color(197, 187, 178);
//                BUTTON_SECONDARY_COLOR = new Color(153, 143, 135);
//                SCROLL_BAR = LOW_CONTRAST_1;
//                break;

            // Stormy Theme
            case STORMY:
                // General
                PRIMARY = new Color(53, 70, 91);
                BACKGROUND = new Color(43, 43, 43);
                LOW_CONTRAST_1 = new Color(89, 91, 93);
                LOW_CONTRAST_1 = new Color(64, 66, 68);
                LOW_CONTRAST_2 = new Color(116, 111, 102);
                HIGH_CONTRAST_1 = new Color(175, 177, 179);
                HIGH_CONTRAST_2 = new Color(102, 102, 102);
                // Specific
                TEXT = new Color(184, 188, 189);
                TEXT_SELECTION = TEXT;
                MESSAGE_BORDER = BACKGROUND;
                MESSAGE_NAME_BG = LOW_CONTRAST_1;
                MESSAGE_ITEM_BG = BACKGROUND;
                MESSAGE_TIMER_BG = LOW_CONTRAST_2;
                MESSAGE_PRICE_TEXT = TEXT;
                MENUBAR_EXPAND_BUTTON = PRIMARY;
                BUTTON_SECONDARY_COLOR = ColorManager.BACKGROUND;
                SCROLL_BAR = PRIMARY;
                break;

//            // Vaal Theme
//            case VAAL:
//                // General
//                PRIMARY = new Color(84, 25, 12);
//                BACKGROUND = new Color(43, 43, 43);
//                LOW_CONTRAST_1 = new Color(89, 91, 93);
//                LOW_CONTRAST_1 = new Color(64, 66, 68);
//                LOW_CONTRAST_2 = new Color(116, 111, 102);
//                HIGH_CONTRAST_1 = new Color(175, 177, 179);
//                HIGH_CONTRAST_2 = new Color(102, 102, 102);
//                // Specific
//                TEXT = new Color(184, 188, 189);
//                TEXT_SELECTION = TEXT;
//                MESSAGE_BORDER = PRIMARY;
//                MESSAGE_NAME_BG = LOW_CONTRAST_1;
//                MESSAGE_ITEM_BG = BACKGROUND;
//                MESSAGE_TIMER_BG = LOW_CONTRAST_2;
//                MESSAGE_PRICE_TEXT = TEXT;
//                MENUBAR_EXPAND_BUTTON = PRIMARY;
//                BUTTON_SECONDARY_COLOR = ColorManager.BACKGROUND;
//                SCROLL_BAR = PRIMARY;
//                break;

            // Monika
            case MONOKAI:
                // General
                PRIMARY = new Color(32, 33, 32);
                PRIMARY = new Color(30, 31, 28);
                BACKGROUND = new Color(39, 40, 34);
                LOW_CONTRAST_1 = new Color(65, 67, 57);
                LOW_CONTRAST_2 = new Color(52, 53, 47);
                HIGH_CONTRAST_1 = Color.WHITE;
                HIGH_CONTRAST_2 = Color.LIGHT_GRAY;
                // Specific
                TEXT = new Color(204, 204, 199);
                TEXT_SELECTION = TEXT;
                MESSAGE_BORDER = PRIMARY;
                MESSAGE_NAME_BG = LOW_CONTRAST_1;
                MESSAGE_ITEM_BG = BACKGROUND;
                MESSAGE_TIMER_BG = LOW_CONTRAST_2;
                MESSAGE_PRICE_TEXT = TEXT;
                MENUBAR_EXPAND_BUTTON = LOW_CONTRAST_1;
                BUTTON_SECONDARY_COLOR = LOW_CONTRAST_1;
                SCROLL_BAR = LOW_CONTRAST_1;
                break;

            // Monika
//            case MONOKAI_ALT_1:
//                // General
//                PRIMARY = new Color(69, 52, 73);
//                PRIMARY = new Color(153, 89, 27);
//                BACKGROUND = new Color(39, 40, 34);
//                LOW_CONTRAST_1 = new Color(65, 67, 57);
//                LOW_CONTRAST_2 = new Color(52, 53, 47);
//                HIGH_CONTRAST_1 = Color.WHITE;
//                HIGH_CONTRAST_2 = Color.LIGHT_GRAY;
//                // Specific
//                TEXT = new Color(204, 204, 199);
//                TEXT_SELECTION = TEXT;
//                MESSAGE_BORDER = BACKGROUND;
//                MESSAGE_NAME_BG = LOW_CONTRAST_1;
//                MESSAGE_ITEM_BG = BACKGROUND;
//                MESSAGE_TIMER_BG = LOW_CONTRAST_2;
//                MESSAGE_PRICE_TEXT = TEXT;
//                MENUBAR_EXPAND_BUTTON = PRIMARY;
//                BUTTON_SECONDARY_COLOR = lighter(PRIMARY);
//                SCROLL_BAR = LOW_CONTRAST_1;
//                break;


        }
        // Mutual
        TEXT_EDIT_BACKGROUND = LOW_CONTRAST_1;
        BORDER_PRIMARY = BorderFactory.createLineBorder(ColorManager.PRIMARY);
        BORDER_TEXT = BorderFactory.createLineBorder(ColorManager.TEXT);
        BORDER_LOW_CONTRAST_1 = BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_1);

        // Color Blind Mode
        if (colorBlindMode) {
            GREEN_SALE = new Color(30, 136, 229);
            PLAYER_JOINED_INCOMING = GREEN_SALE;
            GREEN_APPROVE = GREEN_SALE;
            RED_SALE = new Color(216, 27, 96);
            PLAYER_JOINED_OUTGOING = RED_SALE;
            RED_DENY = RED_SALE;
            SCANNER_BACKGROUND = new Color(209, 151, 7);
        }

    }

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

    public static ColorTheme getCurrentColorTheme() {
        return currentColorTheme;
    }

    public static void setColorBlindMode(boolean state) {
        colorBlindMode = state;
    }


}
