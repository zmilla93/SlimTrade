package com.slimtrade;

import com.slimtrade.core.managers.ClipboardManager;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.managers.SetupManager;
import com.slimtrade.core.observing.GlobalKeyboardListener;
import com.slimtrade.core.observing.GlobalMouseListener;
import com.slimtrade.core.observing.MacroEventManager;
import com.slimtrade.core.observing.improved.EventManager;
import com.slimtrade.core.utility.ChatParser;
import com.slimtrade.core.utility.FileMonitor;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.UpdateChecker;
import com.slimtrade.debug.Debugger;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.dialogs.LoadingDialog;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.setup.SetupWindow;
import com.slimtrade.gui.windows.UpdateDialog;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static Debugger debugger;
    public static FrameManager frameManager;
    public static MacroEventManager macroEventManager = new MacroEventManager();
    public static EventManager eventManager = new EventManager();
    public static SaveManager saveManager;
    public static ChatParser chatParser = new ChatParser();
    public static FileMonitor fileMonitor;
    public static Logger logger = Logger.getLogger("slim");
    public static UpdateChecker updateChecker;
    public static GlobalKeyboardListener globalKeyboard;
    public static GlobalMouseListener globalMouse;
    public static LoadingDialog loadingDialog;

    // Flags
    public static boolean checkUpdateOnLaunch = true;
    public static boolean debugMode = false;
    public static boolean allowPrerelease = false;
    public static boolean forceUI = false;
    public static boolean testFeatures = false;

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        // Launch Args
        if (args.length > 0) {
            for (String s : args) {
                switch (s) {
                    // Debug
                    case "-d":
                        debugMode = true;
                        break;
                    // No update check on launch
                    case "-nu":
                        checkUpdateOnLaunch = false;
                        break;
                    // Force the overlay to always be shown
                    case "-ui":
                        forceUI = true;
                        break;
                    // Enable test features
                    case "-tf":
                        testFeatures = true;
                        break;
                    case "-pre":
                        allowPrerelease = true;
                        break;
                }
            }
        }

        //Loading Dialog
        SwingUtilities.invokeLater(() -> {
            loadingDialog = new LoadingDialog();
            loadingDialog.setAlwaysOnTop(false);
            loadingDialog.setAlwaysOnTop(true);
        });

        // Logger
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        // Setup
        ColorManager.setTheme(ColorTheme.SOLARIZED_LIGHT);
        updateChecker = new UpdateChecker();
        globalMouse = new GlobalMouseListener();
        globalKeyboard = new GlobalKeyboardListener();

        // Save Manager
        saveManager = new SaveManager();
        saveManager.loadFromDisk();
        saveManager.loadStashFromDisk();
        saveManager.loadOverlayFromDisk();

        ClipboardManager clipboardManager = new ClipboardManager();

        // GUI
        try {
            SwingUtilities.invokeAndWait(() -> {

                //Debug Mode
                if (debugMode) {
                    debugger = new Debugger();
                    debugger.setState(Frame.ICONIFIED);
                }

                frameManager = new FrameManager();
                ColorManager.setColorBlindMode(App.saveManager.saveFile.colorBlindMode);
                eventManager.updateAllColors(App.saveManager.saveFile.colorTheme);
                SaveManager.recursiveLoad(FrameManager.optionsWindow);

                //TEST
//                App.eventManager.recursiveColor(FrameManager.optionsWindow);
//                FrameManager.optionsWindow.revalidate();
//                FrameManager.optionsWindow.repaint();

            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // POE Interface
        try {
            PoeInterface poe = new PoeInterface();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // JNativeHook Setup
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }

        // Finalize
        GlobalScreen.addNativeMouseListener(globalMouse);
        GlobalScreen.addNativeKeyListener(globalKeyboard);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> closeProgram()));
        loadingDialog.dispose();
        App.launch();
        System.out.println("SlimTrade launched!");

    }


    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            if (SetupManager.isSetupRequired()) {
                // First time setup window
                FrameManager.setupWindow = new SetupWindow();
                FrameManager.windowState = WindowState.SETUP;
                FrameManager.setupWindow.setVisible(true);
            } else {
                // Launch
                FrameManager.windowState = WindowState.NORMAL;
                fileMonitor = new FileMonitor();
                fileMonitor.startMonitor();
                chatParser.init();
                if(App.saveManager.saveFile.enableMenubar) {
                    FrameManager.menubarToggle.setShow(true);
                    if(!globalMouse.isGameFocused()) {
                        FrameManager.menubarToggle.setVisible(false);
                    }
                }
                FrameManager.trayButton.addAdditionalOptions();
                // Check for update
                if (checkUpdateOnLaunch) {
                    updateChecker.checkForUpdates();
                    if (updateChecker.isUpdateAvailable()) {
                        UpdateDialog updateDialog = new UpdateDialog();
                        updateDialog.setVisible(true);
                        FrameManager.optionsWindow.recolorUpdateButton();
                    }
                }
                SaveManager.recursiveLoad(FrameManager.optionsWindow);
            }
        });
    }


    private static void closeProgram() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        if(fileMonitor != null) {
            fileMonitor.stopMonitor();
        }
        System.out.println("SlimTrade Terminated");
    }

}
