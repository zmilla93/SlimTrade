package com.slimtrade.gui.components;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.options.macro.MacroCustomizerRow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class AddRemovePanel extends JPanel implements IColorable {

    private GridBagConstraints gc = new GridBagConstraints();
    private final int SPACER = 2;

    private ArrayList<JPanel> panels = new ArrayList<>();

    public Color color;

    public AddRemovePanel() {
        assert(SwingUtilities.isEventDispatchThread());
        this.setLayout(new GridBagLayout());
        gc.gridx = 0;
        gc.gridy = 0;
    }

    public void addRemovablePanel(JPanel panel) {
        assert(SwingUtilities.isEventDispatchThread());
        panels.clear();
        for (Component c : this.getComponents()) {
            if (c.isVisible()) {
                panels.add((JPanel) c);
            }
        }
        panels.add(panel);
        int panelCount = this.getComponentCount();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = panelCount;
        gc.insets.top = panelCount > 0 ? SPACER : 0;
        gc.weightx = 1;
        this.add(panel, gc);
        ColorManager.recursiveColor(panel);
        this.revalidate();
        this.repaint();
    }

    public void refreshPanels() {
        assert(SwingUtilities.isEventDispatchThread());
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.top = 0;
        int i = 0;
        for (Component c : this.getComponents()) {
            if (c.isVisible()) {
                this.add(c, gc);
                gc.insets.top = SPACER;
                gc.gridy++;
                i++;
            }
        }
        this.revalidate();
        this.repaint();
    }

    public void clearHiddenPanels() {
        assert(SwingUtilities.isEventDispatchThread());
        for (Component c : this.getComponents()) {
            if (!c.isVisible()) {
                this.remove(c);
            }
        }
    }

    public void setEnabledAll(boolean state) {
        assert(SwingUtilities.isEventDispatchThread());
        for (Component c : this.getComponents()) {
            if (c instanceof MacroCustomizerRow) {
                ((MacroCustomizerRow) c).setEnabledAll(state);
            }
        }
    }

    public void shiftUp(JPanel panel) {
        assert(SwingUtilities.isEventDispatchThread());
        int i = panels.indexOf(panel);
        if (i > 0) {
            Collections.swap(panels, i, i - 1);
        }
        this.removeAll();
        gc.gridy = 0;
        gc.insets.top = 0;
        for (JPanel p : panels) {
            this.add(p, gc);
            gc.insets.top = SPACER;
            gc.gridy++;
        }
        this.getParent().revalidate();
        this.getParent().repaint();
    }

    public void shiftDown(JPanel panel) {
        assert(SwingUtilities.isEventDispatchThread());
        int i = panels.indexOf(panel);
        if (i < panels.size() - 1) {
            Collections.swap(panels, i, i + 1);
        }
        this.removeAll();
        gc.gridy = 0;
        gc.insets.top = 0;
        for (JPanel p : panels) {
            this.add(p, gc);
            gc.insets.top = SPACER;
            gc.gridy++;
        }
        this.getParent().revalidate();
        this.getParent().repaint();
    }

    @Override
    public void updateColor() {
        if (color == null) {
            this.setBackground(ColorManager.LOW_CONTRAST_1);
        } else {
            this.setBackground(color);
        }
        this.revalidate();
        this.repaint();
    }

}
