package com.slimtrade.gui.options.display;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.gui.messaging.TradeMessagePanel;

import javax.swing.*;

public class DisplayDebugPanel extends JPanel {

    public DisplayDebugPanel() {
        setBorder(BorderFactory.createTitledBorder("Debug Previews"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false));
        add(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE), false));
    }

}
