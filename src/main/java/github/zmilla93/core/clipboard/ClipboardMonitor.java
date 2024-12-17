package github.zmilla93.core.clipboard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Detects when the clipboard's currently copied string changes, and informs {@link ClipboardListener}s.
 * A better solution would be using jna callbacks, but that is more complicated and Windows only.
 * This is a good starting solution that can always be used as a cross-platform fallback.
 */
public class ClipboardMonitor {

    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static Thread thread;
    private static String currentString;
    private static boolean running = false;
    private static boolean initialized = false;
    private static final int sleepDurationMs = 200;
    private static final ArrayList<ClipboardListener> listeners = new ArrayList<>();

    public static void addListener(ClipboardListener listener) {
        listeners.add(listener);
        startThread();
    }

    public static void removeListener(ClipboardListener listener) {
        listeners.remove(listener);
        if (listeners.isEmpty())
            stopThread();
    }

    private static void tryUpdateClipboardContents() {
        try {
            Transferable contents = clipboard.getContents(DataFlavor.stringFlavor);
            if (!contents.isDataFlavorSupported(DataFlavor.stringFlavor)) return;
            String newString = (String) contents.getTransferData(DataFlavor.stringFlavor);
            if (!newString.equals(currentString)) {
                currentString = newString;
                if (initialized) {
                    for (ClipboardListener listener : listeners)
                        listener.onStringChange(newString);
                }
                initialized = true;
            }
        } catch (UnsupportedFlavorException | IOException ignore) {
        }
    }

    private static void stopThread() {
        if (thread == null) return;
        thread.interrupt();
        running = false;
    }

    private static void startThread() {
        if (running) return;
        running = true;
        initialized = false;
        thread = new Thread(() -> {
            System.out.println("Starting clipboard monitoring thread.");
            while (true) {
                tryUpdateClipboardContents();
                try {
                    Thread.sleep(sleepDurationMs);
                } catch (InterruptedException e) {
                    System.out.println("Clipboard monitoring thread stopped.");
                    return;
                }
            }
        });
        thread.start();
    }

}
