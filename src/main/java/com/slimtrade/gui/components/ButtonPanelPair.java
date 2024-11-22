package com.slimtrade.gui.components;

import com.slimtrade.core.ninja.NinjaTabType;
import com.slimtrade.gui.ninja.NinjaGridPanel;

import javax.swing.*;

public class ButtonPanelPair {

    public final JButton button;
    public final NinjaGridPanel panel;
    public final NinjaTabType tabType;

    public ButtonPanelPair(JButton button, NinjaGridPanel panel, NinjaTabType tabType) {
        this.button = button;
        this.panel = panel;
        this.tabType = tabType;
    }

}
