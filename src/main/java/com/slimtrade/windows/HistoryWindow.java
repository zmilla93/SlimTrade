package main.java.com.slimtrade.windows;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.FrameManager;
import main.java.com.slimtrade.core.TradeOffer;
import main.java.com.slimtrade.core.TradeUtility;
import main.java.com.slimtrade.dialog.BasicWindowDialog;
import main.java.com.slimtrade.panels.HistoryRowPanel;

public class HistoryWindow extends BasicWindowDialog{

	private static final long serialVersionUID = 1L;
	public static int width = 900;
	public static int height = 400;
	private int maxTrades = 40;
	private ArrayList<TradeOffer> incomingTrades = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRowPanel> incomingTradeRows = new ArrayList<HistoryRowPanel>();
	private ArrayList<TradeOffer> outgoingTrades = new ArrayList<TradeOffer>();
	
	JPanel headerPanel = new JPanel();
	JPanel historyContainer = new JPanel();
	
	public HistoryWindow(String title){
		super("History");
		this.resizeWindow(width, height);
		
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		
		headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
		JButton b1 = new JButton("Incoming");
		JButton b2 = new JButton("Outgoing");
		JButton b3 = new JButton("Saved");
		headerPanel.add(b1);
		headerPanel.add(b2);
		headerPanel.add(b3);
		
		historyContainer.setLayout(new BoxLayout(historyContainer, BoxLayout.PAGE_AXIS));
		historyContainer.setPreferredSize(new Dimension((int)(width*0.9), HistoryRowPanel.height*maxTrades));
		
		JScrollPane scrollPane = new JScrollPane(historyContainer);
		scrollPane.setPreferredSize(new Dimension(width, height));
		
		container.add(headerPanel);
		container.add(scrollPane);

		
		FrameManager.centerFrame(this);
		this.setVisible(true);
	}
	
	public void addTradeData(TradeOffer trade){
		
		switch(trade.msgType){
		case INCOMING_TRADE:
			int i = 0;
			for(TradeOffer savedTrade : incomingTrades){
				if(TradeUtility.isDuplicateTrade(trade, savedTrade)){
					incomingTrades.remove(i);
					break;
				}
				i++;
			}
			if(incomingTrades.size() == maxTrades){
				incomingTrades.remove(0);
			}
			incomingTrades.add(trade);
			break;
		case OUTGOING_TRADE:
			break;
		default:
			break;
		}
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
