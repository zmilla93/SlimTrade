package com.slimtrade.gui.options.macro;

import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.options.ISaveable;

public class MacroPanelOutgoing extends MacroPanel implements ISaveable {

    public MacroPanelOutgoing() {
        super(MessageType.OUTGOING_TRADE);
    }

}
