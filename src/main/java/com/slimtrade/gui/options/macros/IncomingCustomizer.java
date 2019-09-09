package com.slimtrade.gui.options.macros;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.slimtrade.Main;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImageCustom;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.ToggleButton;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

//TODO : CLEANUP
public class IncomingCustomizer extends ContainerPanel implements ISaveable {
	private static final long serialVersionUID = 1L;
	private MessagePanel exampleTradeIn;

	private AbstractWindow parent;

	// private JTextField callbackLeft;
	// private JTextField waitLeft;
	// private JTextField waitRight;
	private JTextField thankLeft;
	private JTextField thankRight;

	private AddRemovePanel customPanel;

	private JButton addButton = new JButton("Add");

	// private ContentPanel customPanel

	// private ArrayList<CustomMacroRow> customRows = new
	// ArrayList<CustomMacroRow>();
//	private CustomMacroRow[] customRows = new CustomMacroRow[20];
//	private int customCount = 0;
	public static final int CUSTOM_MAX = 10;

	public IncomingCustomizer(AbstractWindow parent) {
		// super(false);
		this.setVisible(false);
		this.parent = parent;

		refreshTrade();

		// INCOMING PRESETS
		PresetMacroRow callbackInPreset = new PresetMacroRow("Save", "icons/phone.png");
		callbackInPreset.getRow("Right Mouse", "Save message to trade history");
		PresetMacroRow refreshInPreset = new PresetMacroRow("Refresh", "icons/refresh1.png", true);
		refreshInPreset.getRow("Left Mouse", "Hi, are you still interested in my [item] listed for [amount]?");
		PresetMacroRow closePreset = new PresetMacroRow("Close", "icons/close.png");
		closePreset.getRow("Left Mouse", "Close this message");
		closePreset.getRow("Right Mouse", "Close all incoming trades with the same item name and price");

		PresetMacroRow invitePreset = new PresetMacroRow("Invite", "icons/invite.png", true);
		invitePreset.getRow("Left Mouse", "Invite player to your party");
		PresetMacroRow tradePreset = new PresetMacroRow("Trade", "icons/cart.png", true);
		tradePreset.getRow("Left Mouse", "Trades with a player");
		PresetMacroRow thankPreset = new PresetMacroRow("Thank", "icons/thumb1.png");
		thankLeft = thankPreset.getRow("Left Mouse", "thanks");
		thankRight = thankPreset.getRow("Right Mouse", "");
		PresetMacroRow leavePreset = new PresetMacroRow("Kick", "icons/leave.png", true);
		leavePreset.getRow("Left Mouse", "Kick player from your party");

		// INCOMING
		// TODO : insets
		ToggleButton presetButton = new ToggleButton("Preset Macros");
		ToggleButton customButton = new ToggleButton("Custom Macros");

		customPanel = new AddRemovePanel();
		customPanel.setBorder(null);

		// gc.insets = new Insets(2, 0, 0, 0);
		// addRow(incomingLabel, gc);
		GridBagConstraints gc = new GridBagConstraints();
		container.setLayout(new GridBagLayout());
		gc.insets.bottom = 2;
		container.add(exampleTradeIn, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, 10), gc);
		gc.gridy++;
		container.add(presetButton, gc);
		gc.gridy++;
		container.add(callbackInPreset, gc);
		gc.gridy++;
		container.add(refreshInPreset, gc);
		gc.gridy++;
		container.add(closePreset, gc);
		gc.gridy++;
		container.add(invitePreset, gc);
		gc.gridy++;
		container.add(tradePreset, gc);
		gc.gridy++;
		container.add(thankPreset, gc);
		gc.gridy++;
		container.add(leavePreset, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, 10), gc);
		gc.gridy++;
		container.add(customButton, gc);
		gc.gridy++;
		container.add(addButton, gc);
		gc.gridy++;
		container.add(customPanel, gc);
		gc.insets.bottom = 2;
		gc.gridx = 0;
		gc.gridy = 0;
		// customPanel.autoResize();

		

		// IncomingCustomizer local = this;
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewMacro();
			}
		});
		load();
		// this.autoResize();
	}

	private void refreshTrade() {
		try {
			container.remove(exampleTradeIn);
		} catch (NullPointerException e) {
		}
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		Random rng = new Random();
		TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
		exampleTradeIn = new MessagePanel(tradeIn, new Dimension(400, 40), false);
		exampleTradeIn.stopTimer();
		container.add(exampleTradeIn, gc);
		parent.revalidate();
		parent.repaint();
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
		CustomMacroRow row = new CustomMacroRow(customPanel);
		customPanel.addPanel(row);
		return row;
	}

	public void revertChanges() {
		customPanel.revertChanges();
	}

	public void loadPresets() {
		thankLeft.setText(Main.saveManager.getString("macros", "in", "preset", "thank", "left"));
		thankRight.setText(Main.saveManager.getString("macros", "in", "preset", "thank", "right"));
	}

	private void loadFromSave() {
		for (int i = 0; i < CUSTOM_MAX; i++) {
			if (Main.saveManager.hasEntry("macros", "in", "custom", "button" + i)) {
				CustomMacroRow row = addNewMacro();
				ButtonRow buttonRow = ButtonRow.valueOf(Main.saveManager.getString("macros", "in", "custom", "button" + i, "row"));
				PreloadedImageCustom buttonImage = PreloadedImageCustom.valueOf(Main.saveManager.getString("macros", "in", "custom", "button" + i, "image"));
				row.setButtonRow(buttonRow);
				row.setButtonImage(buttonImage);
				row.setTextLMB(Main.saveManager.getString("macros", "in", "custom", "button" + i, "left"));
				row.setTextRMB(Main.saveManager.getString("macros", "in", "custom", "button" + i, "right"));
			} else {
				return;
			}
		}
		
	}

	public void save() {
		// CUSTOM BUTTONS
		Main.saveManager.deleteObject("macros", "in", "custom");
		int index = 0;
		customPanel.saveChanges();
		for (Component c : customPanel.getComponents()) {
			CustomMacroRow p = (CustomMacroRow) c;
			Main.saveManager.putObject(p.getButtonImage().name(), "macros", "in", "custom", "button" + index, "image");
			Main.saveManager.putObject(p.getButtonRow().name(), "macros", "in", "custom", "button" + index, "row");
			Main.saveManager.putObject(p.getTextLMB(), "macros", "in", "custom", "button" + index, "left");
			Main.saveManager.putObject(p.getTextRMB(), "macros", "in", "custom", "button" + index, "right");
			index++;
		}
		Main.saveManager.putObject(index, "macros", "in", "custom", "count");
		// PRESET BUTTONS
		Main.saveManager.putObject(thankLeft.getText(), "macros", "in", "preset", "thank", "left");
		Main.saveManager.putObject(thankRight.getText(), "macros", "in", "preset", "thank", "right");
		refreshTrade();
		customPanel.saveChanges();
	}

	public void load() {
		customPanel.removeAll();
		loadPresets();
		loadFromSave();
		customPanel.saveChanges();
	}

}
