package com.slimtrade.gui.options;

import com.slimtrade.gui.components.ErrorLabel;
import com.slimtrade.gui.options.general.KalguurSettingsPanel;

public class KalguurOptionPanel extends AbstractOptionPanel {

    public KalguurOptionPanel() {
        addHeader("Deprecated");
        addComponent(new ErrorLabel("This feature is for Path of Exile 1's Settlers of Kalguur league. It is likely to be removed in the future."));
        addVerticalStrut();
        addHeader("Kalguur Helper");
        addComponent(new KalguurSettingsPanel());
    }

}
