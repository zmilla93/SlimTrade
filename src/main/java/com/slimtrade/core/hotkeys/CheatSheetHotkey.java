package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.windows.CheatSheetWindow;

public class CheatSheetHotkey implements IHotkeyAction {

    private CheatSheetWindow window;

    public CheatSheetHotkey(CheatSheetWindow window) {
        this.window = window;
    }

    @Override
    public void execute() {
        window.setVisible(!window.isVisible());
    }

}
