package com.slimtrade.gui.options.macros;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Random;

import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.messaging.AbstractMessagePanel;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.options.ContentPanel_REMOVE;
import com.slimtrade.gui.panels.BufferPanel;

public class OutgoingCustomizer extends ContentPanel_REMOVE {

	private static final long serialVersionUID = 1L;
	private MessagePanel exampleTrade;

	public OutgoingCustomizer() {
		// TODO : Colors
		super(false);
		Random rng = new Random();
		TradeOffer tradeOut = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");

		AbstractMessagePanel exampleTradeOut = new MessagePanel(tradeOut, new Dimension(400, 40), false);
		exampleTradeOut.stopTimer();

		// OUTGOING PRESETS
		PresetMacroRow refreshOutPreset = new PresetMacroRow("Refresh", "icons/refresh1.png", true);
		refreshOutPreset.getRow("Left Mouse", "Resend trade message");
		PresetMacroRow warpOutPreset = new PresetMacroRow("Refresh", "icons/warp.png", true);
		warpOutPreset.getRow("Left Mouse", "Warp to other player's hideout");
		PresetMacroRow thankOutPreset = new PresetMacroRow("Refresh", "icons/thumb.png", true);
		thankOutPreset.getRow("Left Mouse", "thanks");
		PresetMacroRow leaveOutPreset = new PresetMacroRow("Refresh", "icons/leave.png", true);
		leaveOutPreset.getRow("Left Mouse", "Kick yourself from the party");
		PresetMacroRow homeOutPreset = new PresetMacroRow("Refresh", "icons/home.png", true);
		homeOutPreset.getRow("Left Mouse", "Warp home");

		gc.insets = new Insets(2, 0, 0, 0);
		addRow(exampleTradeOut, gc);
		addRow(new BufferPanel(0, 10), gc);
		addRow(refreshOutPreset, gc);
		addRow(warpOutPreset, gc);
		addRow(thankOutPreset, gc);
		addRow(leaveOutPreset, gc);
		addRow(homeOutPreset, gc);

		this.autoResize();
	}

}
