package com.slimtrade.gui.components;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    public final GridBagConstraints gc = ZUtil.getGC();

    private static final int HORIZONTAL_INSET = 5;
    private static final int VERTICAL_INSET = 5;

    public ButtonPanel() {
        setLayout(new GridBagLayout());
        gc.insets = new Insets(VERTICAL_INSET, HORIZONTAL_INSET, VERTICAL_INSET, 0);
    }

    @Override
    public Component add(Component comp) {
        gc.insets.right = HORIZONTAL_INSET;
        add(comp, gc);
        gc.gridx++;
        return comp;
    }
}
