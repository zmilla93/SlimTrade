package com.slimtrade.gui.options.general;

import com.slimtrade.App;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class GridBagPanel extends JPanel {

    protected GridBagConstraints gc;

    public GridBagPanel() {
        if (App.debugUIBorders >= 1) setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
    }

}
