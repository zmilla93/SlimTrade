package com.slimtrade.gui.options;

import com.slimtrade.gui.options.general.BasicsPanel;
import com.slimtrade.gui.options.general.EnableFeaturesPanel;

public class GeneralOptionPanel extends AbstractOptionPanel {

    public GeneralOptionPanel() {

        BasicsPanel basicsPanel = new BasicsPanel();
        EnableFeaturesPanel enableFeaturesPanel = new EnableFeaturesPanel();

        addHeader("Basics");
        addPanel(basicsPanel);
        addHeader("Enable Features");
        addPanel(enableFeaturesPanel);

    }

}
