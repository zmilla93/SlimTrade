package com.slimtrade.core.hotkeys;

import org.jnativehook.keyboard.NativeKeyEvent;

public class HotkeyData {

    public final int keyCode;
    public final int modifiers;

    public HotkeyData(int keyCode, int modifiers) {
        this.keyCode = keyCode;
        this.modifiers = modifiers;
    }

    @Override
    public String toString() {
        if (modifiers > 0) {
            return NativeKeyEvent.getModifiersText(modifiers) + "+" + NativeKeyEvent.getKeyText(keyCode);
        }
        return NativeKeyEvent.getKeyText(keyCode);
    }

}
