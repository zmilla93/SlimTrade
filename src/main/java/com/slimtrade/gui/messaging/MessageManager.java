package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.enums.ExpandDirection;

//TODO : Could reuse panels instead of creating/destroying constantly, especially rigid areas
public class MessageManager extends BasicDialog {

	private static final long serialVersionUID = 1L;

	public static final int buffer = 1;
	private final int MAX_MESSAGES = 20;
	private int messageCount = 0;
	private AbstractMessagePanel[] messages = new AbstractMessagePanel[MAX_MESSAGES];
	private Component[] rigidAreas = new Component[MAX_MESSAGES];
	private ArrayList<TradeOffer> trades = new ArrayList<TradeOffer>();
	
	private Container container;
	
	private Point startingPos;
	private ExpandDirection expandDirection;

	public MessageManager() {
		this.setBounds(1220, 0, 0, 0);
		this.setBackground(ColorManager.CLEAR);
		container = this.getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		this.expandDirection = ExpandDirection.valueOf(Main.saveManager.getEnumValue(ExpandDirection.class, "overlayManager", "messageManager", "expandDirection"));
	}

	// TODO : Clean up stash helper removal
	public void addMessage(TradeOffer trade) {
		if (messageCount == MAX_MESSAGES) {
			return;
		}
		int i = 0;
		while (messages[i] != null) {
			i++;
		}
		messages[i] = new TradePanelA(trade, new Dimension(400, 40));
		rigidAreas[i] = Box.createRigidArea(new Dimension(1, buffer));
		final int closeIndex = i;
		messages[i].getCloseButton().addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					removeMessage(closeIndex);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					if(messages[closeIndex].trade.messageType == MessageType.INCOMING_TRADE){
						closeSimilarTrades(closeIndex, true);
					}else if(messages[closeIndex].trade.messageType == MessageType.OUTGOING_TRADE){
						closeSimilarTrades(closeIndex, false);
					}					
					// closeOtherOutgoing(closeIndex);
				}

			}
		});
		boolean close = Main.saveManager.getBool("general", "closeOnKick");
		if (close && messages[i].getKickLeaveButton() != null) {
			messages[i].getKickLeaveButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					removeMessage(closeIndex);
				}
			});
		}
		if(expandDirection == ExpandDirection.UP){
			container.add(messages[i], 0);
			container.add(rigidAreas[i], 0);
		}else{
			container.add(messages[i]);
			container.add(rigidAreas[i]);
		}
		
		messageCount++;
		refresh();
		FrameManager.forceAllToTop();
	}

	private void removeMessage(int i) {
		if (messages[i].getMessageType() == MessageType.INCOMING_TRADE) {
			// messages[i].stashHelper.highlighterTimer.stop();
			// messages[i].stashHelper.itemHighlighter.destroy();
			TradePanelA t = (TradePanelA) messages[i];
			if (t.getStashHelper() != null) {
				FrameManager.stashHelperContainer.remove(t.getStashHelper());
				FrameManager.stashHelperContainer.refresh();
			}
		}
		container.remove(messages[i]);
		container.remove(rigidAreas[i]);
		messages[i] = null;
		rigidAreas[i] = null;
		messageCount--;
		refresh();
	}

	private void closeSimilarTrades(int index, boolean deleteCurrent) {
		TradeOffer tradeA = messages[index].trade;
		int i = 0;
		for (AbstractMessagePanel msg : messages) {
			if (msg != null && msg instanceof TradePanelA) {
				if (i != index || deleteCurrent) {
					TradePanelA p = (TradePanelA) msg;
					TradeOffer tradeB = p.getTrade();
					final int checkCount = 5;
					int check = 0;
					if (tradeA.messageType.equals(tradeB.messageType)) {
						check++;
					}
					if (tradeA.itemName.equals(tradeB.itemName)) {
						check++;
					}
					if (tradeA.itemCount.equals(tradeB.itemCount)) {
						check++;
					}
					if (tradeA.priceTypeString.equals(tradeB.priceTypeString)) {
						check++;
					}
					if (tradeA.priceCount.equals(tradeB.priceCount)) {
						check++;
					}
					if (check == checkCount) {
						this.removeMessage(i);
					}
				}

			}
			i++;
		}
	}
	
	public void setExpandDirection(ExpandDirection direction){
		this.expandDirection = direction;
		container.removeAll();
		if(direction == ExpandDirection.UP){
			for(int i = 0;i<MAX_MESSAGES;i++){
				if(messages[i]!=null){
					container.add(messages[i], 0);
					container.add(rigidAreas[i], 0);
				}
			}
		}else{
			for(int i = 0;i<MAX_MESSAGES;i++){
				if(messages[i]!=null){
					container.add(messages[i]);
					container.add(rigidAreas[i]);
				}
			}
		}
		refresh();
	}	

	// TODO : Move resize to another function to be more consistent with refresh
	// function?
	public void refresh() {
		if(expandDirection == ExpandDirection.UP){
			this.setLocation(startingPos.x, startingPos.y-MessagePanel.totalHeight*(messageCount-1)-buffer*(messageCount-1));
		}else{
			
		}
		this.setSize(MessagePanel.totalWidth, MessagePanel.totalHeight * messageCount + buffer * messageCount);
		this.revalidate();
		this.repaint();
	}

	public boolean isDuplicateTrade(TradeOffer trade) {
		for (AbstractMessagePanel msg : messages) {
			if (msg != null && msg instanceof TradePanelA) {
				TradePanelA t = (TradePanelA) msg;
				if (TradeUtility.isDuplicateTrade(t.getTrade(), trade)) {
					return true;
				}
			}
		}
		return false;
	}

	public void updateLocation() {
		int x = Main.saveManager.getInt("overlayManager", "messageManager", "x");
		int y = Main.saveManager.getInt("overlayManager", "messageManager", "y");
		this.startingPos = new Point(x, y);
		this.setLocation(x, y);
	}

}
