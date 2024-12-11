package github.zmilla93.gui.kalguur;

import github.zmilla93.core.data.HourMinute;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.components.PlaceholderTextField;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KalguurTimerPanel extends JPanel implements ISavable {

    private final JDialog parentWindow;
    private final JTextField inputFiled = new PlaceholderTextField("Timer (h:m)", 6);
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
    }

    private void submitInput() {
        String input = inputFiled.getText().replaceAll("\\s*", "");
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

        rowContainer.add(new KalguurTimerRow(parentWindow, rowContainer, expirationTime.toInstant()));

        parentWindow.pack();
        SaveManager.appStateSaveFile.saveToDisk();
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
            if (row.isExpired() && SaveManager.settingsSaveFile.data.kalguurAutoClearTimers) continue;
            timestamps.add(expirationTime.toString());
        }
        SaveManager.appStateSaveFile.data.kalguurTimers = timestamps;
    }

    @Override
    public void load() {
        for (int i = rowContainer.getComponentCount() - 1; i >= 0; i--) {
            KalguurTimerRow row = (KalguurTimerRow) rowContainer.getComponent(i);
            row.destroyTimer();
        }
        boolean save = false;
        for (String timestamp : SaveManager.appStateSaveFile.data.kalguurTimers) {
            Instant expirationTime = Instant.parse(timestamp);
            HourMinute remainingTime = KalguurTimerRow.getRemainingTime(expirationTime);
            if (SaveManager.settingsSaveFile.data.kalguurAutoClearTimers
                    && remainingTime.hour <= 0
                    && remainingTime.minute <= 0) {
                FrameManager.messageManager.addKalguurMessage();
                save = true;
            } else {
                KalguurTimerRow row = new KalguurTimerRow(parentWindow, rowContainer, Instant.parse(timestamp));
                rowContainer.add(row);
                if (row.isExpired()) FrameManager.messageManager.addKalguurMessage();
            }
        }
        if (save) SaveManager.appStateSaveFile.saveToDisk();
        parentWindow.pack();
    }

}
