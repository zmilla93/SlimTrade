package com.zrmiller.slimtrade;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MessageWindowManager extends JFrame{

	private int curMsgCount = 0;
	private final int MAX_MSG_COUNT = 10;
	public MessageWindow[] msgPanel = new MessageWindow[MAX_MSG_COUNT];
	public REFERENCE_GUI ref = new REFERENCE_GUI();
	
	public JButton closeButton = new JButton();
	
	public MessageWindowManager(){
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
 		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		this.setVisible(true);
		
		//TEMP Close Button
		this.add(closeButton);	
		int closeButtonSize = 20;
		int closeButtonOffset = 5;
		closeButton.setBounds(5, ref.screenHeight-closeButtonSize-50, closeButtonSize, closeButtonSize);
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {System.exit(0);;}
		});
	}
	
	public void addTradeWindow(String type, String playerName, String item, int itemQuant, String price, int priceQuant){
		this.addTradeWindow(new TradeOffer(type, playerName, item, itemQuant, price, priceQuant));
	}
	
	public void addTradeWindow(TradeOffer trade){
		if(curMsgCount<MAX_MSG_COUNT){
			int msgIndex = 0;
			int i = 0;
			while(i<this.MAX_MSG_COUNT){
				if (msgPanel[i] == null || msgPanel[i].managerVisiblity == false){
					msgIndex = i;
					i=this.MAX_MSG_COUNT;
				}
				i++;
			}
			MessageWindow newMsg = new MessageWindow(trade);
			this.add(newMsg);
			newMsg.orderIndex = curMsgCount;
			final int index = curMsgCount;
			newMsg.buttonClose.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseClicked(java.awt.event.MouseEvent evt) {removeWindow(index);refresh();}
			});
			System.out.println("Message Index : " + msgIndex);
			msgPanel[msgIndex] = newMsg;
			msgPanel[msgIndex].managerVisiblity = true;
			curMsgCount++;
			this.alignWindows();
			this.refresh();
		}

	}
	
	public void removeWindow(int r){
		System.out.println("Removing Window #" + r);
		this.curMsgCount--;
		msgPanel[r].managerVisiblity = false;
		this.remove(msgPanel[r]);
		int i = 0;
		while(i<this.MAX_MSG_COUNT){
			if(msgPanel[i] !=  null && msgPanel[i].managerVisiblity && msgPanel[i].orderIndex>r){
				System.out.println("Fixing index : " + msgPanel[i].orderIndex);
				msgPanel[i].orderIndex--;
			}
			i++;
		}
		this.alignWindows();
		this.refresh();
		System.out.println("Message closed!");
	}
	
	public void alignWindows(){
		System.out.println("Aligning windows...");
		int i = 0;
		int j = 0;
		while(i<MAX_MSG_COUNT){
			if(msgPanel[i] != null && msgPanel[i].managerVisiblity){
				System.out.println("INDEX : " + msgPanel[i].orderIndex);
				msgPanel[i].setLocation(ref.offsetX, ref.offsetY+msgPanel[i].orderIndex*(ref.msgHeight+ref.borderWidthTop+ref.borderWidthBottom));
				j++;
			}
			i++;
		}
		this.refresh();
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
