package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;

import javax.swing.*;
import java.awt.*;

public class LagTestDialog extends AbstractResizableWindow implements IColorable {

    public LagTestDialog() {
        super("Lag Test");
        container.setLayout(FrameManager.gridBag);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        // Combo Box
        JComboBox<String> combo = new CustomCombo<>();
        combo.addItem("ASDF");
        combo.addItem("1234");
        container.add(combo, gc);
        gc.gridy++;




        //Finalize
        App.eventManager.addColorListener(this);
        updateColor();

    }

    @Override
    public void updateColor() {
        super.updateColor();
    }
}
