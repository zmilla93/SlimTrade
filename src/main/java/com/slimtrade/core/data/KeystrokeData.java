package com.slimtrade.core.data;

import org.jnativehook.keyboard.NativeKeyEvent;

public class KeystrokeData {

    public final int keyCode;
    public final int modifiers;

    public KeystrokeData(int keyCode, int modifiers) {
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
