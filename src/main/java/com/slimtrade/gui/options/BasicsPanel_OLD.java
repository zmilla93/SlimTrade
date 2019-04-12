package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.observing.REDO_MacroEventManager;
import main.java.com.slimtrade.enums.DateStyle;
import main.java.com.slimtrade.enums.TimeStyle;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.options.general.AdvancedPanel;
import main.java.com.slimtrade.gui.options.general.AudioPanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class BasicsPanel_OLD extends ContentPanel_REMOVE implements ISaveable {

	private static final long serialVersionUID = 1L;
	private int bufferX = 30;
	private int bufferY = 5;
	
	JTextField characterInput = new JTextField();
	private JCheckBox guildCheckbox = new JCheckBox();
	private JCheckBox kickCheckbox = new JCheckBox();
	JComboBox<TimeStyle> timeCombo = new JComboBox<TimeStyle>();
	JComboBox<DateStyle> dateCombo = new JComboBox<DateStyle>();
	JComboBox<OrderType> orderCombo = new JComboBox<OrderType>();
	JSpinner countSpinner;

	AudioPanel audioPanel = new AudioPanel();
	AdvancedPanel advancedPanel = new AdvancedPanel();
	
	BasicsPanel_OLD() {
		super(false);
		// TODO : Cleaup panels
		// this.setBuffer(0, 0);
		this.setLayout(new GridBagLayout());
		JPanel generalPanel = new JPanel(new GridBagLayout());
		JPanel historyPanel = new JPanel(new GridBagLayout());
		JPanel audioOuter = new JPanel(new GridBagLayout());

		generalPanel.setBackground(Color.CYAN);
		historyPanel.setBackground(Color.orange);
		audioOuter.setBackground(Color.blue);

		JPanel firstPanel = new JPanel(new GridBagLayout());
		JPanel secondPanel = new JPanel(new GridBagLayout());
		JPanel outerPanel = new JPanel(new GridBagLayout());

		// GENERAL
		JLabel characterLabel = new JLabel("Character");
		

		ToggleButton generalButton = new ToggleButton("Basics");
		ToggleButton historyButton = new ToggleButton("History");
		ToggleButton audioButton = new ToggleButton("Audio");
		ToggleButton advancedButton = new ToggleButton("Save Path");

		generalButton.active = true;
		historyButton.active = true;
		audioButton.active = true;
		
		// GENERAL
		JLabel stashLabel = new JLabel("Stash Location");
		JLabel overlayLabel = new JLabel("Overlay Layout");
		JLabel guildLabel = new JLabel("Show Guild Name");
		JLabel kickLabel = new JLabel("Close on Kick");

		// HISTORY
		JLabel timeLabel = new JLabel("Time Format");
		JLabel dateLabel = new JLabel("Date Format");
		JLabel orderLabel = new JLabel("Order");
		JLabel countlabel = new JLabel("Message Count");
		
		
		JLabel audioLabel = new JLabel("Audio");
		JLabel advancedLabel = new JLabel("Advanced");

		
		
		// General Buttons
		JButton stashButton = new JButton("Edit");
		stashButton.setFocusable(false);
		JButton overlayButton = new JButton("Edit");
		overlayButton.setFocusable(false);

		guildCheckbox.setFocusable(false);

		kickCheckbox.setFocusable(false);
		

		// History Buttons

		timeCombo.setFocusable(false);
		dateCombo.setFocusable(false);
		orderCombo.setFocusable(false);
		for (TimeStyle s : TimeStyle.values()) {
			timeCombo.addItem(s);
		}
		
		for (DateStyle s : DateStyle.values()) {
			dateCombo.addItem(s);
		}
		for (OrderType t : OrderType.values()) {
			orderCombo.addItem(t);
		}
		// Message Count
		SpinnerModel spinnerModel = new SpinnerNumberModel(50, 0, 100, 10);
		countSpinner = new JSpinner(spinnerModel);
		((DefaultEditor) countSpinner.getEditor()).getTextField().setEditable(false);
		countSpinner.setFocusable(false);

		// General Label
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;

		generalPanel.add(characterLabel, gc);
		gc.gridx=1;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		generalPanel.add(characterInput, gc);
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy++;
		
		generalPanel.add(stashLabel, gc);
		gc.gridx = 1;
		generalPanel.add(new BufferPanel(50, 0), gc);
		gc.gridx = 2;
		// gc.fill = gc.BOTH;
		generalPanel.add(stashButton, gc);
		gc.gridx = 0;

		gc.gridy++;
		generalPanel.add(overlayLabel, gc);
		gc.gridx = 2;
		generalPanel.add(overlayButton, gc);
		gc.gridx = 0;
		gc.gridy++;
		generalPanel.add(guildLabel, gc);
		gc.gridx = 2;
		generalPanel.add(guildCheckbox, gc);
		gc.gridx = 0;
		gc.gridy++;
		generalPanel.add(kickLabel, gc);
		gc.gridx = 2;
		generalPanel.add(kickCheckbox, gc);

		
		
		//History
		
		// gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		// gc.fill = GridBagConstraints.BOTH;
		historyPanel.add(timeLabel, gc);
		gc.insets.right = 0;
		gc.gridx = 1;
		historyPanel.add(new BufferPanel(50, 0), gc);
		gc.gridx = 2;
		historyPanel.add(timeCombo, gc);
		gc.gridx = 0;
		gc.gridy++;
		
		historyPanel.add(dateLabel, gc);
		gc.gridx = 2;
		historyPanel.add(dateCombo, gc);
		gc.gridx = 0;
		gc.gridy++;

		historyPanel.add(orderLabel, gc);
		gc.gridx = 2;
		historyPanel.add(orderCombo, gc);
		gc.gridx = 0;
		gc.gridy++;
		
		historyPanel.add(countlabel, gc);
		gc.gridx = 2;
		gc.gridwidth = 1;
		historyPanel.add(countSpinner, gc);
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy++;
		// gc.fill = GridBagConstraints.NONE;
		// firstPanel.add(new BufferPanel(0, bufferY * 2), gc);
		// gc.gridy++;
		//
		// AUDIO
		gc.gridwidth = 4;
		gc.insets.top = 20;
		gc.insets.bottom = 5;
		firstPanel.add(audioLabel, gc);
		// gc.fill = gc.BOTH;
		gc.insets.top = 0;
		gc.insets.bottom = 5;
		// gc.gridwidth=1;
		gc.gridx = 0;
		gc.gridy++;
		
//		audioPanel.setVisible(true);
//		audioPanel.autoResize();
		audioOuter.add(new JLabel("Audio"), gc);
		// firstPanel.add(audioPanel, gc);
		gc.gridwidth = 1;
		gc.insets.top = 0;
		gc.insets.bottom = 5;
		gc.gridx = 0;
		gc.gridy++;

		// HISTORY
		gc.gridwidth = 4;
		gc.insets.top = 20;
		gc.insets.bottom = 5;
		firstPanel.add(advancedLabel, gc);
		gc.insets.top = 0;
		gc.insets.bottom = 5;
		// gc.gridwidth=1;
		gc.gridx = 0;
		gc.gridy++;
		
		
		// adv.setVisible(false);
		firstPanel.add(advancedPanel, gc);

		gc.gridx = 0;
		gc.gridy++;

		// this.addRow(c, gc);
		// outerPanel.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();

		int bufferTop = 10;
		int bufferBottom = 25;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets.bottom = bufferTop;


//		this.repaint();

		gc.insets.top = bufferTop;
		this.add(generalButton, gc);
		gc.insets.top = 0;
		gc.gridy++;
		gc.insets.bottom = bufferBottom;
		this.add(generalPanel, gc);
		gc.insets.bottom = bufferTop;
		gc.gridy++;
		this.add(historyButton, gc);
		gc.gridy++;
		gc.insets.bottom = bufferBottom;
		this.add(historyPanel, gc);
		gc.insets.bottom = bufferTop;
		gc.gridy++;
		this.add(audioButton, gc);
		gc.gridy++;
		gc.insets.bottom = bufferBottom;
		this.add(audioPanel, gc);
		gc.insets.bottom = bufferTop;
		gc.gridy++;
		this.add(advancedButton, gc);
		gc.gridy++;
		gc.insets.bottom = bufferBottom;
		this.add(advancedPanel, gc);
		gc.insets.bottom = 5;

		link(generalButton, generalPanel);
		link(historyButton, historyPanel);
		link(audioButton, audioPanel);
		link(advancedButton, advancedPanel);

		stashButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameManager.hideAllFrames();
				FrameManager.stashOverlayWindow.setShow(true);
			}
		});

		overlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameManager.hideAllFrames();
				FrameManager.overlayManager.showDialog();
			}
		});

		this.load();
		this.repaint();
	}

	private void link(ToggleButton button, JPanel panel) {
		JPanel local = this;
		button.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (button.active) {
					panel.setVisible(true);
				} else {
					panel.setVisible(false);
				}
				local.setPreferredSize(null);
				local.setPreferredSize(local.getPreferredSize());
			}
		});
	}

	public void save() {
		
		audioPanel.save();
		advancedPanel.save();
		String characterName = characterInput.getText().replaceAll("\\s", "");
		if(characterName.equals("")){
			characterName = null;
		}
		REDO_MacroEventManager.setCharacterName(characterName);
		Main.saveManager.putObject(characterName, "general", "character");
		Main.saveManager.putObject(guildCheckbox.isSelected(), "general", "showGuild");
		Main.saveManager.putObject(kickCheckbox.isSelected(), "general", "closeOnKick");

		TimeStyle time = (TimeStyle) timeCombo.getSelectedItem();
		DateStyle date = (DateStyle) dateCombo.getSelectedItem();
		OrderType order = (OrderType) orderCombo.getSelectedItem();
		

		FrameManager.historyWindow.setTimeStyle(time);
		FrameManager.historyWindow.setDateStyle(date);
		FrameManager.historyWindow.setOrderType(order);
		
		Main.saveManager.putObject(time.name(), "history", "timeStyle");
		Main.saveManager.putObject(date.name(), "history", "dateStyle");
		Main.saveManager.putObject(order.name(), "history", "orderType");
		Main.saveManager.putObject((int) countSpinner.getValue(), "history", "messageCount");
		
		
	}

	public void load() {
		audioPanel.load();
		advancedPanel.load();
		try {
			String characterName = Main.saveManager.getString("general", "character");
			REDO_MacroEventManager.setCharacterName(characterName);
			characterInput.setText(characterName);
			guildCheckbox.setSelected(Main.saveManager.getBool("general", "showGuild"));
			kickCheckbox.setSelected(Main.saveManager.getBool("general", "closeOnKick"));

			TimeStyle time = TimeStyle.valueOf(Main.saveManager.getEnumValue(TimeStyle.class, "history", "timeStyle"));
			DateStyle date = DateStyle.valueOf(Main.saveManager.getEnumValue(DateStyle.class, "history", "dateStyle"));
			OrderType order = OrderType.valueOf(Main.saveManager.getEnumValue(OrderType.class, "history", "orderType"));

			timeCombo.setSelectedItem(time);
			dateCombo.setSelectedItem(date);
			orderCombo.setSelectedItem(order);
			countSpinner.setValue(Main.saveManager.getDefaultInt(0, 100, 50, "history", "messageCount"));
			
			
		} catch (NullPointerException e) {
			Main.logger.log(Level.WARNING, "Error loading basics panel information");
		}
		
	}

}
