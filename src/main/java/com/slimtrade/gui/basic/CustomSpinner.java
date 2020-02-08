package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class CustomSpinner extends JSpinner implements IColorable {

    public CustomSpinner(SpinnerModel model) {
        super(model);
//        setFocusable(false);
        ((DefaultEditor) this.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor) this.getEditor()).getTextField().setHighlighter(null);
        App.eventManager.addColorListener(this);
        updateColor();
    }

    @Override
    public void updateColor() {
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
        this.getEditor().getComponent(0).setForeground(ColorManager.TEXT);
        this.getEditor().getComponent(0).setBackground(ColorManager.BACKGROUND);
        for(Component c : this.getComponents()) {
            if(c instanceof BasicArrowButton) {
                BasicArrowButton b = (BasicArrowButton)c;
                b.setBackground(ColorManager.BACKGROUND);
                b.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
            }
        }
    }
}
