package github.zmilla93.core.managers;

import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.VisibilityManager;
import github.zmilla93.modules.updater.ZLogger;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

// FIXME : Check threading with this and POEInterface
@Deprecated
public class QuickPasteManager {

    private static Thread thread;
    private static String clipboardContents;

    private static QuickPasteMode mode;
    private static final int CHECK_DELAY_MS = 250;

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
//        HotkeyManager.loadHotkeys();
    }

    public QuickPasteMode getMode() {
        return mode;
    }

    private static void attemptQuickPaste() {
        TradeOffer offer = TradeOffer.getTradeFromQuickPaste(clipboardContents);
        if (offer != null) {
            if (POEInterface.focusGame()) {
                POEInterface.pasteFromClipboard(false);
                clearClipboard();
                VisibilityManager.showOverlay();
            }
        }
    }

    public static synchronized void attemptHotkeyQuickPaste() {
        try {
            clipboardContents = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            TradeOffer offer = TradeOffer.getTradeFromQuickPaste(clipboardContents);
            if (offer != null) {
                if (POEInterface.focusGame()) {
                    POEInterface.pasteFromClipboard(false);
                    VisibilityManager.showOverlay();
                }
            }
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearClipboard() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
    }

    private static void stopListenerThread() {
        if (thread != null && thread.isAlive()) thread.interrupt();
    }

    private static void startListenerThread() {
        if (thread != null && thread.isAlive()) thread.interrupt();
        try {
            clipboardContents = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            TradeOffer offer = TradeOffer.getTradeFromQuickPaste(clipboardContents);
            if (offer != null) clearClipboard();
        } catch (UnsupportedFlavorException | IOException e) {
//            e.printStackTrace();
        }
        thread = new Thread(() -> {
            boolean running = true;
            while (running) {
                try {
                    try {
                        DataFlavor[] flavors = Toolkit.getDefaultToolkit().getSystemClipboard().getAvailableDataFlavors();
                        boolean valid = false;
                        for (DataFlavor flavor : flavors) {
                            if (flavor == DataFlavor.stringFlavor) {
                                valid = true;
                                break;
                            }
                        }
                        if (!valid) return;
                        String newContents = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        if (newContents != null && !newContents.equals(clipboardContents)) {
                            clipboardContents = newContents;
                            attemptQuickPaste();
                        }
                    } catch (IllegalStateException e) {
                        ZLogger.err("Clipboard in illegal state.");
//                        e.printStackTrace();
                        // Ignore, this can get thrown any time another app is using the clipboard
                    } catch (UnsupportedFlavorException | IOException e) {
                        ZLogger.err("Error while reading clipboard.");
                        e.printStackTrace();
                    }
                    Thread.sleep(CHECK_DELAY_MS);
                } catch (InterruptedException e) {
                    running = false;
                }
            }
        });
        thread.start();
    }

}
