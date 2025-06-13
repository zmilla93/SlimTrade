package github.zmilla93.core.utility;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.WinDef;
import github.zmilla93.core.References;
import github.zmilla93.core.data.PasteReplacement;
import github.zmilla93.core.hotkeys.HotkeyData;
import github.zmilla93.core.jna.JnaAwtEvent;
import github.zmilla93.core.jna.NativePoeWindow;
import github.zmilla93.core.jna.NativeWindow;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.gui.components.poe.POEFileChooser;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.windows.DummyWindow;
import github.zmilla93.modules.updater.ZLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    // FIXME : Switch to new ZUtil function
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static final Random random = new Random();

    public static final HashSet<String> gameTitleSet = new HashSet<>();
    private static final String[] gameTitles = new String[]{
            "Path of Exile",
            "Path of Exile 2",
            "Path of Exile on GeForce NOW",
            "Path of Exile 2 on GeForce NOW",
    };

    /**
     * Steam Game App ID from <a href="https://steamdb.info">SteamDB</a>.
     */
    private static final String[] linuxGameClasses = new String[]{
            "steam_app_238960",
            "steam_app_2694490",
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
        // Open Chat
        HotkeyData poeChatHotkey = SaveManager.settingsSaveFile.data.poeChatHotkey;
        boolean openChatOverride = false;
        // If a custom chat hotkey is set, try using that.
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
        // If the custom hotkey failed or isn't set, fallback to using enter
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
        if (input == null || input.isEmpty()) return;
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
     * Attempts to focus the Path of Exile game window.
     *
     * @return True if window was focused successfully
     */
    public static boolean focusGame() {
        assert (!SwingUtilities.isEventDispatchThread());
        // FIXME : Add support for more platforms.
        if (isGameFocused()) return true;
        switch (Platform.current) {
            case WINDOWS:
                // Show, click, then hide a dummy window.
                // This is required because the Java program needs focus before it can give focus to another program.
                FrameManager.dummyWindow.setVisible(true);
                Point point = MouseInfo.getPointerInfo().getLocation();
                point.x -= DummyWindow.HALF_SIZE;
                point.y -= DummyWindow.HALF_SIZE;
                FrameManager.dummyWindow.setLocation(point);
                robot.mousePress(0);
                robot.mouseRelease(0);
                FrameManager.dummyWindow.setVisible(false);
                // Focus the Path of Exile game window
                // FIXME : This might be the best spot to add platform support?
                NativePoeWindow.focusPathOfExileNativeWindow();
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
                break;
            case LINUX:
                // Linux X11 using xdotool
                if (SaveManager.linuxSaveFile.data.getFocusUsingXDoTool()) {
                    for (String gameClass : linuxGameClasses) {
                        ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", "xdotool windowactivate --sync $(xdotool search --onlyvisible --class " + gameClass);
                        try {
                            Process process = processBuilder.start();
                            int exitCode = process.waitFor();
                            if (exitCode == 0) break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            ZLogger.log(e.getStackTrace());
                        }
                    }
                }
                break;
            default:
                // Non Windows or Linux X11 OS, just return true
                return true;
        }
        return isGameFocused();
    }

    public static boolean isGameFocused() {
        return isGameFocused(false);
    }

    public static boolean isGameFocused(boolean includeApp) {
        WinDef.HWND focusedWindow = null;
        String focusedWindowTitle = null;

        switch (Platform.current) {
            case WINDOWS:
                focusedWindow = NativeWindow.getFocusedWindow();
                if (focusedWindow == null) return false;
                if (focusedWindow.equals(POEWindow.getGameHandle())) return true;
                focusedWindowTitle = WindowUtils.getWindowTitle(focusedWindow);
                break;
            case LINUX:
                if (SaveManager.linuxSaveFile.data.getFocusUsingXDoTool()) {
                    ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", "xdotool getwindowname $(xdotool getwindowfocus)");
                    try {
                        Process process = processBuilder.start();
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        focusedWindowTitle = br.readLine();
                    } catch (Exception e) {
                        ZLogger.log(e.getStackTrace());
                        e.printStackTrace();
                    }
                }
                break;
            default:
                // TODO : Add support for more platforms
                return true;
        }

        if (focusedWindowTitle == null) return false;
        if (includeApp) {
            if (focusedWindowTitle.startsWith(References.APP_PREFIX)) return true;
            if (focusedWindowTitle.equals(POEFileChooser.getWindowTitle(Game.PATH_OF_EXILE_1))) return true;
            if (focusedWindowTitle.equals(POEFileChooser.getWindowTitle(Game.PATH_OF_EXILE_2))) return true;
        }
        if (gameTitleSet.contains(focusedWindowTitle)) {
            if (Platform.current == Platform.WINDOWS) {
                NativePoeWindow.setPOEGameWindow(focusedWindow);
            }
            return true;
        }
        return false;
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
            } catch (InterruptedException ignore) {
            }
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        });
    }

}
