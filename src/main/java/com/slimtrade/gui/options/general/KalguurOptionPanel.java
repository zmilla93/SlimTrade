package com.slimtrade.gui.options.general;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class KalguurOptionPanel extends JPanel implements ISavable {

    private final HotkeyButton kalguurWindowHotkey = new HotkeyButton();
    private final JCheckBox clearCompletedTimers = new JCheckBox("Auto Clear Completed Timers");

    private final GridBagConstraints gc = ZUtil.getGC();

    public KalguurOptionPanel() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        JLabel label1 = new JLabel("Set timers to track shipments. Quickly divide by 5.");
        JLabel label2 = new StyledLabel("Press ENTER to save values.").bold();
//        addRow(new ComponentPair(label1, label2));
        addRow(label1);
        addRow(label2);
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.WEST;
        addRow(new ComponentPair(new JLabel("Window Hotkey"), kalguurWindowHotkey));
        addRow(clearCompletedTimers);
    }

    private void addRow(Component component) {
        add(component, gc);
        gc.gridy++;
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.kalguurWindowHotkey = kalguurWindowHotkey.getData();
        SaveManager.settingsSaveFile.data.kalguurAutoClearTimers = clearCompletedTimers.isSelected();
    }

    @Override
    public void load() {
        kalguurWindowHotkey.setData(SaveManager.settingsSaveFile.data.kalguurWindowHotkey);
        clearCompletedTimers.setSelected(SaveManager.settingsSaveFile.data.kalguurAutoClearTimers);
    }

}
