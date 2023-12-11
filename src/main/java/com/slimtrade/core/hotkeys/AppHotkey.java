package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.windows.CustomDialog;

public class AppHotkey implements IHotkeyAction {

    private final CustomDialog window;

    public AppHotkey(CustomDialog window) {
        this.window = window;
    }

    @Override
    public void execute() {
        window.setVisible(!window.isVisible());
    }

}
