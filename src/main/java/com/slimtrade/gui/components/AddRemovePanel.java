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
public abstract class AddRemovePanel<T> extends JPanel {

    public final JButton deleteButton = new IconButton(DefaultIcon.CLOSE);
    public final JLabel dragButton = new JLabel();

    public AddRemovePanel() {

        // FIXME : Create a class that handles auto resizing and color theme!
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
        ((AddRemoveContainer) getParent()).shiftUp(this);
        button.requestFocus();
    }

    public void shiftDown(JButton button) {
        ((AddRemoveContainer) getParent()).shiftDown(this);
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

    public abstract T getData();

    public abstract void setData(T data);

}
