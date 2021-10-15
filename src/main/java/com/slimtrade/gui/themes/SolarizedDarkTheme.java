package com.slimtrade.gui.themes;

import java.awt.*;

public class SolarizedDarkTheme extends BaseColorTheme{
    public SolarizedDarkTheme(){
        PRIMARY = new Color(0, 44, 57);
        BACKGROUND = new Color(3, 56, 70);
        LOW_CONTRAST_1 = new Color(0, 43, 54);
        LOW_CONTRAST_2 = new Color(59, 94, 118);
        HIGH_CONTRAST_1 = new Color(56, 116, 137);
        HIGH_CONTRAST_2 = new Color(102, 102, 102);
        // Specific
        TEXT = new Color(197, 197, 192);
        TEXT_SELECTION = TEXT;
        MESSAGE_BORDER = PRIMARY;
        MESSAGE_NAME_BG = LOW_CONTRAST_1;
        MESSAGE_ITEM_BG = BACKGROUND;
        MESSAGE_TIMER_BG = LOW_CONTRAST_2;
        MESSAGE_PRICE_TEXT = TEXT;
        MENUBAR_EXPAND_BUTTON = PRIMARY;
        BUTTON_SECONDARY_COLOR = BACKGROUND;
        SCROLL_BAR = PRIMARY;
    }
}
