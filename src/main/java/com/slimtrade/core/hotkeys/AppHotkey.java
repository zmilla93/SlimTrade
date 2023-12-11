package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.managers.FrameManager;

import java.awt.*;

public class AppHotkey implements IHotkeyAction {

    public enum AppWindow {OPTIONS, HISTORY, CHAT_SCANNER, STASH_SORT_WINDOW}

    private AppWindow window;

    // FIXME : Should just pass a reference to the window directly once all windows are using the new system
    public AppHotkey(AppWindow window) {
        this.window = window;
    }

    @Override
    public void execute() {
        switch (window) {
            case OPTIONS:
                toggleVisibility(FrameManager.optionsWindow);
                break;
            case HISTORY:
                toggleVisibility(FrameManager.historyWindow);
                break;
            case STASH_SORT_WINDOW:
                toggleVisibility(FrameManager.stashSortingWindow);
                break;
            case CHAT_SCANNER:
                toggleVisibility(FrameManager.chatScannerWindow);
                break;
        }
    }

    private void toggleVisibility(Window w) {
        w.setVisible(!w.isVisible());
    }

}
