package main.java.com.slimtrade.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import main.java.com.slimtrade.FrameManager;
import main.java.com.slimtrade.TradeOffer;
import main.java.com.slimtrade.dialog.BasicWindowDialog;
import main.java.com.slimtrade.panels.HistoryRowPanel;

public class HistoryWindow extends BasicWindowDialog{

	private static final long serialVersionUID = 1L;
	public static int width = 800;
	public static int height = 400;
	private int maxTrades = 50;
	private ArrayList<TradeOffer> tradesIncoming = new ArrayList<TradeOffer>();
	private ArrayList<TradeOffer> tradesOutgoing = new ArrayList<TradeOffer>();
	
	JPanel historyContainer = new JPanel();
	
	public HistoryWindow(String title){
		super("History");
		this.resizeWindow(width, height);

		UIManager.put("ScrollBar.track", new ColorUIResource(Color.GREEN));
		
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		historyContainer.setPreferredSize(new Dimension((int)(width*0.9), height*4));
		
		JScrollPane scrollPane = new JScrollPane(historyContainer);
		scrollPane.setPreferredSize(new Dimension(width, height));
		container.add(scrollPane);

		
		FrameManager.centerFrame(this);
		this.setVisible(true);
	}
	
	public void addTrade(TradeOffer trade){
		switch(trade.msgType){
		case INCOMING_TRADE:
			tradesIncoming.add(trade);
			HistoryRowPanel row = new HistoryRowPanel(trade.playerName, trade.itemName, trade.priceTypeString, trade.priceCount);
//			row.setAlignmentX(CENTER_ALIGNMENT);
			historyContainer.add(row);
			break;
		case OUTGOING_TRADE:
			tradesOutgoing.add(trade);
			break;
		default:
			break;
		}
	}
	
}
