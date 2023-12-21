package com.slimtrade.gui.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * A JTextField that allows settings a placeholder text.
 * Focus = Show/hide placeholder based on gaining/losing focus
 * Change = Show/hide placeholder based on text change
 */
public class PlaceholderTextField extends JTextField implements FocusListener, DocumentListener {

    public enum Mode {
        FOCUS, CHANGE
    }

    private final StyledLabel placeholderLabel = new StyledLabel();
    private Mode mode = Mode.CHANGE;

    public PlaceholderTextField() {
        super();
        setup();
    }

    public PlaceholderTextField(String placeholderText) {
        setPlaceholderText(placeholderText);
        setup();
    }

    public PlaceholderTextField(String text, String placeholderText) {
        super(text);
        setPlaceholderText(placeholderText);
        setup();
    }

    public PlaceholderTextField(int columns) {
        super(columns);
        setup();
    }

    public PlaceholderTextField(String placeholderText, int columns) {
        super(columns);
        setPlaceholderText(placeholderText);
        setup();
    }

    public PlaceholderTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        setup();
    }

    public void setPlaceholderText(String text) {
        placeholderLabel.setText(text);
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    private void setup() {
        placeholderLabel.setItalic(true);
        placeholderLabel.setColorKey("TitledBorder.titleColor");
        addFocusListener(this);
        getDocument().addDocumentListener(this);
        updateUI();
    }

    private boolean isTextEmpty() {
        return getText().equals("");
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (mode == Mode.FOCUS) placeholderLabel.setVisible(false);
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (mode == Mode.FOCUS)
            if (isTextEmpty()) placeholderLabel.setVisible(true);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (mode == Mode.CHANGE)
            placeholderLabel.setVisible(false);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (mode == Mode.CHANGE)
            if (isTextEmpty()) placeholderLabel.setVisible(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // Do nothing
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (placeholderLabel == null) return;
        setLayout(new BorderLayout());
        add(placeholderLabel, BorderLayout.WEST);
    }

}
