package main.java.com.slimtrade.gui.options.customizer;

import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JSlider;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.messaging.AbstractMessagePanel;
import main.java.com.slimtrade.gui.messaging.TradePanelA;
import main.java.com.slimtrade.gui.options.AbstractContentPanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class MessageCustomizerPanel extends AbstractContentPanel {

	private static final long serialVersionUID = 1L;

	private JLabel incomingLabel = new JLabel("Incoming Trade");
	private JLabel outgoingLabel = new JLabel("Outgoing Trade");
	
	public MessageCustomizerPanel() {
		
		//TODO : Colors
		Random rng = new Random();
		TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
		TradeOffer tradeOut = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");

		
		AbstractMessagePanel exampleTradeIn = new TradePanelA(tradeIn, 40);
		AbstractMessagePanel exampleTradeOut = new TradePanelA(tradeOut, 40);
		exampleTradeIn.stopTimer();
		exampleTradeOut.stopTimer();
		
		PresetMacroRow callbackInPreset = new PresetMacroRow("Callback", "/resources/icons/phone.png");
		callbackInPreset.addEditRow("Left Mouse", "I'm busy, want me to message you back in a little bit?");
		callbackInPreset.addRow("Right Mouse", "Save message to trade history");
		PresetMacroRow waiInPreset = new PresetMacroRow("Wait", "/resources/icons/clock1.png");
		waiInPreset.addEditLMB("Left Mouse", "One Sec");
		waiInPreset.addEditRMB("Right Mouse", "One Min");
		PresetMacroRow refreshInPreset = new PresetMacroRow("Refresh", "/resources/icons/refresh1.png", true);
		refreshInPreset.addRow("Left Mouse", "Hi, are you still interested in my [item] listed for [amount]?");
		PresetMacroRow closePreset = new PresetMacroRow("Close", "/resources/icons/close.png");
		closePreset.addRow("Left Mouse", "Close this message");
		closePreset.addRow("Right Mouse", "Close all incoming trades with the same item name and price");
		
		PresetMacroRow invitePreset = new PresetMacroRow("Invite", "/resources/icons/invite.png", true);
		invitePreset.addRow("Left Mouse", "Invite player to your party");
		PresetMacroRow tradePreset = new PresetMacroRow("Trade", "/resources/icons/cart.png", true);
		tradePreset.addRow("Left Mouse", "Trades with a player");
		PresetMacroRow thankPreset = new PresetMacroRow("Thank", "/resources/icons/invite.png", true);
		thankPreset.addEditLMB("Left Mouse", "thanks");
		PresetMacroRow leavePreset = new PresetMacroRow("Kick", "/resources/icons/leave.png", true);
		leavePreset.addRow("Left Mouse", "Kick player from your party");
		
//		PresetMacroRow invitePreset = new PresetMacroRow("Close", "/resources/icons/close.png");
//		closePreset.addRow("Left Mouse", "Close this message");
//		closePreset.addRow("Right Mouse", "Close all incoming trades with the same item name and price");
		
		
		JSlider sizeSlider = new JSlider();
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setMinimum(30);
		sizeSlider.setMaximum(50);
		
		int buffer = 8;
		addRow(incomingLabel, gc);
		addRow(exampleTradeIn, gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(callbackInPreset, gc);addRow(new BufferPanel(0,buffer), gc);
		addRow(waiInPreset, gc);addRow(new BufferPanel(0,buffer), gc);
		addRow(refreshInPreset, gc);addRow(new BufferPanel(0,buffer), gc);
		addRow(closePreset, gc);addRow(new BufferPanel(0,buffer), gc);
		addRow(invitePreset, gc);addRow(new BufferPanel(0,buffer), gc);
		addRow(tradePreset, gc);addRow(new BufferPanel(0,buffer), gc);
		addRow(thankPreset, gc);addRow(new BufferPanel(0,buffer), gc);
		addRow(leavePreset, gc);addRow(new BufferPanel(0,buffer), gc);
		
		addRow(new BufferPanel(0,10), gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		
		
		addRow(outgoingLabel, gc);
		addRow(exampleTradeOut, gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		
		this.autoResize();
//		addRow(sizeSlider, gc);
//		sizeSlider.addChangeListener(new ChangeListener(){
//			public void stateChanged(ChangeEvent arg0) {
//				exampleTrade.resizeMessage(sizeSlider.getValue());
//				System.out.println(sizeSlider.getValue());
//			}
//		});
	}

}
