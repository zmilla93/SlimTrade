package com.slimtrade.gui.components;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A child that can be added to a container and easily reordered.
 *
 * @see AddRemoveContainer
 */
public class AddRemovePanel extends JPanel {

    public final AddRemoveContainer<? extends AddRemovePanel> parent;
    public final JButton deleteButton = new IconButton(DefaultIcon.CLOSE);
    public final JLabel dragButton = new JLabel();

    public AddRemovePanel(AddRemoveContainer<? extends AddRemovePanel> parent) {
        this.parent = parent;

        // FIXME : Create a class that handles auto resizing!
        dragButton.setIcon(ThemeManager.getColorIcon(DefaultIcon.DRAG.path()));
        dragButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Container parent = getParent();
                if (parent instanceof AddRemoveContainer)
                    ((AddRemoveContainer<?>) parent).setComponentBeingDragged(AddRemovePanel.this);
            }
        });
        dragButton.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));


        deleteButton.addActionListener(e -> removeFromParent());
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
        Component parentComponent = getParent();
        if (!(parentComponent instanceof AddRemoveContainer)) return;
        AddRemoveContainer parent = (AddRemoveContainer) parentComponent;
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

}
