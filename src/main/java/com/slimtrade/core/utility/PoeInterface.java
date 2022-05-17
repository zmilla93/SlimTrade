package com.slimtrade.core.utility;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.managers.FrameManager;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

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

public class PoeInterface {

    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static Robot robot;
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

    public static void paste() {
        if (!isGameFocused()) return;
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
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.waitForIdle();
        try {
            System.out.println("pasty:" + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void paste(String text) {
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
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void runCommand(String input){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if(!focusGame()) return;
                paste(input);
            }
        });

    }

    public static void runCommand(String input, TradeOffer tradeOffer) {
        executor.execute(() -> {
            if (!isGameFocused())
                focusGame();
            int maxWeight = 100;
            int wait = 0;
            while (!isGameFocused() && wait < maxWeight) {
                try {
                    wait++;
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!isGameFocused()) return;
            System.out.println("Waited: " + wait);
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

    public static void runCommandOLD(String input, TradeOffer tradeOffer) {
        if (!isGameFocused())
            focusGame();
        if (!isGameFocused())
            return;
        ArrayList<String> commands = ZUtil.getCommandList(input, tradeOffer);
        if (commands.size() == 1) paste(commands.get(0));
        else {
            executor.execute(() -> {
                for (String s : commands) {
                    paste(s);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static boolean focusGame() {
        FrameManager.dummyWindow.setVisible(true);
        FrameManager.dummyWindow.setLocation(MouseInfo.getPointerInfo().getLocation());
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
        System.out.println("Wintitle:" + getFocusedWindowTitle());
        return getFocusedWindowTitle().equals("Path of Exile");
    }

    public static String getFocusedWindowTitle() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);
    }

    public static void findInStash(String s) {
        executor.execute(() -> {
            focusGame();
            if (!isGameFocused()) {
                return;
            }
            StringSelection pasteString = new StringSelection(s);
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
