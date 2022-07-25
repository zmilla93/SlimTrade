package com.slimtrade.gui.options;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.general.*;

import javax.swing.*;

public class GeneralOptionPanel extends AbstractOptionPanel {

    // FIXME : Move stash button to poe panel
    private JButton stashButton = new JButton("Adjust Stash Location");
    private final BasicsPanel basicsPanel = new BasicsPanel();

    public GeneralOptionPanel() {

        addHeader("Basics");
        addPanel(basicsPanel);
        addVerticalStrut();
//        addHeader("Display");
//        addPanel(new DisplaySettingsPanel());
//        addVerticalStrut();
        addHeader("Enable Features");
        addPanel(new EnableFeaturesPanel());
        addVerticalStrut();
        addHeader("Message Popups");
        addPanel(new MessageSettingsPanel());
        addVerticalStrut();
        addHeader("History");
        addPanel(new HistoryOptionPanel());
        addVerticalStrut();
        addHeader("Path of Exile");
        addPanel(new PathOfExilePanel());
        addPanel(stashButton);
        addListeners();
    }

    public BasicsPanel getBasicsPanel() {
        return basicsPanel;
    }

    private void addListeners() {
        stashButton.addActionListener(e -> {
            FrameManager.setWindowVisibility(AppState.EDIT_STASH);
        });
    }

}
