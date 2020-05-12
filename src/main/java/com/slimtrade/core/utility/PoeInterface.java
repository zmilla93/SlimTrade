package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.enums.LangRegex;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.User32;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PoeInterface extends Robot {

    private static StringSelection pasteString;
    private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static Robot robot;

    private static String wtbText_English = "like to buy your";
    private static String wtbText_Russian = "Здравствуйте, хочу купить у вас";
    private static String wtbText_Korean = "구매하고 싶습니다";
    private static String wtbText_Thai = "สวัสดี, เราต้องการจะชื้อของคุณ";
    private static String[] wtbTextArray;

    public PoeInterface() throws AWTException {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        wtbTextArray = new String[]{wtbText_English, wtbText_Russian, wtbText_Korean, wtbText_Thai};
    }

    public static void attemptQuickPaste() {
        String text;
        try {
            text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            return;
        }
        if (text == null) {
            return;
        }
        attemptQuickPaste(text);
    }

    public static void attemptQuickPaste(String text) {
        if (text == null) {
            return;
        }
        boolean valid = false;
        if (text.startsWith("@")) {
            for (LangRegex l : LangRegex.values()) {
//                TradeOffer trade = App.chatParser.validateQuickPaste(text, l);
                if (text.contains(l.CONTAINS_TEXT) && App.chatParser.validateQuickPaste(text, l)) {
                    valid = true;
                    break;
                }
            }
        }
        if (valid) {
            pasteWithFocus(text);
        }
    }

    public static void paste(String s, boolean... send) {
        pasteString = new StringSelection(s);
        try {
            clipboard.setContents(pasteString, null);
        } catch (IllegalStateException e) {
            System.out.println("[SlimTrade] Failed to read clipboard, aborting.");
            return;
        }
        PoeInterface.focus();
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        if (send.length == 0 || send[0]) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        FrameManager.forceAllToTop();
    }

    public static void pasteWithFocus(String s) {
        new Thread(() -> {
            pasteString = new StringSelection(s);
            clipboard.setContents(pasteString, null);
            focus();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            PointerType hwnd;
            byte[] windowText = new byte[512];
            int i = 0;
            String curWindowTitle;
            do {
                hwnd = User32.INSTANCE.GetForegroundWindow();
                if (hwnd != null) {
                    User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
                    curWindowTitle = Native.toString(windowText);
                    if (curWindowTitle.equals(References.POE_WINDOW_TITLE)) {
                        break;
                    } else if (i > 400) {
                        return;
                    }
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                i++;
            } while (true);
            FrameManager.forceAllToTop();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }).start();
    }

//    public static void runCommand(String[] commands, String player, String item, String price, String fullMessage) {
//        runCommand(commands, player, item, price, fullMessage, mes);
//    }

    public static void runCommand(String command) {
        runCommand(command, null);
    }

    public static void runCommand(String command, MessageType type) {
        new Thread(() -> {
            focus();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            PointerType hwnd;
            byte[] windowText = new byte[512];
            int i = 0;
            String curWindowTitle;
            do {
                hwnd = User32.INSTANCE.GetForegroundWindow();
                if (hwnd != null) {
                    User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
                    curWindowTitle = Native.toString(windowText);
                    if (curWindowTitle.equals(References.POE_WINDOW_TITLE)) {
                        break;
                    } else if (i > 400) {
                        return;
                    }
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                i++;
            } while (true);
            FrameManager.forceAllToTop();
            pasteString = new StringSelection(command);
            int attempt = 1;
            int MAX_ATTEMPTS = 3;
            while (attempt <= MAX_ATTEMPTS) {
                try {
                    clipboard.setContents(pasteString, null);
                    break;
                } catch (IllegalStateException e) {
                    // TODO : LOG
                    System.out.println("Retrying clipboard...");
                    if (attempt == MAX_ATTEMPTS) {
                        System.out.println("Aborting clipboard...");
                        return;
                    }
                }
                attempt++;
            }
            //TODO Open
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void runCommand(String[] commands, String player, String item, String price, String fullMessage, MessageType messageType) {
        if (commands.length == 0) {
            return;
        }
        new Thread(() -> {
            focus();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            PointerType hwnd;
            byte[] windowText = new byte[512];
            int i = 0;
            String curWindowTitle;
            do {
                hwnd = User32.INSTANCE.GetForegroundWindow();
                if (hwnd != null) {
                    User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
                    curWindowTitle = Native.toString(windowText);
                    if (curWindowTitle.equals(References.POE_WINDOW_TITLE)) {
                        break;
                    } else if (i > 400) {
                        return;
                    }
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                i++;
            } while (true);
            FrameManager.forceAllToTop();
            for (String cmd : commands) {
                cmd = cmd.replaceAll("\\{self\\}", App.saveManager.saveFile.characterName);
                cmd = cmd.replaceAll("\\{player\\}", player);
                cmd = cmd.replaceAll("\\{item\\}", item);
                cmd = cmd.replaceAll("\\{price\\}", price);
                cmd = cmd.replaceAll("\\{message\\}", fullMessage);
                pasteString = new StringSelection(cmd);
                int attempt = 1;
                int MAX_ATTEMPTS = 3;
                while (attempt <= MAX_ATTEMPTS) {
                    try {
                        clipboard.setContents(pasteString, null);
                        break;
                    } catch (IllegalStateException e) {
                        // TODO : LOG
                        System.out.println("Retrying clipboard...");
                        if (attempt == MAX_ATTEMPTS) {
                            System.out.println("Aborting clipboard...");
                            return;
                        }
                    }
                    attempt++;
                }
                if (cmd.contains("/invite")) {
                    FrameManager.messageManager.showStashHelper(fullMessage, messageType);
                }
                robot.keyPress(KeyEvent.VK_ALT);
                robot.keyRelease(KeyEvent.VK_ALT);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void findInStash(String s) {
        focus();
        pasteString = new StringSelection(s);
        clipboard.setContents(pasteString, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_F);
        robot.keyRelease(KeyEvent.VK_F);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        FrameManager.forceAllToTop();
    }

    public static void focus() {
        User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
            char[] className = new char[512];
            User32.INSTANCE.GetClassName(hWnd, className, 512);
            String wText = Native.toString(className);
            if (wText.isEmpty()) {
                return true;
            }
            if (wText.equals("POEWindowClass")) {
                User32.INSTANCE.SetForegroundWindow(hWnd);
                return false;
            }
            return true;
        }, null);
    }

    public static boolean isPoeFocused() {

        byte[] windowText = new byte[512];
        PointerType hwnd = User32Custom.INSTANCE.GetForegroundWindow();
        User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        String curWindowTitle = Native.toString(windowText);
        if (curWindowTitle.equals(References.POE_WINDOW_TITLE)) {
            return true;
        }
        return false;
    }
}
