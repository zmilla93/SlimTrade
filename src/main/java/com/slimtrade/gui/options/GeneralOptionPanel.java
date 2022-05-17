package com.slimtrade.gui.options;

import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.general.BasicsPanel;
import com.slimtrade.gui.options.general.DisplaySettingsPanel;
import com.slimtrade.gui.options.general.EnableFeaturesPanel;
import com.slimtrade.gui.options.general.PathOfExilePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralOptionPanel extends AbstractOptionPanel {

    private JButton stashButton = new JButton("Adjust Stash Location");

    public GeneralOptionPanel() {

        addHeader("Basics");
        addPanel(new BasicsPanel());
        addVerticalStrut();
        addHeader("Display");
        addPanel(new DisplaySettingsPanel());
        addVerticalStrut();
        addHeader("Enable Features");
        addPanel(new EnableFeaturesPanel());
        addVerticalStrut();
        addHeader("Path of Exile");
        addPanel(new PathOfExilePanel());
        addPanel(stashButton);

        addListeners();
    }

    private void addListeners() {
        stashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameManager.optionsWindow.setVisible(false);
                FrameManager.stashGridWindow.setVisible(true);
            }
        });
    }

}
