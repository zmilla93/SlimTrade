package com.slimtrade.gui.messaging;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class NotificationPanel extends JPanel {

    private int targetWidth = 400;

    ArrayList<WeightedPanel> topPanels = new ArrayList<>();
    ArrayList<WeightedPanel> bottomPanels = new ArrayList<>();

    //    ArrayList<MacroButton> topMacros = new ArrayList<>();
//    ArrayList<MacroButton> bottomMacros = new ArrayList<>();
    protected MacroButton[] topMacros = new MacroButton[0];
    protected MacroButton[] bottomMacros = new MacroButton[0];
    protected Color borderColor = Color.RED;

    private JPanel topButtons;
    private JPanel bottomButtons;


//    GridBagConstraints buttonGC = new GridBagConstraints();

    public NotificationPanel() {
    }

    protected void buildPanel() {
        setLayout(new GridBagLayout());
        JPanel border = new JPanel(new GridBagLayout());
        JPanel contentPanel = new JPanel(new BorderLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(2, 2, 2, 2);
        add(border, gc);
        border.add(contentPanel, gc);

        setBackground(UIManager.getColor("Button.background"));
        border.setBackground(borderColor);

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topInfo = new JPanel(new GridBagLayout());
        topButtons = new JPanel(new GridBagLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel bottomInfo = new JPanel(new GridBagLayout());
        bottomButtons = new JPanel(new GridBagLayout());

        // Build
        topPanel.add(topInfo, BorderLayout.CENTER);
        topPanel.add(topButtons, BorderLayout.EAST);
        bottomPanel.add(bottomInfo, BorderLayout.CENTER);
        bottomPanel.add(bottomButtons, BorderLayout.EAST);

        buildButtonPanels();

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        gc.insets = new Insets(0, 0, 0, 0);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
//        gc.fill = GridBagConstraints.BOTH;
        for (WeightedPanel panel : topPanels) {
            gc.weightx = panel.weight;
            topInfo.add(panel.panel, gc);
            gc.gridx++;
        }
        gc.gridx = 0;
        for (WeightedPanel panel : bottomPanels) {
            gc.weightx = panel.weight;
            bottomInfo.add(panel.panel, gc);
            gc.gridx++;
        }
        setPreferredSize(new Dimension(targetWidth, getPreferredSize().height));
        setMinimumSize(new Dimension(targetWidth, getPreferredSize().height));
    }

    private void buildButtonPanels() {
//        JPanel topButtons = new JPanel(new GridBagLayout());
//        JPanel bottomButtons = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridy = 0;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.VERTICAL;
        int strutHeight = 0;
        int topStrutX;
        for (MacroButton macro : topMacros) {
            JButton button;
            System.out.println("PATH:" + DefaultIcon.PLAY.path);
            System.out.println("icon:" + macro.icon);
//            System.out.println("PATH:" + macro.icon.path);
            if (macro.buttonType == MacroButton.MacroButtonType.TEXT)
                button = new NotificationTextButton(macro.text);
            else
//                button = new NotificationIconButton(macro.icon.path);
                button = new NotificationIconButton(macro.icon.path);
            if (button.getPreferredSize().height > strutHeight) strutHeight = button.getPreferredSize().height;
            topButtons.add(button, gc);
            gc.gridx++;
        }
        topStrutX = gc.gridx;

        // Close Button
        JButton closeButton = new NotificationIconButton(DefaultIcon.CLOSE.path);
        topButtons.add(closeButton, gc);
        gc.gridx = 0;

        for (MacroButton macro : bottomMacros) {
            JButton button;
            if (macro.buttonType == MacroButton.MacroButtonType.TEXT)
                button = new NotificationTextButton(macro.text);
            else
//                button = new NotificationIconButton(macro.icon.path);
                button = new NotificationIconButton(macro.icon.path);
            if (button.getPreferredSize().height > strutHeight) strutHeight = button.getPreferredSize().height;
            bottomButtons.add(button, gc);
            gc.gridx++;
        }
//        bottomButtons.add(Box.createVerticalStrut(strutHeight), gc);
//        gc.gridx = topStrutX;
//        topButtons.add(Box.createVerticalStrut(strutHeight), gc);
        JPanel self = this;
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameManager.messageManager.removeMessage(self);
            }
        });
    }

    protected void addTopPanel(JPanel panel, float weight) {
        WeightedPanel weightedPanel = new WeightedPanel(panel, weight);
        topPanels.add(weightedPanel);
    }

    protected void addBottomPanel(JPanel panel, float weight) {
        WeightedPanel weightedPanel = new WeightedPanel(panel, weight);
        bottomPanels.add(weightedPanel);
    }

    protected void addTopButton(JButton button) {
//        topButtons.add(button);
    }

    protected void addBottomButton(JButton button) {
//        bottomButtons.add(button);
    }

}

class WeightedPanel {
    JPanel panel;
    float weight;

    public WeightedPanel(JPanel panel, float weight) {
        this.panel = panel;
        this.weight = weight;
    }
}