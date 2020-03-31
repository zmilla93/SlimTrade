package com.slimtrade.gui.options.macro;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.basic.GridBagPanel;
import com.slimtrade.gui.options.ListButton;

public class MacroSelector extends GridBagPanel implements IColorable {

    public ListButton incomingButton = new ListButton("Incoming Macros");
    public ListButton outgoingButton = new ListButton("Outgoing Macros");

    public MacroSelector() {

        this.add(incomingButton, gc);
        gc.gridx++;
        gc.insets.left = 100;
        this.add(outgoingButton, gc);

        ListButton.link(this, incomingButton);
        ListButton.link(this, outgoingButton);

    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
    }
}
