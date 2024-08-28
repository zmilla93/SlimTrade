package com.slimtrade.gui.options;

import javax.swing.*;

public class OptionListPanel {

    public final String title;
    public final JPanel panel;
    public final boolean isSeparator;

    public OptionListPanel() {
        this.title = null;
        this.panel = null;
        isSeparator = true;
    }

    public OptionListPanel(String title) {
        this.title = title;
        this.panel = null;
        isSeparator = true;
    }

    public OptionListPanel(String title, JPanel panel) {
        this.title = title;
        this.panel = panel;
        isSeparator = false;

    }

    @Override
    public String toString() {
        return title;
    }

}
