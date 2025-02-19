package github.zmilla93.gui.messaging;

import github.zmilla93.core.data.PasteReplacement;
import github.zmilla93.core.data.PlayerMessage;
import github.zmilla93.core.enums.ButtonRow;
import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;
import github.zmilla93.modules.theme.ThemeColor;
import github.zmilla93.modules.updater.ZLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;

public class ChatScannerMessagePanel extends NotificationPanel {

    private final PlayerMessage playerMessage;
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public ChatScannerMessagePanel(ChatScannerEntry scannerEntry, PlayerMessage playerMessage) {
        this(scannerEntry, playerMessage, true);
    }

    public ChatScannerMessagePanel(ChatScannerEntry scannerEntry, PlayerMessage playerMessage, boolean createListeners) {
        // FIXME : Macros
        super(ThemeColor.SCANNER_MESSAGE, scannerEntry.macros, createListeners);
        this.playerMessage = playerMessage;
        if (playerMessage != null) {
            pasteReplacement = new PasteReplacement(playerMessage.message, playerMessage.player);
            playerNameButton.setText(playerMessage.player);
            itemButton.setText(playerMessage.message);
        }
        pricePanel.add(new JLabel(scannerEntry.title));
        topMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.TOP_ROW);
        bottomMacros = MacroButton.getRowMacros(scannerEntry.macros, ButtonRow.BOTTOM_ROW);
        setup();
    }

    public PlayerMessage getPlayerMessage() {
        return playerMessage;
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        if (playerMessage == null) return;
        if (!playerMessage.isMetaText) addPlayerButtonListener(playerMessage.player);
        itemButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    try {
                        StringSelection copyString = new StringSelection(playerMessage.message);
                        clipboard.setContents(copyString, null);
                    } catch (IllegalStateException err) {
                        ZLogger.err("Failed to set clipboard contents.");
                    }
                }
            }
        });
    }
}
