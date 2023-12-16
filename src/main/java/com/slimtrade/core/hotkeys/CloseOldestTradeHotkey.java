package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;

public class CloseOldestTradeHotkey implements IHotkeyAction {

    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> FrameManager.messageManager.closeOldestTrade());
    }

}
