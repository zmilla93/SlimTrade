package github.zmilla93.gui.overlays;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.TradeUtil;
import github.zmilla93.gui.messaging.NotificationPanel;
import github.zmilla93.gui.messaging.TradeMessagePanel;
import github.zmilla93.modules.saving.ISaveListener;

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
