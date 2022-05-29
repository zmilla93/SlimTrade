package com.slimtrade.core.utility;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.DummyWindow;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Interacts with Path of Exile using a Robot
 * https://docs.oracle.com/javase/7/docs/api/java/awt/Robot.html
 */
public class POEInterface {

    private static Robot robot;
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static final int MAX_TITLE_LENGTH = 1024;

    public static void init() {
        try {
            robot = new Robot();
            robot.setAutoWaitForIdle(true);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void pasteFromClipboard() {
        assert (!SwingUtilities.isEventDispatchThread());
        if (!isGameFocused()) return;
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        // This sleep is required
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.waitForIdle();
        try {
            System.out.println("QuickPaste:" + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void paste(String text) {
        paste(text, false);
    }

    public static void paste(String text, boolean stopBeforeSend) {
        if (!isGameFocused()) return;
        StringSelection pasteString = new StringSelection(text);
        try {
            clipboard.setContents(pasteString, null);
        } catch (IllegalStateException e) {
            System.out.println("Failed to set clipboard contents, aborting...");
            return;
        }
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        if (stopBeforeSend) return;
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void pasteWithFocus(String input) {
        pasteWithFocus(input, false);
    }

    public static void pasteWithFocus(String input, boolean stopBeforeSend) {
        assert (SwingUtilities.isEventDispatchThread());
        executor.execute(() -> {
            if (!focusGame()) return;
            paste(input, stopBeforeSend);
        });
    }

    public static void pasteWithFocus(String input, TradeOffer tradeOffer) {
        assert (SwingUtilities.isEventDispatchThread());
        executor.execute(() -> {
            if (!focusGame()) return;
            ArrayList<String> commands = ZUtil.getCommandList(input, tradeOffer);
            if (commands.size() == 1) paste(commands.get(0));
            else {
                for (String s : commands) {
                    paste(s);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * SlimTrade can't focus Path of Exile unless it has focus itself, so the robot clicks a dummy window first.
     *
     * @return True if game was successfully focused.
     */
    public static boolean focusGame() {
        assert (!SwingUtilities.isEventDispatchThread());
        System.out.println("try focus game");
        if (isGameFocused()) return true;
        FrameManager.dummyWindow.setVisible(true);
        Point point = MouseInfo.getPointerInfo().getLocation();
        point.x -= DummyWindow.HALF_SIZE;
        point.y -= DummyWindow.HALF_SIZE;
        FrameManager.dummyWindow.setLocation(point);
        robot.mousePress(0);
        robot.mouseRelease(0);
        FrameManager.dummyWindow.setVisible(false);
        User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
            char[] className = new char[512];
            User32.INSTANCE.GetClassName(hWnd, className, 512);
            String wText = Native.toString(className);
            if (wText.isEmpty()) {
                return true;
            }
            if (wText.equals("POEWindowClass")) {
                User32.INSTANCE.SetForegroundWindow(hWnd);
                User32.INSTANCE.SetFocus(hWnd);
                User32.INSTANCE.ShowWindow(hWnd, User32.SW_SHOW);
                return false;
            }
            return true;
        }, null);
        int i = 0;
        while (!isGameFocused()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            if (i > 100) {
                break;
            }
        }
        return isGameFocused();
    }

    public static boolean isGameFocused() {
        return isGameFocused(false);
    }

    public static boolean isGameFocused(boolean includeApp) {
        String focusedWindowTitle = getFocusedWindowTitle();
        if (includeApp && focusedWindowTitle.startsWith("SLIMTRADEAPP")) return true;
        return focusedWindowTitle.equals("Path of Exile");
    }

    private static String getFocusedWindowTitle() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);
    }

    /**
     * Adds a string to the clipboard, runs ctrl+f, then ctrl+v.
     * Runs in a separate thread.
     *
     * @param term Word to paste
     */
    public static void searchInStash(String term) {
        assert (SwingUtilities.isEventDispatchThread());
        executor.execute(() -> {
            if (!focusGame()) {
                return;
            }
            StringSelection pasteString = new StringSelection(term);
            clipboard.setContents(pasteString, null);
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_F);
            robot.keyRelease(KeyEvent.VK_F);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        });
    }

}
