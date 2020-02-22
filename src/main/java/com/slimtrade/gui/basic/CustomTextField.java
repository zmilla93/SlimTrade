package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.components.PanelWrapper;

import javax.swing.*;
import javax.swing.border.Border;

public class CustomTextField extends JTextField implements IColorable {

    public boolean enableBorder = true;

    public CustomTextField() {
        super();
    }

    public CustomTextField(int col) {
        super(col);
    }

    public CustomTextField(String text) {
        super(text);
    }


    @Override
    public void updateColor() {
        this.setCaretColor(ColorManager.TEXT);
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setForeground(ColorManager.TEXT);
        this.setSelectionColor(ColorManager.PRIMARY);
        if(enableBorder) {
            this.setBorder(ColorManager.BORDER_TEXT);
        } else {
            this.setBorder(null);
        }

    }

}
