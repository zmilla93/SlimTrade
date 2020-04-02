package com.slimtrade.core.observing.improved;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.ISaveable;

import java.awt.*;
import java.util.ArrayList;

public class EventManager {

    private ArrayList<ISaveable> saveListenerList = new ArrayList<>();

    public void addSaveListener(ISaveable listener) {
        saveListenerList.add(listener);
    }

    // Probably won't be used as ISaveable objects shouldn't be discarded before the program ends
    public void removeSaveListener(ISaveable listener) {
        saveListenerList.add(listener);
    }

    public void updateAllColors(ColorTheme theme) {
        ColorManager.setTheme(theme);
        recursiveColor(FrameManager.optionsWindow);
        recursiveColor(FrameManager.chatScannerWindow);
        recursiveColor(FrameManager.historyWindow);
        recursiveColor(FrameManager.stashOverlayWindow);
        recursiveColor(FrameManager.menubar);
        recursiveColor(FrameManager.menubarToggle);
        FrameManager.overlayManager.updateColor();
        FrameManager.messageManager.updateMessageColors();

        // Temp fix for menubar not repainting when switching between stormy and vaal themes. This can be removed
        // once menubar not being hidden is fixed.
        FrameManager.menubar.repaint();
    }

    public void recursiveColor(Object o) {
        if(o instanceof IColorable) {
            ((IColorable) o).updateColor();
        }
        if(o instanceof Container) {
            for(Component c : ((Container) o).getComponents()) {
                recursiveColor(c);
            }
        }
    }

}
