package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.enums.DateStyle;
import main.java.com.slimtrade.enums.TimeStyle;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.options.advanced.AdvancedPanel;
import main.java.com.slimtrade.gui.options.audio.AudioPanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class BasicsPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private int bufferX = 30;
	private int bufferY = 5;

	private JSlider historySlider = new JSlider();

	BasicsPanel() {
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
		JLabel stashLabel = new JLabel("Stash Location");
		JLabel overlayLabel = new JLabel("Overlay Layout");
		JLabel guildLabel = new JLabel("Show Guild Name");
		JLabel kickLabel = new JLabel("Close on Kick");

		//HISTORY
		JLabel timeLabel = new JLabel("Time Format");
		JLabel dateLabel = new JLabel("Date Format");
		JLabel orderLabel = new JLabel("Order");
		JLabel audioLabel = new JLabel("Audio");
		JLabel advancedLabel = new JLabel("Advanced");

		// General Buttons
		JButton stashButton = new JButton("Edit");
		stashButton.setFocusable(false);
		JButton overlayButton = new JButton("Edit");
		overlayButton.setFocusable(false);
		JCheckBox guildCheckbox = new JCheckBox();
		guildCheckbox.setFocusable(false);
		JCheckBox kickCheckbox = new JCheckBox();
		kickCheckbox.setFocusable(false);
		JLabel countlabel = new JLabel("Message Count");

		// History Buttons
		JComboBox<TimeStyle> timeCombo = new JComboBox<TimeStyle>();
		for (TimeStyle s : TimeStyle.values()) {
			timeCombo.addItem(s);
		}
		timeCombo.setFocusable(false);

		JComboBox<DateStyle> dateCombo = new JComboBox<DateStyle>();
		dateCombo.setFocusable(false);
		for (DateStyle s : DateStyle.values()) {
			dateCombo.addItem(s);
		}

		JComboBox<OrderType> orderCombo = new JComboBox<OrderType>();
		for (OrderType t : OrderType.values()) {
			orderCombo.addItem(t);
		}
		// Message Count
		SpinnerModel model = new SpinnerNumberModel(50, 0, 100, 10);
		JSpinner spinner = new JSpinner(model);
		((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
		;
		spinner.setFocusable(false);

		// General Label
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;

		gc.weightx = 1;
		
//		gc.gridwidth = 3;
//		generalPanel.add(generalLabel, gc);
//		gc.gridwidth = 1;

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
		
		
//		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
//		gc.fill = GridBagConstraints.BOTH;
		historyPanel.add(timeLabel, gc);
		gc.insets.right = 0;
		gc.gridx = 1;
		historyPanel.add(new BufferPanel(50, 0), gc);
		gc.gridx = 2;
		historyPanel.add(timeCombo, gc);
		gc.gridx = 0;
		;
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
		historyPanel.add(spinner, gc);
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
		AudioPanel audioPanel = new AudioPanel();
		audioPanel.setVisible(true);
		audioPanel.autoResize();
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
		AdvancedPanel adv = new AdvancedPanel();
//		adv.setVisible(false);
		 firstPanel.add(adv, gc);

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

		ToggleButton generalButton = new ToggleButton("General");
		ToggleButton historyButton = new ToggleButton("History");
		ToggleButton audioButton = new ToggleButton("Audio");
		ToggleButton advancedButton = new ToggleButton("Save Path");

		generalButton.active = true;
		historyButton.active = true;
		audioButton.active = true;
		this.repaint();
		
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
		this.add(adv, gc);
		gc.insets.bottom = 5;

		link(generalButton, generalPanel);
		link(historyButton, historyPanel);
		link(audioButton, audioPanel);
		link(advancedButton, adv);

		

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

}
