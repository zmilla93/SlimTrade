package com.slimtrade.core.hotkeys;

import com.slimtrade.core.utility.searchInStash;

public class PoeHotkey implements IHotkeyAction {

    String command;

    public PoeHotkey(String command) {
        this.command = command;
    }

    @Override
    public void execute() {
        searchInStash.paste(command);
    }
}
