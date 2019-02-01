package main.java.com.slimtrade.gui.basic;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import main.java.com.slimtrade.gui.panels.MessagePanel_OLD;

public class BasicMovablePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int offsetX = 0;
	private int offsetY = 0;
	
	public BasicMovablePanel(){
		this.setSize(MessagePanel_OLD.totalWidth, MessagePanel_OLD.totalHeight);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
		//this.add(msg);
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
		    	offsetX = e.getXOnScreen()-getPosX();
		    	offsetY = e.getYOnScreen()-getPosY();
		    }
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
		    public void mouseDragged(MouseEvent e) {
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
