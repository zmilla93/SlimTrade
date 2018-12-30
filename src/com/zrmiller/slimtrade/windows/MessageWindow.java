
package com.zrmiller.slimtrade.windows;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.zrmiller.slimtrade.ColorManager;
import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.PoeInterface;
import com.zrmiller.slimtrade.TradeOffer;
import com.zrmiller.slimtrade.buttons.BasicIconButton;
import com.zrmiller.slimtrade.panels.BasicIcon;
import com.zrmiller.slimtrade.panels.StashHelper;

//TODO : Refocus POE on all clicks

public class MessageWindow extends JPanel{
	
	//GLOBAL
	private static final long serialVersionUID = 1L;
	Robot robot;
	private static int width = 400;
	private static int height= 40;
	private static int borderThickness = 2;
	private int rowHeight = height/2;
	public static int totalWidth = width+borderThickness*4;
	public static int totalHeight = height+borderThickness*4;
	
	private TradeOffer trade;
	
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
	private JPanel itemPanel = new JPanel();
	private JLabel itemCountLabel = new JLabel();
	private JLabel itemLabel = new JLabel();
	
	//Buttons Top	
	public BasicIconButton callbackButton;
	public BasicIconButton waitButton;
	public BasicIconButton stillInterestedButton;
	public BasicIconButton repeatMessageButton;
	public BasicIconButton closeButton;
	//Buttons Bottom
	private BasicIconButton inviteToPartyButton;
	private BasicIconButton tpToHideoutButton;
	private BasicIconButton tradeButton;
	private BasicIconButton thankButton;
	private BasicIconButton leavePartyButton;
	private BasicIconButton kickButton;
	private BasicIconButton tpHomeButton;
	
	//ITEM HIGHLIGHTER + TIMER
	public StashHelper stashHelper;
//	private JPanel itemHighlighter;
//	private ActionListener hideHighlighter = new ActionListener(){
//		@Override
//		public void actionPerformed(ActionEvent e){
//			itemHighlighter.setVisible(false);
//			highlighterTimer.stop();
//		}
//	};
//	private Timer highlighterTimer = new Timer(2500, hideHighlighter);
	
	
	public MessageWindow(TradeOffer trade){
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
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
			itemWidthMult = 0.8;
			buttonTopMult = 0.1;
			buttonBotMult = 0.2;
			break;
		default:
			break;
		}
		
		//BORDER
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
		topPanel.setLayout(Overlay.flowCenter);
		topPanel.setPreferredSize(new Dimension(width, rowHeight));
		topPanel.setBackground(Color.CYAN);
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
		 * CENTER PANEL
		 */
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		centerPanel.setPreferredSize(new Dimension(width,rowHeight));
		container.add(centerPanel, BorderLayout.CENTER);
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
		
//		itemLabel = new JLabel(trade.itemName);
		itemPanel.setPreferredSize(new Dimension((int)(width*itemWidthMult), rowHeight));
//		itemPanel.add(itemLabel, BorderLayout.CENTER);
		centerPanel.add(itemPanel);
		
		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		centerButtonPanel.setPreferredSize(new Dimension((int)(width*buttonBotMult), rowHeight));
		centerPanel.add(centerButtonPanel);
		
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
		tpHomeButton = new BasicIconButton("/home.png");
		
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
				//.replaceAll("[.]0", "")
//				String price = trade.itemCount == 0 ? trade.itemName : trade.itemCount + " " + trade.itemName;
				PoeInterface.paste("Hi, are you still interested in my " + item + " for " + trade.priceCount.toString().replaceAll("[.]0", "") + " " + trade.priceTypeString + "?");
			}});
			closeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				if(evt.getButton() == MouseEvent.BUTTON1){
					System.out.println("!!!");
					stashHelper.itemHighlighter.setVisible(false);
			    	Overlay.messageContainer.remove(stashHelper.itemHighlighter);
			    	Overlay.stashHelperContainer.remove(stashHelper);
			    	Overlay.stashHelperContainer.refresh();
				}
			}});
			closeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
				if(evt.getButton() == MouseEvent.BUTTON3){PoeInterface.paste("@" + trade.playerName + " sold, sorry");}
			}});
			
			//BUTTOM BUTTONS
			centerButtonPanel.add(inviteToPartyButton);
			centerButtonPanel.add(tradeButton);
			centerButtonPanel.add(thankButton);
			centerButtonPanel.add(kickButton);
			//
			inviteToPartyButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
					PoeInterface.paste("/invite " + trade.playerName);
					stashHelper.setVisible(true);
				}});
			kickButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste("/kick " + trade.playerName);}});
			
			//STASH HELPER
			Border blankBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
			Border hoverBorder = BorderFactory.createLineBorder(Color.BLACK);
			stashHelper = new StashHelper(trade.stashtabX, trade.stashtabY,  trade.stashtabName, trade.itemName);
			stashHelper.setVisible(false);
			Overlay.stashHelperContainer.add(stashHelper);
