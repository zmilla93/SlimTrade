package com.slimtrade.core.data;

import com.slimtrade.core.hotkeys.HotkeyData;

public class CheatSheetData {

    public final String title;
    public final String extension;
    public final String fileName;
    public final HotkeyData hotkeyData;

    // FIXME : File info is immutable, but hotkey data (and potential future settings like size/opacity) is not.
    //         If more features are added, file info should be moved to its own class to make this more clear.
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
