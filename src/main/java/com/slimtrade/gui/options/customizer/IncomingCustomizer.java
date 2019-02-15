package main.java.com.slimtrade.gui.options.customizer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;
import main.java.com.slimtrade.gui.messaging.TradePanelA;
import main.java.com.slimtrade.gui.options.ContentPanel;
import main.java.com.slimtrade.gui.options.RemovablePanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

//TODO : CLEANUP
public class IncomingCustomizer extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private TradePanelA exampleTradeIn;

	private AbstractWindowDialog parent;
	
	private JTextField callbackLeft;
	private JTextField waitLeft;
	private JTextField waitRight;
	private JTextField thankLeft;
	private JTextField thankRight;

	private ContentPanel customPanel;

	private JButton addButton = new JButton("Add");

	// private ContentPanel customPanel

	// private ArrayList<CustomMacroRow> customRows = new
	// ArrayList<CustomMacroRow>();
	private CustomMacroRow[] customRows = new CustomMacroRow[20];
	private int customCount = 0;
	private final int CUSTOM_MAX = 5;

	public IncomingCustomizer(AbstractWindowDialog parent) {
		this.parent = parent;
		Random rng = new Random();
		TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
		exampleTradeIn = new TradePanelA(tradeIn, new Dimension(400,40));
		exampleTradeIn.stopTimer();
		// INCOMING PRESETS
		PresetMacroRow callbackInPreset = new PresetMacroRow("Callback", "/resources/icons/phone.png");
		callbackLeft = callbackInPreset.getRow("Left Mouse", "I'm busy, want me to message you back in a little bit?!", true);
		callbackInPreset.getRow("Right Mouse", "Save message to trade history");
		PresetMacroRow waitInPreset = new PresetMacroRow("Wait", "/resources/icons/clock1.png");
		waitLeft = waitInPreset.getRow("Left Mouse", "One Sec", true);
		waitRight = waitInPreset.getRow("Right Mouse", "One Min", true);
		PresetMacroRow refreshInPreset = new PresetMacroRow("Refresh", "/resources/icons/refresh1.png", true);
		refreshInPreset.getRow("Left Mouse", "Hi, are you still interested in my [item] listed for [amount]?");
		PresetMacroRow closePreset = new PresetMacroRow("Close", "/resources/icons/close.png");
		closePreset.getRow("Left Mouse", "Close this message");
		closePreset.getRow("Right Mouse", "Close all incoming trades with the same item name and price");

		PresetMacroRow invitePreset = new PresetMacroRow("Invite", "/resources/icons/invite.png", true);
		invitePreset.getRow("Left Mouse", "Invite player to your party");
		PresetMacroRow tradePreset = new PresetMacroRow("Trade", "/resources/icons/cart.png", true);
		tradePreset.getRow("Left Mouse", "Trades with a player");
		PresetMacroRow thankPreset = new PresetMacroRow("Thank", "/resources/icons/thumb1.png");
		thankLeft = thankPreset.getRow("Left Mouse", "thanks", true);
		thankRight = thankPreset.getRow("Right Mouse", "", true);
		PresetMacroRow leavePreset = new PresetMacroRow("Kick", "/resources/icons/leave.png", true);
		leavePreset.getRow("Left Mouse", "Kick player from your party");

		// INCOMING
		// TODO : insets
		gc.insets = new Insets(2, 0, 0, 0);
		// addRow(incomingLabel, gc);
		addRow(exampleTradeIn, gc);
		addRow(new BufferPanel(0, 10), gc);
		addRow(new JLabel("PRESET MACROS"), gc);
		addRow(callbackInPreset, gc);
		addRow(waitInPreset, gc);
		addRow(refreshInPreset, gc);
		addRow(closePreset, gc);
		addRow(invitePreset, gc);
		addRow(tradePreset, gc);
		addRow(thankPreset, gc);
		addRow(leavePreset, gc);

		addRow(new BufferPanel(0, 10), gc);
		addRow(new JLabel("CUSTOM MACROS"), gc);
		customPanel = new ContentPanel(true);
		customPanel.setBorder(null);
		customPanel.setBuffer(0, 0);

		// customPanel.addRow(new CustomMacroRow(), gc);
		addRow(addButton, gc);
		addRow(customPanel, gc);
		gc.insets = new Insets(0, 0, 5, 0);
		gc.gridx = 0;
		gc.gridy = 0;
		customPanel.autoResize();

		loadPresets();
		loadFromSave();

		IncomingCustomizer local = this;
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewMacro();
				parent.autoReisize();
			}
		});

		this.autoResize();
	}

	// TODO : Need to sort out limiting...
	private CustomMacroRow addNewMacro() {
		int i = 0;
		for (Component c : customPanel.getComponents()) {
			if (c.isVisible()) {
				i++;
			}
		}
		if (i == CUSTOM_MAX) {
			return null;
		}
		CustomMacroRow row = new CustomMacroRow();
		// int i = 0;
		// for (i = 0; i < CUSTOM_MAX; i++) {
		// if (customRows[i] == null) {
		// break;
		// }
		// if (i == CUSTOM_MAX-1) {
		// return null;
		// }
		// }
		// customRows[i] = row;
		customPanel.addRow(row, gc);
		customPanel.autoResize();
		this.autoResize();
		// int i = i;
		ContentPanel local = this;
		row.getDeleteButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				row.setVisible(false);
				row.setDelete(true);
				customPanel.autoResize();
				local.autoResize();
				customCount--;
				parent.autoReisize();
			}
		});
		customCount++;
		return row;
		// customText
	}

	public void loadFromSave() {
		System.out.println("Loading...");
		for (int i = 0; i < CUSTOM_MAX; i++) {
			System.out.println("ENTRY!");
			if (Main.saveManager.hasEntry("macros", "in", "custom", "button" + i)) {
				CustomMacroRow row = addNewMacro();
				row.setFresh(false);
				row.setTextLMB(Main.saveManager.getString("macros", "in", "custom", "button" + i, "left"));
				row.setTextRMB(Main.saveManager.getString("macros", "in", "custom", "button" + i, "right"));
			} else {
				return;
			}
		}
	}

	public void reset() {
		for (Component c : customPanel.getComponents()) {
			RemovablePanel p = (RemovablePanel) c;
			if (p != null) {
				if (p.isFresh()) {
					customPanel.remove(p);
					customCount--;
					p = null;
				} else if (p.isDelete()) {
					p.setVisible(true);
					p.setDelete(false);
				}
			}
		}
		customPanel.autoResize();
		this.autoResize();
	}

	// TODO : Order
	public void saveData() {
		//CUSTOM BUTTONS
		Main.saveManager.deleteArray("macros", "in", "custom");
		int index = 0;
		for (Component c : customPanel.getComponents()) {
			CustomMacroRow p = (CustomMacroRow) c;
			if (p.isDelete()) {
				customPanel.remove(p);
				// customRows[i] = null;
				c = null;
				customCount--;
			} else {
				p.setFresh(false);
				Main.saveManager.putString(p.getTextLMB(), "macros", "in", "custom", "button" + index, "left");
				Main.saveManager.putString(p.getTextRMB(), "macros", "in", "custom", "button" + index, "right");
				index++;
			}
		}
		Main.saveManager.putInt(index, "macros", "in", "custom", "count");

		//PRESET BUTTONS
		Main.saveManager.putString(callbackLeft.getText(), "macros", "in", "preset", "callback", "left");
		Main.saveManager.putString(waitLeft.getText(), "macros", "in", "preset", "wait", "left");
		Main.saveManager.putString(waitRight.getText(), "macros", "in", "preset", "wait", "right");
		Main.saveManager.putString(thankLeft.getText(), "macros", "in", "preset", "thank", "left");
		Main.saveManager.putString(thankRight.getText(), "macros", "in", "preset", "thank", "right");
	}

	public void loadPresets() {
		callbackLeft.setText(Main.saveManager.getString("macros", "in", "preset", "callback", "left"));
		waitLeft.setText(Main.saveManager.getString("macros", "in", "preset", "wait", "left"));
		waitRight.setText(Main.saveManager.getString("macros", "in", "preset", "wait", "right"));
		thankLeft.setText(Main.saveManager.getString("macros", "in", "preset", "thank", "left"));
		thankRight.setText(Main.saveManager.getString("macros", "in", "preset", "thank", "right"));
	}

	private void addCustom() {

	}

}
