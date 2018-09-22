package com.zrmiller.slimtrade;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException, AWTException {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		ChatParser parser = new ChatParser();
		long st = System.nanoTime();
		parser.update();
		long et = System.nanoTime();
		System.out.println("Program launched in " + (et-st)/1000000 + " miliseconds.");
		

		//Screen Frame + Screen Panel
		JFrame screenFrame = new JFrame();
		screenFrame.setLayout(null);
		screenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screenFrame.setUndecorated(true);
		Container screen = screenFrame.getContentPane();
		
		//Screen Components
		MenuBar menuBar = new MenuBar();
		MessageManager msgManager = new MessageManager();
		msgManager.setLocation(500, 0);
		screen.add(menuBar);
		screen.add(msgManager);

		//Once everything else is done, show screen overlay
		screenFrame.setVisible(true);
		
		//Global Buttons
		menuBar.plusButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {msgManager.addMessage();}
		});
		
		//SET TO 1 TO RUN CHAT SCANNER
		/*
		boolean scanChat = true;
		while(){
			//System.out.println("Update");
			int i = parser.update();
			while(i>0){
				//screenManager.addTradeWindow(parser.tradeHistory[parser.getFixedIndex(0-i)]);
				i--;
			}			
			Thread.sleep(500);
		}
		*/
		
	}
}
