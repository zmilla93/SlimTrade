package com.slimtrade.gui.chatscanner;

import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class ScannerSearchTermsPanel extends AbstractOptionPanel {

    private static final int ROWS = 6;
    private static final int COLUMNS = 35;
    private final JTextArea searchTermsInput = new JTextArea(ROWS, COLUMNS);
    private final JTextArea ignoreTermsInput = new JTextArea(ROWS, COLUMNS);

    public ScannerSearchTermsPanel() {
        addHeader("Separate unique phrases using a comma, semicolon, or new line.");
        addVerticalStrut();
        addHeader("Search Terms");
        addComponent(searchTermsInput);
        addVerticalStrut();
        addHeader("Ignore Terms");
        addComponent(ignoreTermsInput);
    }

    public void setSearchTerms(String text) {
        searchTermsInput.setText(text);
    }

    public String getSearchTerms() {
        return searchTermsInput.getText();
    }

    public void setIgnoreTerms(String text) {
        ignoreTermsInput.setText(text);
    }

    public String getIgnoreTerms() {
        return ignoreTermsInput.getText();
    }

}
