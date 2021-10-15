package com.slimtrade.gui.themes;

import java.awt.*;

public class SolarizedLightTheme extends BaseColorTheme {

    public SolarizedLightTheme() {
        // General
        PRIMARY = new Color(215, 206, 169);
        BACKGROUND = new Color(255, 245, 235);
        LOW_CONTRAST_1 = new Color(227, 222, 212);
        LOW_CONTRAST_2 = new Color(191, 186, 176);
        HIGH_CONTRAST_1 = new Color(60, 60, 60);
        HIGH_CONTRAST_2 = new Color(102, 102, 102);
        // Specific
        TEXT = HIGH_CONTRAST_1;
        TEXT_SELECTION = PRIMARY;
        MESSAGE_BORDER = PRIMARY;
        MESSAGE_NAME_BG = new Color(227, 222, 212);
        MESSAGE_ITEM_BG = new Color(204, 199, 189);
        MESSAGE_TIMER_BG = LOW_CONTRAST_2;
        MESSAGE_PRICE_TEXT = PRIMARY;
        MENUBAR_EXPAND_BUTTON = PRIMARY;
        BUTTON_SECONDARY_COLOR = BACKGROUND;
        SCROLL_BAR = PRIMARY;
    }
}
