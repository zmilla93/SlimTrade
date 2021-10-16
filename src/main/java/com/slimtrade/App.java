package com.slimtrade;

import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.debug.Debugger;
import com.slimtrade.core.managers.*;
import com.slimtrade.core.observing.GlobalKeyboardListener;
import com.slimtrade.core.observing.GlobalMouseListener;
import com.slimtrade.core.update.UpdateManager;
import com.slimtrade.core.utility.ChatParser;
import com.slimtrade.core.utility.FileMonitor;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.dialogs.LoadingDialog;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.popups.PatchNotesWindow;
import com.slimtrade.gui.popups.UpdateDialog;
import com.slimtrade.gui.setup.SetupWindow;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    // Logic
    public static Debugger debugger;
    public static Logger logger = Logger.getLogger("slim");
    public static ChatParser chatParser = new ChatParser();
    public static FileMonitor fileMonitor;
    // Managers
    public static FontManager fontManager;
    public static AudioManager audioManager;
    public static LockManager lockManager;
    public static UpdateManager updateManager;
    public static FrameManager frameManager;
    public static SaveManager saveManager;
    public static ClipboardManager clipboardManager;
    // JNativeHook
    public static GlobalKeyboardListener globalKeyboard;
    public static GlobalMouseListener globalMouse;
    // Launch arguments
    public static boolean update = false;
    public static boolean clean = false;
    public static boolean patch = false;
    public static boolean patchNotes = false;
    public static boolean ignoreUpdate = false;
    public static boolean forceUI = false;
    public static boolean testFeatures = false;
    public static String updateTargetVersion = null;
    public static String debuggerTimestamp = null;
    public static String launcherPath = null;
    // Loading
    public static LoadingDialog loadingDialog;

    public static void main(String[] args) {

        // Save Manager
        saveManager = new SaveManager();
        saveManager.loadSettingsFromDisk();
        // Lock File
        lockManager = new LockManager(saveManager.INSTALL_DIRECTORY);
        boolean lock = lockManager.tryAndLock("app.lock");
        if (!lock) {
            System.out.println("Slimtrade is already running. Terminating new instance.");
            System.exit(0);
            return;
        }
        // Handle Launch Args
        if (args.length > 0) {
            for (String s : args) {
                if (s.startsWith("launcher:")) {
                    launcherPath = s.replace("launcher:", "");
                } else if (s.startsWith("update:")) {
                    update = true;
                    updateTargetVersion = s.replace("update:", "");
                } else if (s.startsWith("debugger:")) {
                    debuggerTimestamp = s.replace("debugger:", "");
                }
                switch (s) {
                    case "update":
                        update = true;
                        break;
                    case "clean":
                        clean = true;
                        break;
                    case "patch":
                        patch = true;
                        break;
                    case "patchNotes":
                        patchNotes = true;
                        break;
                    case "ignoreUpdate":
                    case "-nu":
                        ignoreUpdate = true;
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

        // Set launcher path
        if (launcherPath == null) {
            try {
                launcherPath = URLDecoder.decode(new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath(), "UTF-8");
            } catch (UnsupportedEncodingException | URISyntaxException e) {
                e.printStackTrace();
            }
        }

        // Debugger
        if (debuggerTimestamp == null) {
            debugger = new Debugger();
            debuggerTimestamp = debugger.getTimestamp();
        } else {
            debugger = new Debugger(debuggerTimestamp);
        }

        // Auto Update
        updateManager = new UpdateManager();
        if (update) {
            App.debugger.log("Force updating...");
            updateManager.update();
        } else if (patch) {
            App.debugger.log("Patching...");
            updateManager.patch();
        } else if (clean) {
            App.debugger.log("Cleaning...");
            updateManager.clean();
//            versionTag = null;
        } else {
            if (!ignoreUpdate && saveManager.settingsSaveFile.autoUpdate) {
                if (updateManager.isUpdateAvailable()) {
                    App.debugger.log("Auto updating...");
                    updateManager.update();
                }
            }
        }

        // Load Fonts
        fontManager = new FontManager();
        fontManager.loadFonts();

        // Show Loading Dialog
        SwingUtilities.invokeLater(() -> {
            loadingDialog = new LoadingDialog();
            loadingDialog.setAlwaysOnTop(true);
        });

        // Reduce logging level for JNativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        // Hook into mouse and keyboard
        globalMouse = new GlobalMouseListener();
        globalKeyboard = new GlobalKeyboardListener();

        // Load save files from disk
        saveManager.loadScannerFromDisk();
        saveManager.loadStashFromDisk();
        saveManager.loadOverlayFromDisk();

        // Clipboard Manager
        clipboardManager = new ClipboardManager();
        clipboardManager.setListeningState(saveManager.settingsSaveFile.quickPasteSetting == QuickPasteSetting.AUTOMATIC);

        // Set Color Theme
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Loading using tempTheme fixes a bug where icon images are not correctly loaded into combo boxes in macro customizer
                ColorTheme theme = App.saveManager.settingsSaveFile.colorTheme;
                ColorTheme tempTheme = theme == ColorTheme.SOLARIZED_LIGHT ? ColorTheme.MONOKAI : ColorTheme.SOLARIZED_LIGHT;
                ColorManager.setTheme(tempTheme);
                frameManager = new FrameManager();
                ColorManager.setColorBlindMode(App.saveManager.settingsSaveFile.colorBlindMode);
                SaveManager.recursiveLoad(FrameManager.optionsWindow);
                ColorManager.setTheme(theme);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // POE Interface
        PoeInterface.init();

        // JNativeHook Setup
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }

        // Audio
        audioManager = new AudioManager();

        // Finalize
        GlobalScreen.addNativeMouseListener(globalMouse);
        GlobalScreen.addNativeMouseMotionListener(globalMouse);
        GlobalScreen.addNativeKeyListener(globalKeyboard);
        Runtime.getRuntime().addShutdownHook(new Thread(App::closeProgram));
        SwingUtilities.invokeLater(() -> {
            FrameManager.generateCheatSheets();
            loadingDialog.dispose();
            App.launch();
        });
        App.debugger.log("SlimTrade launched!");
    }

    public static void launch() {
        if (SetupManager.isSetupRequired()) {
            // First time setup window
            FrameManager.setupWindow = new SetupWindow();
            FrameManager.windowState = WindowState.SETUP;
            FrameManager.setupWindow.setVisible(true);
        } else {
            // Launch
            // Reload to get correct client path
            FrameManager.optionsWindow.reloadGeneralPanel();
            FrameManager.windowState = WindowState.NORMAL;
            fileMonitor = new FileMonitor();
            fileMonitor.startMonitor();
            chatParser.init();
            if (App.saveManager.settingsSaveFile.enableMenubar) {
                FrameManager.menubarToggle.setShow(true);
                if (!globalMouse.isGameFocused() && !PoeInterface.isPoeFocused(false)) {
                    FrameManager.menubarToggle.setVisible(false);
                }
            }
            FrameManager.trayButton.addAdditionalOptions();
            // Show Patch Notes
            if (patchNotes) {
                FrameManager.patchNotesWindow = new PatchNotesWindow();
            }
            // Check for update if auto updates are off
            else if (!ignoreUpdate && !saveManager.settingsSaveFile.autoUpdate) {
                if (updateManager.isUpdateAvailable()) {
                    FrameManager.optionsWindow.showUpdateButton();
                    UpdateDialog updateDialog = new UpdateDialog();
                    updateDialog.setVisible(true);
                }
            }
            if (!ignoreUpdate) {
                updateManager.runDelayedUpdateCheck();
            }
        }
    }

    private static void closeProgram() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        if (fileMonitor != null) {
            fileMonitor.stopMonitor();
        }
        debugger.log("SlimTrade Terminated\n");
        debugger.close();
        lockManager.closeLock();
        lockManager.deleteLock();
    }

}
