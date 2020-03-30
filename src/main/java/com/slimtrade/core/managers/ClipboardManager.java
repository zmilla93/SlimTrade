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

    public ClipboardManager() {

        ClipboardOwner owner = this;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(clipboard.getContents(this), null);

        clipboard.addFlavorListener(new FlavorListener() {
            @Override
            public void flavorsChanged(FlavorEvent e) {
                if(App.saveManager.saveFile.quickPasteSetting != QuickPasteSetting.AUTOMATIC) {
                    return;
                }
                Transferable t = clipboard.getContents(this);
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
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        clipboard.setContents(t, owner);
                    }).start();
                } else {
                    lastMessage = null;
                }
            }
        });

    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // Do Nothing
    }
}
