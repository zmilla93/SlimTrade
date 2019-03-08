package main.java.com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.enums.DateStyle;
import main.java.com.slimtrade.enums.TimeStyle;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.options.OrderType;
import main.java.com.slimtrade.gui.options.Saveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class HistoryOptionsPanel extends ContainerPanel implements Saveable {

	private static final long serialVersionUID = 1L;

	private JComboBox<TimeStyle> timeCombo = new JComboBox<TimeStyle>();
	private JComboBox<DateStyle> dateCombo = new JComboBox<DateStyle>();
	private JComboBox<OrderType> orderCombo = new JComboBox<OrderType>();
	private SpinnerModel spinnerModel = new SpinnerNumberModel(50, 0, 100, 5);
	private JSpinner limitSpinner = new JSpinner(spinnerModel);

	public HistoryOptionsPanel() {

		JLabel timeLabel = new JLabel("Time Format");
		JLabel dateLabel = new JLabel("Date Format");
		JLabel orderLabel = new JLabel("Order");
		JLabel limitLabel = new JLabel("Message Limit");
		JLabel limitInfoLabel = new JLabel("Message limit will not be applied until restart.");

		timeCombo.setFocusable(false);
		dateCombo.setFocusable(false);
		orderCombo.setFocusable(false);
		limitSpinner.setFocusable(false);
		((DefaultEditor) limitSpinner.getEditor()).getTextField().setEditable(false);

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
		
		Main.saveManager.putObject(time.name(), "history", "timeStyle");
		Main.saveManager.putObject(date.name(), "history", "dateStyle");
		Main.saveManager.putObject(order.name(), "history", "orderType");
		Main.saveManager.putObject((int) limitSpinner.getValue(), "history", "messageCount");
	}

	@Override
	public void load() {
		TimeStyle time = TimeStyle.valueOf(Main.saveManager.getEnumValue(TimeStyle.class, "history", "timeStyle"));
		DateStyle date = DateStyle.valueOf(Main.saveManager.getEnumValue(DateStyle.class, "history", "dateStyle"));
		OrderType order = OrderType.valueOf(Main.saveManager.getEnumValue(OrderType.class, "history", "orderType"));

		timeCombo.setSelectedItem(time);
		dateCombo.setSelectedItem(date);
		orderCombo.setSelectedItem(order);
		limitSpinner.setValue(Main.saveManager.getDefaultInt(0, 100, 50, "history", "messageCount"));
	}

}
