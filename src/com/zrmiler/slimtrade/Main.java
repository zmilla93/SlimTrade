package com.zrmiler.slimtrade;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.zrmiler.slimtrade.panels.MenuBar;
import com.zrmiler.slimtrade.panels.MoveablePanel;
import com.zrmiler.slimtrade.panels.OptionsPanel;
import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class Main {
	
	//must be even, or backgrounds must match in color
	public static Dimension screenSize;
	public static MenuBar menubar;
	public static OptionsPanel optionsPanel;
	
	
	public static void main(String[] args) {
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame screenFrame = new JFrame();
		screenFrame.setLayout(null);
		screenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screenFrame.setUndecorated(true);
		screenFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		screenFrame.setAlwaysOnTop(true);
		
		Container screenContainer = screenFrame.getContentPane();
		
		JButton exitButton = new JButton();
		exitButton.setBounds(0, 0, 20, 20);
		screenContainer.add(exitButton);
		
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				screenFrame.dispose();
			}
		});
		
		JButton menuButton = new JButton();
		menuButton.setBounds(0, (int)(screenSize.getHeight()-20), 20, 20);
		screenContainer.add(menuButton);
		
		menubar = new MenuBar();
		screenContainer.add(menubar);
		
		menubar.getQuitButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		MessageManager msgManager = new MessageManager();
		screenContainer.add(msgManager);
		
		optionsPanel = new OptionsPanel();
		optionsPanel.getCloseButton().addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	optionsPanel.setVisible(false);
		    }
		});
		screenContainer.add(optionsPanel);
		
		TradeOffer testTrade= new TradeOffer(MessageType.INCOMING_TRADE, "PLAYER_NAME1", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5);
		MessageWindow testMessage = new MessageWindow(testTrade);
		testMessage.setBounds(500, 500, MessageWindow.totalWidth, MessageWindow.totalHeight);
		//screenFrame.add(testMessage);
		
		MoveablePanel p = new MoveablePanel();
		screenFrame.add(p);
		
		
		//Finish screenContainer
		screenFrame.pack();
		screenFrame.setVisible(true);
	}

}
