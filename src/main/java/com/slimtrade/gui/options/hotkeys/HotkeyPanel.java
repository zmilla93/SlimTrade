package com.slimtrade.gui.options.hotkeys;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.components.SectionHeader;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.general.LabelComponentPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class HotkeyPanel extends ContainerPanel implements ISaveable, IColorable {

    // Info
    JLabel info1 = new CustomLabel("Hotkeys can be given control, alt, and shift as modifiers.");
    JLabel info2 = new CustomLabel("Use escape to clear a hotkey.");

    // SlimTrade
    JLabel betrayalLabel = new CustomLabel("Betrayal Guide");
    JLabel chatScannerLabel = new CustomLabel("Chat Scanner");
    JLabel closeTradeLabel = new CustomLabel("Close Oldest Trade");
    JLabel historyLabel = new CustomLabel("History");
    JLabel optionsLabel = new CustomLabel("Options");

    HotkeyInputPane betrayalHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane chatScannerHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane closeTradeHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane historyHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane optionsHotkeyInput = new HotkeyInputPane();

    // POE
    JLabel delveLabel = new CustomLabel("Delve");
    JLabel dndLabel = new CustomLabel("Do Not Disturb");
    JLabel exitLabel = new CustomLabel("Exit to Menu");
    JLabel harvestLabel = new CustomLabel("Harvest");
    JLabel hideoutLabel = new CustomLabel("Hideout");
    JLabel leavePartyLabel = new CustomLabel("Leave Party");
    JLabel menagerieLabel = new CustomLabel("Menagerie");
    JLabel metamorphLabel = new CustomLabel("Metamorph");
    JLabel remainingLabel = new CustomLabel("Remaining Monsters");

    HotkeyInputPane delveHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane dndHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane exitHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane harvestHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane hideoutHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane leavePartyHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane menagerieHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane metamorphHotkeyInput = new HotkeyInputPane();
    HotkeyInputPane remainingHotkeyInput = new HotkeyInputPane();

    // Containers
    JPanel slimTradeInner = new JPanel(new GridBagLayout());
    JPanel slimTradeOuter = new JPanel(new GridBagLayout());
    JPanel poeInner = new JPanel(new GridBagLayout());
    JPanel poeOuter = new JPanel(new GridBagLayout());

    public HotkeyPanel() {
        poeInner.setOpaque(false);
        slimTradeInner.setOpaque(false);
        SectionHeader slimHeader = new SectionHeader("SlimTrade");
        SectionHeader poeHeader = new SectionHeader("Path of Exile");
        leavePartyLabel.setToolTipText("Character name must be set correctly in order to work.");

        // Combined Panel
        // SlimTrade
        LabelComponentPanel betrayalPanel = new LabelComponentPanel(betrayalLabel, betrayalHotkeyInput);
        LabelComponentPanel chatScannerPanel = new LabelComponentPanel(chatScannerLabel, chatScannerHotkeyInput);
        LabelComponentPanel closeTradePanel = new LabelComponentPanel(closeTradeLabel, closeTradeHotkeyInput);
        LabelComponentPanel historyPanel = new LabelComponentPanel(historyLabel, historyHotkeyInput);
        LabelComponentPanel optionsPanel = new LabelComponentPanel(optionsLabel, optionsHotkeyInput);

        // POE
        LabelComponentPanel delvePanel = new LabelComponentPanel(delveLabel, delveHotkeyInput);
        LabelComponentPanel dndPanel = new LabelComponentPanel(dndLabel, dndHotkeyInput);
        LabelComponentPanel exitPanel = new LabelComponentPanel(exitLabel, exitHotkeyInput);
        LabelComponentPanel harvestPanel = new LabelComponentPanel(harvestLabel, harvestHotkeyInput);
        LabelComponentPanel hideoutPanel = new LabelComponentPanel(hideoutLabel, hideoutHotkeyInput);
        LabelComponentPanel leavePanel = new LabelComponentPanel(leavePartyLabel, leavePartyHotkeyInput);
        LabelComponentPanel menageriePanel = new LabelComponentPanel(menagerieLabel, menagerieHotkeyInput);
        LabelComponentPanel metamorphPanel = new LabelComponentPanel(metamorphLabel, metamorphHotkeyInput);
        LabelComponentPanel remainingPanel = new LabelComponentPanel(remainingLabel, remainingHotkeyInput);

        int i = 10;
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(i, i, i, i);
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        slimTradeOuter.add(slimTradeInner, gc);
        poeOuter.add(poeInner, gc);
        gc.insets = new Insets(0, 0, 0, 0);

        // SlimTrade Panel
        slimTradeInner.add(betrayalPanel, gc);
        gc.insets.top = 4;
        gc.gridy++;
        slimTradeInner.add(chatScannerPanel, gc);
        gc.gridy++;
        slimTradeInner.add(closeTradePanel, gc);
        gc.gridy++;
        slimTradeInner.add(historyPanel, gc);
        gc.gridy++;
        slimTradeInner.add(optionsPanel, gc);

        // POE Panel
        gc.gridy = 0;
        poeInner.add(delvePanel, gc);
        gc.insets.top = 4;
        gc.gridy++;
        poeInner.add(dndPanel, gc);
        gc.gridy++;
        poeInner.add(exitPanel, gc);
        gc.gridy++;
        poeInner.add(harvestPanel, gc);
        gc.gridy++;
        poeInner.add(hideoutPanel, gc);
        gc.gridy++;
        poeInner.add(leavePanel, gc);
        gc.gridy++;
        poeInner.add(menageriePanel, gc);
        gc.gridy++;
        poeInner.add(metamorphPanel, gc);
        gc.gridy++;
        poeInner.add(remainingPanel, gc);

        // Build Panel
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        int small = 4;
        int large = 16;

        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.top = large;
        container.add(slimHeader, gc);
        gc.gridy += 2;
        container.add(poeHeader, gc);
        gc.gridy -= 1;
        gc.gridwidth = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.top = small;
        container.add(slimTradeOuter, gc);
        gc.gridy += 2;
        container.add(poeOuter, gc);
        load();

    }

    @Override
    public void save() {
        App.saveManager.settingsSaveFile.betrayalHotkey = betrayalHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.chatScannerHotkey = chatScannerHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.closeTradeHotkey = closeTradeHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.historyHotkey = historyHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.optionsHotkey = optionsHotkeyInput.getHotkeyData();

        App.saveManager.settingsSaveFile.delveHotkey = delveHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.dndHotkey = dndHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.exitHotkey = exitHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.harvestHotkey = harvestHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.hideoutHotkey = hideoutHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.leavePartyHotkey = leavePartyHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.menagerieHotkey = menagerieHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.metamorphHotkey = metamorphHotkeyInput.getHotkeyData();
        App.saveManager.settingsSaveFile.remainingHotkey = remainingHotkeyInput.getHotkeyData();
    }

    @Override
    public void load() {
        App.globalKeyboard.clearHotkeyListener();

        betrayalHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.betrayalHotkey);
        chatScannerHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.chatScannerHotkey);
        closeTradeHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.closeTradeHotkey);
        historyHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.historyHotkey);
        optionsHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.optionsHotkey);

        delveHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.delveHotkey);
        dndHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.dndHotkey);
        exitHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.exitHotkey);
        harvestHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.harvestHotkey);
        hideoutHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.hideoutHotkey);
        leavePartyHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.leavePartyHotkey);
        menagerieHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.menagerieHotkey);
        metamorphHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.metamorphHotkey);
        remainingHotkeyInput.updateHotkey(App.saveManager.settingsSaveFile.remainingHotkey);

    }

    @Override
    public void updateColor() {
        super.updateColor();
        poeOuter.setBackground(ColorManager.BACKGROUND);
        slimTradeOuter.setBackground(ColorManager.BACKGROUND);
        poeOuter.setBorder(ColorManager.BORDER_LOW_CONTRAST_2);
        slimTradeOuter.setBorder(ColorManager.BORDER_LOW_CONTRAST_2);
    }
}
