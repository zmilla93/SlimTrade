package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.hotkeys.HotkeyData;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.awt.*;

public class HotkeyButton extends JButton {

    private HotkeyData data;
    private static final String UNSET_TEXT = "Unset Hotkey";

    public HotkeyButton() {
        super(UNSET_TEXT);
        HotkeyButton self = this;
        setLayout(new FlowLayout());
        addActionListener(e -> {
            setText("Press Any Key");
            App.globalKeyboardListener.listenForHotkey(self);
        });
    }

    public void setData(HotkeyData data) {
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
            this.setText(UNSET_TEXT);
        }
    }

    public HotkeyData getData() {
        return data;
    }

}
