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
        addComponent(basicsPanel);
        addVerticalStrut();
        addHeader("Enable Features");
        addComponent(new EnableFeaturesPanel());
        addVerticalStrut();
        addHeader("Message Popups");
        addComponent(new MessageSettingsPanel());
        addVerticalStrut();
        addHeader("History");
        addComponent(new HistoryOptionPanel());
        addVerticalStrut();
        addHeader("Path of Exile");
        addComponent(new PathOfExilePanel());
        addComponent(stashButton);
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
