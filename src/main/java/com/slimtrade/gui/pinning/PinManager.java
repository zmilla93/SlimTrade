package com.slimtrade.gui.pinning;

import com.slimtrade.core.managers.SaveManager;

import java.util.ArrayList;
import java.util.HashMap;

public class PinManager {

    private static HashMap<String, IPinnable> pinnables = new HashMap<>();

    public static void addPinnable(IPinnable pinnable) {
        pinnables.put(pinnable.getPinTitle(), pinnable);
    }

    public static void removePinnable(IPinnable pinnable) {
        pinnables.remove(pinnable.getPinTitle());
    }

    public static void applyPins() {
        for (PinData data : SaveManager.pinSaveFile.data.pinnables) {
            IPinnable pinnable = pinnables.get(data.title);
            if (pinnable != null) {
                pinnable.applyPin(data.rectangle);
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

}
