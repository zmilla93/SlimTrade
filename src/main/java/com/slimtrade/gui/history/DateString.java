package com.slimtrade.gui.history;

import com.slimtrade.core.enums.DateFormat;
import com.slimtrade.core.managers.SaveManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateString {

    public final String originalText;
    private String formattedText;
    private DateFormat cachedFormat = null;

    public DateString(String text) {
        this.originalText = text;
    }

    @Override
    public String toString() {
        if (originalText == null) return null;
        if (cachedFormat != SaveManager.settingsSaveFile.data.historyDateFormat) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SaveManager.settingsSaveFile.data.historyDateFormat.getFormat());
            LocalDate date = LocalDate.parse(originalText, DateTimeFormatter.ISO_DATE);
            formattedText = date.format(formatter);
            cachedFormat = SaveManager.settingsSaveFile.data.historyDateFormat;
        }
        return formattedText;
    }

}
