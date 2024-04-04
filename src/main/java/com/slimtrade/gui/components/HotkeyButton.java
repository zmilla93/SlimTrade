package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.hotkeys.HotkeyData;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;

/**
 * A button for settings hotkeys.
 */
public class HotkeyButton extends JButton {

    private HotkeyData data;
    private static final String UNSET_TEXT = "Hotkey Not Set";

    public HotkeyButton() {
        super(UNSET_TEXT);
        addActionListener(e -> {
            setText("Press Any Key");
            App.globalKeyboardListener.listenForHotkey(HotkeyButton.this);
        });
    }

    public void updateText() {
        if (data != null) setText(data.toString());
        else setText(UNSET_TEXT);
    }

    public HotkeyData getData() {
        return data;
    }

    public void setData(HotkeyData data) {
        this.data = data;
        if (data != null && data.keyCode == NativeKeyEvent.VC_ESCAPE) this.data = null;
        updateText();
    }

}
