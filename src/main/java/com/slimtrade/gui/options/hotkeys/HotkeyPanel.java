package com.slimtrade.gui.options.hotkeys;

import com.slimtrade.App;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.general.LabelComponentPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class HotkeyPanel extends ContainerPanel implements ISaveable {

    // Info
    JLabel info1 = new CustomLabel("Hotkeys can be given control, alt, and shift as modifiers.");
    JLabel info2 = new CustomLabel("Use escape to clear a hotkey.");

    // Label + Hotkey Input
    JLabel closeTradeLabel = new CustomLabel("Close Oldest Trade");
    HotkeyInputPane closeTradeHotkeyInput = new HotkeyInputPane();

    JLabel remainingLabel = new CustomLabel("Remaining Monsters");
    HotkeyInputPane remainingHotkeyInput = new HotkeyInputPane();

    JLabel hideoutLabel = new CustomLabel("Warp to Hideout");
    HotkeyInputPane hideoutHotkeyInput = new HotkeyInputPane();

    JLabel leavePartyLabel = new CustomLabel("Leave Party");
    HotkeyInputPane leavePartyHotkeyInput = new HotkeyInputPane();

    JLabel betrayalLabel = new CustomLabel("Toggle Betrayal Guide");
    HotkeyInputPane betrayalHotkeyInput = new HotkeyInputPane();

    public HotkeyPanel() {
        // Combined Panel
        LabelComponentPanel closeTradePanel = new LabelComponentPanel(closeTradeLabel, closeTradeHotkeyInput);
        LabelComponentPanel remainingPanel = new LabelComponentPanel(remainingLabel, remainingHotkeyInput);
        LabelComponentPanel hideoutPanel = new LabelComponentPanel(hideoutLabel, hideoutHotkeyInput);
        LabelComponentPanel leavePanel = new LabelComponentPanel(leavePartyLabel, leavePartyHotkeyInput);
        LabelComponentPanel betrayalPanel = new LabelComponentPanel(betrayalLabel, betrayalHotkeyInput);

        // Tooltips
        remainingLabel.setToolTipText("Uses POE's /remaining command to tell you how many monsters are left in a zone.");
        leavePartyLabel.setToolTipText("Kick yourself from a party. Make sure your character name is set correctly.");

        // Build Panel
        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        container.add(info2, gc);
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 4;
        container.add(closeTradePanel, gc);
        gc.gridy++;
        container.add(remainingPanel, gc);
        gc.gridy++;
        container.add(hideoutPanel, gc);
        gc.gridy++;
        container.add(leavePanel, gc);
        gc.gridy++;
        container.add(betrayalPanel, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        gc.gridy++;

        load();
    }

    @Override
    public void save() {
        App.saveManager.saveFile.closeTradeHotkey = closeTradeHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.remainingHotkey = remainingHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.hideoutHotkey = hideoutHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.leavePartyHotkey = leavePartyHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.betrayalHotkey = betrayalHotkeyInput.getHotkeyData();
    }

    @Override
    public void load() {
        App.globalKeyboard.clearHotkeyListener();
        closeTradeHotkeyInput.updateHotkey(App.saveManager.saveFile.closeTradeHotkey);
        remainingHotkeyInput.updateHotkey(App.saveManager.saveFile.remainingHotkey);
        hideoutHotkeyInput.updateHotkey(App.saveManager.saveFile.hideoutHotkey);
        leavePartyHotkeyInput.updateHotkey(App.saveManager.saveFile.leavePartyHotkey);
        betrayalHotkeyInput.updateHotkey(App.saveManager.saveFile.betrayalHotkey);
    }

}
