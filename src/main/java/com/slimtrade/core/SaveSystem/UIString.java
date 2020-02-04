package com.slimtrade.core.SaveSystem;

import javax.swing.*;
import java.awt.*;

public class UIString implements SaveElement {

    public String value;
    public Component component;


    @Override
    public void revertUI() {
        if (component instanceof JTextField) {
            ((JTextField) component).setText(value);
        }

    }

    public String getValue() {
        if (component instanceof JTextField) {
            return ((JTextField) component).getText();
        }
        return null;
    }
}
