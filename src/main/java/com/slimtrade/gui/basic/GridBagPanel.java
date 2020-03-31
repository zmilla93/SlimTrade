package com.slimtrade.gui.basic;

import javax.swing.*;
import java.awt.*;

public class GridBagPanel extends JPanel {

    protected GridBagConstraints gc = new GridBagConstraints();

    public GridBagPanel() {
        super(new GridBagLayout());
        gc.gridx = 0;
        gc.gridy = 0;
    }

    public void removePanel() {
    }

}
