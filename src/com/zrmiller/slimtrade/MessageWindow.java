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
	JPanel container = new JPanel();
	
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

		this.setLayout(null);
		this.setBackground(Color.magenta);
		this.add(container);
		Dimension d;
		
		//Container
		d = new Dimension(ref.totalWidth, ref.totalHeight);
		container.setMinimumSize(d);
		container.setMaximumSize(d);
		container.add(topPanel);
		container.add(bottomPanel);
		container.setBackground(Color.pink);
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setBounds(0, 0, ref.totalWidth, ref.totalHeight);
		
		//Row Panels
		topPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		topPanel.setBackground(Color.red);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		bottomPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		bottomPanel.setBackground(Color.red);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		
		//NAME
		d = new Dimension((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight);
		namePanel.setMinimumSize(d);
		namePanel.setMaximumSize(d);
		namePanel.setBackground(Color.blue);
		nameLabel.setText(trade.name);
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		
		//Price
		d = new Dimension((int) (ref.priceWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight);
		pricePanel.setLayout(new FlowLayout());
		pricePanel.setMinimumSize(d);
		pricePanel.setMaximumSize(d);
		pricePanel.setBackground(Color.green);
		priceLabel.setText(trade.price);
		pricePanel.add(priceLabel);
		topPanel.add(pricePanel);
		
		//Item
		d = new Dimension(ref.itemWidth, ref.itemHeight);
		itemPanel.setMinimumSize(d);
		itemPanel.setMaximumSize(d);
		itemPanel.setBackground(Color.orange);
		itemLabel.setText(trade.item);
		itemPanel.add(itemLabel);
		bottomPanel.add(itemPanel);
		
		//Buttons Row 1
		d = new Dimension(ref.buttonWidth, ref.buttonHeight);
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
	}
	
}
