package com.slimtrade.gui.options;

import com.slimtrade.core.enums.PathOfExileLeague;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.ButtonWrapper;
import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;

public class NinjaOptionPanel extends AbstractOptionPanel implements ISavable {

    private final HotkeyButton windowHotkeyButton = new HotkeyButton();
    private final JComboBox<PathOfExileLeague> leagueCombo = new JComboBox<>();

    public NinjaOptionPanel() {
        for (PathOfExileLeague league : PathOfExileLeague.values()) leagueCombo.addItem(league);

        addHeader("Info");
        addComponent(new JLabel("Displays an overlay on the stash with pricing info from poe.ninja."));
        addComponent(new ComponentPair(new JLabel("Window Hotkey"), new ButtonWrapper(windowHotkeyButton)));
        addVerticalStrut();

        addHeader("Customize");
        addComponent(new ComponentPair(new JLabel("Path of Exile League"), leagueCombo));
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.pathOfExileLeague = (PathOfExileLeague) leagueCombo.getSelectedItem();
    }

    @Override
    public void load() {
        leagueCombo.setSelectedItem(SaveManager.settingsSaveFile.data.pathOfExileLeague);
    }

}
