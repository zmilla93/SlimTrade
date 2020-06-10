package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.custom.CustomToolTip;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasicButton extends JButton implements IColorable {

    private static final long serialVersionUID = 1L;

    private final ButtonModel model;

    private Border borderDefault;
    private Border borderRollover;
    private Border borderDisabled;

    public Color primaryColor = ColorManager.PRIMARY;
    public Color secondaryColor = ColorManager.BACKGROUND;

    private Border bufferBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
    private Border bufferBorderSlim = BorderFactory.createEmptyBorder(4, 14, 4, 14);

    private boolean forcePress;

    // TODO : Secondary Color

    public BasicButton() {
        this.model = this.getModel();
        buildButton();
    }

    public BasicButton(String text) {
        super(text);
        this.model = this.getModel();
        buildButton();
    }

    //TODO : Check mouse button?
    //TODO : Currently paints twice per action...
    // TODO : Adjust border
    private void buildButton() {

        setContentAreaFilled(false);
        setFocusPainted(false);
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                model.setRollover(true);
            }

            public void mouseExited(MouseEvent e) {
                model.setRollover(false);
            }

            public void mousePressed(MouseEvent e) {
                model.setPressed(true);
            }

            public void mouseReleased(MouseEvent e) {
                model.setPressed(false);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        ButtonModel model = getModel();
        //BORDER
        Color curPrimary;
        if (model.isEnabled()) {
            curPrimary = primaryColor;
            if (model.isRollover()) {
                this.setBorder(borderRollover);
            } else {
                this.setBorder(borderDefault);
            }
        } else {
            curPrimary = ColorManager.lighter(primaryColor);
            this.setBorder(borderDisabled);
        }
        //FILL
        if (model.isPressed() && model.isRollover() || forcePress) {
            g2.setPaint(curPrimary);
        } else if (model.isPressed() && !model.isRollover()) {
            g2.setPaint(ColorManager.LOW_CONTRAST_1);
        } else {
            g2.setPaint(new GradientPaint(new Point(0, 0), secondaryColor, new Point(0, getHeight()), curPrimary));
        }
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public void updateColor() {
        primaryColor = ColorManager.PRIMARY;
        secondaryColor = ColorManager.BUTTON_SECONDARY_COLOR;
        this.setForeground(ColorManager.TEXT);
        borderDefault = BorderFactory.createCompoundBorder(ColorManager.BORDER_TEXT, bufferBorder);
        Border b1 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_2), BorderFactory.createLineBorder(primaryColor));
        borderRollover = BorderFactory.createCompoundBorder(b1, bufferBorderSlim);
        borderDisabled = BorderFactory.createCompoundBorder(ColorManager.BORDER_LOW_CONTRAST_1, bufferBorder);
    }

    @Override
    public JToolTip createToolTip() {
        return new CustomToolTip(this);
    }

    public void setForcePress(boolean state) {
        this.forcePress = state;
    }

}
