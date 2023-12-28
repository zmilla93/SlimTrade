package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class StashFolderSetupPanel extends AbstractSetupPanel {

    private final JRadioButton yesButton = new JRadioButton("Yes");
    private final JRadioButton noButton = new JRadioButton("No");

    public StashFolderSetupPanel(JButton nextButton) {
        super(nextButton);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(yesButton);
        buttonGroup.add(noButton);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        contentPanel.add(new JLabel("Are you using any stash folders?"), gc);
        gc.gridy++;
        contentPanel.add(buttonPanel, gc);

        yesButton.addActionListener(e -> validateNextButton());
        noButton.addActionListener(e -> validateNextButton());
    }

    public boolean isUsingFolders() {
        return yesButton.isSelected();
    }

    @Override
    public boolean isSetupValid() {
        return yesButton.isSelected() || noButton.isSelected();
    }

}
