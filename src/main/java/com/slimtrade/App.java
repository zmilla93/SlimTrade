package com.slimtrade;

import com.slimtrade.core.chatparser.ChatParser;
import com.slimtrade.core.jna.GlobalKeyboardListener;
import com.slimtrade.core.jna.GlobalMouseListener;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.LanguageManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.HotkeyManager;
import com.slimtrade.gui.managers.SystemTrayManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static FrameManager frameManager;
    public static LanguageManager languageManager;
    public static SaveManager saveManager;
    public static AudioManager audioManager;
    public static SystemTrayManager systemTrayManager;
    public static GlobalKeyboardListener globalKeyboardListener;
    public static GlobalMouseListener globalMouseListener;
    public static HotkeyManager hotkeyManager;

    public static ChatParser chatParser = new ChatParser();

    public static boolean initialized;

    public enum State {LOADING, RUNNING, EDIT_OVERLAY}

    private static State state = State.LOADING;

    public static void main(String[] args) {

        // This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

//        ColorManager.setTheme(ColorTheme.CARBON);

        // Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(App::closeProgram));

        // Managers
        saveManager = new SaveManager();
        languageManager = new LanguageManager();
        audioManager = new AudioManager();
        hotkeyManager = new HotkeyManager();
        PoeInterface.init();

        // UI
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Init System Tray Button
                systemTrayManager = new SystemTrayManager();
                // Initialize all GUI windows
                FrameManager.init();
                // Load save file to GUI
                ColorManager.setTheme(SaveManager.settingsSaveFile.data.colorTheme);
                SaveManager.settingsSaveFile.registerSavableContainer(FrameManager.optionsWindow);
                SaveManager.settingsSaveFile.revertChanges();
                SaveManager.stashSaveFile.revertChanges();

                FrameManager.optionsWindow.reloadExampleTrades();
                FrameManager.optionsWindow.revalidate();


                // Show Windows
                FrameManager.messageManager.setVisible(true);
                FrameManager.debugWindow.setVisible(true);
                FrameManager.optionsWindow.setVisible(true);
//                FrameManager.historyWindow.setVisible(true);
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
//        globalMouseListener = new GlobalMouseListener();
        GlobalScreen.addNativeKeyListener(globalKeyboardListener);
//        GlobalScreen.addNativeMouseMotionListener(globalMouseListener);

        // Chat Parser
        // Chat Parser Listeners
        chatParser.addPreloadTradeListener(FrameManager.historyWindow);
        chatParser.addTradeListener(FrameManager.historyWindow);
        chatParser.addOnLoadedCallback(FrameManager.historyWindow);
        chatParser.addTradeListener(FrameManager.messageManager);
        // Chat Parser Init
        chatParser.open(SaveManager.settingsSaveFile.data.clientPath);
        HotkeyManager.loadHotkeys();

        initialized = true;
        setState(State.RUNNING);
        System.out.println("Slimtrade Launched!");

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
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

}