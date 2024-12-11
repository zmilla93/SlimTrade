package github.zmilla93.gui.components;

import github.zmilla93.core.ninja.NinjaTabType;
import github.zmilla93.gui.ninja.NinjaGridPanel;

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
