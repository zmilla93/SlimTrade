package main.java.com.slimtrade.windows;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.FrameManager;
import main.java.com.slimtrade.core.TradeOffer;
import main.java.com.slimtrade.core.TradeUtility;
import main.java.com.slimtrade.dialog.BasicWindowDialog;
import main.java.com.slimtrade.panels.HistoryRowPanel;

public class HistoryWindow extends BasicWindowDialog{

	private static final long serialVersionUID = 1L;
	public static int width = 800;
	public static int height = 400;
	private int maxTrades = 20;
	private ArrayList<TradeOffer> incomingTrades = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRowPanel> incomingTradeRows = new ArrayList<HistoryRowPanel>();
	
	
	private ArrayList<TradeOffer> outgoingTrades = new ArrayList<TradeOffer>();
	
	
	JPanel historyContainer = new JPanel();
	
	public HistoryWindow(String title){
		super("History");
		this.resizeWindow(width, height);
		

		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		historyContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		historyContainer.setLayout(new BoxLayout(historyContainer, BoxLayout.PAGE_AXIS));
		historyContainer.setPreferredSize(new Dimension((int)(width*0.9), height*4));
		
		JScrollPane scrollPane = new JScrollPane(historyContainer);
		scrollPane.setPreferredSize(new Dimension(width, height));
		container.add(scrollPane);

		
		FrameManager.centerFrame(this);
		this.setVisible(true);
	}
	
	public void addTrade(TradeOffer trade, boolean update){
		switch(trade.msgType){
		case INCOMING_TRADE:
			for(TradeOffer savedTrade : incomingTrades){
				if(TradeUtility.isDuplicateTrade(trade, savedTrade)){
					return;
				}
			}
			if(incomingTrades.size() > maxTrades){
//				historyContainer.remove(incomingTradeRows.get(0));
//				incomingTradeRows.remove(0);
				incomingTrades.remove(0);
			}
			incomingTrades.add(trade);
//			HistoryRowPanel row = new HistoryRowPanel(trade.date, trade.time, trade.playerName, trade.itemName, trade.priceTypeString, trade.priceCount);
//			historyContainer.add(row);
			break;
		case OUTGOING_TRADE:
			break;
		default:
			break;
		}
		this.revalidate();
		this.repaint();
	}
	
	public void buildHistory(){
		for(TradeOffer trade : incomingTrades){
			HistoryRowPanel row = new HistoryRowPanel(trade);
			historyContainer.add(row);
		}
		this.revalidate();
		this.repaint();
	}
	
}
