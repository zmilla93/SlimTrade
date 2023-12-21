package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.gui.components.LanguageTextField;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class BasicsPanel extends GridBagPanel implements ISavable {

    private final JPanel outerPanel = new JPanel();

    private final JTextField characterNameInput = new LanguageTextField(14);
    private final JCheckBox showGuildName = new JCheckBox();
    private final JCheckBox folderOffset = new JCheckBox("Using Stash Folders");
    private final JComboBox<QuickPasteManager.QuickPasteMode> quickPasteCombo = new JComboBox<>();
    private final HotkeyButton quickPasteHotkey = new HotkeyButton();
    private final JButton editOverlayButton = new JButton("Edit Overlay");

    public BasicsPanel() {
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.LINE_END;
        setLayout(new BorderLayout());
        outerPanel.setLayout(new GridBagLayout());
        // Add values to combos
        for (QuickPasteManager.QuickPasteMode mode : QuickPasteManager.QuickPasteMode.values())
            quickPasteCombo.addItem(mode);
        // Add components
        addLabelComponentPair("Character Name", characterNameInput);
        addLabelComponentPair("Quick Paste", quickPasteCombo);
        addLabelComponentPair("Quick Paste Hotkey", quickPasteHotkey);
        addComponent(folderOffset);
        addComponent(editOverlayButton);

        add(outerPanel, BorderLayout.WEST);

        addListeners();
    }

    private void addListeners() {
        editOverlayButton.addActionListener(e -> FrameManager.setWindowVisibility(AppState.EDIT_OVERLAY));

    }

    private void addLabelComponentPair(String text, JComponent component) {
        outerPanel.add(new JLabel(text), gc);
        gc.gridx++;
        outerPanel.add(Box.createHorizontalStrut(10), gc);
        gc.gridx++;
        outerPanel.add(component, gc);
        gc.gridx = 0;
        gc.gridy++;
    }

    private void addComponent(JComponent component) {
        outerPanel.add(component, gc);
        gc.gridy++;
    }

    public void refreshCharacterName() {
        characterNameInput.setText(SaveManager.settingsSaveFile.data.characterName);
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.showGuildName = showGuildName.isSelected();
        SaveManager.settingsSaveFile.data.folderOffset = folderOffset.isSelected();
        SaveManager.settingsSaveFile.data.characterName = characterNameInput.getText();
        SaveManager.settingsSaveFile.data.quickPasteMode = (QuickPasteManager.QuickPasteMode) quickPasteCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.quickPasteHotkey = quickPasteHotkey.getData();
        QuickPasteManager.setMode(SaveManager.settingsSaveFile.data.quickPasteMode);
    }

    @Override
    public void load() {
        showGuildName.setSelected(SaveManager.settingsSaveFile.data.showGuildName);
        folderOffset.setSelected(SaveManager.settingsSaveFile.data.folderOffset);
        characterNameInput.setText(SaveManager.settingsSaveFile.data.characterName);
        quickPasteCombo.setSelectedItem(SaveManager.settingsSaveFile.data.quickPasteMode);
        quickPasteHotkey.setData(SaveManager.settingsSaveFile.data.quickPasteHotkey);
        QuickPasteManager.setMode(SaveManager.settingsSaveFile.data.quickPasteMode);
    }

}
