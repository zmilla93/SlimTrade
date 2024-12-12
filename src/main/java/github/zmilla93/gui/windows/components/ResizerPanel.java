package github.zmilla93.gui.windows.components;

import javax.swing.*;
import java.awt.*;

public class ResizerPanel extends JPanel {

    public ResizerPanel(int orientation, int size) {
        boolean north = orientation == SwingUtilities.NORTH;
        boolean south = orientation == SwingUtilities.SOUTH;
        boolean east = orientation == SwingUtilities.EAST;
        boolean west = orientation == SwingUtilities.WEST;
        boolean valid = north || south || east || west;
        if (!valid) {
            throw new RuntimeException(getClass().getSimpleName() + " expects orientation North, South, East, or West.");
        }
        setLayout(new BorderLayout());
        if (north || south) {
            add(Box.createVerticalStrut(size), BorderLayout.CENTER);
            if (north) setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
            else setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        } else {
            add(Box.createHorizontalStrut(size), BorderLayout.CENTER);
            if (east) setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
            else setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        }
    }

    private void addMouseListeners() {

    }

}
