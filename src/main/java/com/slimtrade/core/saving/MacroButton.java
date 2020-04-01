package com.slimtrade.core.saving;

import com.slimtrade.core.managers.HotkeyManager;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.observing.poe.CommandManager;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.CustomIcons;

public class MacroButton {

    public ButtonRow row;
    public String leftMouseResponse;
    public String rightMouseResponse;
    public CustomIcons image;
    public HotkeyData hotkeyData;
    public boolean closeOnClick;
    private String[] commandsLeft = null;
    private String[] commandsRight = null;

    public MacroButton(ButtonRow row, String leftMouseResponse, String rightMouseResponse, CustomIcons image, HotkeyData hotkeyData, boolean closeOnClick) {
        this.row = row;
        this.leftMouseResponse = leftMouseResponse;
        this.rightMouseResponse = rightMouseResponse;
        this.image = image;
        this.hotkeyData = hotkeyData;
        this.closeOnClick = closeOnClick;
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

    public String[] getCommandsLeft() {
        if(this.commandsLeft == null) {
            commandsLeft = HotkeyManager.getCommandList(leftMouseResponse);
        }
        return commandsLeft;
    }

    public String[] getCommandsRight() {
        if(this.commandsRight == null) {
            commandsRight = HotkeyManager.getCommandList(rightMouseResponse);
        }
        return commandsRight;
    }

}
