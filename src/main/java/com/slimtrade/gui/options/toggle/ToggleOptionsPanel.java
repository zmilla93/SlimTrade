package com.slimtrade.gui.options.toggle;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.custom.CustomCheckbox;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class ToggleOptionsPanel extends ContainerPanel implements ISaveable, IColorable {

    private JLabel info1;
    private JLabel updateLabel;
    private JLabel incomingLabel;
    private JLabel outgoingLabel;
    private JLabel itemHighlighterLabel;
    private JLabel menubarLabel;

    private JCheckBox updateCheckbox;
    private JCheckBox incomingCheckbox;
    private JCheckBox outgoingCheckbox;
    private JCheckBox itemHighlighterCheckbox;
    private JCheckBox menubarCheckbox;

    public ToggleOptionsPanel() {

        info1 = new CustomLabel("Check boxes to enable features.");
        updateLabel = new CustomLabel("Update Automatically");
        incomingLabel = new CustomLabel("Incoming Messages");
        outgoingLabel = new CustomLabel("Outgoing Messages");
        itemHighlighterLabel = new CustomLabel("Item Highlighter");
        menubarLabel = new CustomLabel("Menubar Button");

        itemHighlighterLabel.setToolTipText("Disables SlimTrade's item highlighter shown during incoming trades. The stash info window and search features are unaffected.");
        menubarLabel.setToolTipText("Hides the small icon button used to access the options. Use the system tray for all of the same features.");

        updateCheckbox = new CustomCheckbox();
        incomingCheckbox = new CustomCheckbox();
        outgoingCheckbox = new CustomCheckbox();
        itemHighlighterCheckbox = new CustomCheckbox();
        menubarCheckbox = new CustomCheckbox();

        // Horizontal Strut + Info
        gc.weightx = 1;
        gc.gridwidth = 2;
        container.add(Box.createHorizontalStrut(240), gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        container.add(info1, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        gc.gridwidth = 1;

        // Auto Update
        gc.anchor = GridBagConstraints.WEST;
        container.add(updateLabel, gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        container.add(updateCheckbox, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Incoming Messages
        gc.anchor = GridBagConstraints.WEST;
        container.add(incomingLabel, gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        container.add(incomingCheckbox, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Outgoing Messages
        gc.anchor = GridBagConstraints.WEST;
        container.add(outgoingLabel, gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        container.add(outgoingCheckbox, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Item Highlighter
        gc.anchor = GridBagConstraints.WEST;
        container.add(itemHighlighterLabel, gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        container.add(itemHighlighterCheckbox, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Menubar
        gc.anchor = GridBagConstraints.WEST;
        container.add(menubarLabel, gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        container.add(menubarCheckbox, gc);
        gc.gridx = 0;
        gc.gridy++;

    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBackground(ColorManager.BACKGROUND);
    }

    @Override
    public void save() {
        App.saveManager.settingsSaveFile.autoUpdate = updateCheckbox.isSelected();
        App.saveManager.settingsSaveFile.enableIncomingTrades = incomingCheckbox.isSelected();
        App.saveManager.settingsSaveFile.enableOutgoingTrades = outgoingCheckbox.isSelected();
        App.saveManager.settingsSaveFile.enableItemHighlighter = itemHighlighterCheckbox.isSelected();
        App.saveManager.settingsSaveFile.enableMenubar = menubarCheckbox.isSelected();
    }

    @Override
    public void load() {
        updateCheckbox.setSelected(App.saveManager.settingsSaveFile.autoUpdate);
        incomingCheckbox.setSelected(App.saveManager.settingsSaveFile.enableIncomingTrades);
        outgoingCheckbox.setSelected(App.saveManager.settingsSaveFile.enableOutgoingTrades);
        itemHighlighterCheckbox.setSelected(App.saveManager.settingsSaveFile.enableItemHighlighter);
        menubarCheckbox.setSelected(App.saveManager.settingsSaveFile.enableMenubar);
    }

}
