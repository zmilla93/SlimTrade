package com.slimtrade.gui.options.searching;

import com.slimtrade.core.hotkeys.HotkeyData;

// FIXME : Should terms just be an ArrayList for simplicity?
public record StashSearchGroupData(String title, HotkeyData hotkeyData, StashSearchTermData[] terms) {

}
