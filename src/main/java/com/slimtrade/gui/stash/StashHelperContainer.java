package com.slimtrade.gui.stash;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.colortheme.IThemeListener;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

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
        System.out.println(getHeight());
        target.y -= getHeight() + OFFSET;
        setLocation(target);
    }

    public void addHelper(TradeOffer offer) {
        Random rng = new Random();
//        rng.nextInt(26);
        StashHelperPanel panel = new StashHelperPanel(this, offer, StashTabColor.values()[rng.nextInt(26)]);
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(panel, gc);

        updateLocation();
        refresh();
    }

    public void removeHelper(JPanel panel) {

    }

    public void refresh() {
        revalidate();
        repaint();
        pack();
    }

    @Override
    public void onThemeChange() {
        refresh();
    }
}
