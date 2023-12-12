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
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.VersionNumber;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.HotkeyManager;
import com.slimtrade.gui.managers.SetupManager;
import com.slimtrade.gui.managers.SystemTrayManager;
import com.slimtrade.gui.pinning.PinManager;
import com.slimtrade.gui.windows.LoadingDialog;
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
    private static LoadingDialog loadingDialog;
    private static LockManager lockManager;

    public static ChatParser chatParser;
    public static ChatParser preloadParser;

    private static AppState state = AppState.LOADING;
    private static AppState previousState = AppState.LOADING;

    public static boolean debug = true;
    public static boolean chatInConsole = false;

    public static void main(String[] args) {

        // Lock file to prevent duplicate instances
        lockManager = new LockManager(SaveManager.getSaveDirectory(), "app.lock");
        boolean lockSuccess = lockManager.tryAndLock();
        if (!lockSuccess) {
            System.out.println("SlimTrade is already running. Terminating new instance.");
            System.exit(0);
        }

        // This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

        // Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(App::closeProgram));

        // Init minimum for loading dialog
        ColorManager.loadFonts();
        SaveManager.init();

        // FIXME : Temp
        // Version Test
        VersionNumber v1 = new VersionNumber("v0.3.5");
        VersionNumber v2 = new VersionNumber("v0.4.0");
        VersionNumber v3 = new VersionNumber("v0.4.5");
        VersionNumber target = new VersionNumber("v0.4.0");
        int i1 = v1.compareTo(target);
        int i2 = v2.compareTo(target);
        int i3 = v3.compareTo(target);
        System.out.println("patch?" + (i1));
        System.out.println("patch?" + (i2));
        System.out.println("patch?" + (i3));

        // Loading Dialog
        try {
            SwingUtilities.invokeAndWait(() -> {
                ColorManager.setIconSize(SaveManager.settingsSaveFile.data.iconSize);
                ColorManager.setFontSize(SaveManager.settingsSaveFile.data.fontSize);
                ColorManager.setTheme(SaveManager.settingsSaveFile.data.colorTheme);
                loadingDialog = new LoadingDialog();
                loadingDialog.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // Init Managers
        CurrencyType.initIconList();
        LangRegex.compileAll();
        POEInterface.init();
        AudioManager.init();
        FontManager.loadFonts();

        // UI
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Init System Tray Button
                systemTrayManager = new SystemTrayManager();
                // Initialize all GUI windows
                FrameManager.init();
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // Reduce logging level for JNativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

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

        SwingUtilities.invokeLater(() -> loadingDialog.dispose());

        System.out.println("Slimtrade Launched");

    }

    private static void runSetupWizard() {
        SwingUtilities.invokeLater(() -> {
            FrameManager.setupWindow.setup();
            FrameManager.setWindowVisibility(AppState.SETUP);
        });
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

            PinManager.applyPins();
            FrameManager.showAppFrames();
        });
        initParsers();
        HotkeyManager.loadHotkeys();
        App.setState(AppState.RUNNING);
    }

    private static void initParsers() {
        // FIXME : make this less robust now that parser is fixed
        if (preloadParser != null) preloadParser.close();
        if (chatParser != null) chatParser.close();

        chatParser = new ChatParser();
        preloadParser = new ChatParser();

        preloadParser.addTradeListener(FrameManager.historyWindow);
        preloadParser.addOnLoadedCallback(FrameManager.historyWindow);
        chatParser.addTradeListener(FrameManager.historyWindow);
        chatParser.addTradeListener(FrameManager.messageManager);
        preloadParser.addOnLoadedCallback(() -> {
            preloadParser.close();
            preloadParser = null;
        });
        chatParser.addJoinedAreaListener(FrameManager.messageManager);
        preloadParser.open(SaveManager.settingsSaveFile.data.clientPath, false);
        chatParser.open(SaveManager.settingsSaveFile.data.clientPath, true);

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