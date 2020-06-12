package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomSpinner;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class HistorySettingsPanel extends ContainerPanel implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;

    private CustomCombo<TimeStyle> timeCombo = new CustomCombo<>();
    private CustomCombo<DateStyle> dateCombo = new CustomCombo<>();
    private CustomCombo<OrderType> orderCombo = new CustomCombo<>();
    private SpinnerModel spinnerModel = new SpinnerNumberModel(50, 0, 100, 5);
    private JSpinner limitSpinner = new CustomSpinner(spinnerModel);

    private JLabel timeLabel = new CustomLabel("Time Format");
    private JLabel dateLabel = new CustomLabel("Date Format");
    private JLabel orderLabel = new CustomLabel("Order");
    private JLabel limitLabel = new CustomLabel("Message Limit");

    public HistorySettingsPanel() {
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
        gc.insets.top = 5;
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

    }

    @Override
    public void save() {
        TimeStyle timeStyle = (TimeStyle) timeCombo.getSelectedItem();
        DateStyle dateStyle = (DateStyle) dateCombo.getSelectedItem();
        OrderType orderType = (OrderType) orderCombo.getSelectedItem();

        App.saveManager.saveFile.timeStyle = timeStyle;
        App.saveManager.saveFile.dateStyle = dateStyle;
        App.saveManager.saveFile.orderType = orderType;
        App.saveManager.saveFile.historyLimit = ((int) limitSpinner.getValue());
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

    public TimeStyle getTimeStyle() {
        return (TimeStyle) timeCombo.getSelectedItem();
    }

    public DateStyle getDateStyle() {
        return (DateStyle) dateCombo.getSelectedItem();
    }

    public OrderType getOrderType() {
        return (OrderType) orderCombo.getSelectedItem();
    }

    public int getMessageLimit() {
        return (int) limitSpinner.getValue();
    }

}
