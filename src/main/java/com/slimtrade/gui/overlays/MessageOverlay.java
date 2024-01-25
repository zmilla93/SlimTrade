package com.slimtrade.gui.overlays;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.gui.messaging.NotificationPanel;
import com.slimtrade.gui.messaging.TradeMessagePanel;

public class MessageOverlay extends AbstractOverlayFrame {

    public final NotificationPanel notificationPanel;

    public MessageOverlay() {
        super(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false), "Example Message");
        this.notificationPanel = (NotificationPanel) this.panel;
    }

}
