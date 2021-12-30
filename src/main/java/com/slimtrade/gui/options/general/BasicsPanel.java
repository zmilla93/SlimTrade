package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ColorTheme;

import javax.swing.*;
import java.awt.*;

public class BasicsPanel extends GridBagPanel {

    JPanel outerPanel = new JPanel();

    public BasicsPanel() {
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.LINE_END;

        setLayout(new BorderLayout());
        outerPanel.setLayout(new GridBagLayout());

        // Components
        JTextField characterName = new JTextField(10);
        JCheckBox showGuildName = new JCheckBox();
        JCheckBox folderOffset = new JCheckBox();
        JCheckBox colorBlind = new JCheckBox();
        JComboBox<ColorTheme> colorTheme = new JComboBox<>();
        JComboBox<String> quickPasteCombo = new JComboBox<>();
        JButton editOverlayButton = new JButton("Edit Overlay");
        colorTheme.setMaximumRowCount(10);
        for (ColorTheme theme : ColorTheme.values()) colorTheme.addItem(theme);
        colorTheme.addItemListener(e -> ColorManager.setTheme((ColorTheme) colorTheme.getSelectedItem()));

        gc.gridwidth = 2;
        JComponent guildCheckbox = addCheckbox("Show Guild Name", showGuildName);
        JComponent folderCheckbox = addCheckbox("Folder Offer", folderOffset);
        JComponent colorBlindCheckbox = addCheckbox("Color Blind", colorBlind);
        gc.gridwidth = 1;
        addLabelComponentPair("Character Name", characterName);
        addLabelComponentPair("Color Theme", colorTheme);

//        addComponent(new JButton("Do Some Shit"));
        addLabelComponentPair("Quick Paste", quickPasteCombo);
        addComponent(editOverlayButton);

        add(outerPanel, BorderLayout.WEST);

        App.saveManager.registerSaveElement(guildCheckbox, "showGuildName", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(folderCheckbox, "folderOffset", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(colorBlindCheckbox, "colorBlindMode", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(characterName, "characterName", App.saveManager.settingsSaveFile);
        App.saveManager.registerSaveElement(colorTheme, "colorTheme", App.saveManager.settingsSaveFile);
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

}
