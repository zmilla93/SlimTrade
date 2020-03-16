package com.slimtrade.gui.options.hotkeys;

import com.slimtrade.App;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.basics.LabelComponentPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class HotkeyPanel extends ContainerPanel implements ISaveable {

    // Info
    JLabel info1 = new CustomLabel("Hotkeys can be given control, alt, and shift as modifiers.");
    JLabel info2 = new CustomLabel("Use escape to clear a hotkey.");

    // Label + Hotkey Input
    JLabel remainingLabel = new CustomLabel("Remaining Monsters");
    HotkeyInputPane remainingHotkeyInput = new HotkeyInputPane();

    JLabel hideoutLabel = new CustomLabel("Warp to Hideout");
    HotkeyInputPane hideoutHotkeyInput = new HotkeyInputPane();

    JLabel leavePartyLabel = new CustomLabel("Leave Party");
    HotkeyInputPane leavePartyHotkeyInput = new HotkeyInputPane();

    JLabel betrayalLabel = new CustomLabel("Toggle Betrayal Guide");
    HotkeyInputPane betrayalHotkeyInput = new HotkeyInputPane();

    JLabel pasteLabel = new CustomLabel("Quick Paste Trade");
    HotkeyInputPane pasteHotkeyInput = new HotkeyInputPane();

    public HotkeyPanel() {
        this.setVisible(false);

        // Combined Panel
        LabelComponentPanel remainingPanel = new LabelComponentPanel(remainingLabel, remainingHotkeyInput);
        LabelComponentPanel hideoutPanel = new LabelComponentPanel(hideoutLabel, hideoutHotkeyInput);
        LabelComponentPanel leavePanel = new LabelComponentPanel(leavePartyLabel, leavePartyHotkeyInput);
        LabelComponentPanel betrayalPanel = new LabelComponentPanel(betrayalLabel, betrayalHotkeyInput);
        LabelComponentPanel quickPastePanel = new LabelComponentPanel(pasteLabel, pasteHotkeyInput);

        // Tooltips
        remainingLabel.setToolTipText("Uses POE's /remaining command to tell you how many monsters are left in a zone.");
        leavePartyLabel.setToolTipText("Kick yourself from a party. Make sure your character name is set correctly.");
        pasteLabel.setToolTipText("If you have a trade message copied to your clipboard, it will be pasted into Path of Exile.");

        // Build Panel
        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        container.add(info2, gc);
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 4;
        container.add(remainingPanel, gc);
        gc.gridy++;
        container.add(hideoutPanel, gc);
        gc.gridy++;
        container.add(leavePanel, gc);
        gc.gridy++;
        container.add(betrayalPanel, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        container.add(quickPastePanel, gc);
        gc.gridy++;

    }

    @Override
    public void save() {
        App.saveManager.saveFile.remainingHotkey = remainingHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.hideoutHotkey = hideoutHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.leavePartyHotkey = leavePartyHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.betrayalHotkey = betrayalHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.quickPasteHotkey = pasteHotkeyInput.getHotkeyData();
    }

    @Override
    public void load() {
        App.globalKeyboard.clearHotkeyListener();
        remainingHotkeyInput.updateHotkey(App.saveManager.saveFile.remainingHotkey);
        hideoutHotkeyInput.updateHotkey(App.saveManager.saveFile.hideoutHotkey);
        leavePartyHotkeyInput.updateHotkey(App.saveManager.saveFile.leavePartyHotkey);
        betrayalHotkeyInput.updateHotkey(App.saveManager.saveFile.betrayalHotkey);
        pasteHotkeyInput.updateHotkey(App.saveManager.saveFile.quickPasteHotkey);
    }

}
