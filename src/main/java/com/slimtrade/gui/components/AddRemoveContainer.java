package com.slimtrade.gui.components;

import com.slimtrade.core.utility.ZUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A panel that allows children to be added, removed, and easily reordered.
 *
 * @see AddRemovePanel
 */
// FIXME (Optimize) : Could probably simplify this by storing index in AddRemovePanel, but not a high priority
public class AddRemoveContainer<T extends AddRemovePanel> extends JPanel {

    private final HashMap<Component, Integer> panelToInt = new HashMap<>();
    private final HashMap<Integer, Component> intToPanel = new HashMap<>();
    private boolean usingGeneric = false;

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

    protected void shiftUp(Component panel) {
        if (panelToInt.size() <= 1) return;
        int index = panelToInt.get(panel);
        if (index == 0) return;
        int swapIndex = index - 1;
        swapPanels(index, swapIndex);
    }

    protected void shiftDown(Component panel) {
        if (panelToInt.size() <= 1) return;
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
            super.add(comp, gc);
            gc.insets.top = spacing;
            intToPanel.put(i, comp);
            panelToInt.put(comp, i);
        }
        revalidate();
        repaint();
    }

    private void rebuildMaps() {
        intToPanel.clear();
        panelToInt.clear();
        for (int i = 0; i < getComponentCount(); i++) {
            intToPanel.put(i, getComponent(i));
            panelToInt.put(getComponent(i), i);
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<T> getComponentsTyped() {
        // Warnings are suppressed here because there is no clean way to safely cast to a generic type.
        // Component types are checked when being added, so errors should be caught before ever getting to here.
        ArrayList<T> components = new ArrayList<>();
        for (Component c : getComponents()) {
            components.add((T) c);
        }
        return components;
    }

    private void genericMisuse() {
        System.err.println("AddRemoveContainer is operating on a class different than the generic class it was assigned!");
        ZUtil.printCallingFunction(AddRemoveContainer.class);
    }

    private void checkGeneric() {
        if (!usingGeneric) genericMisuse();
        usingGeneric = false;
    }

    public T add(T component) {
        usingGeneric = true;
        add((Component) component);
        return component;
    }

    @Override
    public Component add(Component comp) {
        checkGeneric();
        gc.gridy = panelToInt.size();
        super.add(comp, gc);
        panelToInt.put(comp, panelToInt.size());
        intToPanel.put(intToPanel.size(), comp);
        gc.insets.top = spacing;
        revalidate();
        repaint();
        return comp;
    }

    @Override
    public void remove(Component comp) {
        super.remove(comp);
        rebuildMaps();
        rebuild();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        panelToInt.clear();
        intToPanel.clear();
    }

    //
    // Add functions that should not be used - Prints error if they are.
    //

    private void incorrectAddMethod() {
        System.err.println("AddRemoveContainer should only have elements added using the add(Component) method!");
        ZUtil.printCallingFunction(AddRemoveContainer.class);
    }

    @Override
    public Component add(String name, Component comp) {
        incorrectAddMethod();
        return super.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        incorrectAddMethod();
        return super.add(comp, index);
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        incorrectAddMethod();
        super.add(comp, constraints);
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        incorrectAddMethod();
        super.add(comp, constraints, index);
    }

}
