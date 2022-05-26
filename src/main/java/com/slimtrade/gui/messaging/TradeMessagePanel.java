package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.ColorThemeType;
import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stash.StashHelperPanel;
import com.slimtrade.gui.stash.StashHighlighterFrame;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TradeMessagePanel extends NotificationPanel {

    private StashHelperPanel stashHelperPanel;
    private StashHighlighterFrame highlighterFrame;

    public TradeMessagePanel(TradeOffer offer) {
        this(offer, true);
    }

    public TradeMessagePanel(TradeOffer trade, boolean createListeners) {
        super(createListeners);
        tradeOffer = trade;
        if (FrameManager.stashHelperContainer != null && trade.offerType == TradeOffer.TradeOfferType.INCOMING) {
            highlighterFrame = new StashHighlighterFrame();
            stashHelperPanel = FrameManager.stashHelperContainer.addHelper(trade);
        }
        playerNameButton.setText(trade.playerName);
        itemButton.setItems(trade.getItems());
        pricePanel.setItem(new SaleItem(trade.priceTypeString, trade.priceQuantity));

        // Message type specific stuff
        switch (trade.offerType) {
            case INCOMING:
                topMacros = SaveManager.settingsSaveFile.data.incomingTopMacros;
                bottomMacros = SaveManager.settingsSaveFile.data.incomingBottomMacros;


                break;
            case OUTGOING:
                topMacros = SaveManager.settingsSaveFile.data.outgoingTopMacros;
                bottomMacros = SaveManager.settingsSaveFile.data.outgoingBottomMacros;
                messageColor = new Color(180, 72, 72);
                break;
        }
        if (createListeners) addListeners();
        updateUI();
        setup();
    }

    private void addListeners() {
        switch (tradeOffer.offerType) {
            case INCOMING:
                itemButton.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            stashHelperPanel.setVisible(true);
                            FrameManager.stashHelperContainer.refresh();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            FrameManager.itemIgnoreWindow.setItemName(tradeOffer.itemName);
                        }
                    }
                });
                break;
            case OUTGOING:
                break;
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (tradeOffer == null) return;
        StashTabColor stashTabColor = tradeOffer.getStashTabColor();
        switch (tradeOffer.offerType) {
            case INCOMING:
                if (!SaveManager.settingsSaveFile.data.applyStashColorToMessage || stashTabColor == StashTabColor.ZERO) {
                    if (ColorManager.getCurrentTheme().themeType == ColorThemeType.LIGHT) {
                        messageColor = new Color(105, 201, 97);
                    } else {
                        messageColor = new Color(38, 145, 32);
                    }
                } else {
                    messageColor = tradeOffer.getStashTabColor().getBackground();
                }
                break;
            case OUTGOING:
                if (ColorManager.getCurrentTheme().themeType == ColorThemeType.LIGHT) {
                    messageColor = new Color(180, 72, 72);
                } else {
                    messageColor = new Color(133, 17, 17);
                }
                break;
            case CHAT_SCANNER:
                if (ColorManager.getCurrentTheme().themeType == ColorThemeType.LIGHT) {
                    messageColor = new Color(222, 144, 89);
                } else {
                    messageColor = new Color(175, 89, 24);
                }
                break;
        }
        applyMessageColor(messageColor);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        if (tradeOffer.offerType == TradeOffer.TradeOfferType.INCOMING) {
            highlighterFrame.dispose();
            FrameManager.stashHelperContainer.remove(stashHelperPanel);
            FrameManager.stashHelperContainer.refresh();
        }
    }

}
