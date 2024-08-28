package com.slimtrade.gui.options;

import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;

public class NinjaOptionPanel extends AbstractOptionPanel implements ISavable {

    private final HotkeyButton windowHotkeyButton = new HotkeyButton();

    public NinjaOptionPanel() {
        addHeader("Info");
        addComponent(new JLabel("Displays an overlay on the stash with pricing info from poe.ninja."));
        addComponent(new ComponentPair(new JLabel("Window Hotkey"), windowHotkeyButton));
        addVerticalStrut();

        addHeader("Customize");
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }

}
