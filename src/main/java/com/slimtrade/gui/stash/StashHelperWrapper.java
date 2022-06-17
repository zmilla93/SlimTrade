package com.slimtrade.gui.stash;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StashHelperWrapper extends JPanel {

    public StashHelperWrapper(TradeOffer tradeOffer) {
        assert (tradeOffer.isBulkTrade);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        ArrayList<SaleItem> items = tradeOffer.getItems();
        setOpaque(false);
        for (int i = 0; i < items.size(); i++) {
            StashHelperPanel panel = new StashHelperPanel(tradeOffer, i);
            add(panel, gc);
            gc.gridx++;
            gc.insets.left = StashHelperContainer.INSET;
        }
        FrameManager.stashHelperContainer.addHelper(this);
    }

    public void cleanup() {
        FrameManager.stashHelperContainer.remove(this);
        for (Component c : getComponents()) {
            if (c instanceof StashHelperPanel) {
                ((StashHelperPanel) c).cleanup();
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for (Component c : getComponents()) {
            c.setVisible(visible);
        }
    }
}
