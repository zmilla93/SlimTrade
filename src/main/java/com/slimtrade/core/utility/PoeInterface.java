package com.slimtrade.core.utility;

import com.slimtrade.core.trading.TradeOffer;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void paste(String text) {
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
    }

    public static void runCommand(String input, TradeOffer tradeOffer) {
        executor.execute(() -> {
            if (!isGameFocused())
                focusGame();
            int maxWeight = 100;
            int wait = 0;
            while(!isGameFocused() && wait < maxWeight){
                try {
                    wait++;
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(!isGameFocused()) return;
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
        if(!isGameFocused())
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

    public static void focusGame(){
        User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
            char[] className = new char[512];
            User32.INSTANCE.GetClassName(hWnd, className, 512);
            String wText = Native.toString(className);
            if (wText.isEmpty()) {
                return true;
            }
            if (wText.equals("POEWindowClass")) {
                User32.INSTANCE.ShowWindow(hWnd, User32.SW_SHOW);
                User32.INSTANCE.SetForegroundWindow(hWnd);
                User32.INSTANCE.SetFocus(hWnd);
                return false;
            }
            return true;
        }, null);
    }

    public static boolean isGameFocused() {
        return getFocusedWindowTitle().equals("Path of Exile");
    }

    public static String getFocusedWindowTitle() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);
    }

}
