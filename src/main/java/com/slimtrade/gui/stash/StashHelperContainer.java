package com.slimtrade.gui.stash;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class StashHelperContainer extends JDialog {

    private Container contentPanel;
    private static final int OFFSET = 30;
    private static final int FOLDEr_OFFSET = 65;
    private GridBagConstraints gc = ZUtil.getGC();

    public StashHelperContainer() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        contentPanel = getContentPane();
        contentPanel.setLayout(new FlowLayout(5));
        contentPanel.setLayout(new GridBagLayout());
        gc.insets.right = 5;

        setBackground(ColorManager.TRANSPARENT);

        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
        addHelper(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
        setVisible(true);
        updateLocation();

        ColorManager.addFrame(this);
    }

    public void updateLocation() {
        Point target = SaveManager.stashSaveFile.data.gridRect.getLocation();
        System.out.println(getHeight());
        target.y -= getHeight() + OFFSET;
        setLocation(target);
    }

    public void addHelper(TradeOffer offer) {
        StashHelperPanel panel = new StashHelperPanel(offer, StashTabColor.TEN);
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(panel, gc);
        pack();
        updateLocation();
    }

    public void removeHelper(JPanel panel) {

    }

}
