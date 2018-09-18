package com.zrmiller.slimtrade;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuBar extends JPanel{
	
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int screenWidth = (int) Math.round(screenSize.getWidth());
		final int screenHeight = (int) Math.round(screenSize.getHeight());
		
		final int buttonSize = 30;
		final int buttonCount = 5;
		final int offsetX = 10;
		final int offsetY = 30;

		JButton exitButton = new JButton();
		JButton button1 = new JButton();
		JButton button2 = new JButton();
		JButton button3 = new JButton();
		JButton button4 = new JButton();
			
	public MenuBar(){
		this.setLayout(null);
		this.setBounds(offsetX, screenHeight-buttonSize-offsetY, buttonSize*buttonCount, buttonSize);
		exitButton.setBounds(0, 0, buttonSize, buttonSize);
		button1.setBounds(buttonSize, 0, buttonSize, buttonSize);
		button2.setBounds(buttonSize*2, 0, buttonSize, buttonSize);
		button3.setBounds(buttonSize*3, 0, buttonSize, buttonSize);
		button4.setBounds(buttonSize*4, 0, buttonSize, buttonSize);
		this.add(exitButton);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		
		//Local Button Actions
		exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {System.exit(0);}
		});
	}
	
}
