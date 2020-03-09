package com.slimtrade.gui.history;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.slimtrade.App;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.options.OrderType;

public class HistoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// private TradeOffer savedTrade;

	private ArrayList<TradeOffer> trades = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRow> tradePanels = new ArrayList<HistoryRow>();


	private JPanel contentPanel;
	
	private static int maxTrades = 10;
	
	private boolean close = false;
	
	HistoryPanel() {
		this.setLayout(new BorderLayout());
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		this.add(contentPanel, BorderLayout.CENTER);
		this.setMaxTrades(App.saveManager.saveFile.historyLimit);
	}

	public void addTrade(TradeOffer trade, boolean updateUI) {
		int i = 0;
		// Delete old duplicate
		for (TradeOffer savedTrade : trades) {
			if (TradeUtility.isDuplicateTrade(trade, savedTrade)) {
				trades.remove(i);
				if (updateUI) {
					contentPanel.remove(tradePanels.get(i));
					tradePanels.remove(i);
				}
				break;
			}
			i++;
		}
		// Delete oldest trade if at max trades
		if (trades.size() >= maxTrades && maxTrades > 0) {
			trades.remove(0);
			if (updateUI) {
				contentPanel.remove(tradePanels.get(0));
				tradePanels.remove(0);
			}
		}
		// Add new trade
		trades.add(trade);
		if (updateUI) {
			HistoryRow row = new HistoryRow(trade, close);
			tradePanels.add(row);
			if(HistoryWindow.orderType == OrderType.NEW_FIRST){
				contentPanel.add(tradePanels.get(tradePanels.size() - 1), 0);
			}else{
				contentPanel.add(tradePanels.get(tradePanels.size() - 1));
			}
			App.eventManager.recursiveColor(row);
			this.revalidate();
			this.repaint();
		}
	}

	public void initUI() {
//		Debugger.benchmarkStart();
		for (TradeOffer trade : trades) {
			HistoryRow row = new HistoryRow(trade, close);
			if(HistoryWindow.orderType == OrderType.NEW_FIRST){
				contentPanel.add(row, 0);
			}else{
				contentPanel.add(row);
			}
			tradePanels.add(row);
		}
		App.eventManager.recursiveColor(this);
//		App.logger.log(Level.INFO, "HISTORY BUILD TIME : " + Debugger.benchmark());
		this.revalidate();
		this.repaint();
	}
	
	public void refreshOrder(){
		if(HistoryWindow.orderType == OrderType.NEW_FIRST){
			for(HistoryRow row : tradePanels){
				contentPanel.add(row, 0);
			}
		}else{
			for(HistoryRow row : tradePanels){
				contentPanel.add(row);
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	public void updateDate(){
		for(HistoryRow row : tradePanels){
			row.updateDate();
		}
	}
	
	public void updateTime(){
		for(HistoryRow row : tradePanels){
			row.updateTime();
		}
	}
	
	public void setMaxTrades(int maxTrades){
		HistoryPanel.maxTrades = maxTrades;
	}
	
	public void setClose(boolean close){
		this.close = close;
	}
	
}
