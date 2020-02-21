package com.slimtrade.core.observing.improved;

import java.awt.*;
import java.util.ArrayList;

import com.slimtrade.enums.ColorTheme;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.ISaveable;

import javax.swing.*;

public class EventManager {

    private ArrayList<ISaveable> saveListenerList = new ArrayList<>();
    private ArrayList<IColorable> colorListenerList = new ArrayList<>();

//    public void addColorListener(IColorable listener) {
//        if (colorListenerList.contains(listener)) {
//            return;
//        }
////        int i = colorListenerList.size();
//        colorListenerList.add(listener);
////        System.out.print("IColor");
////        if (i == colorListenerList.size()) {
////            System.out.print("!");
////        }
////        System.out.println("++(" + colorListenerList.size() + "):" + listener);
//    }

//    public void removeColorListener(IColorable listener) {
////        int i = colorListenerList.size();
//        colorListenerList.remove(listener);
////        if (i == colorListenerList.size()) {
////            System.out.print("!");
////        }
////        System.out.println("--(" + colorListenerList.size() + "):" + listener);
//    }

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
