package com.slimtrade.core.data;

import com.slimtrade.core.hotkeys.HotkeyData;

public class CheatSheetData {

    public String title;
    public String extension;
    public String fileName;
    public HotkeyData hotkeyData;

    public CheatSheetData(String fileName, HotkeyData hotkeyData) {
        this.fileName = fileName;
        int extIndex = fileName.lastIndexOf('.');
        title = fileName.substring(0, extIndex);
        extension = fileName.substring(extIndex + 1);
        this.hotkeyData = hotkeyData;
    }

    public boolean isValid() {
        switch (extension) {
            case "png":
            case "jpg":
            case "jpeg":
            case "gif":
                return true;
        }
        return false;
    }

}
