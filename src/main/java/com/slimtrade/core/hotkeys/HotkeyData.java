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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HotkeyData) {
            HotkeyData otherData = (HotkeyData) obj;
            if (keyCode != otherData.keyCode) return false;
            if (modifiers != otherData.modifiers) return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + keyCode;
        result = 31 * result + modifiers;
        return result;
    }

}
