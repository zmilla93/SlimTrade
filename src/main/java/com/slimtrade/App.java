package com.slimtrade;

import com.slimtrade.core.chatparser.ChatParser;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.core.jna.GlobalKeyboardListener;
import com.slimtrade.core.jna.GlobalMouseListener;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.FontManager;
import com.slimtrade.core.managers.LockManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.LangRegex;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.HotkeyManager;
import com.slimtrade.gui.managers.SetupManager;
import com.slimtrade.gui.managers.SystemTrayManager;
import com.slimtrade.gui.pinning.PinManager;
import com.slimtrade.gui.windows.LoadingWindow;
import com.slimtrade.gui.windows.UpdateProgressWindow;
import com.slimtrade.modules.stopwatch.Stopwatch;
import com.slimtrade.modules.theme.ThemeManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static SystemTrayManager systemTrayManager;
    public static GlobalKeyboardListener globalKeyboardListener;
    public static GlobalMouseListener globalMouseListener;
    private static LoadingWindow loadingWindow;
    private static UpdateProgressWindow updateProgressWindow;
    private static LockManager lockManager;

    public static ChatParser chatParser;
    public static ChatParser preloadParser;

    private static AppState state = AppState.LOADING;
    private static AppState previousState = AppState.LOADING;
    private static boolean themesHaveBeenInitialized = false;

    // Debug Flags
    public static boolean debug = true;
    public static boolean chatInConsole = false;
    public static int debugUIBorders = 0; // Adds borders to certain UI elements. 0 for off, 1 or 2 for debugging
    private static final boolean debugProfileLaunch = false;

    public static void main(String[] args) {
        Stopwatch.start();
        // Lock file to prevent duplicate instances
        lockManager = new LockManager(SaveManager.getSaveDirectory(), "app.lock");
        boolean lockSuccess = lockManager.tryAndLock();
        if (!lockSuccess) {
            System.out.println("SlimTrade is already running. Terminating new instance.");
            System.exit(0);
        }

        if (debugProfileLaunch) System.out.println("Profiling launch actions....");

        // This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

        // Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(App::closeProgram));

        // Reduce logging level for JNativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        // Init minimum for loading dialog
        Stopwatch.start();
//        ThemeManager.loadFonts();
        SaveManager.init();

        // Initialize all themes
        // FIXME: Initializing themes should be done as late as possible, but before any UI is shown
        initializeThemes();
        profileLaunch("Fonts and Save File");

        // TODO : Updating
        try {
            SwingUtilities.invokeAndWait(() -> {
                updateProgressWindow = new UpdateProgressWindow();
                updateProgressWindow.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
//        if (true) return;

        // Loading Dialog
        try {
            Stopwatch.start();
            SwingUtilities.invokeAndWait(() -> {
                loadingWindow = new LoadingWindow();
                loadingWindow.setVisible(true);
            });
            profileLaunch("ThemeManager");
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // Init Managers
        Stopwatch.start();
        CurrencyType.initIconList();
        LangRegex.compileAll();
        POEInterface.init();
        AudioManager.init();
        profileLaunch("Managers Launched");

        // UI
        try {
            Stopwatch.start();
            SwingUtilities.invokeAndWait(() -> {
                // Init System Tray Button
                systemTrayManager = new SystemTrayManager();
                // Initialize all GUI windows
                FrameManager.init();
            });
            profileLaunch("UI Creation");
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // JNativeHook Setup
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        globalKeyboardListener = new GlobalKeyboardListener();
        globalMouseListener = new GlobalMouseListener();
        GlobalScreen.addNativeKeyListener(globalKeyboardListener);
        GlobalScreen.addNativeMouseListener(globalMouseListener);
        GlobalScreen.addNativeMouseMotionListener(globalMouseListener);

        // Final Setup
        if (SetupManager.getSetupPhases().size() > 0) runSetupWizard();
        else launchApp();

        SwingUtilities.invokeLater(() -> loadingWindow.dispose());

        if (debugProfileLaunch) System.out.println("Profiling launch complete!\n");
        System.out.println("Slimtrade Launched");

    }

    private static void profileLaunch(String context) {
        if (!debugProfileLaunch) return;
        System.out.println("\t" + context + ": " + Stopwatch.getElapsedSeconds());
    }

    private static void runSetupWizard() {
        SwingUtilities.invokeLater(() -> {
            FrameManager.setupWindow.setup();
            FrameManager.setWindowVisibility(AppState.SETUP);
        });
    }

    private static void initializeThemes() {
        if (themesHaveBeenInitialized) return;
        try {
            SwingUtilities.invokeAndWait(() -> {
                FontManager.loadFonts();
                ThemeManager.setTheme(SaveManager.settingsSaveFile.data.theme);
                ThemeManager.setIconSize(SaveManager.settingsSaveFile.data.iconSize);
                ThemeManager.setFontSize(SaveManager.settingsSaveFile.data.fontSize);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        themesHaveBeenInitialized = true;
    }

    public static void launchApp() {
        SwingUtilities.invokeLater(() -> {
            if (FrameManager.setupWindow != null) {
                FrameManager.setupWindow.dispose();
                FrameManager.setupWindow = null;
            }
            SaveManager.settingsSaveFile.revertChanges();
            SaveManager.stashSaveFile.revertChanges();
            SaveManager.chatScannerSaveFile.revertChanges();
            FrameManager.optionsWindow.reloadExampleTrades();
            FrameManager.overlayInfoWindow.load();

            PinManager.applyAllPins();
            FrameManager.showAppFrames();
        });
        initParsers();
        HotkeyManager.loadHotkeys();
        App.setState(AppState.RUNNING);
    }

    public static void initParsers() {
        // FIXME : make this less robust now that parser is fixed?
        if (preloadParser != null) preloadParser.close();
        if (chatParser != null) chatParser.close();
        chatParser = new ChatParser();
        preloadParser = new ChatParser();

        preloadParser.addOnInitCallback(FrameManager.historyWindow);
        preloadParser.addOnLoadedCallback(FrameManager.historyWindow);
        preloadParser.addTradeListener(FrameManager.historyWindow);
        chatParser.addTradeListener(FrameManager.historyWindow);
        chatParser.addTradeListener(FrameManager.messageManager);
        chatParser.addJoinedAreaListener(FrameManager.messageManager);
        preloadParser.addOnLoadedCallback(() -> {
            preloadParser.close();
            preloadParser = null;
            chatParser.open(SaveManager.settingsSaveFile.data.clientPath, true);
        });
        preloadParser.open(SaveManager.settingsSaveFile.data.clientPath, false);
    }

    public static void setState(AppState state) {
        previousState = App.state;
        App.state = state;
    }

    public static AppState getState() {
        return App.state;
    }

    public static AppState getPreviousState() {
        return App.previousState;
    }

    private static void closeProgram() {
        try {
            GlobalScreen.unregisterNativeHook();
            lockManager.closeLock();
            System.out.println("SlimTrade Terminated");
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

}