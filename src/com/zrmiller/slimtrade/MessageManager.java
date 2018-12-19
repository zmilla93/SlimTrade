package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class MessageManager extends JPanel{

	private static final long serialVersionUID = 1L;

	public MessageManager(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBounds(1000, 5, MessageWindow.totalWidth, MessageWindow.totalHeight*4);
		this.setBackground(Color.YELLOW);
		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5));
		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5));
		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5));
		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5));
		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5));
		this.setVisible(true);
	}
	
	private void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	public void addMessage(TradeOffer trade){
		MessageWindow msg = new MessageWindow(trade);
		msg.closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	remove(msg);
		    	refresh();
		    }
		});
		this.add(msg);
	}
	
}
