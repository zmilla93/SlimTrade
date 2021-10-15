package com.slimtrade.gui.history;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.custom.CustomScrollPane;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.options.SelectorButton;
import com.slimtrade.gui.panels.BufferPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class HistoryWindow extends AbstractResizableWindow implements IColorable {
    private static final long serialVersionUID = 1L;

    protected static TimeStyle timeStyle;
    protected static DateStyle dateStyle;
    protected static OrderType orderType;
    // TODO : Move gc to parent?
    private GridBagConstraints gc = new GridBagConstraints();
    public Insets inset = new Insets(0, 0, 0, 0);

    private HistoryPanel incomingPanel = new HistoryPanel();
    private HistoryPanel outgoingPanel = new HistoryPanel();


    private JPanel innerPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    private JScrollPane incomingScroll;
    private JScrollPane outgoingScroll;


    public HistoryWindow() {
        super("History", true, true);
        this.setAlwaysOnTop(false);
        this.setFocusable(true);
        this.setFocusableWindowState(true);
        timeStyle = TimeStyle.H24;
        dateStyle = DateStyle.DDMMYY;
        timeStyle = App.saveManager.settingsSaveFile.timeStyle;
        dateStyle = App.saveManager.settingsSaveFile.dateStyle;
        orderType = App.saveManager.settingsSaveFile.orderType;
        this.setDefaultSize(new Dimension(900, 550));
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = inset;
        container.setLayout(new BorderLayout());

        incomingScroll = new CustomScrollPane(incomingPanel);
        outgoingScroll = new CustomScrollPane(outgoingPanel);

//		incomingScroll.setBorder(null);
//		outgoingScroll.setBorder(null);

        // incomingPanel.setBackground(Color.GREEN);

        innerPanel.setLayout(new BorderLayout());
        innerPanel.add(buttonPanel, BorderLayout.NORTH);
        innerPanel.add(new BufferPanel(10, 0), BorderLayout.WEST);
        innerPanel.add(new BufferPanel(0, 10), BorderLayout.SOUTH);
        innerPanel.add(new BufferPanel(10, 0), BorderLayout.EAST);

        SelectorButton incomingButton = new SelectorButton("Incoming");
        SelectorButton outgoingButton = new SelectorButton("Outgoing");


//		JButton savedButton = new JButton("Saved");

        buttonPanel.add(incomingButton);
        buttonPanel.add(outgoingButton);
//        ListButton.link(buttonPanel, incomingButton);
//        ListButton.link(buttonPanel, outgoingButton);

        incomingButton.selected = true;
//		buttonPanel.add(savedButton);

//        incomingScroll.getVerticalScrollBar().setUI(new CustomScrollBarUI(incomingScroll.getVerticalScrollBar()));
//        outgoingScroll.getVerticalScrollBar().setUI(new CustomScrollBarUI(outgoingScroll.getVerticalScrollBar()));
//        incomingScroll.getVerticalScrollBar().setUI(CustomScrollBarUI.DEFAULT_SCROLLBAR);
//        incomingScroll.getVerticalScrollBar().setUI(CustomScrollBarUI.DEFAULT_SCROLLBAR);
//        outgoingScroll.getVerticalScrollBar().setUI(CustomScrollBarUI.DEFAULT_SCROLLBAR);

        // scrollPane

        container.add(innerPanel, BorderLayout.CENTER);

        incomingButton.addActionListener(e -> {
            innerPanel.remove(outgoingScroll);
            innerPanel.add(incomingScroll, BorderLayout.CENTER);
            ColorManager.recursiveColor(innerPanel);
            innerPanel.revalidate();
            innerPanel.repaint();

        });

        outgoingButton.addActionListener(e -> {
            innerPanel.remove(incomingScroll);
            innerPanel.add(outgoingScroll, BorderLayout.CENTER);
            ColorManager.recursiveColor(innerPanel);
            innerPanel.revalidate();
            innerPanel.repaint();
        });

        innerPanel.add(incomingScroll, BorderLayout.CENTER);
        innerPanel.revalidate();
        innerPanel.repaint();
        pack();

    }

    public void setDateStyle(DateStyle style) {
        HistoryWindow.dateStyle = style;
        incomingPanel.updateDate();
        outgoingPanel.updateDate();
    }

    public void setTimeStyle(TimeStyle style) {
        HistoryWindow.timeStyle = style;
        incomingPanel.updateTime();
        outgoingPanel.updateTime();
    }

    public void setOrderType(OrderType type) {
        HistoryWindow.orderType = type;
        incomingPanel.refreshOrder();
        outgoingPanel.refreshOrder();
    }

    public void addTrade(TradeOffer trade, boolean updateUI) {
        assert(SwingUtilities.isEventDispatchThread());
        if (App.saveManager.settingsSaveFile.historyLimit == 0) {
            return;
        }
        switch (trade.messageType) {
            case CHAT_SCANNER:
                break;
            case INCOMING_TRADE:
                incomingPanel.addTrade(trade, updateUI);
                break;
            case OUTGOING_TRADE:
                outgoingPanel.addTrade(trade, updateUI);
                break;
        }
    }

    public void clearHistory() {
        incomingPanel.clearTrades();
        outgoingPanel.clearTrades();
    }

    public void buildHistory() {
        incomingPanel.initUI();
        outgoingPanel.initUI();
        incomingPanel.revalidate();
        incomingPanel.repaint();
        this.revalidate();
        this.repaint();
    }

    @Override
    public void updateColor() {
        super.updateColor();
        buttonPanel.setBackground(ColorManager.BACKGROUND);
        innerPanel.setBackground(ColorManager.BACKGROUND);
        ColorManager.recursiveColor(innerPanel);
        incomingScroll.setBorder(ColorManager.BORDER_LOW_CONTRAST_1);
        outgoingScroll.setBorder(ColorManager.BORDER_LOW_CONTRAST_1);
    }

    @Override
    public void pinAction(MouseEvent e) {
        super.pinAction(e);
        FrameManager.saveWindowPins();
    }

//    @Override
//    public void save() {
//        App.saveManager.pinSaveFile.historyPin = getPinElement();
//        App.saveManager.savePinsToDisk();
//    }
//
//    @Override
//    public void load() {
//        App.saveManager.loadPinsFromDisk();
//        PinElement pin = App.saveManager.pinSaveFile.historyPin;
//        this.pinned = pin.pinned;
//        if (this.pinned) {
//            applyPinElement(pin);
//        }
//        updatePullbars();
//    }

}
