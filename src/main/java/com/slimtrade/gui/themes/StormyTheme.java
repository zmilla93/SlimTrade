package com.slimtrade.gui.themes;

import java.awt.*;

public class StormyTheme extends BaseColorTheme {
    public StormyTheme(){
        PRIMARY = new Color(53, 70, 91);
        BACKGROUND = new Color(43, 43, 43);
        LOW_CONTRAST_1 = new Color(89, 91, 93);
        LOW_CONTRAST_1 = new Color(64, 66, 68);
        LOW_CONTRAST_2 = new Color(116, 111, 102);
        HIGH_CONTRAST_1 = new Color(175, 177, 179);
        HIGH_CONTRAST_2 = new Color(102, 102, 102);
        // Specific
        TEXT = new Color(197, 197, 192);
        TEXT_SELECTION = TEXT;
        MESSAGE_BORDER = BACKGROUND;
        MESSAGE_NAME_BG = LOW_CONTRAST_1;
        MESSAGE_ITEM_BG = BACKGROUND;
        MESSAGE_TIMER_BG = LOW_CONTRAST_2;
        MESSAGE_PRICE_TEXT = TEXT;
        MENUBAR_EXPAND_BUTTON = PRIMARY;
        BUTTON_SECONDARY_COLOR = BACKGROUND;
        SCROLL_BAR = PRIMARY;
    }
}
