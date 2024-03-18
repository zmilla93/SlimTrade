package com.slimtrade.gui;

import com.slimtrade.gui.buttons.SlimButton;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class TestNotificationPanel extends JPanel {

    JPanel contentPanel;
    GridBagConstraints gc = new GridBagConstraints();
    JPanel currentPanel;
    GridBagConstraints panelGC = new GridBagConstraints();

    int targetWidth = 400;

    public TestNotificationPanel() {
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(1, 1, 1, 1);
        panelGC.gridx = 0;
        panelGC.gridy = 0;
        panelGC.weightx = 1;
        panelGC.fill = GridBagConstraints.HORIZONTAL;
        panelGC.anchor = GridBagConstraints.EAST;

//        JPanel outerPanel = new JPanel(new FlowLayout());
        setLayout(new GridBagLayout());
        JPanel innerPanel = new JPanel(new GridBagLayout());
        contentPanel = new JPanel(new GridBagLayout());
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(innerPanel, gc);
        innerPanel.add(contentPanel, gc);
        gc.insets = new Insets(0, 0, 0, 0);

        Color color = UIManager.getColor("Button.foreground");
        Color color2 = UIManager.getColor("Menu.foreground");
        setBackground(color);
        innerPanel.setBackground(color2);

        // Row #1


        addElement(new JLabel("Hello World!!!"), 30);
        addElement(new JLabel("WEW"), 10);
        addElement(new SlimButton("inv"));
        addElement(new SlimButton("ty"));
        newLine();

        // Test Row
        JPanel testRow = new JPanel(new GridBagLayout());
        testRow.setBackground(Color.RED);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 3;
        testRow.add(new JLabel("Test Username"), gc);
        gc.gridx++;
        gc.weightx = 1;
        testRow.add(new JLabel("3c"), gc);
        gc.gridx++;
        testRow.add(new SlimButton("inv"), gc);
        gc.gridx++;
        testRow.add(new SlimButton("ty"), gc);
        gc.gridx++;

        addElement(testRow);
        newLine();

        // Row #2

        addElement(new SlimButton("cool"));
        addElement(new SlimButton("beans"));
        addElement(new JSlider());

        setPreferredSize(new Dimension(targetWidth, getPreferredSize().height));
        setMinimumSize(new Dimension(targetWidth, getPreferredSize().height));

        setBackground(color);
    }

    private void addElement(JComponent component) {
        addElement(component, -1);
    }

    private void addElement(JComponent component, float percent) {
        if (currentPanel == null) {
            currentPanel = new JPanel(new GridBagLayout());
            contentPanel.add(currentPanel, panelGC);
            panelGC.gridy++;
        }
        // Add Component
        if (percent > 0) {
//            component.setPreferredSize();
            currentPanel.add(component, gc);
        } else {
            currentPanel.add(component, gc);
        }
        gc.gridx++;
    }

    private void newLine() {
        gc.gridx = 0;
        gc.gridy++;
        currentPanel = null;
    }

}
