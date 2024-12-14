package github.zmilla93.gui.stash;

import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.trading.TradeOffer;

import javax.swing.*;
import java.awt.*;

public class StashHighlighterFramePoe2 extends StashHighlighterFrame {

    public StashHighlighterFramePoe2(TradeOffer tradeOffer) {
        super(tradeOffer);
    }

    @Override
    public void updateSizeAndLocation() {
        boolean isQuadTab = isQuadTab();
        Dimension cellSize = isQuadTab ? POEWindow.getPoe2StashCellSizeQuad() : POEWindow.getPoe2StashCellSize();
        float cellCount = isQuadTab ? 24f : 12f;
        int xOffset = Math.round(POEWindow.getPoe2StashBonds().width * ((tradeOffer.stashTabX - 1) / cellCount));
        int yOffset = Math.round(POEWindow.getPoe2StashBonds().height * ((tradeOffer.stashTabY - 1) / cellCount));
        int x = POEWindow.getPoe2StashBonds().x + xOffset;
        int y = POEWindow.getPoe2StashBonds().y + yOffset;
        setLocation(x, y);
        setSize(cellSize);
    }

    @Override
    public void onGameBoundsChange() {
        SwingUtilities.invokeLater(this::updateSizeAndLocation);
    }

}
