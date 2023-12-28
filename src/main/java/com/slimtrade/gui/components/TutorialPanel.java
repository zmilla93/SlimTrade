package com.slimtrade.gui.components;

import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class TutorialPanel extends JPanel {

    private final GridBagConstraints gc = ZUtil.getGC();

    public TutorialPanel() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
    }

    public void addHeader(String title) {
        JLabel label = new JLabel(title);
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);

        // Title Panel
        JPanel titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints titleGC = ZUtil.getGC();
        titlePanel.add(label, titleGC);

        // Outer panel
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(titlePanel, BorderLayout.CENTER);
        outerPanel.add(separator, BorderLayout.SOUTH);

        add(outerPanel, gc);
        gc.gridy++;
        addVerticalStrutSmall();
    }

    public JLabel addLabel(String text) {
        JLabel label = new PlainLabel(text);
        addComponent(label);
        return label;
    }

    public JLabel addLabelBold(String text) {
        JLabel label = new JLabel(text);
        addComponent(label);
        return label;
    }

    public Component addComponent(Component component) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints panelGC = ZUtil.getGC();
        panel.add(component, panelGC);

        add(panel, gc);
        gc.gridy++;
        return component;
    }

    public void addVerticalStrut() {
        add(Box.createVerticalStrut(GUIReferences.INSET), gc);
        gc.gridy++;
    }

    public void addVerticalStrutSmall() {
        add(Box.createVerticalStrut(GUIReferences.SMALL_INSET), gc);
        gc.gridy++;
    }

}
