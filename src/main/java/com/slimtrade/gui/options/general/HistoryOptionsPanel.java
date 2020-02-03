package com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.slimtrade.App;
import com.slimtrade.core.SaveConstants;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.ColorUpdateListener;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

public class HistoryOptionsPanel extends ContainerPanel implements ISaveable, ColorUpdateListener {

	private static final long serialVersionUID = 1L;

	private JComboBox<TimeStyle> timeCombo = new JComboBox<TimeStyle>();
	private JComboBox<DateStyle> dateCombo = new JComboBox<DateStyle>();
	private JComboBox<OrderType> orderCombo = new JComboBox<OrderType>();
	private SpinnerModel spinnerModel = new SpinnerNumberModel(50, 0, 100, 5);
	private JSpinner limitSpinner = new JSpinner(spinnerModel);

	private JLabel timeLabel = new JLabel("Time Format");
	private JLabel dateLabel = new JLabel("Date Format");
	private JLabel orderLabel = new JLabel("Order");
	private JLabel limitLabel = new JLabel("Message Limit");

	public HistoryOptionsPanel() {

		// JLabel limitInfoLabel = new JLabel("Message limit will not be applied
		// until restart.");

		timeCombo.setFocusable(false);
		dateCombo.setFocusable(false);
		orderCombo.setFocusable(false);
		limitSpinner.setFocusable(false);
		((DefaultEditor) limitSpinner.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) limitSpinner.getEditor()).getTextField().setHighlighter(null);

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

		this.updateColor();
		App.eventManager.addListener(this);

		load();

	}

	@Override
	public void save() {
		TimeStyle time = (TimeStyle) timeCombo.getSelectedItem();
		DateStyle date = (DateStyle) dateCombo.getSelectedItem();
		OrderType order = (OrderType) orderCombo.getSelectedItem();

		FrameManager.historyWindow.setTimeStyle(time);
		FrameManager.historyWindow.setDateStyle(date);
		FrameManager.historyWindow.setOrderType(order);

		App.saveManager.putObject(time.name(), SaveConstants.History.TIME_STYLE);
		App.saveManager.putObject(date.name(), SaveConstants.History.DATE_STYLE);
		App.saveManager.putObject(order.name(), SaveConstants.History.ORDER_TYPE);
		App.saveManager.putObject((int) limitSpinner.getValue(), SaveConstants.History.MAX_MESSAGE_COUNT);
	}

	@Override
	public void load() {
		TimeStyle time = TimeStyle.valueOf(App.saveManager.getEnumValue(TimeStyle.class, SaveConstants.History.TIME_STYLE));
		DateStyle date = DateStyle.valueOf(App.saveManager.getEnumValue(DateStyle.class, SaveConstants.History.DATE_STYLE));
		OrderType order = OrderType.valueOf(App.saveManager.getEnumValue(OrderType.class, SaveConstants.History.ORDER_TYPE));

		timeCombo.setSelectedItem(time);
		dateCombo.setSelectedItem(date);
		orderCombo.setSelectedItem(order);
		limitSpinner.setValue(App.saveManager.getDefaultInt(0, 100, 50, SaveConstants.History.MAX_MESSAGE_COUNT));
	}

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
		timeLabel.setForeground(ColorManager.TEXT);
		dateLabel.setForeground(ColorManager.TEXT);
		orderLabel.setForeground(ColorManager.TEXT);
		limitLabel.setForeground(ColorManager.TEXT);
	}

}
