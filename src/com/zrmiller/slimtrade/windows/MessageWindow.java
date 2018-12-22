
package com.zrmiller.slimtrade.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.ColorManager;
import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.TradeOffer;
import com.zrmiller.slimtrade.buttons.BasicButton;
import com.zrmiller.slimtrade.panels.StashHelper;

public class MessageWindow extends JPanel{
	
	//GLOBAL
	private static final long serialVersionUID = 1L;
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
	private JLabel priceLabel = new JLabel();
	private JPanel topButtonPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel itemPanel = new JPanel();
	private JLabel itemLabel = new JLabel();
	
	//Buttons Top	
	public BasicButton callbackButton;
	public BasicButton waitButton;
	public BasicButton stillInterestedButton;
	public BasicButton repeatMessageButton;
	public BasicButton closeButton;
	//Buttons Bottom
	private BasicButton inviteToPartyButton;
	private BasicButton tpToHideoutButton;
	private BasicButton tradeButton;
	private BasicButton thankButton;
	private BasicButton leavePartyButton;
	private BasicButton kickButton;
	private BasicButton tpHomeButton;
	
	//ITEM HIGHLIGHTER + TIMER
	private StashHelper stashHelper = new StashHelper();
	private JPanel itemHighlighter;
	private ActionListener hideHighlighter = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			itemHighlighter.setVisible(false);
			highlighterTimer.stop();
		}
	};
	private Timer highlighterTimer = new Timer(2500, hideHighlighter);
	
	
	public MessageWindow(TradeOffer trade){
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
		
		BasicButton.width = height/2;
		BasicButton.height = height/2;
		
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
		priceLabel = new JLabel(trade.priceType.toString());
		pricePanel.setPreferredSize(new Dimension((int)(width*priceWidthMult), rowHeight));
		pricePanel.add(priceLabel);
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
		itemLabel = new JLabel(trade.itemName);
		itemPanel.setPreferredSize(new Dimension((int)(width*itemWidthMult), rowHeight));
		itemPanel.add(itemLabel, BorderLayout.CENTER);
		centerPanel.add(itemPanel);
		
		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		centerButtonPanel.setPreferredSize(new Dimension((int)(width*buttonBotMult), rowHeight));
		centerPanel.add(centerButtonPanel);
		
		//TODO : Could move these to delaraction if width/height default is dealt with
		//Buttons
		BasicButton.width = rowHeight;
		BasicButton.height = rowHeight;
		//Row 1
		callbackButton = new BasicButton("/phone.png");
		waitButton = new BasicButton("/watch.png");
		stillInterestedButton = new BasicButton("/refresh1.png");
		repeatMessageButton = new BasicButton("/refresh1.png");
		closeButton = new BasicButton("/close.png");
		//Row 2
		inviteToPartyButton = new BasicButton("/invite.png");
		tpToHideoutButton = new BasicButton("/warp.png");
		tradeButton = new BasicButton("/cart2.png");
		thankButton = new BasicButton("/happy.png");
		kickButton = new BasicButton("/leave.png");
		leavePartyButton = new BasicButton("/leave.png");
		tpHomeButton = new BasicButton("/home.png");
		
		switch(trade.msgType){
		case INCOMING_TRADE:
			topButtonPanel.add(callbackButton);
			topButtonPanel.add(waitButton);
			topButtonPanel.add(stillInterestedButton);
			topButtonPanel.add(closeButton);
			//
			centerButtonPanel.add(inviteToPartyButton);
			centerButtonPanel.add(tradeButton);
			centerButtonPanel.add(thankButton);
			centerButtonPanel.add(kickButton);
			break;
		case OUTGOING_TRADE:
			topButtonPanel.add(repeatMessageButton);
			topButtonPanel.add(closeButton);
			//
			centerButtonPanel.add(tpToHideoutButton);
			centerButtonPanel.add(tradeButton);
			centerButtonPanel.add(thankButton);
			centerButtonPanel.add(leavePartyButton);
			centerButtonPanel.add(tpHomeButton);
			break;
		case CHAT_SCANNER:
			break;
		case UNKNOWN:
			break;
		}
		
		
		Border blankBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border hoverBorder = BorderFactory.createLineBorder(Color.RED);
		
//		itemHighlighter.setSize(Overlay.stashWindow.grid.getWidth()/12, Overlay.stashWindow.grid.getWidth()/12);
		itemHighlighter = new JPanel();
		itemHighlighter.setVisible(false);
		double cellWidth = (double)StashWindow.getGridSize().width/12;
		double cellHeight = (double)StashWindow.getGridSize().height/12;
		itemHighlighter.setPreferredSize(new Dimension((int)cellWidth, (int)cellHeight));
		itemHighlighter.setBounds((int)(StashWindow.getWinPos().x+((trade.stashtabX-1)*cellWidth)), (int)(StashWindow.getGridPos().y+((trade.stashtabY-1)*cellHeight)), (int)cellWidth, (int)cellHeight);
		itemHighlighter.setBackground(Color.blue);
		Overlay.optionContainer.add(itemHighlighter);
//		Overlay.screenContainer.revalidate();
//		Overlay.screenContainer.repaint();
//		
		updateColor();
		
		//Item Panel Actions
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	itemPanel.setBorder(hoverBorder);
		    }
		});
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent evt) {
				itemPanel.setBorder(blankBorder);
//				Overlay.fixPanelLayering();
			}
		});
		
		//TODO : Deal with panel layering issue, this is one solution
		itemPanel.addMouseMotionListener(new java.awt.event.MouseAdapter() {
			public void mouseMoved(java.awt.event.MouseEvent evt) {
//				Overlay.fixPanelLayering();
			}
		});
		
		
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	Overlay.stashHelperContainer.add(stashHelper);
		    	Overlay.stashHelperContainer.refresh();
		    }
		});
		
		//Stash Helper
		stashHelper.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent e) {
		    	highlighterTimer.stop();
		    	double cellWidth = (double)StashWindow.getGridSize().width/12;
				double cellHeight = (double)StashWindow.getGridSize().height/12;
				itemHighlighter.setBounds((int)(StashWindow.getGridPos().x+((trade.stashtabX-1)*cellWidth)), (int)(StashWindow.getGridPos().y+((trade.stashtabY-1)*cellHeight)), (int)cellWidth, (int)cellHeight);
		    	itemHighlighter.setVisible(true);
		    }
		});
		
		stashHelper.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				highlighterTimer.restart();
			}
		});
		
	}
	
	private void updateButton(BasicButton button){
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
		this.priceLabel.setForeground(ColorManager.MsgWindow.text);
		this.itemPanel.setBackground(ColorManager.MsgWindow.itemBG);
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