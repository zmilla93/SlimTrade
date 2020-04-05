package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.custom.CustomCheckbox;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.hotkeys.HotkeyInputPane;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;

public class BasicsPanel extends ContainerPanel implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;

    private JTextField characterInput = new LimitTextField(32);
    private JCheckBox guildCheckbox = new CustomCheckbox();
    private JCheckBox colorBlindCheckbox = new CustomCheckbox();
    private CustomCombo<ColorTheme> colorThemeCombo = new CustomCombo<>();
    private CustomCombo<QuickPasteSetting> quickPasteCombo = new CustomCombo<>();
    private HotkeyInputPane quickPasteHotkeyInput = new HotkeyInputPane();
    private JButton editOverlayButton = new BasicButton();

    //Labels
    private JLabel characterLabel;
    private JLabel guildLabel;
    private JLabel colorBlindLabel;
    private JLabel colorThemeLabel;
    private JLabel quickPasteLabel;
    private JLabel quickPasteHotkeyLabel;

    public BasicsPanel() {
        characterLabel = new CustomLabel("Character Name");
        guildLabel = new CustomLabel("Show Guild Name");
        colorBlindLabel = new CustomLabel("Color Blind Mode");
        colorThemeLabel = new CustomLabel("Color Theme");
        quickPasteLabel = new CustomLabel("Quick Paste");
        quickPasteHotkeyLabel = new CustomLabel("Quick Paste Hotkey");

        JPanel showGuildContainer = new JPanel(new BorderLayout());
        guildCheckbox.setOpaque(false);
        showGuildContainer.setOpaque(false);
        showGuildContainer.add(guildCheckbox, BorderLayout.EAST);

        JPanel colorBlindContainer = new JPanel(new BorderLayout());
        colorBlindContainer.setOpaque(false);
        colorBlindCheckbox.setOpaque(false);
        colorBlindContainer.add(colorBlindCheckbox, BorderLayout.EAST);

        JPanel colorThemeContainer = new JPanel(new BorderLayout());
        colorThemeContainer.setOpaque(false);
        colorThemeContainer.add(colorThemeCombo, BorderLayout.EAST);

        JPanel quickPasteContainer = new JPanel(new BorderLayout());
        quickPasteContainer.setOpaque(false);
        quickPasteContainer.add(quickPasteCombo, BorderLayout.EAST);

        JPanel quickPasteHotkeyContainer = new JPanel(new BorderLayout());
        quickPasteHotkeyContainer.setOpaque(false);
        quickPasteHotkeyContainer.add(quickPasteHotkeyInput, BorderLayout.EAST);

        editOverlayButton.setText("Edit Overlay Size & Location");

        for (ColorTheme theme : ColorTheme.values()) {
            colorThemeCombo.addItem(theme);
        }
        for (QuickPasteSetting s : QuickPasteSetting.values()) {
            quickPasteCombo.addItem(s);
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

        // Colorblind Mode
        container.add(colorBlindLabel, gc);
        gc.gridx = 2;

        container.add(colorBlindContainer, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Color Combo
        gc.insets.top = 5;
        gc.insets.bottom = 10;
        container.add(colorThemeLabel, gc);

        gc.gridx = 2;

        container.add(colorThemeContainer, gc);
        gc.insets.top = 0;
        gc.gridx = 0;
        gc.gridy++;

        // Quick Paste
        container.add(quickPasteLabel, gc);
        gc.gridx = 2;

        container.add(quickPasteContainer, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Quick Paste Hotkey
        container.add(quickPasteHotkeyLabel, gc);
        gc.gridx = 2;

        container.add(quickPasteHotkeyContainer, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Edit Overlay Button
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

        quickPasteCombo.addActionListener(e -> {
            updateQuickPasteVis();
        });
    }

    private void updateQuickPasteVis() {
        App.globalKeyboard.clearHotkeyListener();
        if (quickPasteCombo.getSelectedIndex() >= 0 && quickPasteCombo.getSelectedItem() == QuickPasteSetting.HOTKEY) {
            quickPasteHotkeyLabel.setVisible(true);
            quickPasteHotkeyInput.setVisible(true);
        } else {
            quickPasteHotkeyLabel.setVisible(false);
            quickPasteHotkeyInput.setVisible(false);
        }
    }

    @Override
    public void save() {
        String characterName = characterInput.getText().replaceAll("\\s+", "");
        if (characterName.equals("")) {
            characterName = null;
        }
        ColorTheme colorTheme = (ColorTheme) colorThemeCombo.getSelectedItem();
        App.saveManager.saveFile.characterName = characterName;
        App.saveManager.saveFile.showGuildName = guildCheckbox.isSelected();
        App.saveManager.saveFile.colorBlindMode = colorBlindCheckbox.isSelected();
        App.saveManager.saveFile.colorTheme = colorTheme;
        App.saveManager.saveFile.quickPasteSetting = (QuickPasteSetting) quickPasteCombo.getSelectedItem();
        App.saveManager.saveFile.quickPasteHotkey = quickPasteHotkeyInput.getHotkeyData();
    }

    @Override
    public void load() {
        String characterName = App.saveManager.saveFile.characterName;
        characterInput.setText(characterName);
        guildCheckbox.setSelected(App.saveManager.saveFile.showGuildName);
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
        quickPasteCombo.setSelectedItem(App.saveManager.saveFile.quickPasteSetting);
        quickPasteHotkeyInput.updateHotkey(App.saveManager.saveFile.quickPasteHotkey);
        updateQuickPasteVis();
    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBackground(ColorManager.BACKGROUND);
    }

}
