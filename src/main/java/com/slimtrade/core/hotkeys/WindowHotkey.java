package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.windows.CustomDialog;

import javax.swing.*;

public class WindowHotkey implements IHotkeyAction {

    private final CustomDialog window;

    public WindowHotkey(CustomDialog window) {
        this.window = window;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> window.setVisible(!window.isVisible()));
    }

}
