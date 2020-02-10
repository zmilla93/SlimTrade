package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;

public class CustomTextField extends JTextField implements IColorable {

//    public Color backgroundColor = Color.GREEN;

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
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_2);
//        this.setBackground(Color.GREEN);
        this.setForeground(ColorManager.TEXT);
        this.setSelectionColor(ColorManager.PRIMARY);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
    }

}
