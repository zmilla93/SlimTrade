package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.data.PlayerMessage;
import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;

import javax.swing.*;

public class ChatScannerMessagePanel extends NotificationPanel {

    private PlayerMessage playerMessage;

    public ChatScannerMessagePanel(ChatScannerEntry tradeOffer, PlayerMessage playerMessage) {
        this(tradeOffer, playerMessage, true);
    }

    public ChatScannerMessagePanel(ChatScannerEntry scannerEntry, PlayerMessage playerMessage, boolean createListeners) {
        super(createListeners);
        this.playerMessage = playerMessage;
        if (playerMessage != null){
            pasteReplacement = new PasteReplacement(SaveManager.settingsSaveFile.data.characterName, playerMessage.player);
        }
        if(playerMessage != null){
            playerNameButton.setText(playerMessage.player);
            itemButton.setText(playerMessage.message);
        }
        pricePanel.add(new JLabel(scannerEntry.title));
        messageColor = ColorManager.getCurrentTheme().themeType.getColor(TradeOfferType.CHAT_SCANNER_MESSAGE);
        topMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.TOP_ROW);
        bottomMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.BOTTOM_ROW);
        setup();
        applyMessageColor();
    }

}
