package com.slimtrade.core.hotkeys;

import com.slimtrade.gui.managers.FrameManager;

import java.awt.*;

public class AppHotkey implements IHotkeyAction {

    public enum AppWindow {OPTIONS, HISTORY, CHAT_SCANNER}

    private AppWindow window;

    // FIXME : Should just pass a reference to the window directly
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
        }
    }

    private void toggleVisibility(Window w) {
        w.setVisible(!w.isVisible());
    }

}
