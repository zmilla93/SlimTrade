package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.gui.components.ButtonWrapper;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class HotkeyOptionPanel extends AbstractOptionPanel implements ISavable {

    // SlimTrade Hotkeys
    private final HotkeyButton options = new HotkeyButton();
    private final HotkeyButton history = new HotkeyButton();
    private final HotkeyButton chatScanner = new HotkeyButton();
    private final HotkeyButton closeOldestTrade = new HotkeyButton();
    private final HotkeyButton previousMessageTab = new HotkeyButton();
    private final HotkeyButton nexTabMessage = new HotkeyButton();

    private final Component[] previousMessageTabComponents;
    private final Component[] nextMessageTabComponents;

    // POE Hotkeys
    private final HotkeyButton delve = new HotkeyButton();
    private final HotkeyButton doNotDisturb = new HotkeyButton();
    private final HotkeyButton exitToMenu = new HotkeyButton();
    private final HotkeyButton guildHideout = new HotkeyButton();
    private final HotkeyButton hideout = new HotkeyButton();
    private final HotkeyButton leaveParty = new HotkeyButton();
    private final HotkeyButton menagerie = new HotkeyButton();
    private final HotkeyButton necropolis = new HotkeyButton();
    private final HotkeyButton remainingMonsters = new HotkeyButton();

    public static final int SPACING_INSET = 20;
    private final GridBagConstraints gc = new GridBagConstraints();

    public HotkeyOptionPanel() {

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;

        // App Hotkeys
        JPanel appHotkeyPanel = new JPanel(new GridBagLayout());
        addHotkey(appHotkeyPanel, "Options", options);
        addHotkey(appHotkeyPanel, "History", history);
        addHotkey(appHotkeyPanel, "Chat Scanner", chatScanner);
        addHotkey(appHotkeyPanel, "Close Oldest Trade", closeOldestTrade);
        previousMessageTabComponents = addHotkey(appHotkeyPanel, "Previous Message Tab", previousMessageTab);
        nextMessageTabComponents = addHotkey(appHotkeyPanel, "Next Message Tab", nexTabMessage);

        // POE Hotkeys
        JPanel poeHotkeyPanel = new JPanel(new GridBagLayout());
        addHotkey(poeHotkeyPanel, "Delve", delve);
        addHotkey(poeHotkeyPanel, "Do Not Disturb", doNotDisturb);
        addHotkey(poeHotkeyPanel, "Exit to Menu", exitToMenu);
        addHotkey(poeHotkeyPanel, "Guild Hideout", guildHideout);
        addHotkey(poeHotkeyPanel, "Hideout", hideout);
        addHotkey(poeHotkeyPanel, "Leave Party", leaveParty);
        addHotkey(poeHotkeyPanel, "Menagerie", menagerie);
        addHotkey(poeHotkeyPanel, "Necropolis", necropolis);
        addHotkey(poeHotkeyPanel, "Remaining Monsters", remainingMonsters);

        addHeader("Hotkey Info");
        addComponent(new JLabel("Use ctrl, alt, and shift as modifiers."));
        addComponent(new JLabel("Use escape to clear a hotkey."));
        addVerticalStrut();
        addHeader("SlimTrade Hotkeys");
        addComponent(appHotkeyPanel);
        addComponent(Box.createVerticalStrut(GUIReferences.INSET));
        addHeader("Path of Exile Hotkeys");
        addComponent(poeHotkeyPanel);
//        App.saveManager.registerSavable(this);
    }

    private Component[] addHotkey(JPanel panel, String name, HotkeyButton hotkey) {
        Component[] components = new Component[2];
        JLabel label = new JLabel(name);
        ButtonWrapper wrapper = new ButtonWrapper(hotkey);
        components[0] = label;
        components[1] = wrapper;

        panel.add(label, gc);
        gc.gridx++;
        gc.insets.left = SPACING_INSET;
        panel.add(wrapper, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;
        return components;
    }

    public void showHideChangeTabHotkeys(boolean visible) {
        for (Component comp : previousMessageTabComponents) {
            comp.setVisible(visible);
        }
        for (Component comp : nextMessageTabComponents) {
            comp.setVisible(visible);
        }
    }

    @Override
    public void save() {
        // SlimTrade
        SaveManager.settingsSaveFile.data.optionsHotkey = options.getData();
        SaveManager.settingsSaveFile.data.historyHotkey = history.getData();
        SaveManager.settingsSaveFile.data.chatScannerHotkey = chatScanner.getData();
        SaveManager.settingsSaveFile.data.closeTradeHotkey = closeOldestTrade.getData();
        SaveManager.settingsSaveFile.data.previousMessageTabHotkey = previousMessageTab.getData();
        SaveManager.settingsSaveFile.data.nextMessageTabHotkey = nexTabMessage.getData();
        // POE
        SaveManager.settingsSaveFile.data.delveHotkey = delve.getData();
        SaveManager.settingsSaveFile.data.doNotDisturbHotkey = doNotDisturb.getData();
        SaveManager.settingsSaveFile.data.exitToMenuHotkey = exitToMenu.getData();
        SaveManager.settingsSaveFile.data.guildHideoutHotkey = guildHideout.getData();
        SaveManager.settingsSaveFile.data.hideoutHotkey = hideout.getData();
        SaveManager.settingsSaveFile.data.leavePartyHotkey = leaveParty.getData();
        SaveManager.settingsSaveFile.data.menagerieHotkey = menagerie.getData();
        SaveManager.settingsSaveFile.data.necropolisHotkey = necropolis.getData();
        SaveManager.settingsSaveFile.data.remainingMonstersHotkey = remainingMonsters.getData();
    }

    @Override
    public void load() {
        // SlimTrade
        options.setData(SaveManager.settingsSaveFile.data.optionsHotkey);
        history.setData(SaveManager.settingsSaveFile.data.historyHotkey);
        chatScanner.setData(SaveManager.settingsSaveFile.data.chatScannerHotkey);
        closeOldestTrade.setData(SaveManager.settingsSaveFile.data.closeTradeHotkey);
        previousMessageTab.setData(SaveManager.settingsSaveFile.data.previousMessageTabHotkey);
        nexTabMessage.setData(SaveManager.settingsSaveFile.data.nextMessageTabHotkey);
        // POE
        delve.setData(SaveManager.settingsSaveFile.data.delveHotkey);
        doNotDisturb.setData(SaveManager.settingsSaveFile.data.doNotDisturbHotkey);
        exitToMenu.setData(SaveManager.settingsSaveFile.data.exitToMenuHotkey);
        guildHideout.setData(SaveManager.settingsSaveFile.data.guildHideoutHotkey);
        hideout.setData(SaveManager.settingsSaveFile.data.hideoutHotkey);
        leaveParty.setData(SaveManager.settingsSaveFile.data.leavePartyHotkey);
        menagerie.setData(SaveManager.settingsSaveFile.data.menagerieHotkey);
        necropolis.setData(SaveManager.settingsSaveFile.data.necropolisHotkey);
        remainingMonsters.setData(SaveManager.settingsSaveFile.data.remainingMonstersHotkey);
    }
}
