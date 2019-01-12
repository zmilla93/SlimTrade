
package com.zrmiller.slimtrade.panels;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Robot;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.ColorManager;
import com.zrmiller.slimtrade.FrameManager;
import com.zrmiller.slimtrade.PoeInterface;
import com.zrmiller.slimtrade.TradeOffer;
import com.zrmiller.slimtrade.buttons.BasicIconButton;
import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.dialog.StashHelper;

//TODO : Refocus POE on all clicks
public class MessagePanel extends JPanel{
	
	//GLOBAL
	private static final long serialVersionUID = 1L;
	Robot robot;
	private static int width = 400;
	private static int height= 40;
	private static int borderThickness = 2;
	private int rowHeight = height/2;
	public static int totalWidth = width+borderThickness*4;
	public static int totalHeight = height+borderThickness*4;
	public TradeOffer trade;
	
	//Panels
	private JPanel borderOuter = new JPanel();
	private JPanel borderInner = new JPanel();
	private JPanel container = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel namePanel = new JPanel();
	private JLabel nameLabel = new JLabel();
	private JPanel pricePanel = new JPanel();
	private JLabel priceCountLabel = new JLabel();
	private JLabel priceTypeLabel = new JLabel();
	private JPanel topButtonPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	public JPanel itemPanel = new JPanel();
	private JLabel itemCountLabel = new JLabel();
	private JLabel itemLabel = new JLabel();
	public StashHelper stashHelper;
	
