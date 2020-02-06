package com.slimtrade.gui.options.macros;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.SectionHeader;
import com.slimtrade.gui.buttons.BasicButton;
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
public class IncomingCustomizer extends ContainerPanel implements ISaveable, IColorable {

	private static final long serialVersionUID = 1L;
	private MessagePanel exampleTradeIn;

	private AbstractWindow parent;

	// private JTextField callbackLeft;
	// private JTextField waitLeft;
	// private JTextField waitRight;
	private JTextField thankLeft;
	private JTextField thankRight;

	private AddRemovePanel customPanel;

	private JButton addButton = new BasicButton("Add Macro");
//	private JPanel presetPanel = new JPanel(FrameManager.gridbag);
	private JPanel presetPanel = new JPanel(new GridBagLayout());

	// private ContentPanel customPanel

	// private ArrayList<CustomMacroRow> customRows = new
	// ArrayList<CustomMacroRow>();
//	private CustomMacroRow[] customRows = new CustomMacroRow[20];
//	private int customCount = 0;
	public static final int CUSTOM_MAX = 10;

	public IncomingCustomizer(AbstractWindow parent) {
		// super(false);
//        container.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setVisible(false);
		this.parent = parent;

		//TEMP


		refreshTrade();

		// INCOMING PRESETS
//		PresetMacroRow callbackInPreset = new PresetMacroRow("Save", "icons/phone.png");
//		callbackInPreset.getRow("Right Mouse", "Save message to trade history");
		PresetMacroRow refreshInPreset = new PresetMacroRow("Refresh", "icons/refresh1.png", true);
		refreshInPreset.getRow("Left Mouse", "Hi, are you still interested in my [item] listed for [amount]?");
		PresetMacroRow closePreset = new PresetMacroRow("Close", "icons/close.png");
		closePreset.getRow("Left Mouse", "Close this message");
		closePreset.getRow("Right Mouse", "Close all incoming trades with the same item name and price");

		PresetMacroRow invitePreset = new PresetMacroRow("Invite", "icons/invite.png", true);
		invitePreset.getRow("Left Mouse", "Invite player to your party");
		PresetMacroRow tradePreset = new PresetMacroRow("Trade", "icons/cart.png", true);
		tradePreset.getRow("Left Mouse", "Trades with a player");
		PresetMacroRow thankPreset = new PresetMacroRow("Thank", "icons/thumb.png");
		thankLeft = thankPreset.getRow("Left Mouse", "thanks", true);
		thankRight = thankPreset.getRow("Right Mouse", "", true);
		PresetMacroRow leavePreset = new PresetMacroRow("Kick", "icons/leave.png", true);
		leavePreset.getRow("Left Mouse", "Kick player from your party");

		// INCOMING
		// TODO : insets
//		ToggleButton presetButton = new ToggleButton("Preset Macros");
//		ToggleButton customButton = new ToggleButton("Custom Macros");
		SectionHeader exampleHeader = new SectionHeader("Incoming Trade");
		SectionHeader presetHeader = new SectionHeader("Preset Macros");
        SectionHeader customHeader = new SectionHeader("Custom Macros");

		customPanel = new AddRemovePanel();
		customPanel.setBorder(null);

		// gc.insets = new Insets(2, 0, 0, 0);
		// addRow(incomingLabel, gc);
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		//Preset Macros
        presetPanel.add(refreshInPreset, gc);
        gc.gridy++;
        presetPanel.add(closePreset, gc);
        gc.gridy++;
        presetPanel.add(invitePreset, gc);
        gc.gridy++;
        presetPanel.add(tradePreset, gc);
        gc.gridy++;
        presetPanel.add(thankPreset, gc);
        gc.gridy++;
        presetPanel.add(leavePreset, gc);
        gc.gridy++;

        //Everything
        gc.gridy = 0;
		container.setLayout(new GridBagLayout());
//		gc.insets.bottom = 2;
        gc.insets.bottom = FrameManager.gapSmall;
		container.add(exampleHeader, gc);
        gc.gridy++;
        gc.insets.bottom = FrameManager.gapLarge;
		container.add(exampleTradeIn, gc);
		gc.gridy++;
        gc.insets.bottom = FrameManager.gapSmall;
        container.add(presetHeader, gc);
        gc.gridy++;
        gc.insets.bottom = FrameManager.gapLarge;
		container.add(presetPanel, gc);
		gc.gridy++;

        gc.insets.bottom = FrameManager.gapSmall;
		container.add(customHeader, gc);
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
        App.eventManager.addListener(this);
        this.updateColor();
		// this.autoResize();
	}

	private void refreshTrade() {
		try {
			container.remove(exampleTradeIn);
		} catch (NullPointerException e) {
		}
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets.bottom = FrameManager.gapLarge;
		Random rng = new Random();
		TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "BuyerUsername", "Example Item", 1, "chaos", 20, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
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
		load();
	}

//	public void loadPresets() {
//	    // TODO : add customizer
//		thankLeft.setText("thanks");
//		thankRight.setText("thanks");
//	}

	private void loadFromSave() {

	    // Thank Button
        thankLeft.setText(App.saveManager.saveFile.thankIncomingLMB);
        thankRight.setText(App.saveManager.saveFile.thankIncomingRMB);

        // Custom Macros
	    int i = 0;
	    for(MacroButton macro : App.saveManager.saveFile.incomingMacroButtons) {
            CustomMacroRow row = addNewMacro();
            ButtonRow buttonRow = macro.row;
            PreloadedImageCustom buttonImage = macro.image;
            row.setButtonRow(buttonRow);
            row.setButtonImage(buttonImage);
            row.setTextLMB(macro.leftMouseResponse);
            row.setTextRMB(macro.rightMouseResponse);
	        if(++i >= CUSTOM_MAX){
	            return;
            }
        }
	}

	public void save() {
        // PRESET BUTTONS
        App.saveManager.saveFile.thankIncomingLMB = thankLeft.getText();
        App.saveManager.saveFile.thankIncomingRMB = thankRight.getText();
		// CUSTOM BUTTONS
		App.saveManager.saveFile.incomingMacroButtons.clear();
		int index = 0;
		customPanel.saveChanges();
		for (Component c : customPanel.getComponents()) {
			CustomMacroRow row = (CustomMacroRow) c;
			App.saveManager.saveFile.incomingMacroButtons.add(row.getMacroData());
		}
		// Refresh
        customPanel.saveChanges();
		refreshTrade();
	}

	public void load() {
		customPanel.removeAll();
//		loadPresets();
		loadFromSave();
//		customPanel.saveChanges();
		customPanel.updateColor();
	}

    @Override
    public void updateColor() {
	    this.setBackground(ColorManager.LOW_CONSTRAST_1);
        presetPanel.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
        System.out.println(customPanel.getComponentCount());
//        customPanel.setBorder(null);

//        if(customPanel.getComponentCount() > 0) {
//
//        } else {
//
//        }

    }
}
