package com.slimtrade.gui.messaging;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class NotificationPanel extends JPanel {

    private final int targetWidth = 400;

    private final ArrayList<WeightedComponent> topPanels = new ArrayList<>();
    private final ArrayList<WeightedComponent> bottomPanels = new ArrayList<>();

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

    protected void buildPanel() {
        buildPanel(true);
    }

    protected void buildPanel(boolean createListeners) {
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
        topInfo.setBackground(Color.RED);
        topButtons = new JPanel(new GridBagLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel bottomInfo = new JPanel(new GridBagLayout());
        bottomButtons = new JPanel(new GridBagLayout());

        // Build
        topPanel.add(topInfo, BorderLayout.CENTER);
        topPanel.add(topButtons, BorderLayout.EAST);

        bottomPanel.add(bottomInfo, BorderLayout.CENTER);
        bottomPanel.add(bottomButtons, BorderLayout.EAST);

        buildButtonPanels(createListeners);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        gc.insets = new Insets(0, 0, 0, 0);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        for (WeightedComponent panel : topPanels) {
            gc.weightx = panel.weight;
            gc.anchor = GridBagConstraints.EAST;
            topInfo.add(panel.panel, gc);
            gc.gridx++;

        }
        gc.gridx = 0;
        for (WeightedComponent panel : bottomPanels) {
            gc.weightx = panel.weight;
            bottomInfo.add(panel.panel, gc);
            gc.gridx++;
        }
        setPreferredSize(new Dimension(targetWidth, getPreferredSize().height));
        setMinimumSize(new Dimension(targetWidth, getPreferredSize().height));
    }

    // TODO : Need to generalize this function
    private void buildButtonPanels(boolean createListeners) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.VERTICAL;
        int strutHeight = 0;
        int topStrutX;
        for (MacroButton macro : topMacros) {
            JButton button;
            if (macro.buttonType == MacroButton.MacroButtonType.TEXT)
                button = new NotificationTextButton(macro.text);
            else
                button = new NotificationIconButton(macro.icon.path);
            if (createListeners) {
                button.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            PoeInterface.runCommand(macro.lmbResponse, tradeOffer);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            PoeInterface.runCommand(macro.rmbResponse, tradeOffer);
                        }
                    }
                });
            }
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
            if (createListeners) {
                button.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            PoeInterface.runCommand(macro.lmbResponse, tradeOffer);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            PoeInterface.runCommand(macro.rmbResponse, tradeOffer);
                        }
                    }
                });
            }
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

    protected void addTopPanel(JComponent panel, float weight) {
        WeightedComponent weightedPanel = new WeightedComponent(panel, weight);
        topPanels.add(weightedPanel);
    }

    protected void addBottomPanel(JPanel panel, float weight) {
        WeightedComponent weightedPanel = new WeightedComponent(panel, weight);
        bottomPanels.add(weightedPanel);
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

class WeightedComponent {
    JComponent panel;
    float weight;

    public WeightedComponent(JComponent panel, float weight) {
        this.panel = panel;
        this.weight = weight;
    }
}