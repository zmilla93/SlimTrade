package github.zmilla93.gui.messaging;

import github.zmilla93.core.data.PasteReplacement;
import github.zmilla93.core.data.SaleItem;
import github.zmilla93.core.enums.StashTabColor;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.gui.components.CurrencyLabelFactory;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.stash.StashHelperBulkWrapper;
import github.zmilla93.gui.stash.StashHelperPanel;
import github.zmilla93.modules.theme.ThemeColorVariant;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class TradeMessagePanel extends NotificationPanel {

    private StashHelperPanel stashHelperPanel;
    private StashHelperBulkWrapper stashHelperBulkWrapper;
    private final TradeOffer tradeOffer;

    public TradeMessagePanel(TradeOffer offer) {
        this(offer, true);
    }

    public TradeMessagePanel(TradeOffer tradeOffer, boolean createListeners) {
        super(createListeners);
        this.tradeOffer = tradeOffer;
        this.pasteReplacement = new PasteReplacement(tradeOffer.message, tradeOffer.playerName, tradeOffer.itemName, tradeOffer.itemQuantity, tradeOffer.priceName, tradeOffer.priceQuantity);
        if (tradeOffer.offerType == TradeOfferType.INCOMING_TRADE && createListeners) {
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
        CurrencyLabelFactory.applyItemToComponent(itemPanel, tradeOffer.game, tradeOffer.getItems(), forceText);
        itemButton.add(itemPanel);
        CurrencyLabelFactory.applyItemToComponent(pricePanel, tradeOffer.game, new SaleItem(tradeOffer.priceName, tradeOffer.priceQuantity).toArrayList());
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
        setup();
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        TradeMessagePanel self = this;
        addPlayerButtonListener(tradeOffer.playerName);
        switch (tradeOffer.offerType) {
            case INCOMING_TRADE:
                itemButton.addMouseListener(new AdvancedMouseListener() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (stashHelperPanel != null) stashHelperPanel.setVisible(true);
                            if (stashHelperBulkWrapper != null) stashHelperBulkWrapper.setVisible(true);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            FrameManager.itemIgnoreWindow.showWindow(tradeOffer.itemName);
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

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    @Override
    protected void resolveMessageColor() {
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
    }

    @Override
    protected void onInvite() {
        super.onInvite();
        if (stashHelperPanel != null) stashHelperPanel.setVisible(true);
        if (stashHelperBulkWrapper != null) stashHelperBulkWrapper.setVisible(true);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        if (tradeOffer.offerType == TradeOfferType.INCOMING_TRADE) {
            if (stashHelperPanel != null) stashHelperPanel.cleanup();
            if (stashHelperBulkWrapper != null) stashHelperBulkWrapper.cleanup();
        }
    }

}
