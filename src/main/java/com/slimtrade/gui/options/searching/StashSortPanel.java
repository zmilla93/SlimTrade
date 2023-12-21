package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class StashSortPanel extends JPanel {

    private final JTextField titleField = new JTextField();

    public StashSortPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
    }

}
