package com.slimtrade.gui.options.basics;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.MacroEventManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomCheckbox;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;

public class BasicsPanel extends ContainerPanel implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;

    private JTextField characterInput = new LimitTextField(32);
    private JCheckBox guildCheckbox = new CustomCheckbox();
    private JCheckBox kickCheckbox = new CustomCheckbox();
    private JCheckBox colorBlindCheckbox = new CustomCheckbox();
    private CustomCombo<ColorTheme> colorThemeCombo = new CustomCombo<>();
//    private JButton editStashButton = new BasicButton();
    private JButton editOverlayButton = new BasicButton();

    //Labels
    private JLabel characterLabel;
    private JLabel guildLabel;
    private JLabel kickLabel;
    private JLabel colorBlindLabel;
    private JLabel colorThemeLabel;

    public BasicsPanel() {
        characterLabel = new JLabel("Character Name");
        guildLabel = new JLabel("Show Guild Name");
        kickLabel = new JLabel("Close on Kick");
        colorBlindLabel = new CustomLabel("Color Blind Mode");
        colorThemeLabel = new JLabel("Color Theme");

        JPanel showGuildContainer = new JPanel(new BorderLayout());
        guildCheckbox.setOpaque(false);
        showGuildContainer.setOpaque(false);
        showGuildContainer.add(guildCheckbox, BorderLayout.EAST);

        JPanel kickContainer = new JPanel(new BorderLayout());
        kickContainer.setOpaque(false);
        kickCheckbox.setOpaque(false);
        kickContainer.add(kickCheckbox, BorderLayout.EAST);

        JPanel colorBlindContainer = new JPanel(new BorderLayout());
        colorBlindContainer.setOpaque(false);
        colorBlindCheckbox.setOpaque(false);
        colorBlindContainer.add(colorBlindCheckbox, BorderLayout.EAST);

        JPanel colorThemeContainer = new JPanel(new BorderLayout());
        colorThemeContainer.setOpaque(false);
        colorThemeContainer.add(colorThemeCombo, BorderLayout.EAST);

        editOverlayButton.setText("Edit Overlay Location");

        for (ColorTheme theme : ColorTheme.values()) {
            colorThemeCombo.addItem(theme);
        }

        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.bottom = 5;
        gc.weightx = 1;

        // Sizing
        gc.gridx = 1;
        container.add(new BufferPanel(20, 0), gc);
        gc.gridx = 2;
        container.add(new BufferPanel(140, 0), gc);
        gc.gridx = 0;
        gc.gridy++;

        // Character
        container.add(characterLabel, gc);
        gc.gridx = 2;
        container.add(characterInput, gc);
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy++;

        // show Guild
        gc.insets.bottom = 0;
        container.add(guildLabel, gc);
        gc.gridx = 2;

        container.add(showGuildContainer, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Kick on Close
        container.add(kickLabel, gc);
        gc.gridx = 2;

        container.add(kickContainer, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Colorblind Mode
        container.add(colorBlindLabel, gc);
        gc.gridx = 2;

        container.add(colorBlindContainer, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Color Combo
        gc.insets.bottom = 20;
        container.add(colorThemeLabel, gc);
        gc.gridx = 2;

        container.add(colorThemeContainer, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Edit Buttons
        gc.gridwidth = 3;
        gc.insets.bottom = 0;
        container.add(editOverlayButton, gc);
        gc.gridy++;

        editOverlayButton.addActionListener(e -> {
            FrameManager.windowState = WindowState.LAYOUT_MANAGER;
            FrameManager.hideAllFrames();
            FrameManager.overlayManager.showAll();
        });

        colorThemeCombo.addActionListener(e -> {
            if (colorThemeCombo.getSelectedIndex() >= 0) {
                App.eventManager.updateAllColors((ColorTheme) colorThemeCombo.getSelectedItem());

            }
        });

        colorBlindCheckbox.addActionListener(e -> {
            ColorManager.setColorBlindMode(colorBlindCheckbox.isSelected());
            ColorManager.setTheme(ColorManager.getCurrentColorTheme());
            App.eventManager.updateAllColors(ColorManager.getCurrentColorTheme());
        });
    }

    @Override
    public void save() {
        String characterName = characterInput.getText().replaceAll("\\s+", "");
        if (characterName.equals("")) {
            characterName = null;
        }
        MacroEventManager.setCharacterName(characterName);
        ColorTheme colorTheme = (ColorTheme) colorThemeCombo.getSelectedItem();
        App.saveManager.saveFile.characterName = characterName;
        App.saveManager.saveFile.showGuildName = guildCheckbox.isSelected();
        App.saveManager.saveFile.closeOnKick = kickCheckbox.isSelected();
        App.saveManager.saveFile.colorBlindMode = colorBlindCheckbox.isSelected();
        App.saveManager.saveFile.colorTheme = colorTheme;
    }

    @Override
    public void load() {
        String characterName = App.saveManager.saveFile.characterName;
        MacroEventManager.setCharacterName(characterName);
        characterInput.setText(characterName);
        guildCheckbox.setSelected(App.saveManager.saveFile.showGuildName);
        kickCheckbox.setSelected(App.saveManager.saveFile.closeOnKick);
        colorBlindCheckbox.setSelected(App.saveManager.saveFile.colorBlindMode);
        ColorManager.setColorBlindMode(App.saveManager.saveFile.colorBlindMode);
        colorThemeCombo.setSelectedItem(App.saveManager.saveFile.colorTheme);
        if (App.saveManager.saveFile.colorTheme == null) {
            if (colorThemeCombo.getItemCount() > 0) {
                colorThemeCombo.setSelectedIndex(0);
            }
        } else {
            colorThemeCombo.setSelectedItem(App.saveManager.saveFile.colorTheme);
        }
    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBackground(ColorManager.BACKGROUND);
        characterLabel.setForeground(ColorManager.TEXT);
        guildLabel.setForeground(ColorManager.TEXT);
        kickLabel.setForeground(ColorManager.TEXT);
        colorThemeLabel.setForeground(ColorManager.TEXT);
    }

}
