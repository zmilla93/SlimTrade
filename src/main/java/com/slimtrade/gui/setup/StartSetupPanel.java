package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class StartSetupPanel extends JPanel {

    public StartSetupPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        add(new JLabel("Welcome to SlimTrade!"), gc);
        gc.gridy++;
        add(new JLabel("Let's do some quick setup."), gc);
        gc.gridy++;
    }

}
