package com.slimtrade.gui.options.macros;

import java.awt.*;
import java.util.Random;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.basic.SectionHeader;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.enums.PreloadedImageCustom;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;

public class OutgoingCustomizer extends ContainerPanel implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;
    private MessagePanel exampleTradeIn;

    private AbstractWindow parent;

    private JTextField thankLeft;
    private JTextField thankRight;

    private AddRemovePanel customPanel;

    private JButton addButton = new BasicButton("Add Custom Macro");
    private JPanel presetPanel = new JPanel(FrameManager.gridBag);
    private JPanel presetTextPanel = new JPanel(FrameManager.gridBag);

    private String left = "Left Click";
    private String right = "Right Click";
    public static final int CUSTOM_MAX = 10;

    public OutgoingCustomizer(AbstractWindow parent) {
        this.setVisible(false);
        this.parent = parent;
        refreshTrade();

        // INCOMING PRESETS
        PresetMacroRow refreshInPreset = new PresetMacroRow(PreloadedImage.REFRESH.getImage(), true);
        refreshInPreset.getRow(left, "Resend Trade Whisper");
        PresetMacroRow closePreset = new PresetMacroRow(PreloadedImage.CLOSE.getImage());
        closePreset.getRow(left, "Close Trade");
        closePreset.getRow(right, "Save Trade + close all other similar trades");
        PresetMacroRow warpPreset = new PresetMacroRow(PreloadedImage.WARP.getImage(), true);
        warpPreset.getRow(left, "Warp to Seller");

        PresetMacroRow thankPreset = new PresetMacroRow(PreloadedImage.THUMB.getImage());
        thankLeft = thankPreset.getRow(left, "", true);
        thankRight = thankPreset.getRow(right, "", true);
        PresetMacroRow leavePreset = new PresetMacroRow(PreloadedImage.LEAVE.getImage(), true);
        leavePreset.getRow(left, "Leave Party");
        PresetMacroRow homePreset = new PresetMacroRow(PreloadedImage.HOME.getImage(), true);
        homePreset.getRow(left, "Warp to Hideout");

        PresetMacroRow usernamePreset = new PresetMacroRow("Username");
        usernamePreset.getRow(left, "/whois [seller]");
        usernamePreset.getRow(right, "Open empty whisper with seller");

        // INCOMING
        SectionHeader exampleHeader = new SectionHeader("Outgoing Trade");
        SectionHeader presetHeader = new SectionHeader("Preset Macros");
        SectionHeader customHeader = new SectionHeader("Custom Macros");

        customPanel = new AddRemovePanel();
        customPanel.setBorder(null);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        //Preset Macros
        gc.insets.bottom = 4;
        presetPanel.add(refreshInPreset, gc);
        gc.gridy++;
        presetPanel.add(closePreset, gc);
        gc.gridy++;
        presetPanel.add(warpPreset, gc);
        gc.gridy++;
        presetPanel.add(thankPreset, gc);
        gc.gridy++;
        presetPanel.add(leavePreset, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        presetPanel.add(homePreset, gc);
        gc.gridy++;

        // Text Panels
        gc.gridy = 0;
        presetTextPanel.add(usernamePreset, gc);

        //Everything
        gc.gridy = 0;
        container.setLayout(new GridBagLayout());
        gc.insets.bottom = FrameManager.gapSmall;
        container.add(exampleHeader, gc);
        gc.gridy++;
        gc.insets.bottom = FrameManager.gapLarge;
        container.add(exampleTradeIn, gc);
        gc.gridy++;
        gc.insets.bottom = FrameManager.gapSmall;
        container.add(presetHeader, gc);
        gc.gridy++;
        gc.insets.bottom = 4;
        container.add(presetPanel, gc);
        gc.gridy++;
        gc.insets.bottom = FrameManager.gapLarge;
        container.add(presetTextPanel, gc);
        gc.gridy++;

        gc.insets.bottom = FrameManager.gapSmall;
        container.add(customHeader, gc);
        gc.gridy++;
        container.add(addButton, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        container.add(customPanel, gc);
        addButton.addActionListener(e -> addNewMacro());
        load();
        App.eventManager.addColorListener(this);
        this.updateColor();
    }

    private void refreshTrade() {
        if(exampleTradeIn != null){
            container.remove(exampleTradeIn);
            App.eventManager.removeColorListener(exampleTradeIn);
        }
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets.bottom = FrameManager.gapLarge;
        Random rng = new Random();
        TradeOffer tradeIn = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SellerUsername", "Example Item", 1, "chaos", 20, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
        exampleTradeIn = new MessagePanel(tradeIn, new Dimension(400, 40), false);
        exampleTradeIn.stopTimer();
        container.add(exampleTradeIn, gc);
        parent.revalidate();
        parent.repaint();
    }

    private CustomMacroRow addNewMacro() {
        int i = 0;
        for (Component c : customPanel.getComponents()) {
            if (c.isVisible()) {
                i++;
            }
        }
        if (i >= CUSTOM_MAX) {
            return null;
        }
        CustomMacroRow row = new CustomMacroRow(customPanel);
        customPanel.addRemoveablePanel(row);
        return row;
    }

    public void revertChanges() {
        customPanel.revertChanges();
        load();
    }

    private void loadFromSave() {

        // Thank Button
        thankLeft.setText(App.saveManager.saveFile.thankOutgoingLMB);
        thankRight.setText(App.saveManager.saveFile.thankOutgoingRMB);

        // Custom Macros
        int i = 0;
        for(MacroButton macro : App.saveManager.saveFile.outgoingMacroButtons) {
            CustomMacroRow row = addNewMacro();
            ButtonRow buttonRow = macro.row;
            PreloadedImageCustom buttonImage = macro.image;
            row.setButtonRow(buttonRow);
            row.setButtonImage(buttonImage);
            row.setTextLMB(macro.leftMouseResponse);
            row.setTextRMB(macro.rightMouseResponse);
            if(++i >= CUSTOM_MAX){
                return;
            }
        }
    }

    public void save() {
        customPanel.saveChanges();
        // PRESET BUTTONS
        App.saveManager.saveFile.thankOutgoingLMB = thankLeft.getText();
        App.saveManager.saveFile.thankOutgoingRMB = thankRight.getText();
        // CUSTOM BUTTONS
        App.saveManager.saveFile.outgoingMacroButtons.clear();
        int index = 0;
        customPanel.saveChanges();
        for (Component c : customPanel.getComponents()) {
            CustomMacroRow row = (CustomMacroRow) c;
            App.saveManager.saveFile.outgoingMacroButtons.add(row.getMacroData());
        }
        // Refresh
        refreshTrade();
    }

    public void load() {
        customPanel.removeAll();
        loadFromSave();
        customPanel.updateColor();
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_1);
    }
}
