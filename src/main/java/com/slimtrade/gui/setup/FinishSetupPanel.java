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

        setLayout(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(this, AbstractSetupPanel.INSET_SIZE);
        add(mainPanel, BorderLayout.CENTER);
    }

}
