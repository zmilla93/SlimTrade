package github.zmilla93.gui.options.general;

import github.zmilla93.core.enums.DateFormat;
import github.zmilla93.core.enums.HistoryOrder;
import github.zmilla93.core.enums.TimeFormat;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class HistoryOptionPanel extends JPanel implements ISavable {

    private final JComboBox<HistoryOrder> orderCombo = new JComboBox<>();
    private final JComboBox<TimeFormat> timeFormatCombo = new JComboBox<>();
    private final JComboBox<DateFormat> dateFormatCombo = new JComboBox<>();
    private final GridBagConstraints gc = ZUtil.getGC();
    private static final int HORIZONTAL_SPACER = 10;

    public HistoryOptionPanel() {
        setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        for (HistoryOrder order : HistoryOrder.values()) orderCombo.addItem(order);
        for (TimeFormat format : TimeFormat.values()) timeFormatCombo.addItem(format);
        for (DateFormat format : DateFormat.values()) dateFormatCombo.addItem(format);
        addRow("Message Order", orderCombo);
        addRow("Time Format", timeFormatCombo);
        addRow("Date Format", dateFormatCombo);
    }

    private void addRow(String text, JComponent component) {
        add(new JLabel(text), gc);
        gc.gridx++;
        gc.insets.left = HORIZONTAL_SPACER;
        add(component, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.historyOrder = (HistoryOrder) orderCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.historyTimeFormat = (TimeFormat) timeFormatCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.historyDateFormat = (DateFormat) dateFormatCombo.getSelectedItem();
    }

    @Override
    public void load() {
        orderCombo.setSelectedItem(SaveManager.settingsSaveFile.data.historyOrder);
        timeFormatCombo.setSelectedItem(SaveManager.settingsSaveFile.data.historyTimeFormat);
        dateFormatCombo.setSelectedItem(SaveManager.settingsSaveFile.data.historyDateFormat);
    }

}
