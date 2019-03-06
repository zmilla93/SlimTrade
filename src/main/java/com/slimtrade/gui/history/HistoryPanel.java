package main.java.com.slimtrade.gui.history;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.enums.DateStyle;
import main.java.com.slimtrade.enums.TimeStyle;

public class HistoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// private TradeOffer savedTrade;

	private ArrayList<TradeOffer> trades = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRow> tradePanels = new ArrayList<HistoryRow>();

	private int maxTrades = 100;
	private JPanel contentPanel;
	
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
		if (trades.size() == maxTrades) {
			trades.remove(0);
			if (updateUI) {
				contentPanel.remove(tradePanels.get(0));
				tradePanels.remove(0);
			}
		}
		// Add new trade
		trades.add(trade);
		if (updateUI) {
			tradePanels.add(new HistoryRow(trade));
			contentPanel.add(tradePanels.get(tradePanels.size() - 1), 0);
			this.revalidate();
			this.repaint();
		}
	}

	public void initUI() {
//		Debugger.benchmarkStart();
		for (TradeOffer trade : trades) {
			HistoryRow row = new HistoryRow(trade);
			contentPanel.add(row);
			tradePanels.add(row);
		}
//		Main.logger.log(Level.INFO, "HISTORY BUILD TIME : " + Debugger.benchmark());
		this.revalidate();
		this.repaint();
	}
	
	public void setOrder(boolean order){
		if(order){
			for(HistoryRow row : tradePanels){
				contentPanel.add(row);
			}
		}else{
			for(HistoryRow row : tradePanels){
				contentPanel.add(row, 0);
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
	
	public void setTimeStyle(TimeStyle style){
		
	}

}
