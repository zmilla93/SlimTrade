package com.slimtrade.core.observing;

import com.slimtrade.App;
import com.slimtrade.core.managers.HotkeyManager;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.gui.FrameManager;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyboardListener implements NativeKeyListener {

    private HotkeyListener hotkeyListener = null;
    // Masks for the 5 mouse buttons
    private static final int[] removeMasks = {256, 512, 1024, 2048, 4096};

    private static volatile boolean ctrlPressed;

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        // Remove mouse button modifiers
        e.setModifiers(cleanModifiers(e.getModifiers()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlPressed = true;
        }
        // Ignore modifier keys on their own
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL
                || e.getKeyCode() == NativeKeyEvent.VC_ALT
                || e.getKeyCode() == NativeKeyEvent.VC_SHIFT
                || e.getKeyCode() == NativeKeyEvent.VC_NUM_LOCK
                || e.getKeyCode() == NativeKeyEvent.VC_SCROLL_LOCK
                || e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK
                || e.getKeyCode() == NativeKeyEvent.VC_META
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

        // Remaining
        if (checkKey(e, App.saveManager.saveFile.remainingHotkey)) {
            if (App.globalMouse.isGameFocused()) {
                PoeInterface.paste("/remaining");
            }
        }

        // Hideout
        if (checkKey(e, App.saveManager.saveFile.hideoutHotkey)) {
            if (App.globalMouse.isGameFocused()) {
                PoeInterface.paste("/hideout");
            }
        }

        // Leave Party
        if (checkKey(e, App.saveManager.saveFile.leavePartyHotkey)) {
            if (App.globalMouse.isGameFocused() && App.saveManager.saveFile.characterName != null && !App.saveManager.saveFile.characterName.equals("")) {
                PoeInterface.paste("/kick " + App.saveManager.saveFile.characterName);
            }
        }

        // Betrayal
        if (checkKey(e, App.saveManager.saveFile.betrayalHotkey)) {
            if (App.globalMouse.isGameFocused()) {
                FrameManager.betrayalWindow.toggleShow();
                FrameManager.betrayalWindow.refreshVisibility();
            }
        }

        // Quick Paste Trade
        if (App.saveManager.saveFile.quickPasteSetting == QuickPasteSetting.HOTKEY && checkKey(e, App.saveManager.saveFile.quickPasteHotkey)) {
            PoeInterface.attemptQuickPaste();
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlPressed = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Unused
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
        for (int mask : removeMasks) {
            if ((mods & mask) > 0) {
                mods -= mask;
            }
        }
        // Ctrl fix
        if ((mods & 34) > 0 && !ctrlPressed) {
            mods -= 2;
        }
        return mods;
    }

}