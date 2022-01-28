package com.slimtrade.gui.options;

import com.slimtrade.core.saving.ISavable;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.basic.HotkeyButton;

import javax.swing.*;
import java.awt.*;

public class HotkeyOptionPanel extends AbstractOptionPanel implements ISavable {

    // SlimTrade Hotkeys
    private HotkeyButton options = new HotkeyButton();
    private HotkeyButton history = new HotkeyButton();
    private HotkeyButton chatScanner = new HotkeyButton();
    private HotkeyButton closeOldestTrade = new HotkeyButton();

    // POE Hotkeys
    private HotkeyButton delve = new HotkeyButton();
    private HotkeyButton doNotDisturb = new HotkeyButton();
    private HotkeyButton exitToMenu = new HotkeyButton();
    private HotkeyButton guildHideout = new HotkeyButton();
    private HotkeyButton hideout = new HotkeyButton();
    private HotkeyButton leaveParty = new HotkeyButton();
    private HotkeyButton menagerie = new HotkeyButton();
    private HotkeyButton metamorph = new HotkeyButton();
    private HotkeyButton remainingMonsters = new HotkeyButton();

    JPanel appHotkeyPanel = new JPanel(new GridBagLayout());
    JPanel poeHotkeyPanel = new JPanel(new GridBagLayout());

    private final GridBagConstraints gc = new GridBagConstraints();

    public HotkeyOptionPanel() {

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;

        addHotkey(appHotkeyPanel, "Options", options);
        addHotkey(appHotkeyPanel, "History", history);
        addHotkey(appHotkeyPanel, "Chat Scanner", chatScanner);
        addHotkey(appHotkeyPanel, "Close Oldest Trade", closeOldestTrade);
        addHotkey(poeHotkeyPanel, "Delve", delve);
        addHotkey(poeHotkeyPanel, "Do Not Disturb", doNotDisturb);
        addHotkey(poeHotkeyPanel, "Exit to Menu", exitToMenu);
        addHotkey(poeHotkeyPanel, "Guild Hideout", guildHideout);
        addHotkey(poeHotkeyPanel, "Hideout", hideout);
        addHotkey(poeHotkeyPanel, "Leave Party", leaveParty);
        addHotkey(poeHotkeyPanel, "Menagerie", menagerie);
        addHotkey(poeHotkeyPanel, "Metamorph", metamorph);
        addHotkey(poeHotkeyPanel, "Remaining Monsters", remainingMonsters);

        addHeader("Hotkey Info");
        addPanel(new JLabel("Use ctrl, alt, and shift as modifiers."));
        addPanel(new JLabel("Use escape to clear a hotkey."));
        addVerticalStrut();
        addHeader("SlimTrade Hotkeys");
        addPanel(appHotkeyPanel);
        addPanel(Box.createVerticalStrut(GUIReferences.INSET));
        addHeader("Path of Exile Hotkeys");
        addPanel(poeHotkeyPanel);
    }

    private void addHotkey(JPanel panel, String name, HotkeyButton hotkey) {
        panel.add(new JLabel(name), gc);
        gc.gridx++;
        gc.insets.left = 20;
        panel.add(hotkey, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;
    }

    @Override
    public void save() {
//        options.getData()
    }

    @Override
    public void load() {

    }
}
