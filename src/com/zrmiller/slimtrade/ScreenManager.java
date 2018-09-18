package com.zrmiller.slimtrade;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ScreenManager extends JFrame{

	private int curMsgCount = 0;
	private final int MAX_MSG_COUNT = 10;
	public MessageWindow[] msgPanel = new MessageWindow[MAX_MSG_COUNT];
	public REFERENCE_GUI ref = new REFERENCE_GUI();
	public JButton closeButton = new JButton();
	public MenuBar menuBar = new MenuBar();
	
	public ScreenManager(){
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
 		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		this.setVisible(true);
		this.add(menuBar);
	}
	
	public void addTradeWindow(String type, String playerName, String item, int itemQuant, String price, int priceQuant){
		this.addTradeWindow(new TradeOffer(type, playerName, item, itemQuant, price, priceQuant));
	}
	
	public void addTradeWindow(TradeOffer trade){
		if(curMsgCount<MAX_MSG_COUNT){
			int openIndex = 0;
			while(msgPanel[openIndex] != null){
				openIndex++;
			}
			System.out.println("ADDING WINDOW #" + openIndex);
			msgPanel[openIndex] = new MessageWindow(trade);
			this.add(msgPanel[openIndex]);
			int i = openIndex;
			msgPanel[openIndex].buttonClose.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseClicked(java.awt.event.MouseEvent evt) {removeWindow(i);refresh();}
			});
			msgPanel[openIndex].orderIndex=openIndex;
			this.alignWindows();
			this.refresh();
			curMsgCount++;
		}

	}
	
	public void removeWindow(int r){
		System.out.print("REMOVING WINDOW #" + r + " FROM {");
		for (MessageWindow p : msgPanel) {
			if(p != null){
				System.out.print(" " + p.orderIndex + " ");
			}
		}
		System.out.println("}");
		this.remove(msgPanel[r]);
		msgPanel[r] = null;
		int i = 0;
		while(i<this.MAX_MSG_COUNT){
			if(msgPanel[i] != null && msgPanel[i].orderIndex>r){
				System.out.println("Fixing index " + i);
				msgPanel[i].orderIndex--;
			}
			i++;
		}
		this.alignWindows();
		this.refresh();
		curMsgCount--;

	}
	
	public void alignWindows(){
		//System.out.println("Aligning windows...");
		int i = 0;
		while(i<MAX_MSG_COUNT){
			if(msgPanel[i] != null){
				//System.out.println("INDEX : " + msgPanel[i].orderIndex);
				msgPanel[i].setLocation(ref.offsetX, ref.offsetY+msgPanel[i].orderIndex*(ref.msgHeight+ref.borderWidthTop+ref.borderWidthBottom));
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
