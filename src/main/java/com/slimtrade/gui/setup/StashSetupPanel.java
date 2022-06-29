package com.slimtrade.gui.setup;

import javax.swing.*;

public class StashSetupPanel extends AbstractSetupPanel {

    public StashSetupPanel(JButton button) {
        super(button);
        add(new JLabel("STASH"));
    }

    @Override
    public boolean isSetupValid() {
        return false;
    }

}
