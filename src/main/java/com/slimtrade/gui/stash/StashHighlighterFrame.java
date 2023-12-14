package com.slimtrade.gui.stash;

import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.options.stash.StashTabType;
import com.slimtrade.gui.windows.AbstractDialog;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class StashHighlighterFrame extends AbstractDialog {

    private final TradeOffer tradeOffer;
    private Timer timer;

    public StashHighlighterFrame(TradeOffer tradeOffer) {
        assert (SwingUtilities.isEventDispatchThread());
        this.tradeOffer = tradeOffer;
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setBackground(ThemeManager.TRANSPARENT);
        contentPanel.setBackground(ThemeManager.TRANSPARENT);
        contentPanel.setBorder(BorderFactory.createLineBorder(tradeOffer.getStashTabColor().getBackground(), 2));
        timer = new Timer(2000, e -> {
            timer.stop();
            setVisible(false);
        });
        pack();
        setSizeAndLocation();
    }

    private void setSizeAndLocation() {
        boolean isQuadTab = isQuadTab();
        Dimension cellSize = isQuadTab ? SaveManager.stashSaveFile.data.getCellSizeQuad() : SaveManager.stashSaveFile.data.getCellSize();
        float cellCount = isQuadTab ? 24f : 12f;
        int xOffset = Math.round(SaveManager.stashSaveFile.data.gridRect.width * ((tradeOffer.stashTabX - 1) / cellCount));
        int yOffset = Math.round(SaveManager.stashSaveFile.data.gridRect.height * ((tradeOffer.stashTabY - 1) / cellCount));
        int x = SaveManager.stashSaveFile.data.gridRect.x + xOffset;
        int y = SaveManager.stashSaveFile.data.gridRect.y + yOffset;
        setLocation(x, y);
        setSize(cellSize);
    }

    private boolean isQuadTab() {
        if (tradeOffer.stashTabName == null) return false;
        if (tradeOffer.stashTabX > 12 || tradeOffer.stashTabY > 12) return true;
        for (StashTabData savedStashTab : SaveManager.settingsSaveFile.data.stashTabs) {
            if (savedStashTab.matchType == MatchType.EXACT_MATCH && tradeOffer.stashTabName.equals(savedStashTab.stashTabName))
                return savedStashTab.stashTabType == StashTabType.QUAD;
            else if (savedStashTab.matchType == MatchType.CONTAINS_TEXT && tradeOffer.stashTabName.contains(savedStashTab.stashTabName))
                return savedStashTab.stashTabType == StashTabType.QUAD;
        }
        return false;
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

}
