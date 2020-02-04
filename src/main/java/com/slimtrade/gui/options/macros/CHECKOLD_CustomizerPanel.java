package com.slimtrade.gui.options.macros;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JSlider;

import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.messaging.AbstractMessagePanel;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.options.ContentPanel_REMOVE;
import com.slimtrade.gui.panels.BufferPanel;

public class CHECKOLD_CustomizerPanel extends ContentPanel_REMOVE {

	private static final long serialVersionUID = 1L;

	private JLabel incomingLabel = new JLabel("Incoming Trade");
	private JLabel outgoingLabel = new JLabel("Outgoing Trade");
	
	public CHECKOLD_CustomizerPanel() {
		super(false);
		//TODO : Colors
		Random rng = new Random();
		TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
		TradeOffer tradeOut = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");

		
		AbstractMessagePanel exampleTradeIn = new MessagePanel(tradeIn, new Dimension(400,40));
		AbstractMessagePanel exampleTradeOut = new MessagePanel(tradeOut, new Dimension(400,40));
		exampleTradeIn.stopTimer();
		exampleTradeOut.stopTimer();
		
		//INCOMING PRESETS
		PresetMacroRow callbackInPreset = new PresetMacroRow("Callback", "icons/phone.png");
		callbackInPreset.getRow("Left Mouse", "I'm busy, want me to message you back in a little bit?", true);
		callbackInPreset.getRow("Right Mouse", "Save message to trade history");
		PresetMacroRow waitInPreset = new PresetMacroRow("Wait", "icons/clock1.png");
		waitInPreset.getRow("Left Mouse", "One Sec", true);
		waitInPreset.getRow("Right Mouse", "One Min", true);
		PresetMacroRow refreshInPreset = new PresetMacroRow("Refresh", "icons/refresh1.png", true);
		refreshInPreset.getRow("Left Mouse", "Hi, are you still interested in my [item] listed for [amount]?");
		PresetMacroRow closePreset = new PresetMacroRow("Close", "icons/close.png");
		closePreset.getRow("Left Mouse", "Close this message");
		closePreset.getRow("Right Mouse", "Close all incoming trades with the same item name and price");
		
		PresetMacroRow invitePreset = new PresetMacroRow("Invite", "icons/invite.png", true);
		invitePreset.getRow("Left Mouse", "Invite player to your party");
		PresetMacroRow tradePreset = new PresetMacroRow("Trade", "icons/cart.png", true);
		tradePreset.getRow("Left Mouse", "Trades with a player");
		PresetMacroRow thankPreset = new PresetMacroRow("Thank", "icons/invite.png", true);
		thankPreset.getRow("Left Mouse", "thanks", true);
		PresetMacroRow leavePreset = new PresetMacroRow("Kick", "icons/leave.png", true);
		leavePreset.getRow("Left Mouse", "Kick player from your party");
		
		//OUTGOING PRESETS
		PresetMacroRow refreshOutPreset = new PresetMacroRow("Refresh", "icons/refresh1.png", true);
		refreshOutPreset.getRow("Left Mouse", "Resend trade message");
		PresetMacroRow warpOutPreset = new PresetMacroRow("Refresh", "icons/warp.png", true);
		warpOutPreset.getRow("Left Mouse", "Warp to other player's hideout");
		PresetMacroRow thankOutPreset = new PresetMacroRow("Refresh", "icons/thumb.png", true);
		thankOutPreset.getRow("Left Mouse", "thanks", true);
		PresetMacroRow leaveOutPreset = new PresetMacroRow("Refresh", "icons/leave.png", true);
		leaveOutPreset.getRow("Left Mouse", "Kick yourself from the party");
		PresetMacroRow homeOutPreset = new PresetMacroRow("Refresh", "icons/home.png", true);
		homeOutPreset.getRow("Left Mouse", "Warp home");
		
//		PresetMacroRow invitePreset = new PresetMacroRow("Close", "icons/close.png");
//		closePreset.addRow("Left Mouse", "Close this message");
//		closePreset.addRow("Right Mouse", "Close all incoming trades with the same item name and price");
		
		
		JSlider sizeSlider = new JSlider();
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setMinimum(30);
		sizeSlider.setMaximum(50);
		
		//INCOMING
		gc.insets = new Insets(2,0,0,0);
		addRow(incomingLabel, gc);
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
//		addRow(new CustomMacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		
		//OUTGOING
		addRow(outgoingLabel, gc);
		addRow(exampleTradeOut, gc);
		addRow(refreshOutPreset, gc);
		addRow(warpOutPreset, gc);
		addRow(thankOutPreset, gc);
		addRow(leaveOutPreset, gc);
		addRow(homeOutPreset, gc);
		
		this.autoResize();
	}

}
