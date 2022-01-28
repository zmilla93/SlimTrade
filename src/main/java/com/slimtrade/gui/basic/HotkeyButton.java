package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.data.KeystrokeData;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;

public class HotkeyButton extends JButton {

    private KeystrokeData data;

    public HotkeyButton() {
        super("Unset");
        HotkeyButton self = this;
        addActionListener(e -> {
            setText("Press Any Key");
            App.globalKeyboardListener.listenForHotkey(self);
        });
    }

    public void updateHotkey(KeystrokeData data) {
        // Update data
        if (data != null) {
            this.data = data;
        }
        // Clear local data if key is escape
        if (data != null && data.keyCode == NativeKeyEvent.VC_ESCAPE) {
            this.data = null;
        }
        // Update button text
        if (this.data != null) {
            this.setText(this.data.toString());
        } else {
            this.setText("Unset");
        }
    }

    public KeystrokeData getData() {
        return data;
    }

}
