package com.slimtrade.gui.stash;

import com.slimtrade.core.managers.SaveManager;

import javax.swing.*;

public class StashHighlighterFrame extends JDialog {

    public StashHighlighterFrame() {
        assert (SwingUtilities.isEventDispatchThread());
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        pack();
        setSize(SaveManager.stashSaveFile.data.getCellSize());
    }

}
