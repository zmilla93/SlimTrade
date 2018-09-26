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
	
	JButton b1 = new JButton();
	JButton b2 = new JButton();
	JButton b3 = new JButton();
	JButton b4 = new JButton();
	
	public MessageWindow(TradeOffer trade){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		//Temp fix for ugly white margin is to reduce max size
		this.setMaximumSize(new Dimension(ref.totalWidth, ref.totalHeight));
		
		topPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		bottomPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		topPanel.setBackground(Color.red);
		bottomPanel.setBackground(Color.red);
		
		
		//TODO: replace new Dimensions with a variable
		Dimension d = new Dimension();
		//NAME
		namePanel.setMinimumSize(new Dimension((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		//namePanel.setPreferredSize(new Dimension((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		namePanel.setMaximumSize(new Dimension((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		namePanel.setBackground(Color.blue);
		nameLabel.setText(trade.name);
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		
		//Price
		pricePanel.setLayout(new FlowLayout());
		pricePanel.setMinimumSize(new Dimension((int) (ref.priceWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		//pricePanel.setPreferredSize(new Dimension((int) (ref.priceWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		pricePanel.setMaximumSize(new Dimension((int) (ref.priceWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		pricePanel.setBackground(Color.green);
		priceLabel.setText("TEST");
		pricePanel.add(priceLabel);
		topPanel.add(pricePanel);
		
		//Item
		itemPanel.setMinimumSize(new Dimension(ref.itemWidth, ref.itemHeight));
		itemPanel.setMaximumSize(new Dimension(ref.itemWidth, ref.itemHeight));
		itemPanel.setBackground(Color.orange);
		itemLabel.setText(trade.item);
		itemPanel.add(itemLabel);
		bottomPanel.add(itemPanel);
		
		d = new Dimension(ref.buttonWidth, ref.buttonHeight);
		//Buttons Row 1
		expandButton.setMaximumSize(d);
		closeButton.setMaximumSize(d);
		topPanel.add(expandButton);
		topPanel.add(closeButton);
		
		//Buttons Row 2
		b1.setMaximumSize(d);
		b2.setMaximumSize(d);
		b3.setMaximumSize(d);
		b4.setMaximumSize(d);
		bottomPanel.add(b1);
		bottomPanel.add(b2);
		bottomPanel.add(b3);
		bottomPanel.add(b4);
		
		this.add(topPanel);
		this.add(bottomPanel);
		//this.setLayout();
		System.out.println("WIDTH : " + ref.totalWidth);
	}
	
}
