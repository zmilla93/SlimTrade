package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class FinishSetupPanel extends JPanel {

    public FinishSetupPanel() {
        GridBagConstraints gc = ZUtil.getGC();
        setLayout(new GridBagLayout());
        add(new JLabel("Setup complete!"), gc);
        gc.gridy++;
        add(new JLabel("Stay sane, exile."), gc);
    }

}
