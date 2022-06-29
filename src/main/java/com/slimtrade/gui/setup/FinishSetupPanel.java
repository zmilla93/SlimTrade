package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class FinishSetupPanel extends JPanel {

    public FinishSetupPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        mainPanel.add(new JLabel("Setup Complete."), gc);
        gc.gridy++;
        mainPanel.add(new JLabel("Enjoy Trading, Exile!"), gc);
        setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        gc.insets = SetupWindow.OUTER_INSETS;
        add(mainPanel, gc);
    }

}
