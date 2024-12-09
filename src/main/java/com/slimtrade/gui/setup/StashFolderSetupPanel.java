package com.slimtrade.gui.setup;

import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;

public class StashFolderSetupPanel extends AbstractSetupPanel {

    private final JRadioButton yesRadioButton = new JRadioButton("Yes");
    private final JRadioButton noRadioButton = new JRadioButton("No");

    public StashFolderSetupPanel(JButton nextButton) {
        super(nextButton);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(yesRadioButton);
        buttonGroup.add(noRadioButton);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        buttonPanel.add(yesRadioButton);
        buttonPanel.add(noRadioButton);

        AbstractOptionPanel panel = new AbstractOptionPanel(false, false);
        panel.addHeader("Stash Tab Folders");
        panel.addComponent(new JLabel("Do you use any stash tab folders?"));
        panel.addComponent(new JLabel("Using folders affects UI alignment. This settings needs to be updated manually."));
        panel.addVerticalStrutSmall();
        panel.addComponent(yesRadioButton);
        panel.addComponent(noRadioButton);

//        contentPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gc = ZUtil.getGC();
//        contentPanel.add(new JLabel("Do you use any stash tab folders?"), gc);
//        gc.gridy++;
        contentPanel.add(panel, BorderLayout.CENTER);

        yesRadioButton.addActionListener(e -> validateNextButton());
        noRadioButton.addActionListener(e -> validateNextButton());
    }

    public boolean isUsingFolders() {
        return yesRadioButton.isSelected();
    }

    @Override
    public boolean isSetupValid() {
        return yesRadioButton.isSelected() || noRadioButton.isSelected();
    }

}
