package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.data.PlayerMessage;
import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.modules.theme.ThemeColorVariant;
import com.slimtrade.modules.updater.ZLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

public class ChatScannerMessagePanel extends NotificationPanel {

    private final PlayerMessage playerMessage;
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public ChatScannerMessagePanel(ChatScannerEntry scannerEntry, PlayerMessage playerMessage) {
        this(scannerEntry, playerMessage, true);
    }

    public ChatScannerMessagePanel(ChatScannerEntry scannerEntry, PlayerMessage playerMessage, boolean createListeners) {
        super(createListeners);
        this.playerMessage = playerMessage;
        if (playerMessage != null) {
            pasteReplacement = new PasteReplacement(playerMessage.message, playerMessage.player);
            playerNameButton.setText(playerMessage.player);
            itemButton.setText(playerMessage.message);
            addPlayerButtonListener(playerMessage.player);
        }
        pricePanel.add(new JLabel(scannerEntry.title));
        messageColor = ThemeColorVariant.getMessageColor(TradeOfferType.CHAT_SCANNER_MESSAGE);
        topMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.TOP_ROW);
        bottomMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.BOTTOM_ROW);
        setup();
    }

    public PlayerMessage getPlayerMessage() {
        return playerMessage;
    }

    @Override
    protected void resolveMessageColor() {
        messageColor = ThemeColorVariant.getMessageColor(TradeOfferType.CHAT_SCANNER_MESSAGE);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        addPlayerButtonListener(playerMessage.player);
        itemButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    try {
                        StringSelection copyString = new StringSelection(playerMessage.message);
                        clipboard.setContents(copyString, null);
                    } catch (IllegalStateException err) {
                        ZLogger.err("Failed to set clipboard contents, aborting...");
                        return;
                    }
                }
            }
        });
    }
}
