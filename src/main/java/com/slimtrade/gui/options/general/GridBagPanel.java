package com.slimtrade.gui.options.general;

import javax.swing.*;
import java.awt.*;

public class GridBagPanel extends JPanel {

    protected GridBagConstraints gc;

    public GridBagPanel() {
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
    }

}
