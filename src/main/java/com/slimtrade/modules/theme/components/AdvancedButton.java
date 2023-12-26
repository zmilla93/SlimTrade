package com.slimtrade.modules.theme.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * A button that can respond visually to any mouse button instead of just the left mouse button.
 * Defaults to left and right mouse, can be adjusted manually with setAllowedMouseButtons().
 */
public class AdvancedButton extends JButton {

    private boolean mouseDown;
    private boolean inBounds;
    private final Set<Integer> allowedMouseButtons = new HashSet<>();
    private final ButtonModel model;

    private String backgroundKey;
    private String foregroundKey;

    private Color backgroundColor;
    private Color backgroundColorHover;

    public AdvancedButton() {
        this(null);
    }

    public AdvancedButton(String text) {
        setText(text);
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
                repaint();
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
            model.setPressed(mouseDown);
            if (backgroundColorHover != null) super.setBackground(backgroundColorHover);
        } else {
            super.setBackground(backgroundColor);
        }
    }

    public void setBackgroundKey(String key) {
        this.backgroundKey = key;
    }

    public void setForegroundKey(String key) {
        this.foregroundKey = key;
    }

    public void setAllowedMouseButtons(int button) {
        allowedMouseButtons.clear();
        allowedMouseButtons.add(button);
    }

    public void setAllowedMouseButtons(int[] buttons) {
        allowedMouseButtons.clear();
        for (int button : buttons) {
            allowedMouseButtons.add(button);
        }
    }

    public void setBackgroundHover(Color color) {
        setRolloverEnabled(color == null);
        this.backgroundColorHover = color;
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        this.backgroundColor = color;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (backgroundKey != null) setBackground(UIManager.getColor(backgroundKey));
        if (foregroundKey != null) setForeground(UIManager.getColor(foregroundKey));
    }

}
