package com.slimtrade.gui.pinning;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.searching.StashSortingGroupPanel;
import com.slimtrade.gui.options.searching.StashSortingWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PinManager {

    private static final HashMap<String, IPinnable> pinnables = new HashMap<>();
    private static final HashMap<String, IPinnable> cheatSheetPinnables = new HashMap<>();
    private static final HashMap<String, Rectangle> searchWindowMap = new HashMap<>();

    public static void addPinnable(IPinnable pinnable) {
        pinnables.put(pinnable.getPinTitle(), pinnable);
    }

    public static void addSearchPinnable(IPinnable pinnable) {
        cheatSheetPinnables.put(pinnable.getPinTitle(), pinnable);
    }

    public static void removePinnable(IPinnable pinnable) {
        cheatSheetPinnables.remove(pinnable.getPinTitle());
        pinnables.remove(pinnable.getPinTitle());
    }

    public static void applyPins() {
        for (PinData data : SaveManager.pinSaveFile.data.pinnables) {
            IPinnable pinnable = pinnables.get(data.title);
            if (pinnable != null) {
                pinnable.applyPin(data.rect);
            }
        }
    }

    public static void save() {
        ArrayList<PinData> pins = new ArrayList<>();
        for (IPinnable pinnable : pinnables.values()) {
            if (pinnable.isPinned()) {
                pins.add(new PinData(pinnable.getPinTitle(), pinnable.getPinRectangle()));
            }
        }
        SaveManager.pinSaveFile.data.pinnables = pins;
    }

    public static void storeSearchWindowPins() {
        searchWindowMap.clear();
        for (Map.Entry<String, StashSortingWindow> entry : FrameManager.sortingWindows.entrySet()) {
            StashSortingWindow window = entry.getValue();
            pinnables.remove(window.getPinTitle());
            if (!window.isPinned()) continue;
            searchWindowMap.put(window.getCleanTitle(), window.getPinRectangle());
        }
    }

    public static void restoreSearchWindowPins(ArrayList<StashSortingGroupPanel> panels) {
        HashMap<String, Rectangle> newSearchMap = new HashMap<>();
        for (StashSortingGroupPanel panel : panels) {
            String oldName = panel.getSavedGroupName();
            Rectangle rect = searchWindowMap.get(oldName);
            if (rect != null) newSearchMap.put(panel.getGroupName(), rect);
            panel.updateSavedGroupName();
        }
        for (StashSortingWindow window : FrameManager.sortingWindows.values()) {
            Rectangle rect = newSearchMap.get(window.getCleanTitle());
            if (rect == null) continue;
            window.applyPin(rect);
        }
        searchWindowMap.clear();
        save();
        SaveManager.pinSaveFile.saveToDisk();
    }

}
