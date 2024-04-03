package com.slimtrade.gui.overlays;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.messaging.NotificationPanel;
import com.slimtrade.gui.messaging.TradeMessagePanel;
import com.slimtrade.modules.saving.ISaveListener;

import javax.swing.*;
import java.awt.*;

public class MessageOverlay extends AbstractOverlayFrame implements ISaveListener {

    public final NotificationPanel notificationPanel;
    private final JPanel tabWrapper;

    boolean isTabAdded;

    public MessageOverlay() {
        super(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false), "Example Message");
        tabWrapper = new JPanel(new GridBagLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false));
        tabWrapper.add(tabbedPane);
        updateTabDisplay();
        this.notificationPanel = (NotificationPanel) this.panel;
        SaveManager.settingsSaveFile.addListener(this);
    }

    private void updateTabDisplay() {
        if (SaveManager.settingsSaveFile.data.useMessageTabs) {
            if (!isTabAdded) addPanel(tabWrapper);
            isTabAdded = true;
        } else {
            if (isTabAdded) removePanel(tabWrapper);
            isTabAdded = false;
        }
        pack();
    }

    @Override
    public void onSave() {
        updateTabDisplay();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.messageLocation, SaveManager.overlaySaveFile.data.messageExpandDirection);
    }

}
