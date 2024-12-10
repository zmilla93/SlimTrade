package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.gui.components.slimtrade.ResultLabel;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;

public class StashFolderSetupPanel extends AbstractSetupPanel {

    private final JRadioButton yesRadioButton = new JRadioButton("Yes");
    private final JRadioButton noRadioButton = new JRadioButton("No");

    public StashFolderSetupPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(yesRadioButton);
        buttonGroup.add(noRadioButton);

        AbstractOptionPanel panel = new AbstractOptionPanel(false, false);
        panel.addHeader("Stash Tab Folders");
        panel.addComponent(new JLabel("Do you use any stash tab folders? Doing so affects UI alignment."));
        panel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "This settings needs to be updated manually if changed."));
        panel.addVerticalStrutSmall();
        panel.addComponent(yesRadioButton);
        panel.addComponent(noRadioButton);

        contentPanel.add(panel, BorderLayout.CENTER);

        addListeners();
    }

    private void addListeners() {
        yesRadioButton.addActionListener(e -> runSetupValidation());
        noRadioButton.addActionListener(e -> runSetupValidation());
    }

    public boolean isUsingFolders() {
        return yesRadioButton.isSelected();
    }

    @Override
    public void initializeComponents() {
        // FIXME: Implement this!
    }

    @Override
    public boolean isSetupValid() {
        return yesRadioButton.isSelected() || noRadioButton.isSelected();
    }

    @Override
    public void applyCompletedSetup() {
        // TODO: this
    }

}
