package com.zrmiler.slimtrade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		System.out.println("!");
		
		JFrame screenFrame = new JFrame();
		
		screenFrame.setLayout(null);
		screenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screenFrame.setUndecorated(true);
		screenFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.2f));
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
		
		
		
		
		
		/*
		 * 
		 * Message Window Manager
		 * 
		 */
		
		
		
		/*
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		
		/*
		 * 
		 * 
		 * TEST MESSAGE WINDOW
		 * 
		 */
		MessageWindow testMessage = new MessageWindow();
		screenFrame.add(testMessage);
		
		
		//TODO : move pack?
		//Finish screenContainer
		screenFrame.pack();
		screenFrame.setVisible(true);
		System.out.println("FIN");
	}

}
