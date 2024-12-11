package github.zmilla93.gui.components;

import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A panel that can be dynamically added to an {@link AddRemoveContainer} and be easily reordered.
 */
public abstract class AddRemovePanel<T> extends JPanel {

    public final JButton deleteButton = new IconButton(DefaultIcon.CLOSE);
    public final JLabel dragButton = new JLabel();

    public AddRemovePanel() {
        // FIXME : Create a class that handles auto resizing and color theme!
        dragButton.setIcon(ThemeManager.getColorIcon(DefaultIcon.DRAG.path()));
        dragButton.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        dragButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ((AddRemoveContainer<?>) getParent()).setComponentBeingDragged(AddRemovePanel.this);
            }
        });
        deleteButton.addActionListener(e -> removeFromParent());
    }

    public void removeFromParent() {
        Component parentComponent = getParent();
        if (!(parentComponent instanceof AddRemoveContainer<?>)) return;
        //noinspection unchecked
        AddRemoveContainer<AddRemovePanel<T>> parent = (AddRemoveContainer<AddRemovePanel<T>>) parentComponent;
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    public abstract T getData();

    public abstract void setData(T data);

}
