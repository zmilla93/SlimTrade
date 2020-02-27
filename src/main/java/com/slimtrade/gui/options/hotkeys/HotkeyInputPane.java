package com.slimtrade.gui.options.hotkeys;

import com.slimtrade.App;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.observing.HotkeyListener;
import com.slimtrade.gui.buttons.BasicButton;
import org.jnativehook.keyboard.NativeKeyEvent;

public class HotkeyInputPane extends BasicButton implements HotkeyListener {

    private HotkeyData hotkeyData = null;
    private boolean fetchingData;

    public HotkeyInputPane() {
        super("Unset");
        this.setFocusable(false);
        this.addActionListener(e -> {
            if (fetchingData) {
                return;
            }
            fetchingData = true;
            this.setText("Press Any Key");
            App.globalKeyboard.listenForHotkey(this);
        });
    }

    @Override
    public void updateHotkey(HotkeyData hotkeyData) {
        // Update local data
        if (hotkeyData != null) {
            this.hotkeyData = hotkeyData;
        }
        // Clear local data if key is escape
        if (hotkeyData != null && hotkeyData.keyCode == NativeKeyEvent.VC_ESCAPE) {
            this.hotkeyData = null;
        }
        // Update button text
        if (this.hotkeyData != null) {
            this.setText(this.hotkeyData.toString());
        } else {
            this.setText("Unset");
        }
        fetchingData = false;
    }

    public HotkeyData getHotkeyData() {
        return this.hotkeyData;
    }
}
