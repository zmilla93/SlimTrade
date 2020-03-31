package com.slimtrade.gui.options.macro;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.SectionHeader;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.messaging.MessageDialogManager;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MacroPanel extends ContainerPanel implements ISaveable {

    // Info
    JLabel info1 = new CustomLabel("Send whispers or commands using {player}, {self}, {item}, and {price} as variables. Check the box to close trade on use.");
    JLabel info2 = new CustomLabel("Hotkeys run the left click of the oldest trade. Plain messages have @{player} added automatically.");

    SectionHeader tradeHeader = new SectionHeader("Example");

    JPanel messageWrapper = new JPanel(new GridBagLayout());

    AddRemovePanel addRemovePanel = new AddRemovePanel();
    JButton addButton = new BasicButton("Add New Macro");

    public MacroPanel() {
        this.setVisible(false);
        int spacer = 5;

        // Username
        PresetMacroRow usernameMacro = new PresetMacroRow("Username");
        usernameMacro.setLMB("/whois {player}");
        usernameMacro.setRMB("Open empty whisper with {player}");
        usernameMacro.buildPanel();

        // Item
        PresetMacroRow itemMacro = new PresetMacroRow("Item Name");
        itemMacro.setLMB("Open Stash Highlighter");
        itemMacro.setRMB("Ignore Item");
        itemMacro.buildPanel();

        // Close Button
        PresetMacroRow closeMacro = new PresetMacroRow(DefaultIcons.CLOSE);
        closeMacro.setLMB("Close Trade");
        closeMacro.setRMB("Close all trades");
        closeMacro.setAddHotkey(true);
        closeMacro.buildPanel();

        gc.gridy++;
        container.add(tradeHeader, gc);
        gc.insets.top = spacer;
        gc.gridy++;

        container.add(messageWrapper, gc);
        gc.gridy++;

        container.add(new SectionHeader("Inbuilt Macros"), gc);
        gc.gridy++;
        container.add(usernameMacro, gc);
        gc.gridy++;
        container.add(itemMacro, gc);
        gc.gridy++;
        container.add(closeMacro, gc);
        gc.gridy++;

        container.add(new SectionHeader("Custom Macros"), gc);
        gc.gridy++;
        container.add(info1, gc);
        gc.insets.top = 0;
        gc.gridy++;
//        container.add(info2, gc);
//        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.top = spacer;
        container.add(addButton, gc);
        gc.gridy++;
        container.add(addRemovePanel, gc);

        TradeOffer t = new TradeOffer("", "", MessageType.INCOMING_TRADE, "<GLD>", "IncomingTrader123", "Item Name", 1, "chaos", 60, "sale", 1, 1, "", "");
        MessagePanel msg = new MessagePanel(t, MessageDialogManager.DEFAULT_SIZE);
        msg.stopTimer();
        setExampleMessage(msg);

        addButton.addActionListener(e -> {
            MacroCustomizerRow row = new MacroCustomizerRow();
            addRemovePanel.addRemoveablePanel(row);
            ColorManager.setTheme(ColorManager.getCurrentColorTheme());
            App.eventManager.recursiveColor(row);
        });

    }

    public void setExampleMessage(MessagePanel panel) {
        messageWrapper.removeAll();
        messageWrapper.add(panel, new GridBagConstraints());
//        panel.repaint();
    }

    @Override
    public void save() {
    }

    @Override
    public void load() {
    }

}
