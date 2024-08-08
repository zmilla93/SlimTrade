package com.slimtrade.gui.kalguur;

import com.slimtrade.core.data.HourMinute;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.PlaceholderTextField;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KalguurTimerPanel extends JPanel implements ISavable {

    private final JDialog parentWindow;
    private final JTextField inputFiled = new PlaceholderTextField("Timer", 6);
    private final JPanel rowContainer = new JPanel();
    private final Pattern timerPattern = Pattern.compile("((?<hours>\\d*):)?(?<minutes>\\d+)");

    public KalguurTimerPanel(JDialog parentWindow) {
        this.parentWindow = parentWindow;
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(inputFiled, gc);
        gc.gridx++;
        gc.weightx = 0;
//        inputPanel.add(submitButton, gc);

        rowContainer.setLayout(new BoxLayout(rowContainer, BoxLayout.PAGE_AXIS));

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(rowContainer, BorderLayout.SOUTH);

        JButton submitButton = new IconButton(DefaultIcon.PLUS);
        submitButton.addActionListener(e -> submitInput());
        inputFiled.addActionListener(e -> submitInput());
        load();
    }

    private void submitInput() {
        String input = inputFiled.getText();
        inputFiled.setText("");
        Matcher matcher = timerPattern.matcher(input);
        if (!matcher.matches()) return;
        int hour = 0;
        int minute;
        try {
            String hourString = matcher.group("hours");
            if (hourString != null) hour = Integer.parseInt(hourString);
            minute = Integer.parseInt(matcher.group("minutes"));
        } catch (NumberFormatException e) {
            return;
        }
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.HOUR_OF_DAY, hour);
        expirationTime.add(Calendar.MINUTE, minute);

        rowContainer.add(new KalguurTimerRow(parentWindow, rowContainer, this, expirationTime.toInstant()));

        parentWindow.pack();
        save();
    }

    public void clearInput() {
        inputFiled.setText("");
    }

    @Override
    public void save() {
        ArrayList<String> timestamps = new ArrayList<>();
        for (Component comp : rowContainer.getComponents()) {
            KalguurTimerRow row = (KalguurTimerRow) comp;
            Instant expirationTime = row.getExpirationTime();
            HourMinute remainingTime = KalguurTimerRow.getRemainingTime(expirationTime);
            if (remainingTime.hour <= 0 && remainingTime.minute <= 0) continue;
            timestamps.add(expirationTime.toString());
        }
        SaveManager.appStateSaveFile.data.kalguurTimers = timestamps;
        SaveManager.appStateSaveFile.saveToDisk();
    }

    @Override
    public void load() {
        for (int i = rowContainer.getComponentCount() - 1; i >= 0; i--) {
            KalguurTimerRow row = (KalguurTimerRow) rowContainer.getComponent(i);
            row.destroyTimer();
        }
        for (String timestamp : SaveManager.appStateSaveFile.data.kalguurTimers) {
            Instant expirationTime = Instant.parse(timestamp);
            HourMinute remainingTime = KalguurTimerRow.getRemainingTime(expirationTime);
            if (SaveManager.settingsSaveFile.data.kalguurAutoClearTimers
                    && remainingTime.hour <= 0
                    && remainingTime.minute <= 0)
                continue;
            rowContainer.add(new KalguurTimerRow(parentWindow, rowContainer, this, Instant.parse(timestamp)));
        }
        parentWindow.pack();
    }

}
