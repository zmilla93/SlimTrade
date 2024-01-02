package com.slimtrade.gui.options.searching;

import com.slimtrade.core.hotkeys.HotkeyData;

// FIXME : Should terms just be an ArrayList for simplicity?
public class StashSearchGroupData {

    public final int id;
    public static final String PIN_PREFIX = "SearchWindow:";
    public final String title;
    public final HotkeyData hotkeyData;
    public final StashSearchTermData[] terms;

    public StashSearchGroupData(int id, String title, HotkeyData hotkeyData, StashSearchTermData[] terms) {
        this.id = id;
        this.title = title;
        this.hotkeyData = hotkeyData;
        this.terms = terms;
    }

    public String getPinTitle() {
        return PIN_PREFIX + id;
    }

    @Override
    public String toString() {
        return title;
    }

}
