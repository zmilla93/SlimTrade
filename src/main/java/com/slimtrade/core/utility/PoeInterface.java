package com.slimtrade.core.utility;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.OptionsWindow;
import com.sun.jna.Native;
import com.sun.jna.PointerType;

import com.slimtrade.App;
import com.slimtrade.core.References;
//import main.java.com.slimtrade.core.utility.User32;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.jnativehook.keyboard.NativeKeyEvent;

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
            // robot.setAutoDelay(100);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        wtbTextArray = new String[]{wtbText_English, wtbText_Russian, wtbText_Korean, wtbText_Thai};
    }

    public static void attemptQuickPaste() {
        String text = null;
        try {
            text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            return;
        } catch (IOException e) {
            return;
        }
        if (text == null) {
            return;
        }
        boolean valid = false;
        if(text.startsWith("@")) {
            for(String s : wtbTextArray) {
                if(text.contains(s)) {
                    valid = true;
                    break;
                }
            }
        }
        if(valid) {
            pasteWithFocus(text);
        }


//        String matchString = "@(\\S+) ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
//        Matcher matcher = Pattern.compile(matchString).matcher(text);
//        if (matcher.matches()) {
//            pasteWithFocus(text);
//        }
    }

    public static void paste(String s, boolean... send) {
        pasteString = new StringSelection(s);
        clipboard.setContents(pasteString, null);
        PoeInterface.focus();
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        if (send.length == 0 || send[0] == true) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        FrameManager.forceAllToTop();
    }

    public static void pasteWithFocus(String s) {
        new Thread(new Runnable() {
            public void run() {
                pasteString = new StringSelection(s);
                clipboard.setContents(pasteString, null);
                focus();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                PointerType hwnd = null;
                byte[] windowText = new byte[512];
                int i = 0;
                String curWindowTitle = null;
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
//                System.out.println("POE Focus Time : " + i);
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
