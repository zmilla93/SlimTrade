package com.slimtrade.gui.options;

import com.slimtrade.core.utility.GUIReferences;

import javax.swing.*;
import java.awt.*;

public class AbstractOptionPanel extends JPanel {

    protected JPanel contentPanel;
    private final int scrollSpeed = 10;
    protected GridBagConstraints gc = new GridBagConstraints();

    public AbstractOptionPanel() {
        setLayout(new BorderLayout());
        contentPanel = new JPanel(new GridBagLayout());
        JPanel insetPanel = new JPanel(new BorderLayout());
        insetPanel.add(Box.createVerticalStrut(GUIReferences.INSET), BorderLayout.NORTH);
        insetPanel.add(contentPanel);
        JPanel bufferPanel = new JPanel(new BorderLayout());
        bufferPanel.add(insetPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(bufferPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(scrollSpeed);
        add(scrollPane, BorderLayout.CENTER);

        gc.gridx = 0;
        gc.gridy = 0;
//        gc.weightx = 1;
//        gc.weighty = 1;
//        gc.fill = GridBagConstraints.BOTH;
    }

    protected JPanel createHeader(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel labelPanel = new JPanel(new BorderLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(0, 10, 0, 0);
//        labelPanel.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
        JLabel label = new JLabel(title);
//        label.setForeground(UIManager.getColor("Menu.foreground"));\
        Color col = UIManager.getColor("TitledBorder.titleColor");
        label.setForeground(col);
//        label.setForeground(Color.RED);
        labelPanel.add(label, BorderLayout.CENTER);

        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(labelPanel, gc);
        gc.gridy++;
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        gc.fill = GridBagConstraints.BOTH;
//        separators.add(sep);
        gc.insets = new Insets(0, 10, 0, 10);
        panel.add(sep, gc);
        return panel;
    }

    protected void addHeader(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel labelPanel = new JPanel(new BorderLayout());
        GridBagConstraints headerGC = new GridBagConstraints();
        headerGC.gridx = 0;
        headerGC.gridy = 0;
        headerGC.insets = new Insets(0, GUIReferences.INSET, 0, 0);
        JLabel label = new JLabel(title);
        Color col = UIManager.getColor("TitledBorder.titleColor");
        label.setForeground(col);
        labelPanel.add(label, BorderLayout.CENTER);
        headerGC.weightx = 1;
        headerGC.fill = GridBagConstraints.HORIZONTAL;
        panel.add(labelPanel, headerGC);
        headerGC.gridy++;
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        headerGC.fill = GridBagConstraints.BOTH;
        headerGC.insets = new Insets(0, 10, 0, 10);
        panel.add(sep, headerGC);
        int prevFill = gc.fill;
        double prevWeightX = gc.weightx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        contentPanel.add(panel, gc);
        gc.fill = prevFill;
        gc.weightx = prevWeightX;
        gc.gridy++;
    }

    public void addComponent(JComponent component){
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
//        getParent().revalidate();
//        getParent().repaint();
    }

}
