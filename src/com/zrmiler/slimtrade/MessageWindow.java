package com.zrmiler.slimtrade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageWindow extends JPanel{
	
	private static final long serialVersionUID = 1L;
	//private final int borderX, borderY;

	public MessageWindow(){
		
		/*
		 * TODO : Optimize
		 * Size Variables
		 * 
		 */
		
		//MESSAGE WINDOW
		int width = 400;
		int height = 50;
		int borderThickness = 1;
		int rowHeight = height/2;
		
		/*
		 * Basic Settings
		 */
		
		this.setLayout(new BorderLayout());
		this.setBounds(600, 450, width+borderThickness*8, height+borderThickness*8);
		
		/*
		 * BORDER PANELS
		 */
		
		JPanel bgOuter = new JPanel();
		//bgOuter.setLayout(new BorderLayout());
		bgOuter.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
		bgOuter.setPreferredSize(new Dimension(width+borderThickness*4, height+borderThickness*4));
		bgOuter.setBackground(Color.CYAN);
		
		JPanel bgInner = new JPanel();
		//bgInner.setLayout(new BorderLayout());
		bgInner.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
		bgInner.setPreferredSize(new Dimension(width+borderThickness*4, height+borderThickness*4));
		bgInner.setBackground(Color.BLUE);
		bgOuter.add(bgInner, BorderLayout.CENTER);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(width, height));
		container.setBackground(Color.YELLOW);
		bgInner.add(container, BorderLayout.CENTER);
		
		this.add(bgOuter);
		
		/*
		 * TOP PANEL
		 */
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		topPanel.setPreferredSize(new Dimension(width, rowHeight));
		topPanel.setBackground(Color.CYAN);
		//this.add(topPanel, BorderLayout.PAGE_START);
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLabel = new JLabel("GENERIC_NAME");
		nameLabel.setAlignmentY(CENTER_ALIGNMENT);
		namePanel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		namePanel.setBackground(Color.YELLOW);
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		
		JPanel pricePanel = new JPanel();
		JLabel priceLabel = new JLabel("PRICE");
		pricePanel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		pricePanel.setBackground(Color.ORANGE);
		pricePanel.add(priceLabel);
		topPanel.add(pricePanel);
		
		JPanel topButtonPanel = new JPanel();
		topButtonPanel.setPreferredSize(new Dimension((int)(width*0.2), rowHeight));
		topButtonPanel.setBackground(Color.blue);
		topPanel.add(topButtonPanel);
		
		
		
		/*
		 * CENTER PANEL
		 */
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		centerPanel.setPreferredSize(new Dimension(width,rowHeight));
		centerPanel.setBackground(Color.RED);
		//this.add(centerPanel, BorderLayout.CENTER);
		
		JPanel itemPanel = new JPanel();
		JLabel itemLabel = new JLabel();
		itemPanel.setPreferredSize(new Dimension((int)(width*0.8), rowHeight));
		itemPanel.setBackground(Color.GRAY);
		itemPanel.add(itemLabel);
		centerPanel.add(itemPanel);
		
		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setPreferredSize(new Dimension((int)(width*0.2), rowHeight));
		centerButtonPanel.setBackground(Color.MAGENTA);
		centerPanel.add(centerButtonPanel);
		
		
		/*
		 * BOTTOM PANEL - UNUSED
		 */
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(0,0));;
		bottomPanel.setBackground(Color.cyan);
		//this.add(bottomPanel, BorderLayout.PAGE_END);
		
		
		
		/*
		 * SHOW MESSAGE WINDOW
		 */
		this.setVisible(true);
	}
	
}
