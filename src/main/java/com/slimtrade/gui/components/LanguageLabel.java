package com.slimtrade.gui.components;

import com.slimtrade.core.managers.FontManager;

import javax.swing.*;

public class LanguageLabel extends JLabel {

    public LanguageLabel(){

    }

    public LanguageLabel(String text){
        super(text);
        updateFont();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        updateFont();
    }

    private void updateFont(){
        FontManager.applyFont(this);
    }

}
