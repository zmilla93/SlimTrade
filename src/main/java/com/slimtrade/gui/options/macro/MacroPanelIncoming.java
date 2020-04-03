package com.slimtrade.gui.options.macro;

import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.options.ISaveable;

public class MacroPanelIncoming extends MacroPanel implements ISaveable {

    public MacroPanelIncoming() {
        super(MessageType.INCOMING_TRADE);
    }

}
