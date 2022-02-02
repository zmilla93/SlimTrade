package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.saving.ISavable;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.gui.basic.HotkeyButton;
import com.slimtrade.gui.managers.FrameManager;

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
        // Components
//        JTextField characterName = new JTextField(10);
//        JCheckBox showGuildName = new JCheckBox();
//        JCheckBox folderOffset = new JCheckBox();
//        JCheckBox colorBlind = new JCheckBox();
//        JComboBox<ColorTheme> colorTheme = new JComboBox<>();
//        JComboBox<QuickPasteManager.QuickPasteMode> quickPasteCombo = new JComboBox<>();
//        JButton editOverlayButton = new JButton("Edit Overlay");
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

        colorTheme.addActionListener(e -> ColorManager.setTheme((ColorTheme) colorTheme.getSelectedItem()));
        editOverlayButton.addActionListener(e -> FrameManager.setWindowVisibility(AppState.EDIT_OVERLAY));

        App.saveManager.registerSaveElement(guildCheckbox, "showGuildName", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(folderCheckbox, "folderOffset", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(colorBlindCheckbox, "colorBlindMode", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(characterName, "characterName", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(colorTheme, "colorTheme", App.saveManager.settingsSaveFile);
        App.saveManager.registerSavable(this);
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
        App.saveManager.settingsSaveFile.quickPasteMode = (QuickPasteManager.QuickPasteMode) quickPasteCombo.getSelectedItem();
        App.saveManager.settingsSaveFile.quickPasteHotkey = quickPasteHotkey.getData();
        QuickPasteManager.setMode(App.saveManager.settingsSaveFile.quickPasteMode);
    }

    @Override
    public void load() {
        quickPasteCombo.setSelectedItem(App.saveManager.settingsSaveFile.quickPasteMode);
        quickPasteHotkey.setData(App.saveManager.settingsSaveFile.quickPasteHotkey);
        QuickPasteManager.setMode(App.saveManager.settingsSaveFile.quickPasteMode);
    }

}
