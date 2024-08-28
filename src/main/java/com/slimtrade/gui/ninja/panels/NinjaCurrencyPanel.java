package com.slimtrade.gui.ninja.panels;

import com.slimtrade.core.ninja.NinjaTabType;
import com.slimtrade.gui.ninja.AbstractNinjaGridPanel;

public class NinjaCurrencyPanel extends AbstractNinjaGridPanel {

    public NinjaCurrencyPanel() {
        super(null);
    }

    @Override
    public NinjaTabType getTabType() {
        return NinjaTabType.CURRENCY;
    }

}
