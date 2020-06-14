package com.slimtrade.gui.custom;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;

import javax.swing.*;

public class CustomTextPane extends JTextPane implements IColorable {

    @Override
    public void updateColor() {
        this.setCaretColor(ColorManager.TEXT);
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setBorder(ColorManager.BORDER_TEXT);
        this.setSelectionColor(ColorManager.TEXT_SELECTION);
        this.setBorder(null);
    }
}
