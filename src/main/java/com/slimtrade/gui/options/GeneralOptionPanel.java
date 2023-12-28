package com.slimtrade.gui.options;

import com.slimtrade.gui.options.general.*;

public class GeneralOptionPanel extends AbstractOptionPanel {

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
        addComponent(new ClientPathPanel());
    }

    public BasicsPanel getBasicsPanel() {
        return basicsPanel;
    }

}
