package com.zrmiller.slimtrade;

import java.awt.AWTException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException, AWTException {

		ChatParser parser = new ChatParser();
		long st = System.nanoTime();
		parser.update();
		long et = System.nanoTime();
		System.out.println("Program launched in " + (et-st)/1000000 + " miliseconds.");
		
		TradeOffer testTrade = new TradeOffer("in", "Shooty", "Cool Bow", 0, "Chaos", 45);
		
		ScreenManager screenManager = new ScreenManager();
		
		//Global Buttons
		screenManager.menuBar.button1.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {screenManager.addTradeWindow(testTrade);}
		});
		
		
		//SET TO 1 TO RUN CHAT SCANNER
		boolean scanChat = true;
		while(scanChat){
			//System.out.println("Update");
			int i = parser.update();
			while(i>0){
				screenManager.addTradeWindow(parser.tradeHistory[parser.getFixedIndex(0-i)]);
				i--;
			}			
			Thread.sleep(500);
		}
	}
}
