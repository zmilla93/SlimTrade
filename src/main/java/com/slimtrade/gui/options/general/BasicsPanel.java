package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.MacroEventManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomCheckbox;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomTextField;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicsPanel extends ContainerPanel implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;

    private JTextField characterInput = new LimitTextField(32);
    private JCheckBox guildCheckbox = new CustomCheckbox();
    private JCheckBox kickCheckbox = new CustomCheckbox();
    private JCheckBox quickPasteCheckbox = new CustomCheckbox();
    private CustomCombo<ColorTheme> colorThemeCombo = new CustomCombo<>();
    private JButton editStashButton = new BasicButton();
    private JButton editOverlayButton = new BasicButton();

    //Labels
    private JLabel characterLabel;
    private JLabel guildLabel;
    private JLabel kickLabel;
    private JLabel quickPasteLabel;
    private JLabel colorThemeLabel;

    public BasicsPanel() {
        characterLabel = new JLabel("Character Name");
        guildLabel = new JLabel("Show Guild Name");
        kickLabel = new JLabel("Close on Kick");
        quickPasteLabel = new JLabel("Quick Paste Trades");
        colorThemeLabel = new JLabel("Color Theme");

//		this.setBackground(Color.LIGHT_GRAY);

//        App.saveFile.characterName = new SaveElement("charName", characterInput);

//        guildCheckbox.setFocusable(false);
//        kickCheckbox.setFocusable(false);
//        colorThemeCombo.setFocusable(false);
//        editStashButton.setFocusable(false);
//        editOverlayButton.setFocusable(false);

        JPanel showGuildContainer = new JPanel(new BorderLayout());
        guildCheckbox.setOpaque(false);
        showGuildContainer.setOpaque(false);
        showGuildContainer.add(guildCheckbox, BorderLayout.EAST);

        JPanel kickContainer = new JPanel(new BorderLayout());
        kickContainer.setOpaque(false);
        kickCheckbox.setOpaque(false);
        kickContainer.add(kickCheckbox, BorderLayout.EAST);

        JPanel quickPasteContainer = new JPanel(new BorderLayout());
        quickPasteContainer.setOpaque(false);
        quickPasteCheckbox.setOpaque(false);
        quickPasteContainer.add(quickPasteCheckbox, BorderLayout.EAST);

        JPanel colorThemeContainer = new JPanel(new BorderLayout());
        colorThemeContainer.setOpaque(false);
//        colorThemeCombo.setOpaque(false);
        colorThemeContainer.add(colorThemeCombo, BorderLayout.EAST);


        editStashButton.setText("Edit Stash");
        editOverlayButton.setText("Edit Overlay");
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

        // Quick Paste
//        container.add(quickPasteLabel, gc);
//        gc.gridx = 2;
//
//        container.add(quickPasteContainer, gc);
//        gc.gridx = 0;
//        gc.gridy++;

        // Color Combo
        gc.insets.bottom = 5;
        if(App.debugMode) {
            container.add(colorThemeLabel, gc);
            gc.gridx = 2;

            container.add(colorThemeContainer, gc);
            gc.gridx = 0;
            gc.gridy++;
        }


        // Edit Buttons
        gc.gridwidth = 3;
        container.add(editStashButton, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        container.add(editOverlayButton, gc);
        gc.gridy++;

        editStashButton.addActionListener(e -> {
            FrameManager.windowState = WindowState.STASH_OVERLAY;
            FrameManager.hideAllFrames();
            FrameManager.stashOverlayWindow.setShow(true);
            FrameManager.stashOverlayWindow.setAlwaysOnTop(false);
            FrameManager.stashOverlayWindow.setAlwaysOnTop(true);
            FrameManager.stashOverlayWindow.repaint();
        });

        editOverlayButton.addActionListener(e -> {
            FrameManager.windowState = WindowState.LAYOUT_MANAGER;
            FrameManager.hideAllFrames();
            FrameManager.overlayManager.showAll();
        });


        load();

        colorThemeCombo.addActionListener(e -> {
            if(colorThemeCombo.getSelectedIndex() >= 0) {
                App.eventManager.updateAllColors((ColorTheme) colorThemeCombo.getSelectedItem());

            }
        });

        App.eventManager.addColorListener(this);
        this.updateColor();
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
        App.saveManager.saveFile.quickPasteTrades = guildCheckbox.isSelected();
        App.saveManager.saveFile.colorTheme = colorTheme;
    }

    @Override
    public void load() {
        String characterName = App.saveManager.saveFile.characterName;
        MacroEventManager.setCharacterName(characterName);
        characterInput.setText(characterName);
        guildCheckbox.setSelected(App.saveManager.saveFile.showGuildName);
        kickCheckbox.setSelected(App.saveManager.saveFile.closeOnKick);
        quickPasteCheckbox.setSelected(false);
        colorThemeCombo.setSelectedItem(ColorTheme.LIGHT_THEME);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        characterLabel.setForeground(ColorManager.TEXT);
        guildLabel.setForeground(ColorManager.TEXT);
        kickLabel.setForeground(ColorManager.TEXT);
        quickPasteLabel.setForeground(ColorManager.TEXT);
        colorThemeLabel.setForeground(ColorManager.TEXT);
    }

}
