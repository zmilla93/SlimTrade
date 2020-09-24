package com.slimtrade.gui.messaging;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.basic.ColorPanel;
import com.slimtrade.gui.basic.PaintedPanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.panels.PricePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class AbstractMessagePanel extends ColorPanel {

    private static final long serialVersionUID = 1L;
    // TODO Load from drive
    // TODO : Move?
    // TODO : switch totalwidth/windowHeight to use get/set?
    protected MessageType messageType;
    // Heights
    // protected int minHeight;
    // protected int maxHeight;
    protected int messageWidth;
    protected int messageHeight;
    public static final int BORDER_SIZE = 2;
    protected int rowHeight;
    public static int totalWidth;
    public static int totalHeight;
    protected final double timerWeight = 0.1;

    protected static GridBagLayout gb = new GridBagLayout();
    protected GridBagConstraints gc = new GridBagConstraints();

    // Panels
    protected PaintedPanel namePanel = new PaintedPanel();
    protected PricePanel pricePanel = new PricePanel();
    protected PaintedPanel itemPanel = new PaintedPanel();

    protected JPanel borderPanel = new ColorPanel();
    protected JPanel container = new ColorPanel();
    protected PaintedPanel timerPanel = new PaintedPanel();
    protected JLabel timerLabel;
    protected IconButton closeButton;

    protected int buttonCountTop;
    protected int buttonCountBottom;
    // TODO : Change to generic offer
    protected TradeOffer trade;

    private int second = 0;
    private int minute = 1;
    private Timer secondTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            second++;
            if (second > 59) {
                secondTimer.stop();
                minuteTimer.start();
                timerLabel.setText("1m");
            } else {
                timerLabel.setText(second + "s");
            }
        }
    });
    private Timer minuteTimer = new Timer(60000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            minute++;
            timerLabel.setText(minute + "m");
        }
    });

    public AbstractMessagePanel(TradeOffer trade) {
        timerPanel.setLabel(new CustomLabel("0s"));
        timerLabel = timerPanel.getLabel();
        this.trade = trade;
        this.setLayout(gb);
        borderPanel.setLayout(gb);
        container.setLayout(gb);
        gc.gridx = 0;
        gc.gridy = 0;
    }

    public void resizeMessage(int i) {
        System.out.println("Abstract Resize");
    }

    public void setCloseButton(int size) {
        this.closeButton = new IconButton(DefaultIcons.CLOSE, size);
        closeButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 || messageType == MessageType.INCOMING_TRADE) {
                    stopTimer();
                }
            }
        });
    }

    public JButton getCloseButton() {
        return this.closeButton;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    protected void resizeFrames() {
    }

    public void startTimer() {
        secondTimer.start();
    }

    public void stopTimer() {
        secondTimer.stop();
    }

    public void restartTimer() {
        secondTimer.restart();
    }

    @Override
    public void updateColor() {
        super.updateColor();
        timerPanel.setBackgroundColor(ColorManager.BACKGROUND);
        timerPanel.setBorderColor(ColorManager.BACKGROUND);
    }
}
