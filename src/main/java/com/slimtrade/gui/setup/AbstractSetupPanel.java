package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractSetupPanel extends JPanel {

    public static final int INSET_SIZE = 20;

    protected JButton nextButton;
    protected JPanel contentPanel = new JPanel(new BorderLayout());

    public AbstractSetupPanel(JButton nextButton) {
        this.nextButton = nextButton;
        setLayout(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(this, INSET_SIZE);
        add(contentPanel, BorderLayout.CENTER);
    }

    public abstract boolean isSetupValid();

    public void validateNextButton() {
        nextButton.setEnabled(isSetupValid());
    }

}
