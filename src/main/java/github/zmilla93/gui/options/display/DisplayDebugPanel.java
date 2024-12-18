package github.zmilla93.gui.options.display;

import github.zmilla93.core.CommonText;
import github.zmilla93.core.data.PlayerMessage;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;
import github.zmilla93.gui.messaging.ChatScannerMessagePanel;
import github.zmilla93.gui.messaging.TradeMessagePanel;

import javax.swing.*;

public class DisplayDebugPanel extends JPanel {

    public DisplayDebugPanel() {
        setBorder(BorderFactory.createTitledBorder("Debug Previews"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false));
        add(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE), false));
        add(new ChatScannerMessagePanel(new ChatScannerEntry("Search Name"), new PlayerMessage("ExampleUser789", CommonText.LOREM_IPSUM_SHORT), false));
    }

}
