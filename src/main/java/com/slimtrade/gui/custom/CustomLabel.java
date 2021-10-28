package com.slimtrade.gui.custom;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.managers.FontManager;
import com.slimtrade.core.observing.IColorable;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel implements IColorable {

    private Font defaultFont;

    public CustomLabel() {
        this(true);
    }

    public CustomLabel(boolean bold) {
        super();
        buildLabel(bold);
    }

    public CustomLabel(String text) {
        this(text, true);
    }

    public CustomLabel(String text, boolean bold) {
        super(text);
        buildLabel(bold);
    }

    private void buildLabel(boolean bold) {
        defaultFont = getFont();
        setFont(FontManager.getFont(getText(), getFont()));
        setBold(bold);
    }

    public void setBold(boolean state) {
        Font curFont = getFont();
        if (state) {
            setFont(curFont.deriveFont(curFont.getStyle() | Font.BOLD));
        } else {
            setFont(curFont.deriveFont(curFont.getStyle() & ~Font.BOLD));
        }
    }

    @Override
    public JToolTip createToolTip() {
        return new CustomToolTip(this);
    }

    @Override
    public void updateColor() {
        this.setForeground(ColorManager.TEXT);
        createToolTip();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (defaultFont == null) defaultFont = getFont();
        setFont(FontManager.getFont(getText(), defaultFont));
    }
}
