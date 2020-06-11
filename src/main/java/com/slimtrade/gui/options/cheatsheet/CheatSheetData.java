package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.elements.PinElement;

import java.io.File;

public class CheatSheetData {

    public String fileName;
    public String cleanName;
    public HotkeyData hotkeyData;
    public PinElement pinElement;

    public CheatSheetData(String fileName) {
        this.fileName = fileName;
    }

    public String getCleanName() {
        if (cleanName == null) {
            File file = new File(fileName);
            cleanName = file.getName().replaceFirst("\\.\\w+$", "").replaceAll("_", " ").trim();
        }
        return cleanName;
    }

}
