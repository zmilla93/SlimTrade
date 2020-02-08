package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSliderUI extends BasicSliderUI implements IColorable {
    //    private BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f);
    private BasicStroke stroke = new BasicStroke(BasicStroke.CAP_ROUND);
    private Color color;

    public CustomSliderUI(JSlider b) {
        super(b);
        App.eventManager.addColorListener(this);
        updateColor();
//        this.stroke = new BasicStroke();
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
        g.setColor(color);
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

        g.setColor(color);
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
        g.setColor(color);

        // Simple
        g.fillRect(w / 4, 0, w / 2, h);

        // Pointer
//        int cw = w / 2;
//        g.fillRect(1, 1, w - 3, h - 1 - cw);
//        Polygon p = new Polygon();
//        p.addPoint(1, h - cw);
//        p.addPoint(cw - 1, h - 1);
//        p.addPoint(w - 2, h - 1 - cw);
//        g.fillPolygon(p);
//
//        g.setColor(color);
//        g.drawLine(0, 0, w - 2, 0);
//        g.drawLine(0, 1, 0, h - 1 - cw);
//        g.drawLine(0, h - cw, cw - 1, h - 1);
//
//        g.setColor(color);
//        g.drawLine(w - 1, 0, w - 1, h - 2 - cw);
//        g.drawLine(w - 1, h - 1 - cw, w - 1 - cw, h - 1);
//
//        g.setColor(color);
//        g.drawLine(w - 2, 1, w - 2, h - 2 - cw);
//        g.drawLine(w - 2, h - 1 - cw, w - 1 - cw, h - 2);
    }

    @Override
    public void updateColor() {
        color = ColorManager.TEXT;
    }
}
