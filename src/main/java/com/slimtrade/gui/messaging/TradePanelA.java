package main.java.com.slimtrade.gui.messaging;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.StashHelper;

public class TradePanelA extends AbstractMessagePanel {

	private static final long serialVersionUID = 1L;

	private JPanel namePanel = new NameClickPanel();
	private JPanel pricePanel = new JPanel(gb);
	private JPanel itemPanel = new ItemClickPanel();
	protected JPanel topPanel = new JPanel(gb);
	protected JPanel bottomPanel = new JPanel(gb);

	private JLabel nameLabel = new JLabel("NAME");
	private JLabel priceLabel = new JLabel("PRICE");
	private JLabel itemLabel = new JLabel("ITEM");

	protected JPanel buttonPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	protected JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

	private TradeOffer trade;
	private StashHelper stashHelper;

	private int buttonCountTop;
	private int buttonCountBottom;
	
	private IconButton callbackButton;
	private IconButton waitButton;
	private IconButton refreshButton;
	private IconButton inviteButton;
	private IconButton warpButton;
	private IconButton tradeButton;
	private IconButton thankButton;
	private IconButton leaveButton;
	private IconButton kickButton;
	private IconButton homeButton;
	

	//TODO Listeners?
	public TradePanelA(TradeOffer trade, int size) {
		super(size);
		buildPanel(trade, size, true);
	}

	public TradePanelA(TradeOffer trade, int size, boolean makeListeners) {
		super(size);
		buildPanel(trade, size, makeListeners);
//		j=new JButton();
	}

	private void buildPanel(TradeOffer trade, int size, boolean makeListeners) {
		// TODO : move size stuff to super
		this.setMessageType(trade.msgType);
		switch(trade.msgType){
		case CHAT_SCANNER:
			break;
		case INCOMING_TRADE:
			buttonCountTop = 4;
			buttonCountBottom = 4;
			break;
		case OUTGOING_TRADE:
			buttonCountTop = 2;
			buttonCountBottom = 4;
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
		calculateSizes(size);
		refreshButtons(buttonCountTop, buttonCountBottom, this.getMessageType());
		resizeFrames(buttonCountTop, buttonCountBottom);
		
		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.CENTER);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		pricePanel.setLayout(new BorderLayout());
		pricePanel.add(priceLabel, BorderLayout.CENTER);
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		timerPanel.setLayout(new BorderLayout());
		timerPanel.add(timerLabel, BorderLayout.CENTER);
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		itemPanel.setLayout(new BorderLayout());
		itemPanel.add(itemLabel, BorderLayout.CENTER);
		itemLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Color
		namePanel.setBackground(Color.LIGHT_GRAY);
		nameLabel.setOpaque(true);
		nameLabel.setBackground(Color.green);
		pricePanel.setBackground(Color.GRAY);
		itemPanel.setBackground(Color.DARK_GRAY);
		buttonPanelTop.setBackground(Color.ORANGE);
		buttonPanelBottom.setBackground(Color.YELLOW);

//		this.setButtonCount(3, 5);
		
		this.setBackground(Color.BLACK);
		borderPanel.setBackground(Color.CYAN);
		container.setBackground(Color.BLACK);
		topPanel.setBackground(Color.RED);
		bottomPanel.setBackground(Color.RED);

		container.add(topPanel, gc);
		gc.gridy = 1;
		container.add(bottomPanel, gc);
		gc.gridy = 0;
		borderPanel.add(container, gc);
		this.add(borderPanel, gc);

		// gc.fill = GridBagConstraints.BOTH;
		// gc.weightx = 0.7;

		// TOP PANEL
		topPanel.add(namePanel, gc);
		gc.gridx++;
		topPanel.add(pricePanel, gc);
		gc.gridx++;
		topPanel.add(buttonPanelTop, gc);

		// BOTTOM PANEL
		gc.gridx = 0;
		gc.gridy = 0;
		bottomPanel.add(timerPanel, gc);
		gc.gridx++;
		bottomPanel.add(itemPanel, gc);
		gc.gridx++;
		bottomPanel.add(buttonPanelBottom, gc);


		// buttonPanelBottom.add(new IconButton(ImagePreloader.rad, 20));

//		secondTimer.
		this.startTimer();
		this.revalidate();
		this.repaint();
	}
	
	//TODO add button count
	public void resizeMessage(int size){
		calculateSizes(size);
		resizeFrames(3, 5);
		refreshButtons(3, 5, this.getMessageType());
		this.revalidate();
		this.repaint();
	}
	
