package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.gui.components.slimtrade.ResultLabel;

import javax.swing.*;

public class StashFolderSetupPanel extends AbstractSetupPanel {

    private final JRadioButton yesRadioButton = new JRadioButton("Yes");
    private final JRadioButton noRadioButton = new JRadioButton("No");

    public StashFolderSetupPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(yesRadioButton);
        buttonGroup.add(noRadioButton);

        addHeader("Stash Tab Folders");
        addComponent(new JLabel("Do you use any stash tab folders? Doing so affects UI alignment."));
        addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "This settings needs to be updated manually if changed."));
        addVerticalStrutSmall();
        addComponent(yesRadioButton);
        addComponent(noRadioButton);

        addListeners();
    }

    private void addListeners() {
        yesRadioButton.addActionListener(e -> runSetupValidation());
        noRadioButton.addActionListener(e -> runSetupValidation());
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
