package com.slimtrade.gui.kalguur;

import com.slimtrade.core.data.HourMinute;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class KalguurTimerRow extends JPanel {

    private final JDialog parentWindow;
    private final Container parentContainer;
    private final KalguurTimerPanel timerPanel;
    private final HourMinute remainingTime;
    private final JLabel label;
    private final Timer timer;
    private final Instant expirationTime;

    public KalguurTimerRow(JDialog parentWindow, Container parentContainer, KalguurTimerPanel timerPanel, Instant expirationTime) {
        this.expirationTime = expirationTime;
        this.parentWindow = parentWindow;
        this.parentContainer = parentContainer;
        this.timerPanel = timerPanel;
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
        // FIXME : Set timer to 1 minute
        timer = new Timer(1000 * 60, e -> tickTimer());
        timer.start();
        deleteButton.addActionListener(e -> destroyTimer());
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    private void updateLabel() {
        String text = "";
        if (remainingTime.hour > 0) text += remainingTime.hour + "h ";
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
        timerPanel.save();
    }

    private void handleExpiration() {
        if (remainingTime.hour > 0 || remainingTime.minute > 0) return;
        timer.stop();
        if (SaveManager.settingsSaveFile.data.kalguurAutoClearTimers) {
            destroyTimer();
        } else {
            label.setText("Complete");
        }
        FrameManager.messageManager.addKalguurMessage();
    }

    public static HourMinute getRemainingTime(Instant instant) {
        Duration diff = Duration.between(Instant.now().truncatedTo(ChronoUnit.MINUTES), instant.truncatedTo(ChronoUnit.MINUTES));
        int hours = (int) diff.toHours();
        int minutes = (int) diff.minusHours(hours).toMinutes();
        return new HourMinute(hours, minutes);
    }

}
