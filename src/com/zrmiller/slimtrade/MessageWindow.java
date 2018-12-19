package com.zrmiller.slimtrade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zrmiller.slimtrade.datatypes.CurrencyType;

public class MessageWindow extends JPanel{
	
	private static final long serialVersionUID = 1L;
	JButton closeButton;
	
	private static int width = 400;
	private static int height= 40;
	private static int borderThickness = 2;
	private int rowHeight = height/2;
	public static int totalWidth = width+borderThickness*4;
	public static int totalHeight = height+borderThickness*4;
	
	private TradeOffer trade;

	public MessageWindow(TradeOffer trade){
		this.trade = trade;
		/*
		 * TODO : Optimize
		 * Size Variables
		 * 
		 */
		
		/*
		 * Basic Settings
		 */
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));
		
		/*
		 * BORDER PANELS
		 */
		
		JPanel bgOuter = new JPanel();
		bgOuter.setLayout(new FlowLayout(FlowLayout.CENTER, borderThickness, borderThickness));
		bgOuter.setPreferredSize(new Dimension(width+borderThickness*2, height+borderThickness*2));
		bgOuter.setBackground(Color.RED);
		
		JPanel bgInner = new JPanel();
		bgInner.setLayout(new FlowLayout(FlowLayout.CENTER, borderThickness, borderThickness));
		bgInner.setPreferredSize(new Dimension(width+borderThickness*2, height+borderThickness*2));
		bgInner.setBackground(Color.GREEN);
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
		container.add(topPanel, BorderLayout.PAGE_START);
		
		JPanel namePanel = new JPanel();
		//TODO : MODIFY TO CENTER TEXT VERITCALLY
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, borderThickness, borderThickness));
		JLabel nameLabel = new JLabel(trade.playerName);
		namePanel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		nameLabel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		namePanel.setBackground(Color.YELLOW);
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		
		JPanel pricePanel = new JPanel();
		JLabel priceLabel = new JLabel(trade.priceType.toString());
		pricePanel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		pricePanel.setBackground(Color.ORANGE);
		pricePanel.add(priceLabel);
		topPanel.add(pricePanel);
		
		JPanel topButtonPanel = new JPanel();
		topButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		topButtonPanel.setPreferredSize(new Dimension((int)(width*0.2), rowHeight));
		topButtonPanel.setBackground(Color.BLUE);
		topPanel.add(topButtonPanel);
		
		JButton button2 = new JButton();
		button2.setPreferredSize(new Dimension(rowHeight, rowHeight));
		topButtonPanel.add(button2);
		
		closeButton = new JButton();
		closeButton.setPreferredSize(new Dimension(rowHeight, rowHeight));
		topButtonPanel.add(closeButton);
		
		/*
		 * CENTER PANEL
		 */
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		centerPanel.setPreferredSize(new Dimension(width,rowHeight));
		centerPanel.setBackground(Color.RED);
		container.add(centerPanel, BorderLayout.CENTER);
		
		JPanel itemPanel = new JPanel();
		JLabel itemLabel = new JLabel(trade.itemName);
		itemPanel.setPreferredSize(new Dimension((int)(width*0.8), rowHeight));
		itemPanel.setBackground(Color.GRAY);
		itemPanel.add(itemLabel);
		centerPanel.add(itemPanel);
		
		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		centerButtonPanel.setPreferredSize(new Dimension((int)(width*0.2), rowHeight));
		centerButtonPanel.setBackground(Color.MAGENTA);
		centerPanel.add(centerButtonPanel);
		
		JButton button3 = new JButton();
		button3.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button3);
		
		JButton button4 = new JButton();
		button4.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button4);
		
		JButton button5 = new JButton();
		button5.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button5);
		
		JButton button6 = new JButton();
		button6.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button6);
	}
	
	public JButton getCloseButton(){
		return this.closeButton;
	}
	
}
