package com.slimtrade.core.SaveSystem;

import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImageCustom;

public class MacroButton {

    public ButtonRow row;
    public String leftMouseResponse;
    public String rightMouseResponse;
    public PreloadedImageCustom image;

    public  MacroButton(ButtonRow row, String leftMouseResponse, String rightMouseResponse, PreloadedImageCustom image) {
        this.row = row;
        this.leftMouseResponse = leftMouseResponse;
        this.rightMouseResponse = rightMouseResponse;
        this.image = image;
    }

}
