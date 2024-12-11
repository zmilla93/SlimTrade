package github.zmilla93.gui.kalguur;

import github.zmilla93.core.data.HourMinute;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class KalguurTimerRow extends JPanel {

    private final JDialog parentWindow;
    private final Container parentContainer;
    private final HourMinute remainingTime;
    private final JLabel label;
    private final Timer timer;
    private final Instant expirationTime;
    private static final String COMPLETE_TEXT = "Complete";

    public KalguurTimerRow(JDialog parentWindow, Container parentContainer, Instant expirationTime) {
        this.expirationTime = expirationTime;
        this.parentWindow = parentWindow;
        this.parentContainer = parentContainer;
        this.remainingTime = getRemainingTime(expirationTime);
        label = new JLabel();
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        add(label, gc);
        gc.gridx++;
        gc.weightx = 0;
        JButton deleteButton = new IconButton(DefaultIcon.CLOSE);
        add(deleteButton, gc);
        updateLabel();
        timer = new Timer(1000 * 60, e -> tickTimer());
        if (!isExpired()) timer.start();
        deleteButton.addActionListener(e -> destroyTimer());
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public boolean isExpired() {
        return remainingTime.hour <= 0 && remainingTime.minute <= 0;
    }

    private void updateLabel() {
        String text = "";
        if (isExpired()) {
            text = COMPLETE_TEXT;
        }
        if (remainingTime.hour > 0) text += remainingTime.hour + "h";
        if (remainingTime.minute > 0) {
            if (remainingTime.hour > 0) text += " ";
            text += remainingTime.minute + "m";
        }
        label.setText(text);
    }

    private void tickTimer() {
        remainingTime.minute--;
        if (remainingTime.minute <= 0) {
            if (remainingTime.hour > 0) {
                remainingTime.hour--;
                remainingTime.minute = 60;
            }
        }
        updateLabel();
        handleExpiration();
    }

    public void destroyTimer() {
        timer.stop();
        parentContainer.remove(KalguurTimerRow.this);
        parentWindow.pack();
        SaveManager.appStateSaveFile.saveToDisk();
    }

    private void handleExpiration() {
        if (!isExpired()) return;
        timer.stop();
        if (SaveManager.settingsSaveFile.data.kalguurAutoClearTimers) {
            destroyTimer();
        } else {
            label.setText(COMPLETE_TEXT);
        }
        FrameManager.messageManager.addKalguurMessage();
    }

    public static HourMinute getRemainingTime(Instant instant) {
        Duration diff = Duration.between(Instant.now().truncatedTo(ChronoUnit.MINUTES), instant.truncatedTo(ChronoUnit.MINUTES));
        int hours = (int) diff.toHours();
        int minutes = (int) diff.minusHours(hours).toMinutes();
        if (minutes == 0 && hours > 0) {
            hours--;
            minutes += 60;
        }
        return new HourMinute(hours, minutes);
    }

}
