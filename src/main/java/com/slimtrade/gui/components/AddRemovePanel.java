package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.macro.MacroCustomizerRow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class AddRemovePanel extends JPanel implements IColorable {

    private static final long serialVersionUID = 1L;
    private GridBagConstraints gc = new GridBagConstraints();
    private int spacer = 2;

    private ArrayList<JPanel> panels = new ArrayList<>();

    public AddRemovePanel() {
        this.setLayout(FrameManager.gridBag);
        gc.gridx = 0;
        gc.gridy = 0;
    }

//    public void addRemoveablePanel(JPanel panel) {
//        addRemoveablePanel(panel, true);
//    }

    public void addRemoveablePanel(JPanel panel) {
        panels.clear();
        for (Component c : this.getComponents()) {
            if (c.isVisible()) {
                panels.add((JPanel) c);
            }
        }
        panels.add(panel);
        int panelCount = this.getComponentCount();
//        for (JPanel p : panels) {
//            this.add(p, gc);
//            gc.insets.top = spacer;
//            gc.gridy++;
//        }
//        gc.fill = GridBagConstraints.BOTH;gc.fill = GridBagConstraints.BOTH;
        gc.gridy = panelCount;
        gc.insets.top = panelCount > 0 ? spacer : 0;
        this.add(panel, gc);
//        if(panelCount > 0) {
//        if(recolor) {
            App.eventManager.recursiveColor(panel);
            this.revalidate();
            this.repaint();
//        }
    }

    public void refreshPanels() {
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.top = 0;
        int i = 0;
        for (Component c : this.getComponents()) {
            if (c.isVisible()) {
                this.add(c, gc);
                gc.insets.top = spacer;
                gc.gridy++;
                i++;
            }
        }
        this.revalidate();
        this.repaint();
    }

    public void clearHiddenPanels() {
        for (Component c : this.getComponents()) {
            if (!c.isVisible()) {
                this.remove(c);
            }
        }
    }

    public void setEnabledAll(boolean state) {
        for (Component c : this.getComponents()) {
            if (c instanceof MacroCustomizerRow) {
                ((MacroCustomizerRow) c).setEnabledAll(state);
            }
        }
    }

    public void shiftUp(JPanel panel) {
        int i = panels.indexOf(panel);
        if (i > 0) {
            Collections.swap(panels, i, i - 1);
        }
        this.removeAll();
        gc.gridy = 0;
        gc.insets.top = 0;
        for (JPanel p : panels) {
            this.add(p, gc);
            gc.insets.top = spacer;
            gc.gridy++;
        }
        this.getParent().revalidate();
        this.getParent().repaint();
    }

    public void shiftDown(JPanel panel) {
        int i = panels.indexOf(panel);
        if (i < panels.size() - 1) {
            Collections.swap(panels, i, i + 1);
        }
        this.removeAll();
        gc.gridy = 0;
        gc.insets.top = 0;
        for (JPanel p : panels) {
            this.add(p, gc);
            gc.insets.top = spacer;
            gc.gridy++;
        }
        this.getParent().revalidate();
        this.getParent().repaint();
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_1);
        this.revalidate();
        this.repaint();
    }

}
