package com.slimtrade.gui.themes;

import java.awt.*;

public class MonokaiTheme extends BaseColorTheme{

    public MonokaiTheme(){
        // General
        PRIMARY = new Color(30, 31, 28);
        BACKGROUND = new Color(39, 40, 34);
        LOW_CONTRAST_1 = new Color(65, 67, 57);
        LOW_CONTRAST_2 = new Color(52, 53, 47);
        HIGH_CONTRAST_1 = Color.WHITE;
        HIGH_CONTRAST_2 = Color.LIGHT_GRAY;
        // Specific
        TEXT = new Color(197, 197, 192);
        TEXT_SELECTION = TEXT;
        MESSAGE_BORDER = PRIMARY;
        MESSAGE_NAME_BG = LOW_CONTRAST_1;
        MESSAGE_ITEM_BG = BACKGROUND;
        MESSAGE_TIMER_BG = LOW_CONTRAST_2;
        MESSAGE_PRICE_TEXT = TEXT;
        MENUBAR_EXPAND_BUTTON = LOW_CONTRAST_1;
        BUTTON_SECONDARY_COLOR = LOW_CONTRAST_1;
        SCROLL_BAR = LOW_CONTRAST_1;
    }
}
