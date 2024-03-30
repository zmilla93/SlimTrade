package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.data.PlayerMessage;
import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;
import com.slimtrade.modules.theme.ThemeColorVariant;

import javax.swing.*;

public class ChatScannerMessagePanel extends NotificationPanel {

    private final PlayerMessage playerMessage;

    public ChatScannerMessagePanel(ChatScannerEntry scannerEntry, PlayerMessage playerMessage) {
        this(scannerEntry, playerMessage, true);
    }

    public ChatScannerMessagePanel(ChatScannerEntry scannerEntry, PlayerMessage playerMessage, boolean createListeners) {
        super(createListeners);
        this.playerMessage = playerMessage;
        if (playerMessage != null) {
            pasteReplacement = new PasteReplacement(playerMessage.player);
            playerNameButton.setText(playerMessage.player);
            itemButton.setText(playerMessage.message);
            addPlayerButtonListener(playerMessage.player);
        }
        pricePanel.add(new JLabel(scannerEntry.title));
        messageColor = ThemeColorVariant.getMessageColor(TradeOfferType.CHAT_SCANNER_MESSAGE);
        topMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.TOP_ROW);
        bottomMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.BOTTOM_ROW);
        setup();
        applyMessageColor();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        messageColor = ThemeColorVariant.getMessageColor(TradeOfferType.CHAT_SCANNER_MESSAGE);
        applyMessageColor();
    }

}
