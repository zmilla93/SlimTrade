package com.zrmiler.slimtrade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MessageWindow extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static int width = 200;
	private static int height= 70;
	//private final int borderX, borderY;

	public MessageWindow(){
		
		this.setLayout(new BorderLayout());
		this.setBounds(200, 200, 400, 80);
		
		/*
		 * TOP PANEL
		 */
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/2));
		topPanel.setBackground(Color.RED);
		this.add(topPanel, BorderLayout.PAGE_START);
		
		JPanel namePanel = new JPanel();
		//namePanel.setLayout(new FlowLayout());
		namePanel.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()/2));
		namePanel.setBackground(Color.YELLOW);
		topPanel.add(namePanel);
		
		JPanel pricePanel = new JPanel();
		pricePanel.setBackground(Color.BLUE);
		//topPanel.add(pricePanel);
		
		/*
		 * Center Panel
		 */
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.CYAN);
		centerPanel.setPreferredSize(new Dimension(this.getWidth(),0));
		this.add(centerPanel, BorderLayout.CENTER);
		
		/*
		 * BOTTOM PANEL
		 */
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setBackground(Color.GREEN);
		this.add(bottomPanel, BorderLayout.PAGE_END);
		
		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(null);
		bottomPanel.setPreferredSize(new Dimension(this.getWidth(), 0));
		bottomPanel.add(itemPanel);
		
		/*
		 * SHOW MESSAGE WINDOW
		 */
		
		//Needed?
		//topPanel.repaint();
		//topPanel.validate();
		
		this.setVisible(true);
	}
	
}
