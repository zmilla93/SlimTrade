package com.slimtrade.gui.components;

import javax.swing.*;

/**
 * A child that can be added to a container and easily reordered.
 *
 * @see AddRemoveContainer
 */
public class AddRemovePanel extends JPanel {

    public final AddRemoveContainer parent;

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

    public void removeFromParent() {
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

}
