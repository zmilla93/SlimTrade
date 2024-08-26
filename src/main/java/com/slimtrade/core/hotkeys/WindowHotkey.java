package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.windows.VisibilityDialog;

import javax.swing.*;

/**
 * Toggles the visibility of a swing window.
 */
public class WindowHotkey implements IHotkeyAction {

    private final VisibilityDialog window;

    public WindowHotkey(VisibilityDialog window) {
        this.window = window;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> window.setVisible(!window.isVisible()));
    }

}
