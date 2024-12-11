package github.zmilla93.gui.chatscanner;

import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.gui.options.AbstractMacroOptionPanel;

public class ChatScannerMacroPanel extends AbstractMacroOptionPanel {

    public ChatScannerMacroPanel() {
        super(TradeOfferType.CHAT_SCANNER_MESSAGE);
        reloadExampleTrade(new ChatScannerEntry("D"));
    }

}
