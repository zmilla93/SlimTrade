package github.zmilla93.core.utility;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Handles interacting with the system's Clipboard.
 */
public class ClipboardManager {

    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public static void setContents(String value) {
        clipboard.setContents(new StringSelection(value), null);
    }

}