	//Buttons Top	
	public BasicIconButton callbackButton;
	public BasicIconButton waitButton;
	public BasicIconButton stillInterestedButton;
	public BasicIconButton repeatMessageButton;
	public BasicIconButton closeButton;
	//Buttons Bottom
	public BasicIconButton inviteToPartyButton;
	private BasicIconButton tpToHideoutButton;
	private BasicIconButton tradeButton;
	private BasicIconButton thankButton;
	private BasicIconButton leavePartyButton;
	private BasicIconButton kickButton;
	private BasicIconButton tpHomeButton;
	
	
	
	
	public MessagePanel(TradeOffer trade){
		
		FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT, 0, 0);
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		this.trade = trade;
		 // TODO : Optimize
		 // Size Variables
		 
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));
		
		double nameWidthMult = 0;
		double priceWidthMult = 0;
		double itemWidthMult = 0;
		double buttonTopMult = 0;
		double buttonBotMult = 0;
		
		//TODO : Work out the math so that changing size of window guarantees simple ratios
		//Currently buttons are 0.05 mult
		switch(trade.msgType){
		case INCOMING_TRADE:
			nameWidthMult = 0.5;
			priceWidthMult = 0.3;
			itemWidthMult = 0.8;
			buttonTopMult = 0.2;
			buttonBotMult = 0.2;
			break;
		case OUTGOING_TRADE:
			nameWidthMult = 0.55;
			priceWidthMult = 0.35;
			itemWidthMult = 0.75;
			buttonTopMult = 0.1;
			buttonBotMult = 0.25;
			break;
		default:
			break;
		}
		
		//MESSAGE WINDOW BORDER
		borderOuter.setLayout(new FlowLayout(FlowLayout.CENTER, borderThickness, borderThickness));
		borderOuter.setPreferredSize(new Dimension(width+borderThickness*2, height+borderThickness*2));
		
		borderInner.setLayout(new FlowLayout(FlowLayout.CENTER, borderThickness, borderThickness));
		borderInner.setPreferredSize(new Dimension(width+borderThickness*2, height+borderThickness*2));
		borderOuter.add(borderInner, BorderLayout.CENTER);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(width, height));
		borderInner.add(container, BorderLayout.CENTER);
		this.add(borderOuter);
		
		BasicIconButton.width = height/2;
		BasicIconButton.height = height/2;
		
		//TOP PANEL
		LayoutManager panelFlow = new FlowLayout(FlowLayout.CENTER, 0, 1);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(flowLeft);
		topPanel.setPreferredSize(new Dimension(width, rowHeight));
		container.add(topPanel, BorderLayout.PAGE_START);
		
		
		//NAME
		namePanel = new JPanel(panelFlow);
		nameLabel = new JLabel(trade.playerName);
		namePanel.setPreferredSize(new Dimension((int)(width*nameWidthMult), rowHeight));
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		//PRICE
		pricePanel = new JPanel(panelFlow);
		priceCountLabel = new JLabel(trade.priceCount.toString().replaceAll("[.]0", "") + " ");
		pricePanel.add(priceCountLabel);
		BasicIcon.width = rowHeight;
		BasicIcon.height = rowHeight;
		BasicIcon priceIcon;
		if(this.getClass().getResource("/" + trade.priceTypeString + ".png") != null){
			priceIcon = new BasicIcon("/" + trade.priceTypeString + ".png");
			pricePanel.add(priceIcon);
		}else{
			priceTypeLabel = new JLabel(" " + trade.priceTypeString);
		}
		pricePanel.setPreferredSize(new Dimension((int)(width*priceWidthMult), rowHeight));
		pricePanel.add(priceTypeLabel);
		topPanel.add(pricePanel);
		//TOP BUTTON PANEL
		topButtonPanel = new JPanel(panelFlow);
		topButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		topButtonPanel.setPreferredSize(new Dimension((int)(width*buttonTopMult), rowHeight));
		topPanel.add(topButtonPanel);
		
		/*
		 * BOTTOM PANEL
		 */
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		bottomPanel.setPreferredSize(new Dimension(width,rowHeight));
		container.add(bottomPanel, BorderLayout.CENTER);
		itemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		if(trade.itemCount>0){
			itemCountLabel = new JLabel(trade.itemCount.toString().replaceAll("[.]0", "") + " ");
			itemPanel.add(itemCountLabel);
		}
		BasicIcon.width = rowHeight;
		BasicIcon.height = rowHeight;
		BasicIcon itemIcon;
		if(this.getClass().getResource("/" + trade.itemName + ".png") != null){
			itemIcon = new BasicIcon("/" + trade.itemName + ".png");
			itemPanel.add(itemIcon, BorderLayout.CENTER);
		}else{
			itemLabel = new JLabel(trade.itemName);
			itemPanel.add(itemLabel, BorderLayout.CENTER);
		}
		
		itemPanel.setPreferredSize(new Dimension((int)(width*itemWidthMult), rowHeight));
		bottomPanel.add(itemPanel);
		
		JPanel bottomButtonPanel = new JPanel();
		bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		bottomButtonPanel.setPreferredSize(new Dimension((int)(width*buttonBotMult), rowHeight));
		bottomPanel.add(bottomButtonPanel);
		
		//TODO : Could move these to delaraction if width/height default is dealt with
		//Buttons
		BasicIconButton.width = rowHeight;
		BasicIconButton.height = rowHeight;
		//Row 1
		callbackButton = new BasicIconButton("/phone.png");
		waitButton = new BasicIconButton("/clock1.png");
		stillInterestedButton = new BasicIconButton("/refresh1.png");
		repeatMessageButton = new BasicIconButton("/refresh1.png");
		closeButton = new BasicIconButton("/close.png");
		//Row 2
		inviteToPartyButton = new BasicIconButton("/invite.png");
		tpToHideoutButton = new BasicIconButton("/warp.png");
		tradeButton = new BasicIconButton("/cart2.png");
		thankButton = new BasicIconButton("/thumb1.png");
		kickButton = new BasicIconButton("/leave.png");
		leavePartyButton = new BasicIconButton("/leave.png");
		tpHomeButton = new BasicIconButton("/home2.png");
		
		
		
		switch(trade.msgType){
		case INCOMING_TRADE:
			//TOP BUTTONS
			topButtonPanel.add(callbackButton);
			topButtonPanel.add(waitButton);
			topButtonPanel.add(stillInterestedButton);
			topButtonPanel.add(closeButton);
			//
			callbackButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {;}});
			waitButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				if(evt.getButton() == MouseEvent.BUTTON1){
					PoeInterface.paste("@" + trade.playerName + " one sec");
				}else if(evt.getButton() == MouseEvent.BUTTON3){
					PoeInterface.paste("@" + trade.playerName + " one minute");
				}
			}});
			stillInterestedButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				String item = trade.itemCount == 0 ? trade.itemName : trade.itemCount.toString().replaceAll("[.]0", "") + " " + trade.itemName;
				PoeInterface.paste("@" + trade.playerName + " Hi, are you still interested in my " + item + " for " + trade.priceCount.toString().replaceAll("[.]0", "") + " " + trade.priceTypeString + "?");
			}});
			closeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				if(evt.getButton() == MouseEvent.BUTTON1){
					//Reenable refresh later
//			    	FrameManager.stashHelperContainer.refresh();
				}else if(evt.getButton() == MouseEvent.BUTTON3){
					//TODO : Having two actions causes 2nd action to be performed twice?
					//Probably has to do with the clipboard. should combine paste then kick into one method
//					PoeInterface.paste("/kick " + trade.playerName);
					PoeInterface.paste("@" + trade.playerName + " sold, sorry");
				}
			}});
