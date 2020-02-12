package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel implements IColorable {

    public ColorPanel(){
        super();
        App.eventManager.addColorListener(this);
    }

    public ColorPanel(LayoutManager layout){
        super(layout);
        App.eventManager.addColorListener(this);
    }

    @Override
    public void updateColor() {
        //TODO
    }

}
