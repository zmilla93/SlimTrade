package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class MessageWindow extends JPanel{

	REF_MSG_WINDOW ref = new REF_MSG_WINDOW();
	
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel msgContainer = new JPanel();
	JPanel borderPanel = new JPanel();
	
	JPanel namePanel = new JPanel();
	JLabel nameLabel = new JLabel();
	JPanel itemPanel = new JPanel();
	JLabel itemLabel = new JLabel();
	JPanel pricePanel = new JPanel();
	JLabel priceLabel = new JLabel();
	
	JButton expandButton = new JButton();
	JButton closeButton = new JButton();
	
	JButton inviteButton = new JButton();
	JButton tpToPlayerButton = new JButton();
	JButton tradeButton = new JButton();
	JButton thankButton = new JButton();
	JButton kickButton = new JButton();
	JButton leaveButton = new JButton();
	JButton tpHomeButton = new JButton();
	
	public MessageWindow(TradeOffer trade){
		ref.setMessageType(trade.msgType);
		System.out.println(ref.nameWidth + ref.priceWidth + ref.buttonWidth*ref.buttonCountRow1);
		Dimension d = new Dimension(ref.totalWidth, ref.totalHeight);
		this.setLayout(null);
		this.setBackground(Color.magenta);
		this.add(borderPanel);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		
		//Border
		borderPanel.setBounds(0, 0, ref.totalWidth, ref.totalHeight);
		borderPanel.setLayout(null);
		//borderPanel.setMinimumSize(d);
		//borderPanel.setMaximumSize(d);
		borderPanel.add(msgContainer);
		borderPanel.setBackground(ref.borderColor1);
		
		//Message Container
		d = new Dimension(ref.msgWidth, ref.msgHeight);
		msgContainer.setMinimumSize(d);
		msgContainer.setMaximumSize(d);
		msgContainer.add(topPanel);
		msgContainer.add(bottomPanel);
		msgContainer.setBackground(Color.red);
		msgContainer.setLayout(new BoxLayout(msgContainer, BoxLayout.PAGE_AXIS));
		msgContainer.setBounds(ref.borderWidthLeft, ref.borderWidthTop, ref.msgWidth, ref.msgHeight);
		
		//Row Panels
		topPanel.setSize(ref.msgWidth, ref.msgHeight/2);
		topPanel.setBackground(Color.red);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		bottomPanel.setSize(ref.msgWidth, ref.msgHeight/2);
		bottomPanel.setBackground(Color.red);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		
		//NAME
		d = new Dimension((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight);
		namePanel.setLayout(null);
		namePanel.setMinimumSize(d);
		namePanel.setMaximumSize(d);
		namePanel.setBackground(ref.nameBgColor);
		nameLabel.setText(trade.name);
		
		nameLabel.setBounds(ref.labelBufferX, 0, ref.nameWidth, ref.nameHeight);
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		
		//Price
		d = new Dimension((int) (ref.priceWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight);
		pricePanel.setLayout(null);
		pricePanel.setMinimumSize(d);
		pricePanel.setMaximumSize(d);
		pricePanel.setBackground(ref.priceBgColor);
		priceLabel.setText(trade.price);
		priceLabel.setBounds(ref.labelBufferX, 0, ref.priceWidth, ref.priceHeight);
		pricePanel.add(priceLabel);
		topPanel.add(pricePanel);
		
		//Item
		d = new Dimension(ref.itemWidth, ref.itemHeight);
		itemPanel.setLayout(null);
		itemPanel.setMinimumSize(d);
		itemPanel.setMaximumSize(d);
		itemPanel.setBackground(ref.itemBgColor);
		itemLabel.setText(trade.item);
		itemLabel.setBounds(ref.labelBufferX, 0, ref.itemWidth, ref.itemHeight);
		itemPanel.add(itemLabel);
		bottomPanel.add(itemPanel);
		
		//Buttons Row 1
		Border buttonBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		//Border buttonBorder = null;
		d = new Dimension(ref.buttonWidth, ref.buttonHeight);
		int buttonBuffer = -10;
		//ImageIcon Close = new ImageIcon(this.getClass().getResource("/invite.png")).getImage().getScaledInstance(ref.buttonWidth, ref.buttonHeight, Image.SCALE_DEFAULT);
		Image closeIcon = new ImageIcon(this.getClass().getResource("/close.png")).getImage().getScaledInstance(ref.buttonWidth, ref.buttonHeight, Image.SCALE_SMOOTH);
		closeButton.setIcon(new ImageIcon(closeIcon));
		//closeButton.setBorder(null);
		expandButton.setMaximumSize(d);
		closeButton.setMaximumSize(d);
		expandButton.setBorder(buttonBorder);
		closeButton.setBorder(buttonBorder);
		topPanel.add(expandButton);
		topPanel.add(closeButton);
		
		//Buttons Row 2
			
		inviteButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/invite.png")).getImage().getScaledInstance(ref.buttonWidth-10, ref.buttonHeight-10, Image.SCALE_SMOOTH)));
		tradeButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/cart.png")).getImage().getScaledInstance(ref.buttonWidth-10, ref.buttonHeight-10, Image.SCALE_SMOOTH)));
		
		inviteButton.setMaximumSize(d);	
		tpToPlayerButton.setMaximumSize(d);
		tradeButton.setMaximumSize(d);
		thankButton.setMaximumSize(d);
		kickButton.setMaximumSize(d);
		leaveButton.setMaximumSize(d);
		tpHomeButton.setMaximumSize(d);
		inviteButton.setBorder(buttonBorder);
		tpToPlayerButton.setBorder(buttonBorder);
		tradeButton.setBorder(buttonBorder);
		thankButton.setBorder(buttonBorder);
		kickButton.setBorder(buttonBorder);
		leaveButton.setBorder(buttonBorder);
		tpHomeButton.setBorder(buttonBorder);
		
		switch(trade.msgType){
		case INCOMING_TRADE:
			bottomPanel.add(inviteButton);
			bottomPanel.add(tradeButton);
			bottomPanel.add(thankButton);
			bottomPanel.add(kickButton);
			break;
		case OUTGOING_TRADE:
			bottomPanel.add(tpToPlayerButton);
			bottomPanel.add(tradeButton);
			bottomPanel.add(thankButton);
			bottomPanel.add(leaveButton);
			bottomPanel.add(tpHomeButton);
			break;
		default:
			break;
		}
		
		
		//Local Mouse Events
		Border b = BorderFactory.createLineBorder(Color.red);
		Border off = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {itemPanel.setBorder(b);}
		});
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseExited(java.awt.event.MouseEvent evt) {itemPanel.setBorder(off);}
		});
		
		//BUTTONS
		
		
	}
	
}
