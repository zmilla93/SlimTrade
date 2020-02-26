package com.slimtrade.core.observing;

import com.slimtrade.App;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.gui.FrameManager;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;

public class GlobalKeyboardListener implements NativeKeyListener {

    private HotkeyListener hotkeyListener = null;

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        // Useful Print stuff
//        System.out.println("Key Pressed!");
//        System.out.println("\t" + NativeKeyEvent.getKeyText(e.getKeyCode()));
//        System.out.println("\t" + NativeKeyEvent.getModifiersText(e.getModifiers()));

        // Ignore modifier keys on their own
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL
                || e.getKeyCode() == NativeKeyEvent.VC_ALT
                || e.getKeyCode() == NativeKeyEvent.VC_SHIFT
                || e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK
                || e.getKeyCode() == NativeKeyEvent.VC_UNDEFINED) {
            return;
        }

        // If a UI element is waiting for hotkey data, return the data and skip the hotkey logic.
        if (hotkeyListener != null) {
            HotkeyData data = new HotkeyData(e.getKeyCode(), e.getModifiers());
            hotkeyListener.updateHotkey(data);
            hotkeyListener = null;
            return;
        }

        // Betrayal
        if (checkKey(e, App.saveManager.saveFile.betrayalHotkey)) {
            FrameManager.betrayalWindow.toggleShow();
            FrameManager.betrayalWindow.refreshVisibility();
        }

        //
        if (checkKey(e, App.saveManager.saveFile.quickPasteHotkey)) {
            PoeInterface.attemptQuickPaste();
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Unused
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


}