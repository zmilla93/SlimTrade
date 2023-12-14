package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.utility.GUIReferences;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Used to create consistently formatted option panels.
 * Do not add components directly. Instead, use the addHeader() and addComponent() functions.
 */
public abstract class AbstractOptionPanel extends JPanel {

    protected JPanel contentPanel;
    private static final int SCROLL_SPEED = 10;
    private final GridBagConstraints gc = new GridBagConstraints();
    private static final int CONTENT_PANEL_INSET = 10;
    private static final int HEADER_INSET = 5;
    private static final int COMPONENT_INSET = 10;

    public AbstractOptionPanel() {
        this(true);
    }

    public AbstractOptionPanel(boolean addScrollPanel) {
        setLayout(new BorderLayout());
        contentPanel = new JPanel(new GridBagLayout());
        JPanel insetPanel = new JPanel(new BorderLayout());
        if (App.debugUIBorders) {
            setBackground(new Color(96, 236, 122));
            contentPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        }
        insetPanel.add(Box.createVerticalStrut(CONTENT_PANEL_INSET), BorderLayout.NORTH);
        insetPanel.add(Box.createVerticalStrut(CONTENT_PANEL_INSET), BorderLayout.SOUTH);
        insetPanel.add(Box.createHorizontalStrut(CONTENT_PANEL_INSET), BorderLayout.EAST);
        insetPanel.add(Box.createHorizontalStrut(CONTENT_PANEL_INSET), BorderLayout.WEST);
        insetPanel.add(contentPanel, BorderLayout.CENTER);
        JPanel bufferPanel = new JPanel(new BorderLayout());
        bufferPanel.add(insetPanel, BorderLayout.NORTH);
        if (addScrollPanel) {
            JScrollPane scrollPane = new JScrollPane(bufferPanel);
            scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
            super.add(scrollPane, BorderLayout.CENTER);
        } else {
            super.add(bufferPanel, BorderLayout.CENTER);
        }
        gc.gridx = 0;
        gc.gridy = 0;
    }

    public JLabel addHeader(String title) {
        HeaderPanel headerPanel = new HeaderPanel(title);
        int prevFill = gc.fill;
        double prevWeightX = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        contentPanel.add(headerPanel, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeightX;
        gc.gridy++;
        contentPanel.add(Box.createVerticalStrut(2), gc);
        gc.gridy++;
        return headerPanel.getLabel();
    }

    public Component addComponent(Component component) {
        if (App.debugUIBorders) {
            if (component instanceof JPanel)
                ((JPanel) component).setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            else if (App.debugUIBordersAggressive && component instanceof JComponent) {
                JPanel debugPanel = new JPanel(new BorderLayout());
                debugPanel.add(component);
                debugPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
                debugPanel.add(component, BorderLayout.CENTER);
                component = debugPanel;
            }
        }
        int prevFill = gc.fill;
        double prevWeight = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        JPanel outerPanel = new JPanel(new BorderLayout());
        JPanel innerPanel = new JPanel(new BorderLayout());
        outerPanel.add(Box.createHorizontalStrut(0), BorderLayout.WEST);
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        innerPanel.add(component, BorderLayout.WEST);
        contentPanel.add(outerPanel, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeight;
        gc.gridy++;
        return outerPanel;
    }

    public Component addVerticalStrut() {
        Component strut = Box.createVerticalStrut(GUIReferences.INSET);
        addComponent(strut);
        return strut;
    }

    public Component addVerticalStrutSmall() {
        Component strut = Box.createVerticalStrut(GUIReferences.SMALL_INSET);
        addComponent(strut);
        return strut;
    }

    // Components should only be added using provided functions.
    // Override all add functions to warn about improper use.

    @Override
    public Component add(Component comp) {
        incorrectAddMethod();
        return super.add(comp);
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        incorrectAddMethod();
        super.add(comp, constraints);
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
    public void add(Component comp, Object constraints, int index) {
        incorrectAddMethod();
        super.add(comp, constraints, index);
    }

    private void incorrectAddMethod() {
        System.err.println("Components should not be added directly to an OptionPanel! Use addHeader() or addPanel() instead.");
    }

}
