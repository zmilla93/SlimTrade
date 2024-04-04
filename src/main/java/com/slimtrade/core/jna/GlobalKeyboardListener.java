package com.slimtrade.core.jna;

import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.gui.managers.HotkeyManager;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * A keyboard listener used for global hotkeys.
 */
public class GlobalKeyboardListener implements NativeKeyListener {

    private HotkeyButton hotkeyBeingModified = null;
    private static volatile boolean ctrlPressed;
    private static volatile boolean altPressed;
    private static volatile boolean shiftPressed;
    // Masks for the 5 mouse buttons
    private static final int[] mouseMasks = {256, 512, 1024, 2048, 4096};

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Remove mouse button modifiers
        e.setModifiers(cleanModifiers(e.getModifiers()));

        // Track ctrl, alt, and shift key states
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            ctrlPressed = true;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ALT_L || e.getKeyCode() == NativeKeyEvent.VC_ALT_R) {
            altPressed = true;
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
                || e.getKeyCode() == NativeKeyEvent.META_L_MASK
                || e.getKeyCode() == NativeKeyEvent.VC_UNDEFINED
                || NativeKeyEvent.getKeyText(e.getKeyCode()).contains("Unknown")) {
            return;
        }

        // If a UI element is waiting for hotkey data, return the data
        if (hotkeyBeingModified != null) {
            HotkeyData data = new HotkeyData(e.getKeyCode(), e.getModifiers());
            hotkeyBeingModified.setData(data);
            hotkeyBeingModified = null;
        }
        // Otherwise check if the given keystroke is a hotkey
        else HotkeyManager.processHotkey(e);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Track ctrl, alt, and shift key states
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            ctrlPressed = false;
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ALT_L || e.getKeyCode() == NativeKeyEvent.VC_ALT_R) {
            altPressed = false;
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

    public boolean isAltPressed() {
        return altPressed;
    }

    public boolean isShiftPressed() {
        return shiftPressed;
    }

    public void listenForHotkey(HotkeyButton hotkeyListener) {
        // If the hotkey button being modified is clicked a 2nd time, cancel the process
        if (this.hotkeyBeingModified == hotkeyListener) {
            this.hotkeyBeingModified.updateText();
            this.hotkeyBeingModified = null;
            return;
        }
        // Update the hotkey that is being modified
        if (this.hotkeyBeingModified != null) this.hotkeyBeingModified.updateText();
        this.hotkeyBeingModified = hotkeyListener;
    }

    public void clearHotkeyListener() {
        this.hotkeyBeingModified.updateText();
        this.hotkeyBeingModified = null;
    }

    // Removes the 5 mouse buttons as modifiers for key events
    public static int cleanModifiers(int mods) {
        for (int mask : mouseMasks) {
            if ((mods & mask) > 0) {
                mods -= mask;
            }
        }
        return mods;
    }

}
