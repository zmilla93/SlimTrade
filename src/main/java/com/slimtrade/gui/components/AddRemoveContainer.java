package com.slimtrade.gui.components;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AddRemoveContainer extends JPanel {

    private HashMap<Component, Integer> panelToInt = new HashMap<>();
    private HashMap<Integer, Component> intToPanel = new HashMap<>();
    private GridBagConstraints gc = ZUtil.getGC();

    public AddRemoveContainer() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.WEST;
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
        refreshPanels(panel, swapPanel);
    }

    private void refreshPanels(Component comp1, Component comp2) {
        gc.gridy = panelToInt.get(comp1);
        add(comp1, gc);
        gc.gridy = panelToInt.get(comp2);
        add(comp2, gc);
        revalidate();
        repaint();
    }

    private void rebuild() {
        removeAll();
        for (int i = 0; i < intToPanel.size(); i++) {
            Component comp = intToPanel.get(i);
            gc.gridy = i;
            add(comp);
        }
    }

    public Component[] getOrderedComponents() {
        Component[] components = new Component[intToPanel.size()];
        for (int i = 0; i < intToPanel.size(); i++) {
            components[i] = intToPanel.get(i);
        }
        return components;
    }

    @Override
    public Component add(Component comp) {
        gc.gridy = panelToInt.size();
        super.add(comp, gc);
        panelToInt.put(comp, panelToInt.size());
        intToPanel.put(intToPanel.size(), comp);
        return comp;
    }

    @Override
    public void removeAll() {
        super.removeAll();
        panelToInt.clear();
        intToPanel.clear();
    }

}
