package com.slimtrade.gui.components;

import com.slimtrade.core.managers.FontManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Text field that automatically converts to international fonts when needed.
 */
public class LanguageTextField extends JTextField {

    public LanguageTextField() {
        setup();
    }

    public LanguageTextField(int columns) {
        super(columns);
        setup();
    }

    private void setup() {
        updateFont();
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFont();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFont();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Do Nothing
            }
        });
    }

    private void updateFont() {
        FontManager.applyFont(this);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        updateFont();
    }

}
