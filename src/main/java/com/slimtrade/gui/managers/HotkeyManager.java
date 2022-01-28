package com.slimtrade.gui.managers;

import com.slimtrade.gui.basic.HotkeyButton;
import org.jnativehook.keyboard.NativeKeyEvent;

public class HotkeyManager {

    private static HotkeyButton activeHotkeyButton;

    public static void setActiveHotkeyButton(HotkeyButton button) {
        if(activeHotkeyButton != null)
            activeHotkeyButton.updateHotkey(null);
        activeHotkeyButton = button;
    }

    public static void clearActiveHotkeyButton(){
        activeHotkeyButton = null;
    }

    public static void processHotkey(NativeKeyEvent e) {

    }

}
