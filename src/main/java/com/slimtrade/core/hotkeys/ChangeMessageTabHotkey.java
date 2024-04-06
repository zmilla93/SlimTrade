package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;

/**
 * When using tabs, changes the selected tab of the message manager.
 */
public class ChangeMessageTabHotkey implements IHotkeyAction {

    private final int change;

    public ChangeMessageTabHotkey(int change) {
        this.change = change;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> FrameManager.messageManager.changeMessageTab(change));
    }
    
}
