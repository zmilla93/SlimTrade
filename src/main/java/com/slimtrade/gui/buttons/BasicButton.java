package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.custom.CustomToolTip;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BasicButton extends JButton implements IColorable {

    private static final long serialVersionUID = 1L;

    protected Color primaryColor = ColorManager.PRIMARY;
    protected Color secondaryColor = ColorManager.BACKGROUND;
    protected Color inactiveColor = ColorManager.LOW_CONTRAST_1;
//    protected GradientPaint gradientPaint = new GradientPaint(new Point(0, 0), secondaryColor, new Point(0, getHeight()), primaryColor);

    protected Border borderDefault;
    protected Border borderRollover;
    protected Border borderDisabled;

    protected Border bufferBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
    protected Border bufferBorderSlim = BorderFactory.createEmptyBorder(4, 14, 4, 14);

    public boolean selected;
    protected boolean useGradient = true;

    public BasicButton() {
        this(null);
    }

    public BasicButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
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
        if (model.isPressed() && model.isRollover()) {
            g2.setPaint(curPrimary);
        } else if (model.isPressed() && !model.isRollover()) {
            g2.setPaint(ColorManager.LOW_CONTRAST_1);
        } else {
            if (selected) {
                g2.setPaint(primaryColor);
            } else {
                if (useGradient) {
                    if (model.isRollover()) {
                        g2.setPaint(new GradientPaint(new Point(0, 0), secondaryColor, new Point(0, getHeight()), ColorManager.lighter(primaryColor, 10)));
                    } else {
                        g2.setPaint(new GradientPaint(new Point(0, 0), secondaryColor, new Point(0, getHeight()), primaryColor));
                    }
                } else {
                    if (model.isRollover()) {
                        g2.setPaint(ColorManager.lighter(inactiveColor, 15));
                    } else {
                        g2.setPaint(inactiveColor);
                    }
                }
            }
        }
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public void updateColor() {
        primaryColor = ColorManager.PRIMARY;
        secondaryColor = ColorManager.BUTTON_SECONDARY_COLOR;
        inactiveColor = ColorManager.LOW_CONTRAST_1;
        this.setForeground(ColorManager.TEXT);
        borderDefault = BorderFactory.createCompoundBorder(ColorManager.BORDER_TEXT, bufferBorder);
        borderRollover = borderDefault;
        borderDisabled = BorderFactory.createCompoundBorder(ColorManager.BORDER_LOW_CONTRAST_1, bufferBorder);
    }

    @Override
    public JToolTip createToolTip() {
        return new CustomToolTip(this);
    }

}
