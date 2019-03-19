package main.java.com.slimtrade.gui.history;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.enums.DateStyle;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.enums.TimeStyle;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.AbstractResizableWindow;
import main.java.com.slimtrade.gui.options.OrderType;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class HistoryWindow extends AbstractResizableWindow {
	private static final long serialVersionUID = 1L;

	protected static TimeStyle timeStyle;
	protected static DateStyle dateStyle;
	protected static OrderType orderType;
	// TODO : Move gc to parent?
	private GridBagConstraints gc = new GridBagConstraints();
	public Insets inset = new Insets(0, 0, 0, 0);

	HistoryPanel incomingPanel = new HistoryPanel();
	HistoryPanel outgoingPanel = new HistoryPanel();
	HistoryPanel savedPanel = new HistoryPanel();
	// private ArrayList<TradeOffer> incomingTradeData = new
	// ArrayList<TradeOffer>();
	
//	private int maxTrades = 50;
	
	public HistoryWindow() {
		super("History");
		timeStyle = TimeStyle.H24;
		dateStyle = DateStyle.DDMMYY;
		timeStyle = TimeStyle.valueOf(Main.saveManager.getEnumValue(TimeStyle.class, "history", "timeStyle"));
		dateStyle = DateStyle.valueOf(Main.saveManager.getEnumValue(DateStyle.class, "history", "dateStyle"));
		orderType = OrderType.valueOf(Main.saveManager.getEnumValue(OrderType.class, "history", "orderType"));
		this.setPreferredSize(new Dimension(900, 600));
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = inset;
		container.setLayout(new BorderLayout());
		
		savedPanel.setClose(true);

		// GridBagPanel historyContainer = new GridBagPanel();

		JScrollPane incomingScroll = new JScrollPane(incomingPanel);
		JScrollPane outgoingScroll = new JScrollPane(outgoingPanel);
		JScrollPane savedScroll = new JScrollPane(savedPanel);

		// incomingScroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
		// Color.BLACK));
		// outgoingScroll.setBorder(null);

		JPanel buttonPanel = new JPanel();
		JPanel innerPanel = new JPanel();

		// incomingPanel.setBackground(Color.GREEN);

		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(buttonPanel, BorderLayout.NORTH);
		innerPanel.add(new BufferPanel(10, 0), BorderLayout.WEST);
		innerPanel.add(new BufferPanel(0, 10), BorderLayout.SOUTH);
		innerPanel.add(new BufferPanel(10, 0), BorderLayout.EAST);

		JButton incomingButton = new JButton("Incoming");
		JButton outgoingButton = new JButton("Outgoing");
		JButton savedButton = new JButton("Saved");

		buttonPanel.add(incomingButton);
		buttonPanel.add(outgoingButton);
//		buttonPanel.add(savedButton);

		// scrollPane

		container.add(innerPanel, BorderLayout.CENTER);

		JPanel bufferPanel = new BufferPanel(0, 10);

		AbstractResizableWindow local = this;
		incomingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outgoingScroll.setVisible(false);
				savedScroll.setVisible(false);
				incomingScroll.setVisible(true);
				innerPanel.add(incomingScroll, BorderLayout.CENTER);

				innerPanel.revalidate();
				innerPanel.repaint();

			}
		});
		
		outgoingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incomingScroll.setVisible(false);
				savedScroll.setVisible(false);
				outgoingScroll.setVisible(true);
				innerPanel.add(outgoingScroll, BorderLayout.CENTER);

				innerPanel.revalidate();
				innerPanel.repaint();
			}
		});
		
		savedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incomingScroll.setVisible(false);
				outgoingScroll.setVisible(false);
				savedScroll.setVisible(true);
				innerPanel.add(savedScroll, BorderLayout.CENTER);
				
				innerPanel.revalidate();
				innerPanel.repaint();
			}
		});
		
		TradeOffer trade = new TradeOffer("", "", MessageType.INCOMING_TRADE, "<GLD>", "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", 3, 3, "", "");
		savedPanel.addTrade(trade, true);
		
		incomingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		outgoingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		savedScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		innerPanel.add(incomingScroll, BorderLayout.CENTER);
		innerPanel.revalidate();
		innerPanel.repaint();

		this.pack();
		FrameManager.centerFrame(this);
		// incomingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
		case UNKNOWN:
			break;
		default:
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

//	public void setOrder(boolean order) {
//		incomingPanel.setOrder(order);
//	}

}
