package com.slimtrade.gui.components;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * @see AddRemovePanel
 */
public class AddRemoveContainer extends JPanel {

    private final HashMap<Component, Integer> panelToInt = new HashMap<>();
    private final HashMap<Integer, Component> intToPanel = new HashMap<>();
    private final GridBagConstraints gc = ZUtil.getGC();
    private int spacing;

    public AddRemoveContainer() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.WEST;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void shiftUp(Component panel) {
        if (panelToInt.size() < 2) return;
        int index = panelToInt.get(panel);
        if (index == 0) return;
        int swapIndex = index - 1;
        swapPanels(index, swapIndex);
    }

    public void shiftDown(Component panel) {
        if (panelToInt.size() < 2) return;
        int index = panelToInt.get(panel);
        if (index >= intToPanel.size() - 1) return;
        int swapIndex = index + 1;
        swapPanels(index, swapIndex);
    }

    private void swapPanels(int index, int swapIndex) {
        Component panel = intToPanel.get(index);
        Component swapPanel = intToPanel.get(swapIndex);
        panelToInt.put(panel, swapIndex);
        panelToInt.put(swapPanel, index);
        intToPanel.put(swapIndex, panel);
        intToPanel.put(index, swapPanel);
        rebuild();
    }

    private void rebuild() {
        HashMap<Integer, Component> tempIntToPanel = new HashMap<>(intToPanel);
        removeAll();
        gc.insets.top = 0;
        intToPanel.clear();
        panelToInt.clear();
        for (int i = 0; i < tempIntToPanel.size(); i++) {
            Component comp = tempIntToPanel.get(i);
            gc.gridy = i;
            add(comp, gc);
            gc.insets.top = spacing;
            intToPanel.put(i, comp);
            panelToInt.put(comp, i);
        }
        revalidate();
        repaint();
    }

    @Override
    public Component add(Component comp) {
        gc.gridy = panelToInt.size();
        super.add(comp, gc);
        panelToInt.put(comp, panelToInt.size());
        intToPanel.put(intToPanel.size(), comp);
        gc.insets.top = spacing;
        return comp;
    }

    @Override
    public void remove(Component comp) {
        super.remove(comp);
        int index = panelToInt.get(comp);
        panelToInt.remove(comp);
        intToPanel.remove(index);
    }

    @Override
    public void removeAll() {
        super.removeAll();
        panelToInt.clear();
        intToPanel.clear();
    }

}
