package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.enums.ICacheImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IconButton extends JButton implements IColorable {

    public Color colorDefault = ColorManager.LOW_CONTRAST_1;
    public Color colorHover = ColorManager.PRIMARY;
    public Color colorPressed = ColorManager.BACKGROUND;

    public Border borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    public Border borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, ColorManager.TEXT);
    public Border borderPressed = borderHover;

    private ICacheImage cacheImage;

    public IconButton(ICacheImage image, int size) {
        this.cacheImage = image;
        this.setPreferredSize(new Dimension(size, size));
        this.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setBorder(borderDefault);

        final IconButton localButton = this;
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                localButton.getModel().setPressed(true);
            }

            public void mouseReleased(MouseEvent e) {
                localButton.getModel().setPressed(false);
            }
        });
        updateColor();
    }

    /**
     * Sets the icon of this button to an {@link ICacheImage} to be redrawn using the correct color theme.
     * This should be used instead of the JButton setIcon() method.
     *
     * @param image
     */
    public void setCachedImage(ICacheImage image) {
        this.cacheImage = image;
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(colorPressed);
            this.setBorder(borderPressed);
        } else if (getModel().isRollover()) {
            g.setColor(colorHover);
            this.setBorder(borderHover);
        } else {
            g.setColor(colorDefault);
            this.setBorder(borderDefault);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }


    @Override
    public void updateColor() {
        borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, ColorManager.TEXT);
        borderPressed = borderHover;
        colorDefault = ColorManager.LOW_CONTRAST_1;
        colorHover = ColorManager.LOW_CONTRAST_2;
        colorPressed = ColorManager.HIGH_CONTRAST_1;
        this.setIcon(new ImageIcon(cacheImage.getColorImage(ColorManager.TEXT)));
    }

}
