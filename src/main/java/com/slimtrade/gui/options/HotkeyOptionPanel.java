package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.saving.ISavable;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.gui.basic.HotkeyButton;

import javax.swing.*;
import java.awt.*;

public class HotkeyOptionPanel extends AbstractOptionPanel implements ISavable {

    // SlimTrade Hotkeys
    private final HotkeyButton options = new HotkeyButton();
    private final HotkeyButton history = new HotkeyButton();
    private final HotkeyButton chatScanner = new HotkeyButton();
    private final HotkeyButton closeOldestTrade = new HotkeyButton();

    // POE Hotkeys
    private final HotkeyButton delve = new HotkeyButton();
    private final HotkeyButton doNotDisturb = new HotkeyButton();
    private final HotkeyButton exitToMenu = new HotkeyButton();
    private final HotkeyButton guildHideout = new HotkeyButton();
    private final HotkeyButton hideout = new HotkeyButton();
    private final HotkeyButton leaveParty = new HotkeyButton();
    private final HotkeyButton menagerie = new HotkeyButton();
    private final HotkeyButton metamorph = new HotkeyButton();
    private final HotkeyButton remainingMonsters = new HotkeyButton();

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

        App.saveManager.registerSavable(this);
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
        // SlimTrade
        App.saveManager.settingsSaveFile.optionsHotkey = options.getData();
        App.saveManager.settingsSaveFile.historyHotkey = history.getData();
        App.saveManager.settingsSaveFile.chatScannerHotkey = chatScanner.getData();
        App.saveManager.settingsSaveFile.closeTradeHotkey = closeOldestTrade.getData();
        // POE
        App.saveManager.settingsSaveFile.delveHotkey = delve.getData();
        App.saveManager.settingsSaveFile.doNotDisturbHotkey = doNotDisturb.getData();
        App.saveManager.settingsSaveFile.exitToMenuHotkey = exitToMenu.getData();
        App.saveManager.settingsSaveFile.guildHideoutHotkey = guildHideout.getData();
        App.saveManager.settingsSaveFile.hideoutHotkey = hideout.getData();
        App.saveManager.settingsSaveFile.leavePartyHotkey = leaveParty.getData();
        App.saveManager.settingsSaveFile.menagerieHotkey = menagerie.getData();
        App.saveManager.settingsSaveFile.metamorphHotkey = metamorph.getData();
        App.saveManager.settingsSaveFile.remainingMonstersHotkey = remainingMonsters.getData();
    }

    @Override
    public void load() {
        // SlimTrade
        options.setData(App.saveManager.settingsSaveFile.optionsHotkey);
        history.setData(App.saveManager.settingsSaveFile.historyHotkey);
        chatScanner.setData(App.saveManager.settingsSaveFile.chatScannerHotkey);
        closeOldestTrade.setData(App.saveManager.settingsSaveFile.closeTradeHotkey);
        // POE
        delve.setData(App.saveManager.settingsSaveFile.delveHotkey);
        doNotDisturb.setData(App.saveManager.settingsSaveFile.doNotDisturbHotkey);
        exitToMenu.setData(App.saveManager.settingsSaveFile.exitToMenuHotkey);
        guildHideout.setData(App.saveManager.settingsSaveFile.guildHideoutHotkey);
        hideout.setData(App.saveManager.settingsSaveFile.hideoutHotkey);
        leaveParty.setData(App.saveManager.settingsSaveFile.leavePartyHotkey);
        menagerie.setData(App.saveManager.settingsSaveFile.menagerieHotkey);
        metamorph.setData(App.saveManager.settingsSaveFile.metamorphHotkey);
        remainingMonsters.setData(App.saveManager.settingsSaveFile.remainingMonstersHotkey);
    }
}
