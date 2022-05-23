package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.gui.basic.HotkeyButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class BasicsPanel extends GridBagPanel implements ISavable {

    JPanel outerPanel = new JPanel();

    private JTextField characterName = new JTextField(10);
    private JCheckBox showGuildName = new JCheckBox();
    private JCheckBox folderOffset = new JCheckBox();
    private JCheckBox colorBlind = new JCheckBox();
    private JComboBox<ColorTheme> colorTheme = new JComboBox<>();
    private JComboBox<QuickPasteManager.QuickPasteMode> quickPasteCombo = new JComboBox<>();
    private HotkeyButton quickPasteHotkey = new HotkeyButton();
    private JButton editOverlayButton = new JButton("Edit Overlay");

    public BasicsPanel() {
        colorTheme.setMaximumRowCount(10);

        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.LINE_END;

        setLayout(new BorderLayout());
        outerPanel.setLayout(new GridBagLayout());


        // Add values to combos
        for (ColorTheme theme : ColorTheme.values()) colorTheme.addItem(theme);
        for (QuickPasteManager.QuickPasteMode mode : QuickPasteManager.QuickPasteMode.values())
            quickPasteCombo.addItem(mode);

        gc.gridwidth = 2;
        JComponent guildCheckbox = addCheckbox("Show Guild Name", showGuildName);
        JComponent folderCheckbox = addCheckbox("Folder Offer", folderOffset);
        JComponent colorBlindCheckbox = addCheckbox("Color Blind", colorBlind);
        gc.gridwidth = 1;
        addLabelComponentPair("Character Name", characterName);
        addLabelComponentPair("Color Theme", colorTheme);

//        addComponent(new JButton("Do Some Shit"));
        addLabelComponentPair("Quick Paste", quickPasteCombo);
        addLabelComponentPair("Quick Paste Hotkey", quickPasteHotkey);
        addComponent(editOverlayButton);

        add(outerPanel, BorderLayout.WEST);

        colorTheme.addActionListener(e -> SwingUtilities.invokeLater(() -> ColorManager.setTheme((ColorTheme) colorTheme.getSelectedItem())));
        editOverlayButton.addActionListener(e -> FrameManager.setWindowVisibility(AppState.EDIT_OVERLAY));
    }

    private void addLabelComponentPair(String text, JComponent component) {
        gc.insets = new Insets(0, 20, 0, 0);
        outerPanel.add(new JLabel(text), gc);
        gc.gridx++;
        outerPanel.add(Box.createHorizontalStrut(10), gc);
        gc.gridx++;
        outerPanel.add(component, gc);
        gc.gridx = 0;
        gc.gridy++;
    }

    private JComponent addCheckbox(String text, JComponent component) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.weightx = 0;
        gc.gridx = 0;
        gc.insets = new Insets(0, 10, 0, 0);
        panel.add(component, gc);
        gc.gridx++;
        gc.insets = new Insets(0, 5, 0, 0);
        gc.weightx = 1;
        panel.add(new JLabel(text, JLabel.LEFT), gc);
        gc.gridx = 0;
//        gc.gridy++;
        outerPanel.add(panel, this.gc);
        this.gc.gridy++;
        return component;
    }

    private void addComponent(JComponent component) {
        outerPanel.add(component, gc);
        gc.gridy++;
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.showGuildName = showGuildName.isSelected();
        SaveManager.settingsSaveFile.data.folderOffset = folderOffset.isSelected();
        SaveManager.settingsSaveFile.data.colorBlindMode = colorBlind.isSelected();
        SaveManager.settingsSaveFile.data.characterName = characterName.getText();
        SaveManager.settingsSaveFile.data.colorTheme = (ColorTheme) colorTheme.getSelectedItem();

        SaveManager.settingsSaveFile.data.quickPasteMode = (QuickPasteManager.QuickPasteMode) quickPasteCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.quickPasteHotkey = quickPasteHotkey.getData();
        QuickPasteManager.setMode(SaveManager.settingsSaveFile.data.quickPasteMode);
    }

    @Override
    public void load() {
        showGuildName.setSelected(SaveManager.settingsSaveFile.data.showGuildName);
        folderOffset.setSelected(SaveManager.settingsSaveFile.data.folderOffset);
        colorBlind.setSelected(SaveManager.settingsSaveFile.data.colorBlindMode);
        characterName.setText(SaveManager.settingsSaveFile.data.characterName);
        colorTheme.setSelectedItem(SaveManager.settingsSaveFile.data.colorTheme);

        quickPasteCombo.setSelectedItem(SaveManager.settingsSaveFile.data.quickPasteMode);
        quickPasteHotkey.setData(SaveManager.settingsSaveFile.data.quickPasteHotkey);
        QuickPasteManager.setMode(SaveManager.settingsSaveFile.data.quickPasteMode);
    }

}
