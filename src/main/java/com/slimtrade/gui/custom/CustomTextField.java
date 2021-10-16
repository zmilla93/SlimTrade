package com.slimtrade.gui.custom;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CustomTextField extends JTextField implements IColorable {

    public boolean enableBorder = true;
    private Font defaultFont;

    public CustomTextField() {
        super();
        buildTextField();
    }

    public CustomTextField(int col) {
        super(col);
        buildTextField();
    }

    public CustomTextField(String text) {
        super(text);
        buildTextField();
    }

    private void buildTextField() {
        defaultFont = getFont();
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setFont(App.fontManager.getFont(getText(), defaultFont));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setFont(App.fontManager.getFont(getText(), defaultFont));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                setFont(App.fontManager.getFont(getText(), defaultFont));
            }
        });
    }

    @Override
    public void updateColor() {
        this.setCaretColor(ColorManager.TEXT);
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setForeground(ColorManager.TEXT);
        this.setSelectionColor(ColorManager.TEXT_SELECTION);
        if (enableBorder) {
            this.setBorder(ColorManager.BORDER_TEXT);
        } else {
            this.setBorder(null);
        }
    }


}
