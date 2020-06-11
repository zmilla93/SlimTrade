package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.PinSaveFile;
import com.slimtrade.core.saving.elements.PinElement;

import java.io.File;

public class CheatSheetData {

    public String fileName;
    public String cleanName;
    public HotkeyData hotkeyData;
    public PinElement pinElement;
    public transient CheatSheetWindow window;

    public CheatSheetData(String fileName) {
        System.out.println("ADDING : " + fileName);
        this.fileName = fileName;
    }

    public String getCleanName() {
        if (cleanName == null) {
            File file = new File(fileName);
            cleanName = file.getName().replaceFirst("\\.\\w+$", "").replaceAll("_", " ");
        }
        return cleanName;
    }

    public CheatSheetWindow getWindow() {
        if(window == null) {
            window = new CheatSheetWindow(this);
        }
        return window;
    }

}
