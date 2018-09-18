package com.zrmiller.slimtrade;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MessageWindow extends JPanel{

	private REFERENCE_GUI ref = new REFERENCE_GUI();
	public int orderIndex;
	public boolean managerVisiblity;
	
	//Panels
	JPanel panelBorder = new JPanel();
	JPanel panelBorder2 = new JPanel();
	JPanel panelPlayerName = new JPanel();
	JPanel panelItem = new JPanel();
	JPanel panelPrice = new JPanel();
	//Labels
	JLabel labelPlayerName = new JLabel();
	JLabel labelItem = new JLabel();
	JLabel labelPrice = new JLabel();
	//Buttons
	JButton buttonClose = new JButton();
	JButton buttonExpand = new JButton();
	JButton buttonInvite = new JButton();
	JButton buttonTrade = new JButton();
	JButton buttonThank = new JButton();
	JButton buttonKick = new JButton();
	
	public MessageWindow(TradeOffer trade){
		//TEMP?
		ref.setMessageColor(trade.offerType);

		this.setLayout(null);
		this.setBounds(0, 0, ref.msgWidth+ref.borderWidthLeft+ref.borderWidthRight, ref.msgHeight+ref.borderWidthTop+ref.borderWidthBottom);
 		this.setBackground(ref.borderColor1);
 		
		//Name Panel
		this.panelPlayerName.add(labelPlayerName);
		this.add(panelPlayerName);
		panelPlayerName.setBounds(0+ref.borderWidthLeft, 0+ref.borderWidthTop, ref.playerNameWidth, ref.playerNameHeight);
		panelPlayerName.setBackground(ref.nameBgColor);
		panelPlayerName.setLayout(null);
		//Name Label
		labelPlayerName.setBounds(0,0, ref.playerNameWidth, ref.playerNameHeight);
		labelPlayerName.setText("<html>&nbsp;<font style=\"color:" + ref.defaultTextColor + ";\">" + trade.playerName + "</font></html>");
		//Item Panel
		this.panelItem.add(labelItem);
		this.add(panelItem);
		panelItem.setBounds(0+ref.borderWidthLeft, 0+ref.playerNameHeight+ref.borderWidthTop, ref.itemWidth, ref.itemHeight);
		panelItem.setBackground(ref.itemBgColor);
		panelItem.setLayout(null);
		//Item Label
		labelItem.setBounds(0,0, ref.itemWidth, ref.itemHeight);
		labelItem.setBackground(Color.orange);
		if(trade.itemQuant>0){
			labelItem.setText("<html>&nbsp;<font style=\"color:" + ref.defaultTextColor + ";\">" + trade.itemQuant + " "+ trade.item + "</font></html>");
		}else{
			labelItem.setText("<html>&nbsp;<font style=\"color:" + ref.defaultTextColor + ";\">" + trade.item + "</font></html>");
		}
		
		//Price Panel
		this.panelPrice.add(labelPrice);
		this.add(panelPrice);
		panelPrice.setBounds(0+ref.playerNameWidth+ref.borderWidthLeft, 0+ref.borderWidthTop, ref.priceWidth, ref.priceHeight);
		panelPrice.setBackground(ref.priceBgColor);
		panelPrice.setLayout(null);
		//Price Label
		labelPrice.setBounds(0,0, ref.priceWidth, ref.priceHeight);
		labelPrice.setText("<html>&nbsp;<font style=\"color:" + ref.defaultTextColor + ";\">" + trade.priceQuant + " " + trade.price + "</font></html>");
		this.setVisible(true);
		
		//Top Row of Buttons
		this.add(buttonClose);
		buttonClose.setBounds(ref.msgWidth+ref.borderWidthLeft-ref.buttonWidth, 0+ref.borderWidthTop, ref.buttonWidth, ref.buttonHeight);
		this.add(buttonExpand);
		buttonExpand.setBounds(ref.msgWidth+ref.borderWidthLeft-ref.buttonWidth*2, 0+ref.borderWidthTop, ref.buttonWidth, ref.buttonHeight);
		//Bottom Row of Buttons
		this.add(buttonInvite);
		buttonInvite.setBounds(ref.msgWidth+ref.borderWidthLeft-ref.buttonWidth*4, 0+ref.borderWidthTop+ref.buttonHeight, ref.buttonWidth, ref.buttonHeight);
		this.add(buttonTrade);
		buttonTrade.setBounds(ref.msgWidth+ref.borderWidthLeft-ref.buttonWidth*3, 0+ref.borderWidthTop+ref.buttonHeight, ref.buttonWidth, ref.buttonHeight);
		this.add(buttonThank);
		buttonThank.setBounds(ref.msgWidth+ref.borderWidthLeft-ref.buttonWidth*2, 0+ref.borderWidthTop+ref.buttonHeight, ref.buttonWidth, ref.buttonHeight);
		this.add(buttonKick);
		buttonKick.setBounds(ref.msgWidth+ref.borderWidthLeft-ref.buttonWidth, 0+ref.borderWidthTop+ref.buttonHeight, ref.buttonWidth, ref.buttonHeight);
		
		//Button Actions
		/*
		buttonClose.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {}
		});
		*/
		
	}
}
