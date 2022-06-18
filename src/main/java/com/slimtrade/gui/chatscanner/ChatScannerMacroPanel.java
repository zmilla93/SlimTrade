package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.gui.options.AbstractMacroOptionPanel;

public class ChatScannerMacroPanel extends AbstractMacroOptionPanel {

    public ChatScannerMacroPanel() {
        super(TradeOfferType.CHAT_SCANNER_MESSAGE);
        reloadExampleTrade(new ChatScannerEntry("D"));
    }

}
