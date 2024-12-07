package com.slimtrade.core.utility;

import com.slimtrade.core.References;
import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.core.jna.JnaAwtEvent;
import com.slimtrade.core.jna.NativeWindow;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.ClientFileChooser;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.DummyWindow;
import com.slimtrade.modules.updater.ZLogger;
import com.sun.jna.Native;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Interacts with Path of Exile using a <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/Robot.html">Java Robot</a>.
 */
public class POEInterface {

    private static Robot robot;
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static final Random random = new Random();
    private static final int MAX_TITLE_LENGTH = 1024;
    private static WinDef.HWND gameWindowHandle;

    private static final HashSet<String> gameTitleSet = new HashSet<>();
    private static final String[] gameTitles = new String[]{
            "Path of Exile",
            "Path of Exile 2",
            "Path of Exile on GeForce NOW",
            "Path of Exile 2 on GeForce NOW",
    };

    static {
        gameTitleSet.addAll(Arrays.asList(gameTitles));
        try {
            robot = new Robot();
//            robot.setAutoWaitForIdle(true);
        } catch (AWTException e) {
            // FIXME : Logging
            e.printStackTrace();
        }
    }

    // FIXME : invert stop before send
    public static void pasteFromClipboard(boolean stopBeforeSend) {
        assert (!SwingUtilities.isEventDispatchThread());
        // FIXME: isGameFocused is called twice
        if (!isGameFocused()) return;
        // Clear Alt
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_ALT);
        // Try opening chat with custom hotkey
        HotkeyData poeChatHotkey = SaveManager.settingsSaveFile.data.poeChatHotkey;
        boolean openChatOverride = false;
        if (poeChatHotkey != null) {
            int keyCode = JnaAwtEvent.hotkeyToEvent(poeChatHotkey);
            if (keyCode != -1) {
                if (poeChatHotkey.isAltPressed()) robot.keyPress(KeyEvent.VK_ALT);
                if (poeChatHotkey.isCtrlPressed()) robot.keyPress(KeyEvent.VK_CONTROL);
                if (poeChatHotkey.isShiftPressed()) robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                if (poeChatHotkey.isAltPressed()) robot.keyRelease(KeyEvent.VK_ALT);
                if (poeChatHotkey.isCtrlPressed()) robot.keyRelease(KeyEvent.VK_CONTROL);
                if (poeChatHotkey.isShiftPressed()) robot.keyRelease(KeyEvent.VK_SHIFT);
                openChatOverride = true;
            } else ZLogger.err("Invalid JNA to Swing conversion:" + poeChatHotkey + ", " + poeChatHotkey.keyCode);
        }
        // Fallback to opening chat using enter
        if (!openChatOverride) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        // Select all text
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        // Paste
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        // Send Message
        if (!stopBeforeSend) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        // This sleep is required
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignore) {
        }
        robot.waitForIdle();
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
            ZLogger.err("Failed to set clipboard contents.");
            return;
        }
        pasteFromClipboard(stopBeforeSend);
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

    public static void runCommand(String input, PasteReplacement pasteReplacement) {
        if (pasteReplacement == null) return;
        executor.execute(() -> {
            if (!focusGame()) return;
            ArrayList<String> commands = ZUtil.getCommandList(input, pasteReplacement);
            if (commands.size() == 1) paste(commands.get(0));
            else {
                for (String s : commands) {
                    paste(s);
                    try {
                        int rng = random.nextInt(60) + 70;
                        Thread.sleep(rng);
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
        if (isGameFocused()) return true;
        // Show, click, then hide a dummy window.
        // This is required because swing needs focus before it can give focus to another program
        FrameManager.dummyWindow.setVisible(true);
        Point point = MouseInfo.getPointerInfo().getLocation();
        point.x -= DummyWindow.HALF_SIZE;
        point.y -= DummyWindow.HALF_SIZE;
        FrameManager.dummyWindow.setLocation(point);
        robot.mousePress(0);
        robot.mouseRelease(0);
        FrameManager.dummyWindow.setVisible(false);
        // Focus the Path of Exile game window
        focusPathOfExileWindow();
        // Wait until Path of Exile gains focus
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

    // TODO : Add cross platform support
    private static void focusPathOfExileWindow() {
        if (Platform.current != Platform.WINDOWS) return;
        // Use cached window handle if available
        if (gameWindowHandle != null) {
            focusWindowNative(gameWindowHandle);
            return;
        }
        // Enumerate through all windows. Loop continues until the callback returns false
        User32.INSTANCE.EnumWindows((handle, arg1) -> {
            char[] classNameBuffer = new char[512];
            User32.INSTANCE.GetClassName(handle, classNameBuffer, 512);
            String className = Native.toString(classNameBuffer);
            System.out.println(className);
            if (className.isEmpty()) return true;
            // Path of Exile 1 & 2 windows have the class name POEWindowClass
            if (className.equals("POEWindowClass")) {
                focusWindowNative(handle);
                return false;
            }
            // GeForce Now has the class name CEFCLIENT. Unsure if this is unique, so the window title is also checked.
            if (className.equals("CEFCLIENT")) {
                String title = getNativeWindowTitle(handle);
                if (gameTitleSet.contains(title)) {
                    focusWindowNative(handle);
                    return false;
                }
            }
            return true;
        }, null);
    }

    private static void focusWindowNative(WinDef.HWND handle) {
        gameWindowHandle = handle;
        User32.INSTANCE.SetForegroundWindow(handle);
        User32.INSTANCE.SetFocus(handle);
        User32.INSTANCE.ShowWindow(handle, User32.SW_SHOW);
    }

    public static boolean isGameFocused() {
        return isGameFocused(false);
    }

    public static boolean isGameFocused(boolean includeApp) {
        if (Platform.current != Platform.WINDOWS) return true;
        NativeWindow focusedWindow = getFocusedWindowNative();
        if (focusedWindow == null) return false;
        if (gameTitleSet.contains(focusedWindow.title)) {
            gameWindowHandle = focusedWindow.handle;
            return true;
        }
        if (includeApp && focusedWindow.title.startsWith(References.APP_PREFIX)) return true;
        if (includeApp && focusedWindow.title.equals(ClientFileChooser.TITLE)) return true;
        return false;
    }

    // FIXME : Add cross platform support.
    //         Could alternatively add support to isGameFocused if there is a way
    //         to know if POE is focused other than checking the window title.
    private static NativeWindow getFocusedWindowNative() {
        if (Platform.current == Platform.WINDOWS) {
            char[] buffer = new char[MAX_TITLE_LENGTH * 2];
            WinDef.HWND handle = User32.INSTANCE.GetForegroundWindow();
            User32.INSTANCE.GetWindowText(handle, buffer, MAX_TITLE_LENGTH);
            String title = Native.toString(buffer);
            return new NativeWindow(title, handle);
        }
        return null;
    }

    private static String getNativeWindowTitle(WinDef.HWND handle) {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        User32.INSTANCE.GetWindowText(handle, buffer, MAX_TITLE_LENGTH);
        return Native.toString(buffer);
    }

    @Nullable
    public static Rectangle getGameRect() {
        for (DesktopWindow window : WindowUtils.getAllWindows(true)) {
            if (gameTitleSet.contains(window.getTitle())) {
                return window.getLocAndSize();
            }
        }
        return null;
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
