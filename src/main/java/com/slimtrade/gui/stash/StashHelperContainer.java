package com.slimtrade.gui.stash;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.colortheme.IThemeListener;

import javax.swing.*;
import java.awt.*;

public class StashHelperContainer extends JDialog implements IThemeListener {

    private Container contentPanel;
    private static final int OFFSET = 30;
    private static final int FOLDER_OFFSET = 65;
    private GridBagConstraints gc = ZUtil.getGC();

    public StashHelperContainer() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        contentPanel = getContentPane();
        contentPanel.setLayout(new GridBagLayout());
        gc.insets.right = 5;

        setBackground(ColorManager.TRANSPARENT);

//        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
        setVisible(true);
        updateLocation();

        ColorManager.addFrame(this);
        ColorManager.addListener(this);
    }

    public void updateLocation() {
        Point target = SaveManager.stashSaveFile.data.gridRect.getLocation();
        target.y -= getHeight() + OFFSET;
        setLocation(target);
    }

    public StashHelperPanel addHelper(TradeOffer offer, StashHighlighterFrame highlighterFrame) {
        StashHelperPanel panel = new StashHelperPanel(this, offer, highlighterFrame);
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(panel, gc);
        refresh();
        return panel;
    }

    public void refresh() {
        revalidate();
        repaint();
        pack();
        updateLocation();
    }

    @Override
    public void onThemeChange() {
        refresh();
    }
}
