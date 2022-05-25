package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

public class ComponentPair extends JPanel {

    private static final int DEFAULT_INSET = 5;

    public ComponentPair(Component component1, Component component2) {
        this(component1, component2, DEFAULT_INSET);
    }

    public ComponentPair(Component component1, Component component2, int insetSize) {
        setLayout(new BorderLayout());
        add(component1, BorderLayout.WEST);
        add(Box.createHorizontalStrut(insetSize), BorderLayout.CENTER);
        add(component2, BorderLayout.EAST);
    }

}
