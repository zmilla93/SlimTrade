package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.searching.StashSearchWindow;

import javax.swing.*;

/**
 * Toggles the visibility for a SlimTrade search window.
 */
public class SearchWindowHotkey implements IHotkeyAction {

    private final int id;

    public SearchWindowHotkey(int id) {
        this.id = id;
    }

    @Override
    public void execute() {
        for (StashSearchWindow window : FrameManager.searchWindows.values()) {
            if (window.getData().id == id) {
                SwingUtilities.invokeLater(() -> window.setVisible(!window.isVisible()));
                return;
            }
        }
    }

}
