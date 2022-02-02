package com.slimtrade.gui.messaging;

import com.slimtrade.core.trading.TradeOffer;

import javax.swing.*;
import java.awt.*;

public class OverlayTradeMessagePanel extends JPanel {

    public OverlayTradeMessagePanel(){
        TradeMessagePanel tradeMessagePanel = new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
        setLayout(new BorderLayout());
        add(tradeMessagePanel, BorderLayout.CENTER);
        setPreferredSize(getPreferredSize());
        remove(tradeMessagePanel);
        setLayout(new GridBagLayout());
        JLabel label = new JLabel("Example Message");
        add(label);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

}
