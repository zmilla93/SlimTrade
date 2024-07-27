package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.MenubarStyle;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class EnableFeaturesPanel extends JPanel implements ISavable {

    private final GridBagConstraints gc = ZUtil.getGC();

    private final JComboBox<MenubarStyle> menubarStyleCombo = new JComboBox<>();
    private final JCheckBox menubarAlwaysExpanded = new JCheckBox("Menubar Always Expanded");
    private final JCheckBox incomingMessages = new JCheckBox("Incoming Messages");
    private final JCheckBox outgoingMessages = new JCheckBox("Outgoing Messages");
    private final JCheckBox itemHighlighter = new JCheckBox("Item Highlighter");
    private final JCheckBox autoUpdateCheckbox = new JCheckBox("Update Automatically");
    private final JCheckBox hideWhenPOENotFocusedCheckbox = new JCheckBox("Hide overlay when POE is not focused");

    public EnableFeaturesPanel() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.WEST;
        for (MenubarStyle style : MenubarStyle.values()) menubarStyleCombo.addItem(style);
        addRow(new ComponentPair(new JLabel("Menubar Style"), menubarStyleCombo));
        addRow(menubarAlwaysExpanded);
        addRow(incomingMessages);
        addRow(outgoingMessages);
        addRow(itemHighlighter);
        addRow(autoUpdateCheckbox);
        addRow(hideWhenPOENotFocusedCheckbox);
        menubarStyleCombo.addActionListener(e -> updateCheckboxVisibility());
    }

    private void updateCheckboxVisibility() {
        MenubarStyle style = (MenubarStyle) menubarStyleCombo.getSelectedItem();
        if (style == null) return;
        menubarAlwaysExpanded.setVisible(style != MenubarStyle.DISABLED);
    }

    private void addRow(JComponent component) {
        add(component, gc);
        gc.gridy++;
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.menubarStyle = (MenubarStyle) menubarStyleCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.menubarAlwaysExpanded = menubarAlwaysExpanded.isSelected();
        SaveManager.settingsSaveFile.data.enableIncomingTrades = incomingMessages.isSelected();
        SaveManager.settingsSaveFile.data.enableOutgoingTrades = outgoingMessages.isSelected();
        SaveManager.settingsSaveFile.data.enableItemHighlighter = itemHighlighter.isSelected();
        SaveManager.settingsSaveFile.data.enableAutomaticUpdate = autoUpdateCheckbox.isSelected();
        SaveManager.settingsSaveFile.data.hideWhenPOENotFocused = hideWhenPOENotFocusedCheckbox.isSelected();
        // FIXME : Move to listener?
        FrameManager.updateMenubarVisibility();
    }

    @Override
    public void load() {
        menubarStyleCombo.setSelectedItem(SaveManager.settingsSaveFile.data.menubarStyle);
        menubarAlwaysExpanded.setSelected(SaveManager.settingsSaveFile.data.menubarAlwaysExpanded);
        incomingMessages.setSelected(SaveManager.settingsSaveFile.data.enableIncomingTrades);
        outgoingMessages.setSelected(SaveManager.settingsSaveFile.data.enableOutgoingTrades);
        itemHighlighter.setSelected(SaveManager.settingsSaveFile.data.enableItemHighlighter);
        autoUpdateCheckbox.setSelected(SaveManager.settingsSaveFile.data.enableAutomaticUpdate);
        hideWhenPOENotFocusedCheckbox.setSelected(SaveManager.settingsSaveFile.data.hideWhenPOENotFocused);
        updateCheckboxVisibility();
    }
}
