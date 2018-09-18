package com.zrmiller.slimtrade;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

	
	
@SuppressWarnings("serial")
public class TradeWindowManager extends JFrame{
	//Screen Info
	final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final int screenWidth = (int) Math.round(screenSize.getWidth());
	final int screenHeight = (int) Math.round(screenSize.getHeight());
	final int offsetX=1000;
	final int offsetY=0;
	/*
	 * TEMP
	 * CHANGE TO GLOBAL REFERENCE
	 * 		tradeWindowHeight
	 */
	final int borderThickness=4;
	final int tradeWindowHeight=44+borderThickness*1;
	//Window Manager
	private final int maxTradeWindows = 3;
	private int activeIndex = 0;
	TradeWindowOLD[] tradeWindow = new TradeWindowOLD[maxTradeWindows];
	
	//CONSTRUCTOR
	public TradeWindowManager(){
		//TradeWindow[] tradeWindow = new TradeWindow[maxTradeWindows];
	}
	
	//API
	public void addTradeWindow(TradeOffer tradeOffer){
		System.out.println("Adding Trade Window #1...");
		if (activeIndex<maxTradeWindows){
			if(activeIndex>maxTradeWindows-1)activeIndex=0;
			tradeWindow[activeIndex] = new TradeWindowOLD(tradeOffer);
			tradeWindow[activeIndex].setLocation(offsetX, offsetY+activeIndex*tradeWindowHeight);
			activeIndex++;
		}
		
		
	}     
	
	public void addTradeWindow(String type, String playerName, String purchase, int purchaseQuant, String price, int priceQuant){
		System.out.println("Adding Trade Window #2...");
		if (activeIndex<maxTradeWindows){
			if(activeIndex>maxTradeWindows-1)activeIndex=0;
			//tradeWindow[activeIndex] = new TradeWindowOLD(type, playerName, purchase, purchaseQuant, price, priceQuant);
			//tradeWindow[activeIndex].setLocation(offsetX, offsetY+activeIndex*tradeWindowHeight);
			activeIndex++;
			
		}
	}
	
	public void closeTradeWindow(){
	}

	
	//INTERNAL
	
}