//			closeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
//				if(evt.getButton() == MouseEvent.BUTTON3){
//					PoeInterface.paste("@" + trade.playerName + " sold, sorry");
//					PoeInterface.paste("/kick " + trade.playerName);
//				}
//			}});
			
			//BUTTOM BUTTONS
			bottomButtonPanel.add(inviteToPartyButton);
			bottomButtonPanel.add(tradeButton);
			bottomButtonPanel.add(thankButton);
			bottomButtonPanel.add(kickButton);
			inviteToPartyButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			//
			inviteToPartyButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				FrameManager.stashHelperContainer.add(stashHelper);
				stashHelper.setVisible(true);
				FrameManager.stashHelperContainer.refresh();
				PoeInterface.paste("/invite " + trade.playerName);
				
				inviteToPartyButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				tradeButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
				
				
			}});
			tradeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				PoeInterface.paste("/tradewith " + trade.playerName);
				tradeButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				thankButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			}});
			thankButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				PoeInterface.paste("@" + trade.playerName + " thanks");
				thankButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				kickButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			}});
			kickButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				PoeInterface.paste("/kick " + trade.playerName);
				kickButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				closeButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			}});
			
			//STASH HELPER
			Border blankBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
			Border hoverBorder = BorderFactory.createLineBorder(Color.BLACK);
			stashHelper = new StashHelper(trade);
			stashHelper.setVisible(false);
			FrameManager.stashHelperContainer.getContentPane().add(stashHelper);
			
			//Item Panel Actions
			itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseEntered(java.awt.event.MouseEvent evt) {
			    	itemPanel.setBorder(hoverBorder);
			    }
			});
			itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent evt) {
					itemPanel.setBorder(blankBorder);
				}
			});
			itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			    	FrameManager.stashHelperContainer.add(stashHelper);
			    	stashHelper.setVisible(true);
			    	FrameManager.stashHelperContainer.refresh();
			    }
			});
			break;
		case OUTGOING_TRADE:
			//TOP BUTTONS
			topButtonPanel.add(repeatMessageButton);
			topButtonPanel.add(closeButton);
			//
			repeatMessageButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste(trade.sentMessage);}});
			//BOTTOM BUTTONS
			bottomButtonPanel.add(tpToHideoutButton);
			bottomButtonPanel.add(tradeButton);
			bottomButtonPanel.add(thankButton);
			bottomButtonPanel.add(leavePartyButton);
			bottomButtonPanel.add(tpHomeButton);
			tpToHideoutButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			//
			tpToHideoutButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				PoeInterface.paste("/hideout " + trade.playerName);
				tpToHideoutButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				thankButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			}});
			tradeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste("/tradewith " + trade.playerName);}});
			thankButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				PoeInterface.paste("@" + trade.playerName + " thanks");
				thankButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				leavePartyButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			}});
			leavePartyButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				PoeInterface.paste("/kick " + FrameManager.characterWindow.getCharacterName());
				leavePartyButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				tpHomeButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			}});
			tpHomeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				PoeInterface.paste("/hideout");
				tpHomeButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder);
				closeButton.setBorderPresetDefault(ColorManager.MsgWindow.buttonBorder_next);
			}});
//			tradeButton.bgColor = ColorManager.MsgWindow.buttonBG_completed;
			break;
		case CHAT_SCANNER:
			break;
		case UNKNOWN:
			break;
		}
//		container.addMouseListener(new java.awt.event.MouseAdapter() {public void mousePressed(java.awt.event.MouseEvent evt) {
//			System.out.println("!!!!!");
//		}});
		updateColor();

	}

	//TODO : Naming conventions? updatePresetColors?
	private void updateButton(BasicIconButton button){
		button.bgColor = ColorManager.MsgWindow.buttonBG;
		button.bgColor_hover = ColorManager.MsgWindow.buttonBG_hover;
		button.updateBorderPreset();
	}
	
	public void updateColor(){
		//Panels
//		System.out.println(ColorManager.MsgWindow.borderOuter);
		this.borderOuter.setBackground(ColorManager.MsgWindow.borderOuter);
		this.borderInner.setBackground(ColorManager.MsgWindow.borderInner);
		this.namePanel.setBackground(ColorManager.MsgWindow.nameBG);
		this.nameLabel.setForeground(ColorManager.MsgWindow.text);
		this.priceCountLabel.setForeground(ColorManager.MsgWindow.text);
		this.priceTypeLabel.setForeground(ColorManager.MsgWindow.text);
		this.itemPanel.setBackground(ColorManager.MsgWindow.itemBG);
		this.itemCountLabel.setForeground(ColorManager.MsgWindow.text);
		this.itemLabel.setForeground(ColorManager.MsgWindow.text);
		//Buttons
		updateButton(callbackButton);
		updateButton(waitButton);
		updateButton(stillInterestedButton);
		updateButton(repeatMessageButton);
		updateButton(closeButton);
		updateButton(inviteToPartyButton);
		updateButton(tpToHideoutButton);
		updateButton(tradeButton);
		updateButton(thankButton);
		updateButton(kickButton);
		updateButton(leavePartyButton);
		updateButton(tpHomeButton);
		//Row 2

		//Message Type Specific
		switch(trade.msgType){
		case INCOMING_TRADE:
			pricePanel.setBackground(ColorManager.MsgWindow.priceBG_in);
			break;
		case OUTGOING_TRADE:
			pricePanel.setBackground(ColorManager.MsgWindow.priceBG_out);
//			tradeButton.bgColor = ColorManager.MsgWindow.buttonBG_completed;
//			tradeButton.setBackground(ColorManager.MsgWindow.buttonBG_completed);
			break;
		default:
			break;
		}
		
	}
	
	public MessageType getMessageType(){
		return this.trade.msgType;
	}

}