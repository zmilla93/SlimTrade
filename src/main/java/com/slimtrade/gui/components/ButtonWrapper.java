package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Adds a panel to a button with a horizontal strut, functionally giving the button a minimum width.
 */
public class ButtonWrapper extends JPanel {

    private static final int DEFAULT_STRUT_SIZE = 80;

    public ButtonWrapper(JButton button) {
        this(button, DEFAULT_STRUT_SIZE);
    }

    public ButtonWrapper(JButton button, int strutSize) {
        setLayout(new BorderLayout());
        add(button, BorderLayout.CENTER);
        add(Box.createHorizontalStrut(strutSize), BorderLayout.SOUTH);
    }

}
