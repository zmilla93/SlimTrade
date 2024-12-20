package github.zmilla93.gui.options.general;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ComponentPair;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class KalguurSettingsPanel extends JPanel implements ISavable {

    private final HotkeyButton kalguurWindowHotkey = new HotkeyButton();
    private final JCheckBox clearCompletedTimers = new JCheckBox("Auto Clear Completed Timers");

    private final GridBagConstraints gc = ZUtil.getGC();

    public KalguurSettingsPanel() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        JLabel label1 = new JLabel("Set timers to track shipments. Quickly divide by 5.");
        JLabel label2 = new JLabel("Timer format is 'Hours:Minutes', ie '1:20' for 1 hour 20 minutes.");
        JLabel label3 = new StyledLabel("Press ENTER to save values.").bold();
//        addRow(new ComponentPair(label1, label2));
        addRow(label1);
        addRow(label2);
        addRow(label3);
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
