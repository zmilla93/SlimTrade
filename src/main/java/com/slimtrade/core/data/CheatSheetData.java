package com.slimtrade.core.data;

import com.slimtrade.core.hotkeys.HotkeyData;

public class CheatSheetData {

    public String fileName;
    public HotkeyData hotkeyData;

    public CheatSheetData(String fileName, HotkeyData hotkeyData) {
        this.fileName = fileName;
        this.hotkeyData = hotkeyData;
    }

}
