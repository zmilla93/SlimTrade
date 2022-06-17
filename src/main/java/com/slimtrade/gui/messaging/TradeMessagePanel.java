package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stash.StashHelperPanel;
import com.slimtrade.gui.stash.StashHelperWrapper;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class TradeMessagePanel extends NotificationPanel {

    private StashHelperPanel stashHelperPanel;
    private StashHelperWrapper stashHelperWrapper;

    public TradeMessagePanel(TradeOffer offer) {
        this(offer, true);
    }

    public TradeMessagePanel(TradeOffer tradeOffer, boolean createListeners) {
        super(createListeners);
        this.tradeOffer = tradeOffer;
        if (FrameManager.stashHelperContainer != null && tradeOffer.offerType == TradeOffer.TradeOfferType.INCOMING && createListeners) {
            if (this.tradeOffer.isBulkTrade) {
                stashHelperWrapper = new StashHelperWrapper(tradeOffer);
            } else {
                stashHelperPanel = new StashHelperPanel(tradeOffer);
            }
        }
        playerNameButton.setText(tradeOffer.playerName);
        itemButton.setItems(tradeOffer.getItems());
        pricePanel.setItem(new SaleItem(tradeOffer.priceTypeString, tradeOffer.priceQuantity));

        playerNameButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    POEInterface.pasteWithFocus("/whois " + tradeOffer.playerName);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    POEInterface.pasteWithFocus("@" + tradeOffer.playerName, true);
                }
            }
        });
        // Message type specific stuff
        switch (tradeOffer.offerType) {
            case INCOMING:
                topMacros = SaveManager.settingsSaveFile.data.incomingTopMacros;
                bottomMacros = SaveManager.settingsSaveFile.data.incomingBottomMacros;
                break;
            case OUTGOING:
                topMacros = SaveManager.settingsSaveFile.data.outgoingTopMacros;
                bottomMacros = SaveManager.settingsSaveFile.data.outgoingBottomMacros;
                break;
        }
        updateUI();
        setup();
        if (createListeners) addListeners();
    }

    private void addListeners() {
        JPanel self = this;
        switch (tradeOffer.offerType) {
            case INCOMING:
                itemButton.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (stashHelperPanel != null)
                                stashHelperPanel.setVisible(true);
                            if (stashHelperWrapper != null)
                                stashHelperWrapper.setVisible(true);
                            FrameManager.stashHelperContainer.refresh();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            FrameManager.itemIgnoreWindow.setItemName(tradeOffer.itemName);
                        }
                    }
                });
                getCloseButton().addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            FrameManager.messageManager.quickCloseIncoming(tradeOffer);
                        }
                    }
                });
                break;
            case OUTGOING:
                getCloseButton().addMouseListener(new AdvancedMouseListener() {
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
        if (stashHelperWrapper != null) {
            stashHelperWrapper.setVisible(true);
        }
        FrameManager.stashHelperContainer.refresh();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (tradeOffer == null) return;
        StashTabColor stashTabColor = tradeOffer.getStashTabColor();
        if (tradeOffer.offerType == TradeOffer.TradeOfferType.INCOMING
                && SaveManager.settingsSaveFile.data.applyStashColorToMessage
                && stashTabColor != StashTabColor.ZERO
        ) {
            messageColor = tradeOffer.getStashTabColor().getBackground();
            currencyTextColor = tradeOffer.getStashTabColor().getForeground();
        } else {
            messageColor = ColorManager.getCurrentTheme().themeType.getColor(tradeOffer);
        }
        revalidate();
        repaint();
        applyMessageColor();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        if (tradeOffer.offerType == TradeOffer.TradeOfferType.INCOMING) {
            if (stashHelperPanel != null) {
                FrameManager.stashHelperContainer.remove(stashHelperPanel);
                stashHelperPanel.cleanup();
            }
            if (stashHelperWrapper != null) {
                FrameManager.stashHelperContainer.remove(stashHelperWrapper);
                stashHelperWrapper.cleanup();
            }
            FrameManager.stashHelperContainer.refresh();
        }
    }

}
