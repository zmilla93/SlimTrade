package github.zmilla93.gui.components;

import github.zmilla93.App;
import github.zmilla93.core.hotkeys.HotkeyData;
import github.zmilla93.gui.listening.IHotkeyChangeListener;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * A button for settings hotkeys.
 */
public class HotkeyButton extends JButton {

    private HotkeyData data;
    private static final String UNSET_TEXT = "Hotkey Not Set";
    private static final String PRESS_ANY_KEY_TEXT = "Press Any Key";
    private final ArrayList<IHotkeyChangeListener> hotkeyChangeListeners = new ArrayList<>();

    public HotkeyButton() {
        super(UNSET_TEXT);
        addActionListener(e -> {
            setText(PRESS_ANY_KEY_TEXT);
            App.globalKeyboardListener.listenForHotkey(HotkeyButton.this);
        });
    }

    public void updateHotkey() {
        if (data != null) setText(data.toString());
        else setText(UNSET_TEXT);
        for (IHotkeyChangeListener listener : hotkeyChangeListeners) listener.onHotkeyChange(data);
    }

    public HotkeyData getData() {
        return data;
    }

    public void setData(HotkeyData data) {
        this.data = data;
        if (data != null && data.keyCode == NativeKeyEvent.VC_ESCAPE) this.data = null;
        updateHotkey();
    }

    public void addHotkeyChangeListener(IHotkeyChangeListener listener) {
        hotkeyChangeListeners.add(listener);
    }

    // Ignore default event processing to prevent enter and space from double triggering button
    @Override
    protected void processKeyEvent(KeyEvent e) {
        e.consume();
    }

}
