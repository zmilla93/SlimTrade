package com.slimtrade.gui.options.searching;

import com.slimtrade.core.References;
import com.slimtrade.core.hotkeys.HotkeyData;

// FIXME : Should terms just be an ArrayList for simplicity?
public record StashSearchGroupData(String title, HotkeyData hotkeyData, StashSearchTermData[] terms) {

    public static final String PIN_SUFFIX = "::SEARCH_WINDOW";

    public String getPinTitle() {
        return getPinTitle(title);
    }

    public static String getPinTitle(String name) {
        return References.APP_PREFIX + name + PIN_SUFFIX;
    }

}
