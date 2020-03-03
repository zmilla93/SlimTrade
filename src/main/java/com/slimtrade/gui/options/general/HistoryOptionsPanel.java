package com.slimtrade.gui.options.general;

import java.awt.*;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.basic.CustomSpinner;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

public class HistoryOptionsPanel extends ContainerPanel implements ISaveable, IColorable {

	private static final long serialVersionUID = 1L;

	private CustomCombo<TimeStyle> timeCombo = new CustomCombo<>();
	private CustomCombo<DateStyle> dateCombo = new CustomCombo<>();
	private CustomCombo<OrderType> orderCombo = new CustomCombo<>();
	private SpinnerModel spinnerModel = new SpinnerNumberModel(50, 0, 100, 5);
	private JSpinner limitSpinner = new CustomSpinner(spinnerModel);

	private JLabel timeLabel = new JLabel("Time Format");
	private JLabel dateLabel = new JLabel("Date Format");
	private JLabel orderLabel = new JLabel("Order");
	private JLabel limitLabel = new JLabel("Message Limit");

	public HistoryOptionsPanel() {
		for (TimeStyle s : TimeStyle.values()) {
			timeCombo.addItem(s);
		}
		for (DateStyle s : DateStyle.values()) {
			dateCombo.addItem(s);
		}
		for (OrderType t : OrderType.values()) {
			orderCombo.addItem(t);
		}
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH;

		// Sizing
		gc.gridx = 1;
		container.add(new BufferPanel(40, 0), gc);
		gc.gridx = 0;
		gc.gridy++;

		// Time Style
		container.add(timeLabel, gc);
		gc.gridx = 2;
		container.add(timeCombo, gc);
		gc.gridx = 0;
		gc.gridy++;

		// Date Style
		container.add(dateLabel, gc);
		gc.gridx = 2;
		container.add(dateCombo, gc);
		gc.gridx = 0;
		gc.gridy++;

		// OrderType
		container.add(orderLabel, gc);
		gc.gridx = 2;
		container.add(orderCombo, gc);
		gc.gridx = 0;
		gc.gridy++;

		// Message Limit
		container.add(limitLabel, gc);
		gc.gridx = 2;
		gc.gridwidth = 1;
		container.add(limitSpinner, gc);
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy++;

		// Temp Warning
		gc.fill = GridBagConstraints.NONE;
		gc.gridwidth = 3;
		gc.insets.top = 5;
		container.add(new CustomLabel("History changes require restart"), gc);
		load();
	}

	@Override
	public void save() {
		TimeStyle timeStyle = (TimeStyle) timeCombo.getSelectedItem();
		DateStyle dateStyle = (DateStyle) dateCombo.getSelectedItem();
		OrderType orderType = (OrderType) orderCombo.getSelectedItem();

		App.saveManager.saveFile.timeStyle = timeStyle;
		App.saveManager.saveFile.dateStyle = dateStyle;
		App.saveManager.saveFile.orderType = orderType;
        App.saveManager.saveFile.historyLimit = ((int)limitSpinner.getValue());
	}
	@Override
	public void load() {
		timeCombo.setSelectedItem(App.saveManager.saveFile.timeStyle);
		dateCombo.setSelectedItem(App.saveManager.saveFile.dateStyle);
		orderCombo.setSelectedItem(App.saveManager.saveFile.orderType);
		limitSpinner.setValue(App.saveManager.saveFile.historyLimit);
	}

	@Override
	public void updateColor() {
		super.updateColor();
		this.setBackground(ColorManager.BACKGROUND);
		timeLabel.setForeground(ColorManager.TEXT);
		dateLabel.setForeground(ColorManager.TEXT);
		orderLabel.setForeground(ColorManager.TEXT);
		limitLabel.setForeground(ColorManager.TEXT);
	}

}
