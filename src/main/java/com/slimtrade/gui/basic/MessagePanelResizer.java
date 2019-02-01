package main.java.com.slimtrade.gui.basic;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.datatypes.MessageType;
import main.java.com.slimtrade.gui.panels.MessagePanel;

public class MessagePanelResizer extends ResizableDialog {

	private static final long serialVersionUID = 1L;

	private int buffer = 20;
	
	public MessagePanelResizer(){
		minWidth = MessagePanel.totalWidth + borderSize*2 + buffer*2;
		minHeight = MessagePanel.totalHeight + borderSize*2 + buffer*2;
		TradeOffer dummyTrade = new TradeOffer("", MessageType.INCOMING_TRADE, "Player Name", "Item Name", 123, "ex", 5.6);
		MessagePanel dummyMsg = new MessagePanel(dummyTrade);
		centerPanel.add(dummyMsg);
		this.setSize(minWidth, minHeight);
	}
	
}
