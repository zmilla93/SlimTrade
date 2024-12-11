package github.zmilla93.gui.options.display;

import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.gui.messaging.TradeMessagePanel;

import javax.swing.*;

public class DisplayDebugPanel extends JPanel {

    public DisplayDebugPanel() {
        setBorder(BorderFactory.createTitledBorder("Debug Previews"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false));
        add(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE), false));
    }

}
