package com.slimtrade.gui.pinning;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.searching.StashSearchWindowMode;

import java.util.ArrayList;
import java.util.HashMap;

public class PinManager {

    private static final HashMap<String, IPinnable> appWindows = new HashMap<>();
    public static IPinnable searchWindow;

    public static void addAppWindow(IPinnable pinnable) {
        appWindows.put(pinnable.getPinTitle(), pinnable);
    }

    public static void removePinnable(IPinnable pinnable) {
        if (pinnable == searchWindow) searchWindow = null;
        appWindows.remove(pinnable.getPinTitle());
    }

    public static void applyAllPins() {
        applyAppPins();
        applySearchWindowPins();
        // TODO : Cheat Sheets
    }

    public static void applyAppPins() {
        for (PinData data : SaveManager.pinSaveFile.data.appWindows) {
            IPinnable pinnable = appWindows.get(data.title);
            if (pinnable != null) pinnable.applyPin(data.rect);
        }
    }

    public static void applySearchWindowPins() {
        if (SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSearchWindowMode.COMBINED) {
            PinData data = SaveManager.pinSaveFile.data.searchWindow;
            IPinnable window = FrameManager.searchWindow;
            if (data != null && window != null) FrameManager.searchWindow.applyPin(data.rect);
        } else {
            for (PinData data : SaveManager.pinSaveFile.data.searchWindows) {
                IPinnable window = FrameManager.searchWindows.get(data.title);
                if (window != null) window.applyPin(data.rect);
            }
        }
    }

    public static void save() {
        ArrayList<PinData> appPins = new ArrayList<>();
        // App Windows
        for (IPinnable pinnable : appWindows.values()) {
            if (pinnable.isPinned()) appPins.add(new PinData(pinnable.getPinTitle(), pinnable.getPinRectangle()));
        }
        SaveManager.pinSaveFile.data.appWindows = appPins;
        // Search Windows
        ArrayList<PinData> searchPins = new ArrayList<>();
        if (SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSearchWindowMode.COMBINED) {
            IPinnable window = FrameManager.searchWindow;
            if (window.isPinned())
                SaveManager.pinSaveFile.data.searchWindow = new PinData(window.getPinTitle(), window.getPinRectangle());
            else SaveManager.pinSaveFile.data.searchWindow = null;
        } else if (SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSearchWindowMode.SEPARATE) {
            for (IPinnable window : FrameManager.searchWindows.values()) {
                if (window.isPinned()) searchPins.add(new PinData(window.getPinTitle(), window.getPinRectangle()));
            }
            SaveManager.pinSaveFile.data.searchWindows = searchPins;
        }
    }

}