//	    	Overlay.stashHelperContainer.refresh();
			
			
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
			    	stashHelper.setVisible(true);
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
			centerButtonPanel.add(tpToHideoutButton);
			centerButtonPanel.add(tradeButton);
			centerButtonPanel.add(thankButton);
			centerButtonPanel.add(leavePartyButton);
			centerButtonPanel.add(tpHomeButton);
			//
			tpToHideoutButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste("/hideout " + trade.playerName);}});
			leavePartyButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste("/kick " + Overlay.characterWindow.getCharacterName());}});
			tpHomeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste("/hideout");}});
			break;
		case CHAT_SCANNER:
			break;
		case UNKNOWN:
			break;
		}
		//MUTUAL BUTTONS
		tradeButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste("/tradewith " + trade.playerName);}});
		thankButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {PoeInterface.paste("@" + trade.playerName + " thanks");}});
		
//		itemHighlighter.setSize(Overlay.stashWindow.grid.getWidth()/12, Overlay.stashWindow.grid.getWidth()/12);
//		itemHighlighter = new JPanel();
//		itemHighlighter.setVisible(false);
//		double cellWidth = (double)StashWindow.getGridSize().width/12;
//		double cellHeight = (double)StashWindow.getGridSize().height/12;
//		itemHighlighter.setPreferredSize(new Dimension((int)cellWidth, (int)cellHeight));
//		itemHighlighter.setBounds((int)(StashWindow.getWinPos().x+((trade.stashtabX-1)*cellWidth)), (int)(StashWindow.getGridPos().y+((trade.stashtabY-1)*cellHeight)), (int)cellWidth, (int)cellHeight);
//		itemHighlighter.setBackground(Color.blue);
//		Overlay.optionContainer.add(itemHighlighter);
//		Overlay.screenContainer.revalidate();
//		Overlay.screenContainer.repaint();

		updateColor();
		

		
		

		
		//Stash Helper
//		stashHelper.addMouseListener(new java.awt.event.MouseAdapter() {
//		    public void mouseEntered(java.awt.event.MouseEvent e) {
//		    	highlighterTimer.stop();
//		    	double cellWidth = (double)StashWindow.getGridSize().width/12;
//				double cellHeight = (double)StashWindow.getGridSize().height/12;
//				itemHighlighter.setBounds((int)(StashWindow.getGridPos().x+((trade.stashtabX-1)*cellWidth)), (int)(StashWindow.getGridPos().y+((trade.stashtabY-1)*cellHeight)), (int)cellWidth, (int)cellHeight);
//		    	itemHighlighter.setVisible(true);
//		    }
//		});
		
//		stashHelper.addMouseListener(new java.awt.event.MouseAdapter() {
//			public void mouseExited(java.awt.event.MouseEvent e) {
//				highlighterTimer.restart();
//			}
//		});
		
	}
	
	//TODO : Naming conventions? updatePresetColors?
	private void updateButton(BasicIconButton button){
		button.bgColor = ColorManager.MsgWindow.buttonBG;
		button.bgColor_hover = ColorManager.MsgWindow.buttonBG_hover;
		button.updateBorderPreset();
	}
	
	public void updateColor(){
		//Panels
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
		//Row 2

		//Message Type Specific
		switch(trade.msgType){
		case INCOMING_TRADE:
			this.pricePanel.setBackground(ColorManager.MsgWindow.priceBG_in);
			break;
		case OUTGOING_TRADE:
			this.pricePanel.setBackground(ColorManager.MsgWindow.priceBG_out);
			break;
		default:
			break;
		}
		
//		this.closeButton.setBorderPresets(ColorManager.MsgWindow.buttonBorder, ColorManager.MsgWindow.buttonBorder_hover);
		
//		Border buttonBorder = BorderFactory.createLineBorder(Color.black);
//		this.closeButton.setBorder(buttonBorder);
	}

}