	private void calculateSizes(int size){
		if (size % 2 != 0) {
			size++;
		}
		messageHeight = size;
		messageWidth = messageHeight * 10;
		borderSize = 2;
		rowHeight = messageHeight / 2;
		totalWidth = messageWidth + (borderSize * 4);
		totalHeight = messageHeight + (borderSize * 4);
		refreshFont(rowHeight);
		nameLabel.setFont(font);
		priceLabel.setFont(font);
		timerLabel.setFont(font);
		itemLabel.setFont(font);
	}

	protected void refreshButtons(int top, int bottom, MessageType type) {
		for(Component c : buttonPanelTop.getComponents()){
			buttonPanelTop.remove(c);
			c = null;
		}
		for(Component c : buttonPanelBottom.getComponents()){
			buttonPanelBottom.remove(c);
			c = null;
		}
		switch(type){
		case CHAT_SCANNER:
			break;
		case INCOMING_TRADE:
			callbackButton = new IconButton(ImagePreloader.callback, rowHeight);
			waitButton = new IconButton(ImagePreloader.wait, rowHeight);
			refreshButton = new IconButton(ImagePreloader.refresh, rowHeight);
			inviteButton = new IconButton(ImagePreloader.invite, rowHeight);
			tradeButton = new IconButton(ImagePreloader.trade, rowHeight);
			thankButton = new IconButton(ImagePreloader.thank, rowHeight);
			kickButton = new IconButton(ImagePreloader.leave, rowHeight);
			buttonPanelTop.add(callbackButton);
			buttonPanelTop.add(waitButton);
			buttonPanelTop.add(refreshButton);
			
			buttonPanelBottom.add(inviteButton);
			buttonPanelBottom.add(tradeButton);
			buttonPanelBottom.add(thankButton);
			buttonPanelBottom.add(kickButton);
			
			break;
		case OUTGOING_TRADE:
			refreshButton = new IconButton(ImagePreloader.refresh, rowHeight);
			warpButton = new IconButton(ImagePreloader.warp, rowHeight);
			thankButton = new IconButton(ImagePreloader.thank, rowHeight);
			kickButton = new IconButton(ImagePreloader.leave, rowHeight);
			homeButton = new IconButton(ImagePreloader.home, rowHeight);
			
			buttonPanelTop.add(refreshButton);
			
			buttonPanelBottom.add(warpButton);
			buttonPanelBottom.add(thankButton);
			buttonPanelBottom.add(kickButton);
			buttonPanelBottom.add(homeButton);
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
		
		//TODO : update force
		this.setCloseButton(rowHeight);
		buttonPanelTop.add(closeButton);

//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb1.png", rowHeight));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
	}

	protected void resizeFrames(int top, int bottom) {
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));
		borderPanel.setPreferredSize(new Dimension(messageWidth + borderSize * 2, messageHeight + borderSize * 2));
		container.setPreferredSize(new Dimension(messageWidth, messageHeight));
		Dimension s = new Dimension(messageWidth, rowHeight);
		topPanel.setPreferredSize(s);
		bottomPanel.setPreferredSize(s);
		this.buttonCountTop = top;
		this.buttonCountBottom = bottom;
		Dimension sizeTop = new Dimension(rowHeight * top, rowHeight);
		Dimension sizeBottom = new Dimension(rowHeight * bottom, rowHeight);
		buttonPanelTop.setPreferredSize(sizeTop);
		buttonPanelTop.setMinimumSize(sizeTop);
		buttonPanelBottom.setPreferredSize(sizeBottom);
		buttonPanelBottom.setMaximumSize(sizeBottom);
		int nameWidth = (int) ((messageWidth - sizeTop.width) * 0.7);
		int priceWidth = messageWidth - nameWidth - sizeTop.width;
		int timerWidth = (int) (messageWidth * timerWeight);
		int itemWidth = messageWidth - timerWidth - sizeBottom.width;

		namePanel.setPreferredSize(new Dimension(nameWidth, rowHeight));
		pricePanel.setPreferredSize(new Dimension(priceWidth, rowHeight));
		timerPanel.setPreferredSize(new Dimension(timerWidth, rowHeight));
		itemPanel.setPreferredSize(new Dimension(itemWidth, rowHeight));
	}

	public JButton getCloseButton() {
		return this.closeButton;
	}

	public TradeOffer getTrade() {
		return trade;
	}

	public void setTrade(TradeOffer trade) {
		this.trade = trade;
	}

	public StashHelper getStashHelper() {
		return stashHelper;
	}

	public void setStashHelper(StashHelper stashHelper) {
		this.stashHelper = stashHelper;
	}

}
