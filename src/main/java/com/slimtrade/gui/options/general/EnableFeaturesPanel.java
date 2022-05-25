package com.slimtrade.gui.options.general;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class EnableFeaturesPanel extends JPanel implements ISavable {

    GridBagConstraints gc = ZUtil.getGC();

    private final JCheckBox incomingMessages = new JCheckBox();
    private final JCheckBox outgoingMessages = new JCheckBox();
    private final JCheckBox itemHighlighter = new JCheckBox();
    private final JCheckBox menubarButton = new JCheckBox();
    private final JCheckBox autoUpdateCheckbox = new JCheckBox();
    private final JCheckBox hideWhenPOENotFocusedCheckbox = new JCheckBox();

    public EnableFeaturesPanel() {
        setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;

        addRow("Incoming Messages", incomingMessages);
        addRow("Outgoing Messages", outgoingMessages);
        addRow("Item Highlighter", itemHighlighter);
        addRow("Menubar Button", menubarButton);
        addRow("Update Automatically", autoUpdateCheckbox);
        addRow("Hide overlay when POE is not focused", hideWhenPOENotFocusedCheckbox);
    }

    private void addRow(String title, JComponent component) {
        gc.insets = new Insets(0, 10, 2, 0);
        add(component, gc);
        gc.gridx++;
        add(new JLabel(title), gc);
        gc.gridx = 0;
        gc.gridy++;
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.hideWhenPOENotFocused = hideWhenPOENotFocusedCheckbox.isSelected();
    }

    @Override
    public void load() {
        hideWhenPOENotFocusedCheckbox.setSelected(SaveManager.settingsSaveFile.data.hideWhenPOENotFocused);
    }
}
