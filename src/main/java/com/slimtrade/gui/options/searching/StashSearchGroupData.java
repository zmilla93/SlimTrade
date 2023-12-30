package com.slimtrade.gui.options.searching;

import com.slimtrade.core.References;
import com.slimtrade.core.hotkeys.HotkeyData;

// FIXME : Should terms just be an ArrayList for simplicity?
public class StashSearchGroupData {

    public static final String PIN_SUFFIX = "::SEARCH_WINDOW";
    public final String title;
    public final HotkeyData hotkeyData;
    public final StashSearchTermData[] terms;

    public StashSearchGroupData(String title, HotkeyData hotkeyData, StashSearchTermData[] terms) {
        this.title = title;
        this.hotkeyData = hotkeyData;
        this.terms = terms;
    }

    public String getPinTitle() {
        return getPinTitle(title);
    }

    public static String getPinTitle(String name) {
        return References.APP_PREFIX + name + PIN_SUFFIX;
    }

    @Override
    public String toString() {
        return title;
    }

}
