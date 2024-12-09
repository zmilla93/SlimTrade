package com.slimtrade.gui.setup;

import com.slimtrade.core.poe.Game;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.slimtrade.POEFolderPicker;
import com.slimtrade.gui.components.slimtrade.POEInstallFolderExplanationPanel;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;

public class ClientSetupPanel extends AbstractSetupPanel {

    //    private final JCheckBox poe1NotInstalledCheckbox = new JCheckBox("Not Installed");
//    private final JCheckBox poe2NotInstalledCheckbox = new JCheckBox("Not Installed");
    private final JButton moreInfoButton = new JButton("Detailed Explanation");

    public ClientSetupPanel(JButton nextButton) {
        super(nextButton);
        AbstractOptionPanel panel = new AbstractOptionPanel(false, false);
        POEInstallFolderExplanationPanel moreInfoPanel = new POEInstallFolderExplanationPanel(true, false);
        panel.addHeader("Path of Exile Install Folder");
        panel.addComponent(new JLabel("SlimTrade needs to know where Path of Exile is installed in order to read chat logs."));
        panel.addVerticalStrut();
        panel.addHeader(Game.PATH_OF_EXILE_1.toString());
        panel.addComponent(new POEFolderPicker(Game.PATH_OF_EXILE_1));
        panel.addVerticalStrut();
        panel.addHeader(Game.PATH_OF_EXILE_2.toString());
        panel.addComponent(new POEFolderPicker(Game.PATH_OF_EXILE_2));
        panel.addVerticalStrut();
        panel.addComponent(moreInfoButton);
        panel.addComponent(moreInfoPanel);

        contentPanel.add(panel, BorderLayout.CENTER);
        moreInfoButton.addActionListener(e -> {
            moreInfoPanel.setVisible(!moreInfoPanel.isVisible());
            ZUtil.packComponentWindow(this);
        });
    }

    @Override
    public boolean isSetupValid() {
        return true;
    }

}
