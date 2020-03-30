package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSliderUI extends BasicSliderUI {
    private BasicStroke stroke = new BasicStroke(BasicStroke.CAP_ROUND);
    private Color color;

    public CustomSliderUI(JSlider b) {
        super(b);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(12, 16);
    }

    @Override
    public void paintTrack(Graphics g) {
        g.setColor(ColorManager.TEXT);
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(stroke);
        if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
            g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
        } else {
            g2d.drawLine(trackRect.x + trackRect.width / 2, trackRect.y, trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
        }
        g2d.setStroke(old);
    }

    public void paintTicks(Graphics g) {

        Rectangle tickBounds = tickRect;

        g.setColor(ColorManager.TEXT);
        g.translate(0, tickBounds.y);

        if (slider.getMinorTickSpacing() > 0) {
            int value = slider.getMinimum();

            while (value <= slider.getMaximum()) {
                int xPos = xPositionForValue(value);
                paintMinorTickForHorizSlider(g, tickBounds, xPos);

                // Overflow checking
                if (Integer.MAX_VALUE - slider.getMinorTickSpacing() < value) {
                    break;
                }

                value += slider.getMinorTickSpacing();
            }
        }

        if (slider.getMajorTickSpacing() > 0) {
            int value = slider.getMinimum();

            while (value <= slider.getMaximum()) {
                int xPos = xPositionForValue(value);
                paintMajorTickForHorizSlider(g, tickBounds, xPos);

                // Overflow checking
                if (Integer.MAX_VALUE - slider.getMajorTickSpacing() < value) {
                    break;
                }

                value += slider.getMajorTickSpacing();
            }
        }

        g.translate(0, -tickBounds.y);
    }

    @Override
    public void paintThumb(Graphics g) {

        Rectangle knobBounds = thumbRect;
        int w = knobBounds.width;
        int h = knobBounds.height;
        g.translate(knobBounds.x, knobBounds.y);
        g.setColor(ColorManager.TEXT);

        g.fillRect(w / 4, 0, w / 2, h);

    }

}
