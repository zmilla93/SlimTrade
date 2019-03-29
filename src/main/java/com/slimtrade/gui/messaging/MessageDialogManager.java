package main.java.com.slimtrade.gui.messaging;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.components.PanelWrapper;
import main.java.com.slimtrade.gui.enums.ExpandDirection;

public class MessageDialogManager {
	
	private Point anchorPoint = new Point(0, 500);
	private Dimension defaultSize = new Dimension(400, 40);
	private ExpandDirection expandDirection = ExpandDirection.UP;
	
	private final int BUFFER_SIZE = 2;
	private final int MAX_MESSAGE_COUNT = 20;
	private final ArrayList<PanelWrapper> wrapperList = new ArrayList<PanelWrapper>();
	
	public void addMessage(TradeOffer trade){
		//TODO : Check duplicates
		if(wrapperList.size() >= MAX_MESSAGE_COUNT){
			return;
		}
		final MessagePanel panel = new MessagePanel(trade, defaultSize);
		final PanelWrapper wrapper = new PanelWrapper(panel, "SlimTrade Message Window");
		wrapperList.add(wrapper);
		refreshPanelLocations();
		panel.getCloseButton().addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				wrapperList.remove(wrapper);
				wrapper.dispose();
				refreshPanelLocations();
			}
		});
	}
	
	private void refreshPanelLocations(){
		Point targetPoint = new Point(anchorPoint);
		for(PanelWrapper w : wrapperList){
			w.setLocation(targetPoint);
			if(expandDirection == ExpandDirection.DOWN){
				targetPoint.y += w.getHeight() + BUFFER_SIZE;
			}else{
				targetPoint.y -= w.getHeight() + BUFFER_SIZE;
			}
		}
	}
	
	public void setExpandDirection(ExpandDirection dir){
		this.expandDirection = dir;
		refreshPanelLocations();
	}
	
	private boolean isDuplicateTrade(TradeOffer trade) {
		for (PanelWrapper wrapper : wrapperList) {
			MessagePanel msgPanel = (MessagePanel)wrapper.getPanel();
			TradeOffer tradeB = msgPanel.getTrade();
			if(TradeUtility.isDuplicateTrade(trade, tradeB)){
				return true;
			}
		}
		return false;
	}
	
//	private void closeSimilarTrades(int index, boolean deleteCurrent) {
//		TradeOffer tradeA = messages[index].trade;
//		int i = 0;
//		for (AbstractMessagePanel msg : messages) {
//			if (msg != null && msg instanceof MessagePanel) {
//				if (i != index || deleteCurrent) {
//					try {
//						int checkCount = 0;
//						int check = 0;
//						MessagePanel panelA = (MessagePanel) msg;
//						TradeOffer tradeB = panelA.getTrade();
//						if (tradeA.messageType == MessageType.INCOMING_TRADE) {
//							checkCount = 4;
//							if (tradeA.priceType.equals(tradeB.priceType)) {
//								check++;
//							}
//							if (tradeA.priceCount.equals(tradeB.priceCount)) {
//								check++;
//							}
//							if (TradeUtility.cleanItemName(tradeA.itemName).equals(TradeUtility.cleanItemName(tradeB.itemName))) {
//								check++;
//							}
//						} else if (tradeA.messageType == MessageType.OUTGOING_TRADE) {
//							checkCount = 1;
//						}
//						if (tradeA.messageType.equals(tradeB.messageType)) {
//							check++;
//						}
//						if (check == checkCount) {
//							this.removeMessage(i);
//						}
//					} catch (NullPointerException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			i++;
//		}
//	}
	
}
