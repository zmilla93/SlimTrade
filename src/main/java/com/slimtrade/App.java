package com.slimtrade;

import com.slimtrade.core.chatparser.ChatParser;
import com.slimtrade.core.enums.CurrencyImage;
import com.slimtrade.core.jna.GlobalKeyboardListener;
import com.slimtrade.core.jna.GlobalMouseListener;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.LangRegex;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.HotkeyManager;
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

    public static FrameManager frameManager;
    public static SaveManager saveManager;
    public static AudioManager audioManager;
    public static SystemTrayManager systemTrayManager;
    public static GlobalKeyboardListener globalKeyboardListener;
    public static GlobalMouseListener globalMouseListener;
    private static LoadingDialog loadingDialog;

    public static ChatParser chatParser;
    public static ChatParser preloadParser;

    public static boolean initialized;

    public enum State {LOADING, RUNNING, EDIT_OVERLAY}

    private static State state = State.LOADING;

    public static void main(String[] args) {

        // This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

        // Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(App::closeProgram));

        // Init minimum for loading dialog
        SaveManager.init();
        ColorManager.loadFonts();

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
        CurrencyImage.initIconList();
        LangRegex.compileAll();
        POEInterface.init();
        audioManager = new AudioManager();

        // UI
        try {
            SwingUtilities.invokeAndWait(() -> {

                // Init System Tray Button
                systemTrayManager = new SystemTrayManager();
                // Initialize all GUI windows
                FrameManager.init();

                // Load save file to GUI
                ColorManager.setTheme(SaveManager.settingsSaveFile.data.colorTheme);
                ColorManager.setIconSize(SaveManager.settingsSaveFile.data.iconSize);
                ColorManager.setFontSize(SaveManager.settingsSaveFile.data.fontSize);
                SaveManager.settingsSaveFile.revertChanges();
                SaveManager.stashSaveFile.revertChanges();
                FrameManager.optionsWindow.reloadExampleTrades();
                PinManager.applyPins();

                // Show Windows
                FrameManager.messageManager.setVisible(true);
                FrameManager.debugWindow.setVisible(true);
                FrameManager.optionsWindow.setVisible(true);
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

        // Final Setup
        initParsers();
        HotkeyManager.loadHotkeys();
        initialized = true;
        setState(State.RUNNING);
        SwingUtilities.invokeLater(() -> {
            loadingDialog.dispose();
            loadingDialog = null;
        });
        System.out.println("Slimtrade Launched");
    }

    public static void initParsers() {
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
        preloadParser.open(SaveManager.settingsSaveFile.data.clientPath, false);
        chatParser.open(SaveManager.settingsSaveFile.data.clientPath, true);

    }

    public static void setState(State state) {
        App.state = state;
    }

    public static State getState() {
        return App.state;
    }

    private static void closeProgram() {
        try {
            GlobalScreen.unregisterNativeHook();
            System.out.println("SlimTrade Terminated");
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

}