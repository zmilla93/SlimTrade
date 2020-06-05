package com.slimtrade.gui.scanner;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;

import javax.swing.*;
import java.awt.*;

import static com.slimtrade.gui.scanner.ChatScannerWindow.bufferOuter;

public class SearchMacroPanel extends JPanel implements IColorable {

    private JPanel outerPanel = new JPanel(new GridBagLayout());
    JPanel presetMacroPanel;

    public JButton addMacroButton = new BasicButton("Add Macro");
    public AddRemovePanel addRemovePanel = new AddRemovePanel();
    public JTextField thankLeft;
    public JTextField thankRight;

    public SearchMacroPanel() {
        super(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        String either = "Either";
        String left = "Left Click";
        String right = "Right Click";

        // Preset Macros
        presetMacroPanel = new JPanel(new GridBagLayout());

//        PresetMacroRow closePreset = new PresetMacroRow(DefaultIcons.CLOSE);
//        closePreset.getRow(left, "Close trade");
//        closePreset.getRow(right, "Close trade + all similar trades");
//
//        PresetMacroRow invitePreset = new PresetMacroRow(DefaultIcons.INVITE, true);
//        invitePreset.getRow(either, "Invite to Party");
//
//        PresetMacroRow warpPreset = new PresetMacroRow(DefaultIcons.WARP, true);
//        warpPreset.getRow(either, "Warp to Player");
//
//        PresetMacroRow tradePreset = new PresetMacroRow(DefaultIcons.CART, true);
//        tradePreset.getRow(either, "Send Trade Offer");
//
//        PresetMacroRow thankPreset = new PresetMacroRow(DefaultIcons.THUMB);
//        thankLeft = thankPreset.getRow(left, "", true);
//        thankRight = thankPreset.getRow(right, "", true);
//
//        PresetMacroRow leavePreset = new PresetMacroRow(DefaultIcons.LEAVE, true);
//        leavePreset.getRow(either, "Leave Party");
//
//        PresetMacroRow homePreset = new PresetMacroRow(DefaultIcons.HOME, true);
//        homePreset.getRow(either, "Warp to Hideout");
//
//        PresetMacroRow usernamePreset = new PresetMacroRow("Username");
//        usernamePreset.getRow(left, "/whois [username]");
//        usernamePreset.getRow(right, "Open empty whisper with buyer");
//
//        PresetMacroRow itemPreset = new PresetMacroRow("Item Name");
//        itemPreset.getRow(left, "Open Stash Highlighter");
//        itemPreset.getRow(right, "Ignore Item");
//
//        // Build Main Panel
//        gc.fill = GridBagConstraints.BOTH;
//        gc.insets.bottom = 2;
//        presetMacroPanel.add(invitePreset, gc);
//        gc.gridy++;
//        presetMacroPanel.add(warpPreset, gc);
//        gc.gridy++;
//        presetMacroPanel.add(tradePreset, gc);
//        gc.gridy++;
//        presetMacroPanel.add(thankPreset, gc);
//        gc.gridy++;
//        presetMacroPanel.add(leavePreset, gc);
//        gc.gridy++;
//        presetMacroPanel.add(homePreset, gc);
//        gc.gridy++;
//        presetMacroPanel.add(usernamePreset, gc);
        gc.gridy = 0;
        gc.insets.bottom = 5;
        gc.fill = GridBagConstraints.NONE;
        outerPanel.add(presetMacroPanel, gc);
        gc.gridy++;
        outerPanel.add(addMacroButton, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        outerPanel.add(addRemovePanel, gc);

        gc = new GridBagConstraints();
        gc.insets = new Insets(bufferOuter, bufferOuter, bufferOuter, bufferOuter);
        this.add(outerPanel, gc);

//        outerPanel.setBackground(ColorManager.CLEAR);
//        presetMacroPanel.setBackground(ColorManager.CLEAR);
//        addRemovePanel.setBackground(ColorManager.CLEAR);
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(ColorManager.BORDER_TEXT);
        outerPanel.setBackground(Color.ORANGE);
        outerPanel.setBackground(ColorManager.BACKGROUND);
        presetMacroPanel.setBackground(Color.GREEN);
        presetMacroPanel.setBackground(ColorManager.BACKGROUND);
        addRemovePanel.setBackground(ColorManager.BACKGROUND);
    }
}
