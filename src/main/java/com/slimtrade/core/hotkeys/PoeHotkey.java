package com.slimtrade.core.hotkeys;

import com.slimtrade.core.utility.PoeInterface;

public class PoeHotkey implements IHotkeyAction {

    String command;

    public PoeHotkey(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        PoeInterface.paste(command);
    }
}
