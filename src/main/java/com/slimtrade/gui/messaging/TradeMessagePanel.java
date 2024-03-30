package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.gui.components.CurrencyLabelFactory;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stash.StashHelperBulkWrapper;
import com.slimtrade.gui.stash.StashHelperPanel;
import com.slimtrade.modules.theme.ThemeColorVariant;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class TradeMessagePanel extends NotificationPanel {

    private StashHelperPanel stashHelperPanel;
    private StashHelperBulkWrapper stashHelperBulkWrapper;

    public TradeMessagePanel(TradeOffer offer) {
        this(offer, true);
    }

    public TradeMessagePanel(TradeOffer tradeOffer, boolean createListeners) {
        super(createListeners);
        this.tradeOffer = tradeOffer;
        this.pasteReplacement = new PasteReplacement(tradeOffer.message, tradeOffer.playerName, tradeOffer.itemName, tradeOffer.itemQuantity, tradeOffer.priceName, tradeOffer.priceQuantity);
        if (FrameManager.stashHelperContainer != null && tradeOffer.offerType == TradeOfferType.INCOMING_TRADE && createListeners) {
            if (this.tradeOffer.isBulkTrade) {
                stashHelperBulkWrapper = new StashHelperBulkWrapper(tradeOffer);
            } else {
                stashHelperPanel = new StashHelperPanel(tradeOffer);
            }
        }
        playerNameButton.setText(tradeOffer.playerName);
        JPanel itemPanel = new JPanel();
        itemPanel.setOpaque(false);
        boolean forceText = tradeOffer.getItems().size() <= 1;
        CurrencyLabelFactory.applyItemToComponent(itemPanel, tradeOffer.getItems(), forceText);
        itemButton.add(itemPanel);
        CurrencyLabelFactory.applyItemToComponent(pricePanel, new SaleItem(tradeOffer.priceName, tradeOffer.priceQuantity).toArrayList());
        // Message type specific stuff
        switch (tradeOffer.offerType) {
            case INCOMING_TRADE:
                topMacros = SaveManager.settingsSaveFile.data.incomingTopMacros;
                bottomMacros = SaveManager.settingsSaveFile.data.incomingBottomMacros;
                break;
            case OUTGOING_TRADE:
                topMacros = SaveManager.settingsSaveFile.data.outgoingTopMacros;
                bottomMacros = SaveManager.settingsSaveFile.data.outgoingBottomMacros;
                break;
        }
        updateUI();
        setup();
        if (createListeners) addListeners();
    }

    private void addListeners() {
        TradeMessagePanel self = this;
        addPlayerButtonListener(tradeOffer.playerName);
        switch (tradeOffer.offerType) {
            case INCOMING_TRADE:
                itemButton.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (stashHelperPanel != null)
                                stashHelperPanel.setVisible(true);
                            if (stashHelperBulkWrapper != null)
                                stashHelperBulkWrapper.setVisible(true);
                            FrameManager.stashHelperContainer.refresh();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            FrameManager.itemIgnoreWindow.setItemName(tradeOffer.itemName);
                        }
                    }
                });
                closeButton.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            FrameManager.messageManager.quickCloseIncoming(tradeOffer);
                        }
                    }
                });
                break;
            case OUTGOING_TRADE:
                closeButton.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3)
                            FrameManager.messageManager.quickCloseOutgoing(self);
                    }
                });
                break;
        }
    }

    @Override
    protected void onInvite() {
        super.onInvite();
        if (stashHelperPanel != null)
            stashHelperPanel.setVisible(true);
        if (stashHelperBulkWrapper != null) {
            stashHelperBulkWrapper.setVisible(true);
        }
        FrameManager.stashHelperContainer.refresh();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (tradeOffer == null) return;
        StashTabColor stashTabColor = tradeOffer.getStashTabColor();
        if (tradeOffer.offerType == TradeOfferType.INCOMING_TRADE
                && SaveManager.settingsSaveFile.data.applyStashColorToMessage
                && stashTabColor != StashTabColor.ZERO
        ) {
            messageColor = tradeOffer.getStashTabColor().getBackground();
            currencyTextColor = tradeOffer.getStashTabColor().getForeground();
        } else {
            messageColor = ThemeColorVariant.getMessageColor(tradeOffer.offerType);
            currencyTextColor = null;
        }
        revalidate();
        repaint();
        applyMessageColor();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        if (tradeOffer.offerType == TradeOfferType.INCOMING_TRADE) {
            if (stashHelperPanel != null) {
                FrameManager.stashHelperContainer.remove(stashHelperPanel);
                stashHelperPanel.cleanup();
            }
            if (stashHelperBulkWrapper != null) {
                FrameManager.stashHelperContainer.remove(stashHelperBulkWrapper);
                stashHelperBulkWrapper.cleanup();
            }
            FrameManager.stashHelperContainer.refresh();
        }
    }

}
