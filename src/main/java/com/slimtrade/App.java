package com.slimtrade;

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
import com.slimtrade.gui.components.TrayButton;
import com.slimtrade.gui.dialogs.LoadingDialog;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.setup.SetupWindow;
import com.slimtrade.gui.windows.UpdateDialog;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
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
    public static boolean forceUI = false;
    public static boolean testFeatures = false;

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
                }
            }
        }

        //Loading Dialog
        loadingDialog = new LoadingDialog();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        loadingDialog.setAlwaysOnTop(false);
        loadingDialog.setAlwaysOnTop(true);
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ColorManager.setTheme(ColorTheme.LIGHT_THEME);

                //Debug Mode
                if (debugMode) {
                    debugger = new Debugger();
                    debugger.setState(Frame.ICONIFIED);
                }

                // Check for Updates
                updateChecker = new UpdateChecker();
//                updateChecker.checkForUpdates();


                Locale.setDefault(Locale.US);
                saveManager = new SaveManager();
                saveManager.loadFromDisk();
                saveManager.loadStashFromDisk();
                saveManager.loadOverlayFromDisk();

                // POE Interface
                try {
                    PoeInterface poe = new PoeInterface();
                } catch (AWTException e) {
                    e.printStackTrace();
                }

                // Frames
                frameManager = new FrameManager();
                eventManager.updateAllColors(App.saveManager.saveFile.colorTheme);

                // JNativeHook Setup
                try {
                    GlobalScreen.registerNativeHook();
                } catch (NativeHookException e) {
                    e.printStackTrace();
                }
                globalMouse = new GlobalMouseListener();
                globalKeyboard = new GlobalKeyboardListener();
                GlobalScreen.addNativeMouseListener(globalMouse);
                GlobalScreen.addNativeKeyListener(globalKeyboard);

//				ClipboardManager clipboard = new ClipboardManager();

                // Alert about new update
//                if(updateChecker.isUpdateAvailable()){
//                    UpdateDialog d = new UpdateDialog();
//                    d.setVisible(true);
//                }


            }
        });


        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                closeProgram();
            }
        }));
        loadingDialog.dispose();
        App.launch();
        System.out.println("SlimTrade launched!");
    }


    public static void launch() {
        if (SetupManager.isSetupRequired()) {
            // Setup
            FrameManager.setupWindow = new SetupWindow();
            FrameManager.windowState = WindowState.SETUP;
            FrameManager.setupWindow.setVisible(true);
        } else {
            // Launch
            FrameManager.windowState = WindowState.NORMAL;
            fileMonitor = new FileMonitor();
            fileMonitor.startMonitor();
            chatParser.init();
            FrameManager.menubarToggle.setShow(true);
            FrameManager.trayButton = new TrayButton();
            // Check for update
            if (checkUpdateOnLaunch) {
                updateChecker.checkForUpdates();
                if (updateChecker.isUpdateAvailable()) {
                    UpdateDialog updateDialog = new UpdateDialog();
                    updateDialog.setVisible(true);
                }
            }
        }
    }


    private static void closeProgram() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        try {
            fileMonitor.stopMonitor();
        } catch (NullPointerException e) {

        }
        System.out.println("SlimTrade Terminated");
    }

}
