package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Allows setForeground to be used on a panel and have it applied to all of its children.
 */
public class LabelHolder extends JPanel {

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        for (Component c : getComponents()) {
            c.setForeground(fg);
        }
    }

}
