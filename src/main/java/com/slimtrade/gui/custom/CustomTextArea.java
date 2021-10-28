package com.slimtrade.gui.custom;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;

import javax.swing.*;
import java.awt.*;

public class CustomTextArea extends JTextArea implements IColorable {

    private Font defaultFont;

    public CustomTextArea(int rows, int columns) {
        super(rows, columns);
        buildTextArea();
    }

    private void buildTextArea() {
        assert (SwingUtilities.isEventDispatchThread());
//        defaultFont = getFont();
//        getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//            }
//        });
    }

    @Override
    public void updateColor() {
        this.setCaretColor(ColorManager.TEXT);
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setForeground(ColorManager.TEXT);
        this.setBorder(ColorManager.BORDER_TEXT);
        this.setSelectionColor(ColorManager.TEXT_SELECTION);
    }

}
