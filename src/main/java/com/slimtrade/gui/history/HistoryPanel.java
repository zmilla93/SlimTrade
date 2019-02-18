package main.java.com.slimtrade.gui.history;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;

public class HistoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// private TradeOffer savedTrade;

	private ArrayList<TradeOffer> incomingTradeData = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRow> incomingTradePanels = new ArrayList<HistoryRow>();
	private ArrayList<TradeOffer> outgoingTradeData = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRow> outgoingTradePanels = new ArrayList<HistoryRow>();

	private int maxTrades = 50;
	private JPanel contentPanel;
//	private JScrollPane contentScroll;
	
	HistoryPanel() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.RED);
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

//		contentPanel.setBorder(null);
		this.add(contentPanel, BorderLayout.CENTER);
//		initUI();
	}

	public void addTrade(TradeOffer trade, boolean updateUI) {
		int i = 0;
		// Delete old duplicate
		for (TradeOffer savedTrade : incomingTradeData) {
			if (TradeUtility.isDuplicateTrade(trade, savedTrade)) {
				incomingTradeData.remove(i);
				if (updateUI) {
					contentPanel.remove(incomingTradePanels.get(i));
					incomingTradePanels.remove(i);
				}
				break;
			}
			i++;
		}
		// Delete oldest trade if at max trades
		if (incomingTradeData.size() == maxTrades) {
			incomingTradeData.remove(0);
			if (updateUI) {
				contentPanel.remove(incomingTradePanels.get(0));
				incomingTradePanels.remove(0);
			}
		}
		// Add new trade
		incomingTradeData.add(trade);
		if (updateUI) {
			incomingTradePanels.add(new HistoryRow(trade));
			contentPanel.add(incomingTradePanels.get(incomingTradePanels.size() - 1), 0);
			this.revalidate();
			this.repaint();
		}
	}

	public void initUI() {
		for (TradeOffer trade : incomingTradeData) {
			HistoryRow row = new HistoryRow(trade);
			contentPanel.add(row);
			incomingTradePanels.add(row);
		}
		this.revalidate();
		this.repaint();
	}

}
