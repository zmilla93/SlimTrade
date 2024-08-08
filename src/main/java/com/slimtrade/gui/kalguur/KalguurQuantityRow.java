package com.slimtrade.gui.kalguur;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;

import javax.swing.*;
import java.awt.*;

public class KalguurQuantityRow extends JPanel {

    private final int oreCount;

    public KalguurQuantityRow(JDialog parentWindow,KalguurCalculatorPanel calcPanel, Container parentContainer, int oreCount) {
        this.oreCount = oreCount;
        int ingotCount = (int) Math.ceil(oreCount / 5f);
        JLabel label = new JLabel(oreCount + " ~ " + ingotCount);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        add(label, gc);
        gc.gridx++;
        gc.weightx = 0;
        JButton deleteButton = new IconButton(DefaultIcon.CLOSE);
        add(deleteButton, gc);
        deleteButton.addActionListener(e -> {
            parentContainer.remove(KalguurQuantityRow.this);
            parentWindow.pack();
            calcPanel.save();
        });
    }

    public int getOreCount() {
        return oreCount;
    }

}
