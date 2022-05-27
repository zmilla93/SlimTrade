package com.slimtrade.gui.history;

import com.slimtrade.core.enums.TimeFormat;
import com.slimtrade.core.managers.SaveManager;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeString {

    public final String originalText;
    private String formattedText;
    private TimeFormat cachedFormat = null;

    public TimeString(String text) {
        this.originalText = text;
    }

    @Override
    public String toString() {
        if (cachedFormat != SaveManager.settingsSaveFile.data.historyTimeFormat) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SaveManager.settingsSaveFile.data.historyTimeFormat.getFormat());
            LocalTime time = LocalTime.parse(originalText, DateTimeFormatter.ISO_TIME);
            formattedText = time.format(formatter);
            cachedFormat = SaveManager.settingsSaveFile.data.historyTimeFormat;
        }
        return formattedText;
    }

}
