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
	private ArrayList<TradeOffer> incomingTradeData = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRowPanel> incomingTradePanels = new ArrayList<HistoryRowPanel>();
	private ArrayList<TradeOffer> outgoingTradeData = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRowPanel> outgoingTradePanels = new ArrayList<HistoryRowPanel>();
	
	
	JPanel selectionPanel = new JPanel();
	JPanel incomingContainer = new JPanel();
	JPanel outgoingContainer = new JPanel();
	
	public HistoryWindow(String title){
		super("History");
		this.resizeWindow(width, height);
		
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		selectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
		JButton incomingButton = new JButton("Incoming");
		JButton outgoingButton = new JButton("Outgoing");
		JButton savedButton = new JButton("Saved");
		selectionPanel.add(incomingButton);
		selectionPanel.add(outgoingButton);
		selectionPanel.add(savedButton);
		
		incomingContainer.setLayout(new BoxLayout(incomingContainer, BoxLayout.PAGE_AXIS));
		incomingContainer.setPreferredSize(new Dimension((int)(width*0.9), HistoryRowPanel.height*maxTrades));
		outgoingContainer.setLayout(new BoxLayout(outgoingContainer, BoxLayout.PAGE_AXIS));
		outgoingContainer.setPreferredSize(new Dimension((int)(width*0.9), HistoryRowPanel.height*maxTrades));
		
		
		//Move to CustomScrollPane
		JScrollPane incomingScrollPane = new JScrollPane(incomingContainer);
		incomingScrollPane.setPreferredSize(new Dimension(width, height));
		incomingScrollPane.getVerticalScrollBar().setUnitIncrement(8);
		JScrollPane outgoingScrollPane = new JScrollPane(outgoingContainer);
		outgoingScrollPane.setPreferredSize(new Dimension(width, height));
		outgoingScrollPane.getVerticalScrollBar().setUnitIncrement(8);
		
		container.add(selectionPanel);
		container.add(incomingScrollPane);
		container.add(outgoingScrollPane);

		outgoingScrollPane.setVisible(false);
		
		
		FrameManager.centerFrame(this);
		this.setVisible(true);
		
		incomingButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
			outgoingScrollPane.setVisible(false);
			incomingScrollPane.setVisible(true);
			refresh();
		}});
		
		outgoingButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
			incomingScrollPane.setVisible(false);
			outgoingScrollPane.setVisible(true);
			refresh();
		}});
		
	}
	
	private void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	public void addNewRow(TradeOffer trade){
		switch(trade.msgType){
		case INCOMING_TRADE:
			int i = 0;
			for(TradeOffer savedTrade : incomingTradeData){
				if(TradeUtility.isDuplicateTrade(trade, savedTrade)){
					incomingTradeData.remove(i);
					break;
				}
				i++;
			}
			break;
		case OUTGOING_TRADE:
			break;
		default:
			break;
		}
	}
	
	//TODO : outgoing trades don't reorder as they should
	public void addTrade(TradeOffer trade, boolean updateUI){
		int i = 0;
		switch(trade.msgType){
		case INCOMING_TRADE:
			for(TradeOffer savedTrade : incomingTradeData){
				if(TradeUtility.isDuplicateTrade(trade, savedTrade)){
					incomingTradeData.remove(i);
					if(updateUI){
						incomingContainer.remove(incomingTradePanels.get(i));
						incomingTradePanels.remove(i);
					}
					break;
				}
				i++;
			}
			if(incomingTradeData.size() == maxTrades){
				incomingTradeData.remove(0);
				if(updateUI){
					incomingContainer.remove(incomingTradePanels.get(0));
					incomingTradePanels.remove(0);
				}
			}
			incomingTradeData.add(trade);
			if(updateUI){
				incomingTradePanels.add(new HistoryRowPanel(trade));
				incomingContainer.add(incomingTradePanels.get(incomingTradePanels.size()-1));
				this.revalidate();
				this.repaint();
			}
			break;
		case OUTGOING_TRADE:
			for(TradeOffer savedTrade : outgoingTradeData){
				if(TradeUtility.isDuplicateTrade(trade, savedTrade)){
					outgoingTradeData.remove(i);
					if(updateUI){
						outgoingContainer.remove(outgoingTradePanels.get(i));
						outgoingTradePanels.remove(i);
					}
					break;
				}
				i++;
			}
			if(outgoingTradeData.size() == maxTrades){
				outgoingTradeData.remove(0);
				if(updateUI){
					outgoingContainer.remove(outgoingTradePanels.get(0));
					outgoingTradePanels.remove(0);
				}
			}
			outgoingTradeData.add(trade);
			if(updateUI){
				outgoingTradePanels.add(new HistoryRowPanel(trade));
				outgoingContainer.add(outgoingTradePanels.get(outgoingTradePanels.size()-1));
				this.revalidate();
				this.repaint();
			}
			break;
		default:
			break;
		}
	}
	
	public void buildHistory(){
		for(TradeOffer trade : incomingTradeData){
			incomingTradePanels.add(new HistoryRowPanel(trade));
			incomingContainer.add(incomingTradePanels.get(incomingTradePanels.size()-1));
		}
		for(TradeOffer trade : outgoingTradeData){
			outgoingTradePanels.add(new HistoryRowPanel(trade));
			outgoingContainer.add(outgoingTradePanels.get(outgoingTradePanels.size()-1));
		}
		this.revalidate();
		this.repaint();
	}
	
}
