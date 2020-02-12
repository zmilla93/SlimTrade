package com.slimtrade.gui.scanner;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.options.macros.PresetMacroRow;

import javax.swing.*;
import java.awt.*;

public class SearchMacroPanel extends JPanel implements IColorable {

    public JButton addMacroButton = new BasicButton("Add Macro");
    public AddRemovePanel addRemovePanel = new AddRemovePanel();
    public JTextField thankLeft;
    public JTextField thankRight;

    public SearchMacroPanel() {
        super(FrameManager.gridBag);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        String either = "Either";
        String left = "Left Click";
        String right = "Right Click";

        // Preset Macros
        JPanel presetMacroPanel = new JPanel(FrameManager.gridBag);

        PresetMacroRow closePreset = new PresetMacroRow(PreloadedImage.CLOSE.getImage());
        closePreset.getRow(left, "Close trade");
        closePreset.getRow(right, "Close trade + all similar trades");

        PresetMacroRow invitePreset = new PresetMacroRow(PreloadedImage.INVITE.getImage(), true);
        invitePreset.getRow(either, "Invite to Party");

        PresetMacroRow warpPreset = new PresetMacroRow(PreloadedImage.WARP.getImage(), true);
        warpPreset.getRow(either, "Warp to Player");

        PresetMacroRow tradePreset = new PresetMacroRow(PreloadedImage.CART.getImage(), true);
        tradePreset.getRow(either, "Send Trade Offer");

        PresetMacroRow thankPreset = new PresetMacroRow(PreloadedImage.THUMB.getImage());
        thankLeft = thankPreset.getRow(left, "", true);
        thankRight = thankPreset.getRow(right, "", true);

        PresetMacroRow leavePreset = new PresetMacroRow(PreloadedImage.LEAVE.getImage(), true);
        leavePreset.getRow(either, "Leave Party");

        PresetMacroRow homePreset = new PresetMacroRow(PreloadedImage.HOME.getImage(), true);
        homePreset.getRow(either, "Warp to Hideout");

        PresetMacroRow usernamePreset = new PresetMacroRow("Username");
        usernamePreset.getRow(left, "/whois [username]");
        usernamePreset.getRow(right, "Open empty whisper with buyer");

        PresetMacroRow itemPreset = new PresetMacroRow("Item Name");
        itemPreset.getRow(left, "Open Stash Highlighter");
        itemPreset.getRow(right, "Ignore Item");

        // Build Main Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 2;
        presetMacroPanel.add(invitePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(warpPreset, gc);
        gc.gridy++;
        presetMacroPanel.add(tradePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(thankPreset, gc);
        gc.gridy++;
        presetMacroPanel.add(leavePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(homePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(usernamePreset, gc);
        gc.gridy = 0;
        gc.insets.bottom = 5;
        gc.fill = GridBagConstraints.NONE;
        this.add(presetMacroPanel, gc);
        gc.gridy++;
        gc.insets.bottom = 2;
        this.add(addMacroButton, gc);
        gc.gridy++;
        this.add(addRemovePanel, gc);
        gc.insets.bottom = 0;
        gc.gridy = 0;
        App.eventManager.addColorListener(this);
        this.updateColor();
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
        this.setBackground(Color.RED);
    }
}
