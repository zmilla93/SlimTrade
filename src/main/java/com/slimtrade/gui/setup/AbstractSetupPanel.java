package com.slimtrade.gui.setup;

import javax.swing.*;

public abstract class AbstractSetupPanel extends JPanel {

    JButton nextButton;

    public AbstractSetupPanel(JButton nextButton) {
        this.nextButton = nextButton;
    }

    public abstract boolean isSetupValid();

    public void validateNextButton() {
        nextButton.setEnabled(isSetupValid());
    }

}
