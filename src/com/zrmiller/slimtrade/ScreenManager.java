package com.zrmiller.slimtrade;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScreenManager extends JFrame{

	JPanel msgArea = new JPanel();
	
	public ScreenManager() throws InterruptedException{

		//Make overlay fullscreen
		this.setLayout(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(true);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Container container = this.getContentPane();
		MessageManager msgManager = new MessageManager();
		container.add(msgManager);

		
		MenuBar menu = new MenuBar();
		container.add(menu);
		menu.setLocation(screen.width-menu.getWidth(), 0);
		
		this.setVisible(true);
		//this.revalidate();
		//this.repaint();
		
		//Global Buttons
		menu.plusButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {msgManager.addMessage();}
		});
		
	}
	
}
