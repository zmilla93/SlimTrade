package main.java.com.slimtrade.gui.options.customizer;

import java.awt.Insets;
import java.util.Random;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.messaging.TradePanelA;
import main.java.com.slimtrade.gui.options.AbstractContentPanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class IncomingCustomizer extends AbstractContentPanel {
	
	private static final long serialVersionUID = 1L;
	private TradePanelA exampleTradeIn;

	public IncomingCustomizer(){
		Random rng = new Random();
		TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
		exampleTradeIn = new TradePanelA(tradeIn, 40);
		exampleTradeIn.stopTimer();
		//INCOMING PRESETS
		PresetMacroRow callbackInPreset = new PresetMacroRow("Callback", "/resources/icons/phone.png");
		callbackInPreset.getRow("Left Mouse", "I'm busy, want me to message you back in a little bit?", true);
		callbackInPreset.getRow("Right Mouse", "Save message to trade history");
		PresetMacroRow waitInPreset = new PresetMacroRow("Wait", "/resources/icons/clock1.png");
		waitInPreset.getRow("Left Mouse", "One Sec", true);
		waitInPreset.getRow("Right Mouse", "One Min", true);
		PresetMacroRow refreshInPreset = new PresetMacroRow("Refresh", "/resources/icons/refresh1.png", true);
		refreshInPreset.getRow("Left Mouse", "Hi, are you still interested in my [item] listed for [amount]?");
		PresetMacroRow closePreset = new PresetMacroRow("Close", "/resources/icons/close.png");
		closePreset.getRow("Left Mouse", "Close this message");
		closePreset.getRow("Right Mouse", "Close all incoming trades with the same item name and price");
		
		PresetMacroRow invitePreset = new PresetMacroRow("Invite", "/resources/icons/invite.png", true);
		invitePreset.getRow("Left Mouse", "Invite player to your party");
		PresetMacroRow tradePreset = new PresetMacroRow("Trade", "/resources/icons/cart.png", true);
		tradePreset.getRow("Left Mouse", "Trades with a player");
		PresetMacroRow thankPreset = new PresetMacroRow("Thank", "/resources/icons/invite.png", true);
		thankPreset.getRow("Left Mouse", "thanks", true);
		PresetMacroRow leavePreset = new PresetMacroRow("Kick", "/resources/icons/leave.png", true);
		leavePreset.getRow("Left Mouse", "Kick player from your party");
		
		//INCOMING
		gc.insets = new Insets(2,0,0,0);
//		addRow(incomingLabel, gc);
		addRow(exampleTradeIn, gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(callbackInPreset, gc);
		addRow(waitInPreset, gc);
		addRow(refreshInPreset, gc);
		addRow(closePreset, gc);
		addRow(invitePreset, gc);
		addRow(tradePreset, gc);
		addRow(thankPreset, gc);
		addRow(leavePreset, gc);

		addRow(new BufferPanel(0,10), gc);
		addRow(new CustomMacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		
		this.autoResize();
	}
	
	
}
