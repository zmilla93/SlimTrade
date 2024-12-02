package com.slimtrade.gui.options;

import com.slimtrade.gui.options.general.KalguurSettingsPanel;

public class KalguurOptionPanel extends AbstractOptionPanel {

    public KalguurOptionPanel() {
        addHeader("Kalguur Helper");
        addComponent(new KalguurSettingsPanel());
    }

}
