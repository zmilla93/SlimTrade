package com.slimtrade.core.hotkeys;

import com.slimtrade.core.utility.POEInterface;

public class PoeHotkey implements IHotkeyAction {

    String command;

    public PoeHotkey(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        if (!POEInterface.isGameFocused()) return;
        POEInterface.paste(command);
    }
}
