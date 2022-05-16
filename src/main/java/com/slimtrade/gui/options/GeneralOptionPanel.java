package com.slimtrade.gui.options;

import com.slimtrade.gui.options.general.BasicsPanel;
import com.slimtrade.gui.options.general.EnableFeaturesPanel;
import com.slimtrade.gui.options.general.PathOfExilePanel;

public class GeneralOptionPanel extends AbstractOptionPanel {

    public GeneralOptionPanel() {

        addHeader("Basics");
        addPanel(new BasicsPanel());
        addVerticalStrut();
        addHeader("Enable Features");
        addPanel(new EnableFeaturesPanel());
        addVerticalStrut();
        addHeader("Path of Exile");
        addPanel(new PathOfExilePanel());

    }

}
