package com.slimtrade.modules.colortheme;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * A button that responds visually to all mouse inputs.
 */
public class AdvancedButton extends JButton {

    private boolean mouseDown;
    private boolean inBounds;
    public final Set<Integer> allowedMouseButtons = new HashSet<>();
    private final ButtonModel model;

    public AdvancedButton() {
        allowedMouseButtons.add(1);
        allowedMouseButtons.add(3);
        model = getModel();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (!allowedMouseButtons.contains(e.getButton())) return;
                mouseDown = true;
                adjustState();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!allowedMouseButtons.contains(e.getButton())) return;
                mouseDown = false;
                adjustState();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                inBounds = true;
                adjustState();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                inBounds = false;
                adjustState();
            }
        });
    }

    private void adjustState() {
        if (inBounds) {
            model.setRollover(true);
            model.setPressed(mouseDown);
        } else {
            model.setRollover(false);
            model.setPressed(false);
        }
    }

}
