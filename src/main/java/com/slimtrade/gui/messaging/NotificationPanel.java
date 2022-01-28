package com.slimtrade.gui.messaging;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class NotificationPanel extends JPanel {

    private final int targetWidth = 400;

    private final ArrayList<WeightedPanel> topPanels = new ArrayList<>();
    private final ArrayList<WeightedPanel> bottomPanels = new ArrayList<>();

    protected TradeOffer tradeOffer;
    protected MacroButton[] topMacros = new MacroButton[0];
    protected MacroButton[] bottomMacros = new MacroButton[0];
    protected Color borderColor = Color.RED;

    private JPanel topButtons;
    private JPanel bottomButtons;

    private JPanel timerPanel;

    private int second;
    private int minute;
    StringBuilder timerBuilder = new StringBuilder();
    private Timer secondTimer;
    private Timer minuteTimer;

//    private Timer secondTimer = new Timer(1000, new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            second++;
//            if (second > 59) {
//                secondTimer.stop();
//                minuteTimer.start();
//                timerLabel.setText("1m");
//            } else {
//                timerLabel.setText(second + "s");
//            }
//        }
//    });
//    private Timer minuteTimer = new Timer(60000, new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            minute++;
//            timerLabel.setText(minute + "m");
//        }
//    });

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
            button.addActionListener(e -> PoeInterface.runCommand(macro.lmbResponse, tradeOffer));
            if (button.getPreferredSize().height > strutHeight) strutHeight = button.getPreferredSize().height;
            topButtons.add(button, gc);
            gc.gridx++;
        }

        topStrutX = gc.gridx;

        // Close Button
        JButton closeButton = new NotificationIconButton(DefaultIcon.CLOSE.path);
        topButtons.add(closeButton, gc);
        if (closeButton.getPreferredSize().height > strutHeight) strutHeight = closeButton.getPreferredSize().height;
        gc.gridx = 0;

        for (MacroButton macro : bottomMacros) {
            JButton button;
            if (macro.buttonType == MacroButton.MacroButtonType.TEXT)
                button = new NotificationTextButton(macro.text);
            else
                button = new NotificationIconButton(macro.icon.path);
            button.addActionListener(e -> PoeInterface.runCommand(macro.lmbResponse, tradeOffer));
            if (button.getPreferredSize().height > strutHeight) strutHeight = button.getPreferredSize().height;
            bottomButtons.add(button, gc);
            gc.gridx++;
        }
        bottomButtons.add(Box.createVerticalStrut(strutHeight), gc);
        gc.gridx = topStrutX;
        topButtons.add(Box.createVerticalStrut(strutHeight), gc);
        JPanel self = this;
        closeButton.addActionListener(e -> FrameManager.messageManager.removeMessage(self));
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

    protected JPanel getTimerPanel() {
        if (timerPanel == null) {
            timerPanel = new JPanel(new GridBagLayout());
            JLabel timerLabel = new JLabel("0s");
            timerPanel.add(timerLabel);
            secondTimer = new Timer(1000, e -> {
                second++;
                if (second > 59) {
                    secondTimer.stop();
                    minuteTimer.start();
                    timerLabel.setText("1m");
                } else {
                    timerLabel.setText(second + "s");
                }
            });
            minuteTimer = new Timer(60000, e -> {
                minute++;
                timerLabel.setText(minute + "m");
            });
        }
        return timerPanel;
    }

    public void startTimer() {
        secondTimer.start();
    }

    public void stopTimer() {
        secondTimer.stop();
        minuteTimer.stop();
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