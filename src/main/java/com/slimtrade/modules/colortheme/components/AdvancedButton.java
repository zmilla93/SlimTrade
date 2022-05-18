package com.slimtrade.modules.colortheme.components;

import javax.swing.*;
import java.awt.*;
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
    private boolean active;
    private Color previousBackground;
    private Color previousForeground;

    private String backgroundKey;
    private String foregroundKey;

    public AdvancedButton() {
        this(null);
    }

    public AdvancedButton(String text) {
        setText(text);
        setBorder(null);
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

    public void setActive(boolean active) {
        this.active = active;
        adjustState();
    }

    public boolean isActive() {
        return active;
    }

    private void adjustState() {
        if (previousBackground != null) {
            setBackground(previousBackground);
            setForeground(previousForeground);
            previousBackground = null;
            previousForeground = null;
        }
        if (inBounds) {
            model.setRollover(true);
            model.setPressed(mouseDown);
        } else {
            if (active) {
                previousBackground = getBackground();
                previousForeground = getForeground();
                setBackground(previousForeground);
                setForeground(previousBackground);
                model.setRollover(true);
                model.setPressed(true);
            } else {
                model.setRollover(false);
                model.setPressed(false);
            }
        }
    }

    public void setBackgroundKey(String key){
        this.backgroundKey = key;
    }

    public void setForegroundKey(String key){
        this.foregroundKey = key;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if(backgroundKey != null) setBackground(UIManager.getColor(backgroundKey));
        if(foregroundKey != null) setForeground(UIManager.getColor(foregroundKey));
    }
}
