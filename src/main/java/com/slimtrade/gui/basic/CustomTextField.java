package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import javax.swing.border.Border;

public class CustomTextField extends JTextField implements IColorable {

    public Border border = ColorManager.BORDER_TEXT;

    public CustomTextField(){
        super();
        App.eventManager.addColorListener(this);
        updateColor();
    }

    public CustomTextField(int col){
        super(col);
        App.eventManager.addColorListener(this);
        updateColor();
    }

    @Override
    public void setBorder(Border border) {
        super.setBorder(border);
        this.border = border;
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setForeground(ColorManager.TEXT);
        this.setSelectionColor(ColorManager.PRIMARY);
        this.setBorder(border);
    }

}
