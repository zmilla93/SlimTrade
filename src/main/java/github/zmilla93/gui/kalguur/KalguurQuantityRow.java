package github.zmilla93.gui.kalguur;

import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.buttons.IconButton;

import javax.swing.*;
import java.awt.*;

public class KalguurQuantityRow extends JPanel {

    private final int oreCount;

    public KalguurQuantityRow(Window parentWindow, KalguurCalculatorPanel calcPanel, Container parentContainer, int oreCount) {
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
