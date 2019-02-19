package main.java.com.slimtrade.gui.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import main.java.com.slimtrade.enums.DateStyle;
import main.java.com.slimtrade.enums.TimeStyle;
import main.java.com.slimtrade.gui.options.advanced.AdvancedPanel;
import main.java.com.slimtrade.gui.options.audio.AudioPanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class BasicsPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private int bufferX = 30;
	private int bufferY = 5;

	private JSlider historySlider = new JSlider();

	BasicsPanel() {
		this.setLayout(new GridBagLayout());
		JPanel firstPanel = new JPanel(new GridBagLayout());
//		firstPanel.setBackground(Color.orange);
		GridBagConstraints gc = new GridBagConstraints();
		// gc.gridx = 0;
		// gc.gridy = 0;

		// General
		JLabel generalLabel = new JLabel("General");

		// STASH
		JLabel stashLabel = new JLabel("Stash Location");
		JButton stashButton = new JButton("Edit");
		stashButton.setFocusable(false);
		// OVERLAY
		JLabel overlayLabel = new JLabel("Overlay Layout");
		JButton overlayButton = new JButton("Edit");
		overlayButton.setFocusable(false);
		// GUILD NAME
		JLabel guildLabel = new JLabel("Show Guild Name");
		JCheckBox guildCheckbox = new JCheckBox();
		guildCheckbox.setFocusable(false);
		// CLOSE ON KICK
		JLabel kickLabel = new JLabel("Close on Kick");
		JCheckBox kickCheckbox = new JCheckBox();
		kickCheckbox.setFocusable(false);

		// History
		JLabel historyLabel = new JLabel("History");

		// TIME FORMAT
		JLabel timeLabel = new JLabel("Time Format");
		JComboBox<TimeStyle> timeCombo = new JComboBox<TimeStyle>();
		for (TimeStyle s : TimeStyle.values()) {
			timeCombo.addItem(s);
		}
		timeCombo.setFocusable(false);
		// DATE FORMAT
		JLabel dateLabel = new JLabel("Date Format");
		JComboBox<DateStyle> dateCombo = new JComboBox<DateStyle>();
		dateCombo.setFocusable(false);
		for (DateStyle s : DateStyle.values()) {
			dateCombo.addItem(s);
		}
		// Order
		JLabel orderLabel = new JLabel("Order");
		JComboBox<OrderType> orderCombo = new JComboBox<OrderType>();
		for (OrderType t : OrderType.values()) {
			orderCombo.addItem(t);
		}
		// Message Count
		JLabel countlabel = new JLabel("Message Count");
		historySlider.setSnapToTicks(true);
		historySlider.setMinimum(0);
		historySlider.setMaximum(100);
		historySlider.setPaintTicks(true);
		historySlider.setMinorTickSpacing(10);
		historySlider.setMajorTickSpacing(20);
		historySlider.setPaintLabels(true);
		//Audio
		JLabel audioLabel = new JLabel("Audio");
		
		//Advanced
		JLabel advancedLabel = new JLabel("Advanced");

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx=1;

		// General Label
		gc.insets.top = 20;
		gc.insets.bottom = 5;
		gc.gridwidth = 4;
		firstPanel.add(generalLabel, gc);
		gc.gridwidth = 1;
		gc.insets.top = 0;
		gc.insets.bottom = 5;

		gc.gridx = 0;
		gc.gridy++;
		gc.fill = gc.BOTH;
//		gc.insets.right = 50;
//		stashLabel.setMinimumSize(new Dimension(200, 10));
		firstPanel.add(stashLabel, gc);
//		gc.insets.right = 0;
		gc.fill = gc.NONE;
		gc.gridx=1;
		firstPanel.add(new BufferPanel(20,0), gc);
		gc.gridx=2;
		firstPanel.add(new BufferPanel(60,0), gc);
		gc.gridx=3;
		gc.fill = gc.BOTH;
		firstPanel.add(stashButton, gc);
		gc.gridx = 0;
		
		gc.gridy++;
		firstPanel.add(overlayLabel, gc);
		gc.gridx=3;
		firstPanel.add(overlayButton, gc);
		gc.gridx = 0;
		gc.gridy++;
		firstPanel.add(guildLabel, gc);
		gc.gridx=3;
		firstPanel.add(guildCheckbox, gc);
		gc.gridx = 0;
		gc.gridy++;
		firstPanel.add(kickLabel, gc);
		gc.gridx=3;
		firstPanel.add(kickCheckbox, gc);
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = gc.NONE;
		firstPanel.add(new BufferPanel(0, bufferY * 2), gc);
		gc.gridy++;
		gc.gridwidth = 4;
		gc.insets.top = 20;
		gc.insets.bottom = 5;
		// HISTORY
		firstPanel.add(historyLabel, gc);
		gc.insets.top = 0;
		gc.insets.bottom = 5;
		gc.gridwidth = 1;
		gc.gridy++;
		firstPanel.add(new BufferPanel(0, bufferY), gc);
		gc.fill = gc.BOTH;
		gc.gridy++;
		firstPanel.add(timeLabel, gc);
		gc.insets.right = 0;
		gc.gridx=3;
		firstPanel.add(timeCombo, gc);
		gc.gridx = 0;;
		gc.gridy++;
		firstPanel.add(dateLabel, gc);
		gc.gridx=3;
		firstPanel.add(dateCombo, gc);
		gc.gridx = 0;
		gc.gridy++;

		firstPanel.add(orderLabel, gc);
		gc.gridx=3;
		firstPanel.add(orderCombo, gc);
		gc.gridx = 0;
		gc.gridy++;
		firstPanel.add(countlabel, gc);
		gc.gridx=2;
		gc.gridwidth = 2;
		firstPanel.add(historySlider, gc);
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = gc.NONE;
		firstPanel.add(new BufferPanel(0, bufferY * 2), gc);
		gc.gridy++;
		
		// AUDIO
		gc.gridwidth = 4;
		gc.insets.top = 20;
		gc.insets.bottom = 5;
		firstPanel.add(audioLabel, gc);
//		gc.fill = gc.BOTH;
		gc.insets.top = 0;
		gc.insets.bottom = 5;
//		gc.gridwidth=1;
		gc.gridx = 0;
		gc.gridy++;
		AudioPanel audioPanel = new AudioPanel();
		audioPanel.setVisible(true);
		audioPanel.autoResize();
		firstPanel.add(audioPanel, gc);
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
//		gc.gridwidth=1;
		gc.gridx = 0;
		gc.gridy++;
		AdvancedPanel adv = new AdvancedPanel();
//		adv.setVisible(true);
		firstPanel.add(adv, gc);
		
		gc.gridx=0;
		gc.gridy++;
		

		// this.addRow(c, gc);
		
		
		
		this.add(firstPanel);
		//
		// gc.insets = new Insets(0, 0, bufferY, 0);
		// this.addPair(timeLabel, timeCombo, gc);
		// this.addPair(dateLabel, dateCombo, gc);
		// this.addPair(guildLabel, guildCheckbox, gc);
		// gc.insets = new Insets(0, 0, 0, 0);
		// this.addPair(kickLabel, kickCheckbox, gc);
		this.autoResize();
		// this.setMaximumSize(this.getPreferredSize());
	}

}
