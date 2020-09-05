package com.slimtrade.core.observing;

import com.slimtrade.core.managers.HotkeyManager;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyboardListener implements NativeKeyListener {

    private HotkeyListener hotkeyListener = null;

    private static volatile boolean ctrlPressed;
    private static volatile boolean atlPressed;
    private static volatile boolean shiftPressed;
    // Masks for the 5 mouse buttons
    private static final int[] mouseMasks = {256, 512, 1024, 2048, 4096};

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Remove mouse button modifiers
        e.setModifiers(cleanModifiers(e.getModifiers()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            ctrlPressed = true;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ALT_L || e.getKeyCode() == NativeKeyEvent.VC_ALT_R) {
            atlPressed = true;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT_L || e.getKeyCode() == NativeKeyEvent.VC_SHIFT_L) {
            shiftPressed = true;
        }
        // Ignore modifier keys on their own
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R
                || e.getKeyCode() == NativeKeyEvent.VC_ALT_L || e.getKeyCode() == NativeKeyEvent.VC_ALT_R
                || e.getKeyCode() == NativeKeyEvent.VC_SHIFT_L || e.getKeyCode() == NativeKeyEvent.VC_SHIFT_R
                || e.getKeyCode() == NativeKeyEvent.VC_NUM_LOCK
                || e.getKeyCode() == NativeKeyEvent.VC_SCROLL_LOCK
                || e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK
                || e.getKeyCode() == NativeKeyEvent.META_L_MASK ||  e.getKeyCode() == NativeKeyEvent.META_R_MASK
                || e.getKeyCode() == NativeKeyEvent.VC_UNDEFINED
                || NativeKeyEvent.getKeyText(e.getKeyCode()).contains("Unknown")) {
            return;
        }

        // Print stuff
//        System.out.println("Key Pressed! : " + NativeKeyEvent.getKeyText(e.getKeyCode()) + " | " + NativeKeyEvent.getModifiersText(e.getModifiers()));

        // If a UI element is waiting for hotkey data, return the data and skip the hotkey logic.
        if (hotkeyListener != null) {
            HotkeyData data = new HotkeyData(e.getKeyCode(), e.getModifiers());
            hotkeyListener.updateHotkey(data);
            hotkeyListener = null;
            return;
        }

        // Hotkeys
        HotkeyManager.processHotkey(e);

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            ctrlPressed = false;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ALT_L || e.getKeyCode() == NativeKeyEvent.VC_ALT_R) {
            atlPressed = false;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT_L || e.getKeyCode() == NativeKeyEvent.VC_SHIFT_R) {
            shiftPressed = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Unused
    }

    public boolean isCtrlPressed() {
        return ctrlPressed;
    }

    public boolean isAtlPressed() {
        return atlPressed;
    }

    public boolean isShiftPressed() {
        return shiftPressed;
    }

    public void listenForHotkey(HotkeyListener hotkeyListener) {
        if (this.hotkeyListener != null) {
            this.hotkeyListener.updateHotkey(null);
        }
        this.hotkeyListener = hotkeyListener;
    }

    public void clearHotkeyListener() {
        this.hotkeyListener = null;
    }

    private boolean checkKey(NativeKeyEvent e, HotkeyData data) {
        if (data == null) {
            return false;
        }
        if (e.getKeyCode() == data.keyCode && e.getModifiers() == data.modifiers) {
            return true;
        }
        return false;
    }

    // Removes the 5 mouse buttons as modifiers for key events
    public static int cleanModifiers(int mods) {
        for (int mask : mouseMasks) {
            if ((mods & mask) > 0) {
                mods -= mask;
            }
        }
        // Ctrl fix
//        if ((mods & 34) > 0 && !ctrlPressed) {
//            mods -= 2;
//        }
        return mods;
    }

}