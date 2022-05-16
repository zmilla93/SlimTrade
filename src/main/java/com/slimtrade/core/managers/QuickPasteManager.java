package com.slimtrade.core.managers;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.HotkeyManager;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class QuickPasteManager {

    private static Thread thread;
    private static String clipboardContents;

    private static QuickPasteMode mode;

    public enum QuickPasteMode {
        DISABLED, HOTKEY, AUTOMATIC;

        @Override
        public String toString() {
            return ZUtil.enumToString(name());
        }
    }

    public static synchronized void setMode(QuickPasteMode mode) {
        if (mode == null) {
            QuickPasteManager.mode = QuickPasteMode.DISABLED;
            stopListenerThread();
            return;
        }
        QuickPasteManager.mode = mode;
        switch (mode) {
            case AUTOMATIC:
                startListenerThread();
                break;
            case HOTKEY:
            case DISABLED:
            default:
                stopListenerThread();
                break;
        }
        HotkeyManager.loadHotkeys();
    }

    public QuickPasteMode getMode() {
        return mode;
    }

    public static synchronized void attemptHotkeyQuickPaste() {
        try {
            clipboardContents = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            TradeOffer offer = TradeOffer.getTradeOffer(clipboardContents);
            if (offer != null) {
                PoeInterface.focusGame();
                PoeInterface.paste();
            }
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void stopListenerThread() {
        if (thread != null && thread.isAlive()) thread.interrupt();
    }

    private static void startListenerThread() {
        if (thread != null && thread.isAlive()) thread.interrupt();
        try {
            clipboardContents = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            TradeOffer offer = TradeOffer.getTradeOffer(clipboardContents);
            if (offer != null) clearClipboard();
        } catch (UnsupportedFlavorException | IOException e) {
//            e.printStackTrace();
        }
        thread = new Thread(() -> {
            try {
                while (true) {
                    try {
                        String newContents = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        if (newContents != null && !newContents.equals(clipboardContents)) {
                            clipboardContents = newContents;
                            attemptQuickPaste();
                        }
                    } catch (UnsupportedFlavorException | IOException | IllegalStateException e) {
                        // Ignore
                    }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
    }

    private static void attemptQuickPaste() {
        TradeOffer offer = TradeOffer.getTradeOffer(clipboardContents);
        if (offer != null) {
            PoeInterface.focusGame();
            PoeInterface.paste();
            clearClipboard();
        }
    }

    private static void clearClipboard() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
    }

}
