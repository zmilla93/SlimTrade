package com.slimtrade.core.saving;

import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImageCustom;

public class MacroButton {

    public ButtonRow row;
    public String leftMouseResponse;
    public String rightMouseResponse;
    public PreloadedImageCustom image;

    public MacroButton(ButtonRow row, String leftMouseResponse, String rightMouseResponse, PreloadedImageCustom image) {
        this.row = row;
        this.leftMouseResponse = leftMouseResponse;
        this.rightMouseResponse = rightMouseResponse;
        this.image = image;
    }

    public static boolean doButtonsMatch(MacroButton b1, MacroButton b2) {
        int check = 0;
        if(b1.row == b2.row) {
            check++;
        }
        if(b1.leftMouseResponse.equals(b2.leftMouseResponse)) {
            check++;
        }
        if(b1.rightMouseResponse.equals(b2.rightMouseResponse)) {
            check++;
        }
        if(b1.image == b2.image) {
            check++;
        }
        if(check == 4) {
            return true;
        }
        return false;
    }

}
