package com.zrmiller.slimtrade.panels;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.windows.MessagePanel;

public class BasicMoveablePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int offsetX = 0;
	private int offsetY = 0;
	
	public BasicMoveablePanel(){
		this.setSize(MessagePanel.totalWidth, MessagePanel.totalHeight);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
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
