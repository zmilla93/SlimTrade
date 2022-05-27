package com.slimtrade.gui.options;

import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.general.BasicsPanel;
import com.slimtrade.gui.options.general.EnableFeaturesPanel;
import com.slimtrade.gui.options.general.HistoryOptionPanel;
import com.slimtrade.gui.options.general.PathOfExilePanel;

import javax.swing.*;

public class GeneralOptionPanel extends AbstractOptionPanel {

    // FIXME : Move stash button to poe panel
    private JButton stashButton = new JButton("Adjust Stash Location");

    public GeneralOptionPanel() {

        addHeader("Basics");
        addPanel(new BasicsPanel());
        addVerticalStrut();
//        addHeader("Display");
//        addPanel(new DisplaySettingsPanel());
//        addVerticalStrut();
        addHeader("Enable Features");
        addPanel(new EnableFeaturesPanel());
        addVerticalStrut();
        addHeader("History");
        addPanel(new HistoryOptionPanel());
        addVerticalStrut();
        addHeader("Path of Exile");
        addPanel(new PathOfExilePanel());
        addPanel(stashButton);
        addListeners();
    }

    private void addListeners() {
        stashButton.addActionListener(e -> {
            FrameManager.optionsWindow.setVisible(false);
            FrameManager.stashGridWindow.setVisible(true);
        });
    }

}
