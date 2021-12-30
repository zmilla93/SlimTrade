package com.slimtrade.gui.options;

import com.slimtrade.gui.options.general.BasicsPanel;

public class GeneralOptionPanel extends AbstractOptionPanel {

    public GeneralOptionPanel() {

        BasicsPanel basicsPanel = new BasicsPanel();

        addHeader("Basics");
        addPanel(basicsPanel);

    }

}
