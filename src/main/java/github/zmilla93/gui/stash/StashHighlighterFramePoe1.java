package github.zmilla93.gui.stash;

import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.trading.TradeOffer;

import javax.swing.*;
import java.awt.*;

public class StashHighlighterFramePoe1 extends StashHighlighterFrame {

    public StashHighlighterFramePoe1(TradeOffer tradeOffer) {
        super(tradeOffer);
    }

    @Override
    public void updateSizeAndLocation() {
        boolean isQuadTab = isQuadTab();
        Dimension cellSize = isQuadTab ? POEWindow.getPoe1StashCellSizeQuad() : POEWindow.getPoe1StashCellSize();
        float cellCount = isQuadTab ? 24f : 12f;
        int xOffset = Math.round(POEWindow.getPoe1StashBonds().width * ((tradeOffer.stashTabX - 1) / cellCount));
        int yOffset = Math.round(POEWindow.getPoe1StashBonds().height * ((tradeOffer.stashTabY - 1) / cellCount));
        int x = POEWindow.getPoe1StashBonds().x + xOffset;
        int y = POEWindow.getPoe1StashBonds().y + yOffset;
        setLocation(x, y);
        setSize(cellSize);
    }

    @Override
    public void onGameBoundsChange() {
        SwingUtilities.invokeLater(this::updateSizeAndLocation);
    }

}
