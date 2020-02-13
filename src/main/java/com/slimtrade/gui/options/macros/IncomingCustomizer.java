package com.slimtrade.gui.options.macros;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.SectionHeader;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImageCustom;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

//TODO : CLEANUP
public class IncomingCustomizer extends ContainerPanel implements ISaveable, IColorable {

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
    private String either = "Either";
    public static final int CUSTOM_MAX = 10;

    public IncomingCustomizer(AbstractWindow parent) {
        this.setVisible(false);
        this.parent = parent;

        refreshTrade();

        // INCOMING PRESETS
        PresetMacroRow refreshInPreset = new PresetMacroRow(PreloadedImage.REFRESH.getImage(), true);
        refreshInPreset.getRow(either, "Hi, are you still interested in my [item] listed for [price]?");
        PresetMacroRow closePreset = new PresetMacroRow(PreloadedImage.CLOSE.getImage());
        closePreset.getRow(left, "Close trade");
        closePreset.getRow(right, "Close trade + all similar trades");

        PresetMacroRow invitePreset = new PresetMacroRow(PreloadedImage.INVITE.getImage(), true);
        invitePreset.getRow(either, "Invite to Party");
        PresetMacroRow tradePreset = new PresetMacroRow(PreloadedImage.CART.getImage(), true);
        tradePreset.getRow(either, "Send Trade Offer");
        PresetMacroRow thankPreset = new PresetMacroRow(PreloadedImage.THUMB.getImage());
        thankLeft = thankPreset.getRow(left, "", true);
        thankRight = thankPreset.getRow(right, "", true);
        PresetMacroRow leavePreset = new PresetMacroRow(PreloadedImage.LEAVE.getImage(), true);
        leavePreset.getRow(either, "Leave Party");

        PresetMacroRow usernamePreset = new PresetMacroRow("Username");
        usernamePreset.getRow(left, "/whois [buyer]");
        usernamePreset.getRow(right, "Open empty whisper with buyer");
        PresetMacroRow itemPreset = new PresetMacroRow("Item Name");
        itemPreset.getRow(left, "Open Stash Highlighter");
        itemPreset.getRow(right, "Ignore Item");

        // INCOMING
        SectionHeader exampleHeader = new SectionHeader("Incoming Trade");
        SectionHeader presetHeader = new SectionHeader("Preset Macros");
        SectionHeader customHeader = new SectionHeader("Custom Macros");

        customPanel = new AddRemovePanel();
        customPanel.setBorder(null);

        // gc.insets = new Insets(2, 0, 0, 0);
        // addRow(incomingLabel, gc);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.bottom = 4;

        //Preset Buttons
        presetPanel.add(refreshInPreset, gc);
        gc.gridy++;
        presetPanel.add(closePreset, gc);
        gc.gridy++;
        presetPanel.add(invitePreset, gc);
        gc.gridy++;
        presetPanel.add(tradePreset, gc);
        gc.gridy++;
        presetPanel.add(thankPreset, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        presetPanel.add(leavePreset, gc);
        gc.gridy++;

        // Text Panels
        gc.gridy = 0;
        gc.insets.bottom = 4;
        presetTextPanel.add(usernamePreset, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        presetTextPanel.add(itemPreset, gc);

        // Entire Panel
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
        if (exampleTradeIn != null) {
            container.remove(exampleTradeIn);
            App.eventManager.removeColorListener(exampleTradeIn);
        }
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets.bottom = FrameManager.gapLarge;
        Random rng = new Random();
        TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "BuyerUsername", "Example Item", 1, "chaos", 20, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
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
        thankLeft.setText(App.saveManager.saveFile.thankIncomingLMB);
        thankRight.setText(App.saveManager.saveFile.thankIncomingRMB);

        // Custom Macros
        int i = 0;
        for (MacroButton macro : App.saveManager.saveFile.incomingMacroButtons) {
            CustomMacroRow row = addNewMacro();
            ButtonRow buttonRow = macro.row;
            PreloadedImageCustom buttonImage = macro.image;
            row.setButtonRow(buttonRow);
            row.setButtonImage(buttonImage);
            row.setTextLMB(macro.leftMouseResponse);
            row.setTextRMB(macro.rightMouseResponse);
            if (++i >= CUSTOM_MAX) {
                return;
            }
        }
    }

    public void save() {
        customPanel.saveChanges();
        // PRESET BUTTONS
        App.saveManager.saveFile.thankIncomingLMB = thankLeft.getText();
        App.saveManager.saveFile.thankIncomingRMB = thankRight.getText();
        // CUSTOM BUTTONS
        App.saveManager.saveFile.incomingMacroButtons.clear();
        for (Component c : customPanel.getComponents()) {
            CustomMacroRow row = (CustomMacroRow) c;
            App.saveManager.saveFile.incomingMacroButtons.add(row.getMacroData());
        }
        // Refresh
        refreshTrade();
    }

    public void load() {
        customPanel.removeAll();
        loadFromSave();
        customPanel.updateColor();
    }

//    @Override
//    public void updateColor() {
//        super.updateColor();
////        presetPanel.setBackground(ColorManager.LOW_CONTRAST_1);
////        presetTextPanel.setBackground(ColorManager.LOW_CONTRAST_1);
//    }

}
