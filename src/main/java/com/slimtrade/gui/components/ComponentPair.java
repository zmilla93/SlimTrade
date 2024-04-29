package com.slimtrade.gui.components;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class ComponentPair extends JPanel {

    private static final int DEFAULT_INSET = 5;

    public ComponentPair(Component component1, Component component2) {
        this(component1, component2, DEFAULT_INSET);
    }

    public ComponentPair(Component component1, Component component2, int insetSize) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        add(component1, gc);
        gc.gridx++;
        gc.insets.left = insetSize;
        add(component2, gc);
    }

}
