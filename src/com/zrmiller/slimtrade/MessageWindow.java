package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MessageWindow extends JPanel{

	REF_MSG_WINDOW ref = new REF_MSG_WINDOW();
	
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	JPanel namePanel = new JPanel();
	JLabel nameLabel = new JLabel();
	JPanel itemPanel = new JPanel();
	JLabel itemLabel = new JLabel();
	JPanel pricePanel = new JPanel();
	JLabel priceLabel = new JLabel();
	
	JButton expandButton = new JButton();
	JButton closeButton = new JButton();
	
	public MessageWindow(TradeOffer trade){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		//Temp fix for ugly white margin
		this.setMaximumSize(new Dimension(ref.totalWidth-5, ref.totalHeight));
		
		topPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		bottomPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		topPanel.setBackground(Color.red);
		bottomPanel.setBackground(Color.red);
		
		//NAME
		namePanel.setMaximumSize(new Dimension((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		namePanel.setBackground(Color.blue);
		nameLabel.setText(trade.name);
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		
		//Price
		pricePanel.setLayout(new FlowLayout());
		pricePanel.setMaximumSize(new Dimension((int) (ref.priceWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		pricePanel.setBackground(Color.green);
		priceLabel.setText("TEST");
		pricePanel.add(priceLabel);
		topPanel.add(pricePanel);
		
		//Item
		itemPanel.setMaximumSize(new Dimension(ref.itemWidth, ref.itemHeight));
		itemPanel.setBackground(Color.orange);
		itemLabel.setText(trade.item);
		itemPanel.add(itemLabel);
		bottomPanel.add(itemPanel);
		
		//Buttons
		expandButton.setMaximumSize(new Dimension(ref.buttonWidth, ref.buttonHeight));
		closeButton.setMaximumSize(new Dimension(ref.buttonWidth, ref.buttonHeight));
		topPanel.add(expandButton);
		topPanel.add(closeButton);
		
		this.add(topPanel);
		this.add(bottomPanel);
		//this.setLayout();
		System.out.println("WIDTH : " + ref.totalWidth);
	}
	
}
