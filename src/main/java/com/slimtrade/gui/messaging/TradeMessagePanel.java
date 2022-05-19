package com.slimtrade.gui.messaging;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stash.StashHelperPanel;

import java.awt.event.MouseEvent;

public class TradeMessagePanel extends NotificationPanel {

    private StashHelperPanel stashHelperPanel;

    public TradeMessagePanel(TradeOffer trade) {
        tradeOffer = trade;
        if (FrameManager.stashHelperContainer != null)  // Null check is just for debug panels, could remove
            stashHelperPanel = FrameManager.stashHelperContainer.addHelper(trade);
        topMacros = SaveManager.settingsSaveFile.data.incomingTopMacros;
        bottomMacros = SaveManager.settingsSaveFile.data.incomingBottomMacros;
        playerNameButton.setText(trade.playerName);
        itemButton.setText(trade.itemName);
        priceLabel.setText(trade.priceTypeString);
        setup();
        addListeners();
    }

    private void addListeners() {
        itemButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    stashHelperPanel.setVisible(true);
                    FrameManager.stashHelperContainer.refresh();
                }
            }
        });
    }

    @Override
    public void cleanup() {
        super.cleanup();
        // FIXME:
        FrameManager.stashHelperContainer.remove(stashHelperPanel);
        FrameManager.stashHelperContainer.refresh();
    }

}
