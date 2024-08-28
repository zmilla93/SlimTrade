package com.slimtrade.gui.ninja.panels;

import com.slimtrade.core.ninja.NinjaTabType;
import com.slimtrade.gui.ninja.AbstractNinjaGridPanel;

public class NinjaFragmentsPanel extends AbstractNinjaGridPanel {

    public NinjaFragmentsPanel() {
        super("fragments");
    }

    @Override
    public NinjaTabType getTabType() {
        return NinjaTabType.FRAGMENTS;
    }

}
