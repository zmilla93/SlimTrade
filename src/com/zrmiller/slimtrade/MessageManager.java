package com.zrmiller.slimtrade;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.panels.BasicPanel;
import com.zrmiller.slimtrade.windows.MessageWindow;

public class MessageManager extends JPanel{

	private static final long serialVersionUID = 1L;

	private int msgGapSize = 5;
	
	public MessageManager(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBounds(1000, 5, 0, 0);
		this.setBackground(ColorManager.CLEAR);
		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
		this.addMessage(new TradeOffer(MessageType.OUTGOING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
//		this.setVisible(true);
	}
	
	private void refresh(){
		Dimension d = new Dimension(MessageWindow.totalWidth, MessageWindow.totalHeight*(this.getComponentCount()/2) + msgGapSize*(this.getComponentCount()/2));
		this.setPreferredSize(d);
		this.setSize(d);
		this.revalidate();
		this.repaint();
	}
	
	public void addMessage(TradeOffer trade){
		MessageWindow msg = new MessageWindow(trade);
		BasicPanel buffer = new BasicPanel(MessageWindow.totalWidth, msgGapSize, ColorManager.CLEAR);
		msg.closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	remove(msg);
		    	remove(buffer);
		    	refresh();
		    }
		});
		this.add(msg);
		this.add(buffer);
		refresh();
	}
	
}
