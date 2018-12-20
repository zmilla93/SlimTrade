package com.zrmiller.slimtrade.panels;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.MessageWindow;
import com.zrmiller.slimtrade.TradeOffer;
import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class BasicMoveablePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int offsetX = 0;
	private int offsetY = 0;
	
	public BasicMoveablePanel(){
		this.setSize(MessageWindow.totalWidth, MessageWindow.totalHeight);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		MessageWindow msg = new MessageWindow(new TradeOffer(MessageType.INCOMING_TRADE, "PLAYER_NAME", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5));
		//this.add(msg);
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
		    	offsetX = e.getXOnScreen()-getPosX();
		    	offsetY = e.getYOnScreen()-getPosY();
		    }
		});
		
		this.addMouseMotionListener(new java.awt.event.MouseAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent e) {
		    	moveBox(e.getXOnScreen()-offsetX, e.getYOnScreen()-offsetY);
		    }
		});
	}
	
	private int getPosX(){
		return this.getX();
	}
	
	private int getPosY(){
		return this.getY();
	}
	
	private void moveBox(int posX, int posY){
		this.setLocation(posX, posY);
	}
	
}
