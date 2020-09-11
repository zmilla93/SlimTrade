package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.enums.QuickPasteSetting;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * Adds a flavor change listener to the system clipboard to monitor for trade messages being copied.
 */

public class ClipboardManager implements ClipboardOwner {

    private String lastMessage;
    private volatile boolean disabled = false;
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private FlavorListener listener;

    public ClipboardManager() {
        listener = e -> new Thread(() -> {
            if (disabled) {
                return;
            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
            String contents = getClipboardContents();
            if (lastMessage == null) {
                lastMessage = contents;
                if (App.saveManager.settingsSaveFile.quickPasteSetting == QuickPasteSetting.AUTOMATIC) {
//                    PoeInterface.attemptQuickPaste(contents);
                    PoeInterface.attemptQuickPaste();
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                refreshClipboard();
            } else {
                lastMessage = null;
                disabled = true;
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                disabled = false;
            }
        }).start();
    }

    public void setListeningState(boolean state) {
        clipboard.removeFlavorListener(listener);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            clipboard.setContents(clipboard.getContents(this), this);
        } catch (IllegalStateException e) {
            System.out.println("Issue while initializing clipboard");
        }
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (state) {
                clipboard.addFlavorListener(listener);
            } else {
                return;
            }
        }).start();

    }

    private String getClipboardContents() {
        String contents;
        try {
            contents = (String) clipboard.getData(DataFlavor.stringFlavor);
            return contents;
        } catch (UnsupportedFlavorException | IOException | IllegalStateException e) {
            System.out.println("Failed to get clipboard contents");
            return null;
        }
    }

    private void refreshClipboard() {
        Transferable transferable;
        try {
            transferable = clipboard.getContents(this);
        } catch (IllegalStateException e) {
            System.out.println("Failed to refresh clipboard (getContents)");
            return;
        }
        try {
            clipboard.setContents(transferable, this);
        } catch (IllegalStateException e) {
            System.out.println("Failed to refresh clipboard (setContents)");
        }
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable transferable) {
        // Do Nothing
    }

}
