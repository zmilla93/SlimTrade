package com.zrmiller.slimtrade;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MessageManager extends JPanel{
	
	REF_MSG_WINDOW ref = new REF_MSG_WINDOW();
	int messageCount = 0;
	
	public MessageManager(){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0f));
		this.setBackground(Color.cyan);
		refresh();
	}
	
	public void refresh(){
		this.setSize(ref.totalWidth,ref.totalHeight*messageCount);
		this.revalidate();
		this.repaint();
	}
	
	public void addMessage(TradeOffer trade){
		MessageWindow msg = new MessageWindow(trade);
		add(msg);
		msg.closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {removeMessage(msg);}
		});
		messageCount++;
		refresh();
	}
	
	private void removeMessage(MessageWindow m){
		messageCount--;
		remove(m);
		refresh();
	}
}
