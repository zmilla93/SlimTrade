package github.zmilla93.core.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DesignerCopyMonitor implements Runnable {

    private static boolean running = false;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final int delayMs = 100;
    private static String previousString = "";

    private final JTextArea textArea;
    private static DesignerCopyMonitor currentMonitor;
    private static boolean newLine = true;

    public DesignerCopyMonitor(JTextArea textArea) {
        this.textArea = textArea;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(""), null);
    }

    public static void start(JTextArea textArea) {
        running = true;
        newLine = true;
        currentMonitor = new DesignerCopyMonitor(textArea);
        executor.submit(currentMonitor);
    }

    public static void stop() {
        running = false;
    }

    public static void lineBreak() {
        currentMonitor.textArea.setText(currentMonitor.textArea.getText() + "\n");
        newLine = true;
    }

    private void parseClipboard(String value) {
        if (!value.startsWith("Item Class")) return;
        String[] values = value.split("\n");
        if (values.length < 3) return;
        String text = textArea.getText();
        if (!newLine) text += ", ";
        text += values[2];
        textArea.setText(text);
        System.out.println(values[2]);
        newLine = false;
    }

    @Override
    public void run() {
        while (running) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                String value = (String) clipboard.getData(DataFlavor.stringFlavor);
                if (!previousString.equals(value)) {
                    parseClipboard(value);
                    previousString = value;
                }
            } catch (UnsupportedFlavorException | IOException e) {
                throw new RuntimeException(e);
            }
            sleep();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
