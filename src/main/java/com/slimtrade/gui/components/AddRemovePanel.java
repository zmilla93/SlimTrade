package com.slimtrade.gui.components;

import javax.swing.*;

public class AddRemovePanel extends JPanel {

    private AddRemoveContainer parent;

    public AddRemovePanel(AddRemoveContainer parent) {
        this.parent = parent;
    }

    public void shiftUp(JButton button) {
        parent.shiftUp(this);
        button.requestFocus();
    }

    public void shiftDown(JButton button) {
        parent.shiftDown(this);
        button.requestFocus();
    }

}
