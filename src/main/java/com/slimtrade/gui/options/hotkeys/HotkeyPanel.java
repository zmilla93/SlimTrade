package com.slimtrade.gui.options.hotkeys;

import com.slimtrade.App;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.basics.LabelComponentPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class HotkeyPanel extends ContainerPanel implements ISaveable {

    JLabel info1 = new CustomLabel("Hotkeys can be given control, alt, and shift as modifiers.");
    JLabel info2 = new CustomLabel("Use escape to clear a hotkey.");

    JLabel hideoutLabel = new CustomLabel("Warp to Hideout");
    HotkeyInputPane hideoutHotkeyInput = new HotkeyInputPane();

    JLabel betrayalLabel = new CustomLabel("Toggle Betrayal Guide");
    HotkeyInputPane betrayalHotkeyInput = new HotkeyInputPane();

    JLabel pasteLabel = new CustomLabel("Quick Paste Trade");
    HotkeyInputPane pasteHotkeyInput = new HotkeyInputPane();


    public HotkeyPanel() {
        this.setVisible(false);

        // Quick Paste
        LabelComponentPanel hideoutPanel = new LabelComponentPanel(hideoutLabel, hideoutHotkeyInput);
        LabelComponentPanel betrayalPanel = new LabelComponentPanel(betrayalLabel, betrayalHotkeyInput);
        LabelComponentPanel quickPastePanel = new LabelComponentPanel(pasteLabel, pasteHotkeyInput);


        pasteLabel.setToolTipText("If you have a trade message copied, it will be pasted into Path of Exile.");
//        pasteLabel.createToolTip();

        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.top = 10;
        container.add(hideoutPanel, gc);
        gc.gridy++;
        gc.insets.top = 4;
        container.add(betrayalPanel, gc);
        gc.gridy++;
        container.add(quickPastePanel, gc);
        gc.gridy++;

    }

    @Override
    public void save() {
        App.saveManager.saveFile.hideoutHotkey = hideoutHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.betrayalHotkey = betrayalHotkeyInput.getHotkeyData();
        App.saveManager.saveFile.quickPasteHotkey = pasteHotkeyInput.getHotkeyData();
    }

    @Override
    public void load() {
        hideoutHotkeyInput.updateHotkey(App.saveManager.saveFile.hideoutHotkey);
        betrayalHotkeyInput.updateHotkey(App.saveManager.saveFile.betrayalHotkey);
        pasteHotkeyInput.updateHotkey(App.saveManager.saveFile.quickPasteHotkey);
        App.globalKeyboard.clearHotkeyListener();
    }
}
