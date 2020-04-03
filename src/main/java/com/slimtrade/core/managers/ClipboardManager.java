package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.enums.QuickPasteSetting;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 *  Adds a flavor change listener to the system clipboard to monitor for trade messages being copied.
 */

public class ClipboardManager implements ClipboardOwner{

    private String lastMessage;
    private volatile boolean disabled = false;

    public ClipboardManager() {

        ClipboardOwner owner = this;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(clipboard.getContents(this), null);
        clipboard.addFlavorListener(new FlavorListener() {
            @Override
            public void flavorsChanged(FlavorEvent e) {
                if(disabled) {
                    return;
                }
                if(App.saveManager.saveFile.quickPasteSetting != QuickPasteSetting.AUTOMATIC) {
                    try {
                        String contents = (String) clipboard.getData(DataFlavor.stringFlavor);
                        clipboard.setContents(clipboard.getContents(this), null);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        return;
                    }
                    return;
                }
                Transferable t;
                try{
                    t = clipboard.getContents(this);
                } catch (IllegalStateException e1) {
                    return;
                }
                String contents;
                try {
                    contents = (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException | IOException ex) {
                    return;
                }
                if(lastMessage == null) {
                    lastMessage = contents;
                    PoeInterface.attemptQuickPaste(contents);
                    new Thread(() -> {
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        try {
                        clipboard.setContents(t, owner);
                        } catch (IllegalStateException e1) {
                            System.out.println("Clipboard Issue");
                            e1.printStackTrace();
                        }
                    }).start();
                } else {
                    lastMessage = null;
                    disabled = true;
                    new Thread(() -> {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        disabled = false;
                    }).start();
                }
            }
        });

    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // Do Nothing
    }
}
