package com.slimtrade.gui.options;

import com.slimtrade.core.utility.GUIReferences;

import javax.swing.*;
import java.awt.*;

/**
 * @see HeaderPanel
 */
public class AbstractOptionPanel extends JPanel {

    protected JPanel contentPanel;
    private static final int SCROLL_SPEED = 10;
    private final GridBagConstraints gc = new GridBagConstraints();

    public AbstractOptionPanel() {
        this(true);
    }

    public AbstractOptionPanel(boolean addScrollPanel) {
        setLayout(new BorderLayout());
        contentPanel = new JPanel(new GridBagLayout());
        JPanel insetPanel = new JPanel(new BorderLayout());
        insetPanel.add(Box.createVerticalStrut(GUIReferences.INSET), BorderLayout.NORTH);
        insetPanel.add(contentPanel);
        JPanel bufferPanel = new JPanel(new BorderLayout());
        bufferPanel.add(insetPanel, BorderLayout.NORTH);
        if (addScrollPanel) {
            JScrollPane scrollPane = new JScrollPane(bufferPanel);
            scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
            add(scrollPane, BorderLayout.CENTER);
        } else {
            add(bufferPanel, BorderLayout.CENTER);
        }
        gc.gridx = 0;
        gc.gridy = 0;
    }

    public HeaderPanel addHeader(String title) {
//        JPanel panel = new JPanel(new GridBagLayout());
//        JPanel labelPanel = new JPanel(new BorderLayout());
//        GridBagConstraints headerGC = ZUtil.getGC();
//        headerGC.insets = new Insets(0, GUIReferences.INSET, 0, 0);
//        JLabel label = new JLabel(title);
//        Color col = UIManager.getColor("Label.foreground");
//        label.setForeground(col);
//        labelPanel.add(label, BorderLayout.CENTER);
//        headerGC.weightx = 1;
//        headerGC.fill = GridBagConstraints.HORIZONTAL;
//        panel.add(labelPanel, headerGC);
//        headerGC.gridy++;
//        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
//        headerGC.fill = GridBagConstraints.BOTH;
//        headerGC.insets = new Insets(0, 10, 0, 10);
//        panel.add(sep, headerGC);
        HeaderPanel headerPanel = new HeaderPanel(title);
        int prevFill = gc.fill;
        double prevWeightX = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        contentPanel.add(headerPanel, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeightX;
        gc.gridy++;
        return headerPanel;
    }

    public Component addPanel(Component component) {
        int prevFill = gc.fill;
        double prevWeight = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        JPanel outerPanel = new JPanel(new BorderLayout());
        JPanel innerPanel = new JPanel(new BorderLayout());
        outerPanel.add(Box.createHorizontalStrut(GUIReferences.INSET), BorderLayout.WEST);
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        innerPanel.add(component, BorderLayout.WEST);
        contentPanel.add(outerPanel, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeight;
        gc.gridy++;
        return outerPanel;
    }


    public Component addSmallVerticalStrut() {
        Component strut = Box.createVerticalStrut(GUIReferences.SMALL_INSET);
        addPanel(strut);
        return strut;
    }

    public Component addVerticalStrut() {
        Component strut = Box.createVerticalStrut(GUIReferences.INSET);
        addPanel(strut);
        return strut;
    }

}
