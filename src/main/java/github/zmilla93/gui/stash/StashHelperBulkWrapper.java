package github.zmilla93.gui.stash;

import github.zmilla93.core.data.SaleItem;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A wrapper panel for multiple StashHelpers that are linked to a single trade offer.
 *
 * @see StashHelperContainer
 * @see StashHelperPanel
 */
public class StashHelperBulkWrapper extends JPanel {

    private final ArrayList<StashHelperPanel> helperPanels = new ArrayList<>();

    public StashHelperBulkWrapper(TradeOffer tradeOffer) {
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
            helperPanels.add(panel);
        }
        FrameManager.stashHelperContainerLegacy.addHelper(this);
    }

    public ArrayList<StashHelperPanel> getHelperPanels() {
        return helperPanels;
    }

    public void cleanup() {
        FrameManager.stashHelperContainerLegacy.remove(this);
        for (StashHelperPanel panel : helperPanels) panel.cleanup();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for (Component c : getComponents()) {
            c.setVisible(visible);
        }
    }
}
