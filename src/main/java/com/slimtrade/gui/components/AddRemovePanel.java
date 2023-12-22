package com.slimtrade.gui.components;

import javax.swing.*;

/**
 * A child that can be added to a container and easily reordered.
 *
 * @see AddRemoveContainer
 */
public class AddRemovePanel extends JPanel {

    public final AddRemoveContainer<? extends AddRemovePanel> parent;

    public AddRemovePanel(AddRemoveContainer<? extends AddRemovePanel> parent) {
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
        // FIXME : This fails to call the generic version of remove
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

}
