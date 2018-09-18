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
		MessageWindowManager msgManager = new MessageWindowManager();

		
		//SET TO 1 TO RUN CHAT SCANNER
		boolean scanChat = true;
		while(scanChat){
			//System.out.println("Update");
			int i = parser.update();
			while(i>0){
				msgManager.addTradeWindow(parser.tradeHistory[parser.getFixedIndex(0-i)]);
				i--;
			}			
			Thread.sleep(500);
		}
	}
}
