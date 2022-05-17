package com.slimtrade.gui.options.stash;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;

public class StashOptionPanel extends AbstractOptionPanel {

    private JButton addButton = new JButton("Add Stash Tab");
    private JPanel tabContainer = new JPanel(new GridBagLayout());
    private GridBagConstraints gc = ZUtil.getGC();

    public StashOptionPanel() {
        addHeader("Stash Tabs");
        addPanel(addButton);
        addPanel(tabContainer);
        addListeners();
    }

    private void addListeners() {
        Container self = this;
        addButton.addActionListener(e -> {
            gc.gridy = tabContainer.getComponentCount();
            tabContainer.add(new StashRow(tabContainer), gc);
            revalidate();
            repaint();
        });
    }

}
