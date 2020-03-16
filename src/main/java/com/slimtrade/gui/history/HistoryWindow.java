package com.slimtrade.gui.history;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomScrollPane;
import com.slimtrade.gui.options.ListButton;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.panels.BufferPanel;

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
		super("History");
		this.setAlwaysOnTop(false);
		this.setFocusable(true);
		this.setFocusableWindowState(true);
		timeStyle = TimeStyle.H24;
		dateStyle = DateStyle.DDMMYY;
		timeStyle = App.saveManager.saveFile.timeStyle;
		dateStyle = App.saveManager.saveFile.dateStyle;
		orderType = App.saveManager.saveFile.orderType;
		this.setPreferredSize(new Dimension(900, 550));
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

        ListButton incomingButton = new ListButton("Incoming");
        ListButton outgoingButton = new ListButton("Outgoing");



//		JButton savedButton = new JButton("Saved");

		buttonPanel.add(incomingButton);
		buttonPanel.add(outgoingButton);
        ListButton.link(buttonPanel, incomingButton);
        ListButton.link(buttonPanel, outgoingButton);
        incomingButton.active = true;
//		buttonPanel.add(savedButton);

//        incomingScroll.getVerticalScrollBar().setUI(new CustomScrollBarUI(incomingScroll.getVerticalScrollBar()));
//        outgoingScroll.getVerticalScrollBar().setUI(new CustomScrollBarUI(outgoingScroll.getVerticalScrollBar()));
//        incomingScroll.getVerticalScrollBar().setUI(CustomScrollBarUI.DEFAULT_SCROLLBAR);
//        incomingScroll.getVerticalScrollBar().setUI(CustomScrollBarUI.DEFAULT_SCROLLBAR);
//        outgoingScroll.getVerticalScrollBar().setUI(CustomScrollBarUI.DEFAULT_SCROLLBAR);

		// scrollPane

		container.add(innerPanel, BorderLayout.CENTER);

		AbstractResizableWindow local = this;
		incomingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outgoingScroll.setVisible(false);
//				savedScroll.setVisible(false);
				incomingScroll.setVisible(true);
				innerPanel.add(incomingScroll, BorderLayout.CENTER);

				innerPanel.revalidate();
				innerPanel.repaint();

			}
		});
		
		outgoingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incomingScroll.setVisible(false);
//				savedScroll.setVisible(false);
				outgoingScroll.setVisible(true);
				innerPanel.add(outgoingScroll, BorderLayout.CENTER);

				innerPanel.revalidate();
				innerPanel.repaint();
			}
		});

		innerPanel.add(incomingScroll, BorderLayout.CENTER);
		innerPanel.revalidate();
		innerPanel.repaint();

		this.pack();
		FrameManager.centerFrame(this);
	}

	public void setDateStyle(DateStyle style) {
		HistoryWindow.dateStyle = style;
		incomingPanel.updateDate();
	}

	public void setTimeStyle(TimeStyle style) {
		HistoryWindow.timeStyle = style;
		incomingPanel.updateTime();
	}
	
	public void setOrderType(OrderType type){
		HistoryWindow.orderType = type;
		incomingPanel.refreshOrder();
		outgoingPanel.refreshOrder();
	}

	public void addTrade(TradeOffer trade, boolean updateUI) {
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

	public void buildHistory() {
		outgoingPanel.initUI();
		incomingPanel.initUI();
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
		if(incomingPanel.isVisible()) {
			App.eventManager.recursiveColor(outgoingPanel);
		}
		if(outgoingPanel.isVisible()) {
			App.eventManager.recursiveColor(incomingPanel);
		}
		incomingScroll.setBorder(ColorManager.BORDER_LOW_CONTRAST_1);
		outgoingScroll.setBorder(ColorManager.BORDER_LOW_CONTRAST_1);
		incomingPanel.setBackground(Color.GREEN);
		incomingScroll.getRootPane().setBackground(Color.GREEN);
	}
}
