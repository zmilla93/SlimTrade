package com.slimtrade.core.hotkeys;

import com.slimtrade.core.utility.POEInterface;

public class PoeCommandHotkey implements IHotkeyAction {

    String command;

    public PoeCommandHotkey(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        if (!POEInterface.isGameFocused()) return;
        POEInterface.paste(command);
    }

}
