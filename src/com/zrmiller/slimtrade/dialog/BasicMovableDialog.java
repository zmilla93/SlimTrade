package com.zrmiller.slimtrade.dialog;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.TradeUtility;

public class BasicMovableDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;
	
	private int offsetX;
	private int offsetY;
	private boolean screenLock = false;
	private boolean mouseDown = false;
	public JPanel moverPanel;
	
	public BasicMovableDialog(){
		
	}
	
	public BasicMovableDialog(JPanel mover){
		createListeners(mover);
	}
	
	public void createListeners(JPanel p){
		p.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mousePressed(java.awt.event.MouseEvent e) {
		    	if(e.getButton() == MouseEvent.BUTTON1){
		    		offsetX = e.getX();
		    		offsetY = e.getY();
		    		mouseDown = true;
		    		runWindowMover();
		    	}
		    }
		});
		
		p.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					mouseDown = false;
				}
			}
		});
	}
	
	public void setScreenLock(boolean state){
		screenLock = state;
	}
	
	private void moveWindow(Point p){
		this.setLocation(p);
	}
	
	private int getDialogWidth(){
		return this.getWidth();
	}
	
	private int getDialogHeight(){
		return this.getHeight();
	}
	
	private void runWindowMover(){
		new Thread(){
			public void run(){
				while(mouseDown){
					int targetX = MouseInfo.getPointerInfo().getLocation().x-offsetX;
					int targetY = MouseInfo.getPointerInfo().getLocation().y-offsetY;
					if(screenLock){
						if(targetX<0) targetX = 0;
						if(targetX>TradeUtility.screenSize.width-getDialogWidth()) targetX = TradeUtility.screenSize.width-getDialogWidth();
						if(targetY<0) targetY = 0;
						if(targetY>TradeUtility.screenSize.height-getDialogHeight()) targetY = TradeUtility.screenSize.height-getDialogHeight();
					}
					moveWindow(new Point(targetX, targetY));
					MouseInfo.getPointerInfo().getLocation();
				}
			}
		}.start();
	}
	

}
