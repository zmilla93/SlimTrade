package github.zmilla93;

import github.zmilla93.core.References;
import github.zmilla93.core.chatparser.ChatParser;
import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.enums.CurrencyType;
import github.zmilla93.core.enums.SetupPhase;
import github.zmilla93.core.jna.GlobalKeyboardListener;
import github.zmilla93.core.jna.GlobalMouseListener;
import github.zmilla93.core.jna.GlobalMouseWheelListener;
import github.zmilla93.core.managers.AudioManager;
import github.zmilla93.core.managers.FontManager;
import github.zmilla93.core.managers.LockManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.core.managers.HotkeyManager;
import github.zmilla93.gui.managers.SetupManager;
import github.zmilla93.gui.managers.SystemTrayManager;
import github.zmilla93.gui.pinning.PinManager;
import github.zmilla93.gui.windows.LoadingWindow;
import github.zmilla93.gui.windows.TutorialWindow;
import github.zmilla93.modules.stopwatch.Stopwatch;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.updater.UpdateAction;
import github.zmilla93.modules.updater.UpdateManager;
import github.zmilla93.modules.updater.ZLogger;
import github.zmilla93.modules.updater.data.AppInfo;
import github.zmilla93.modules.updater.data.AppVersion;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static GlobalKeyboardListener globalKeyboardListener;
    public static GlobalMouseListener globalMouseListener;
    public static GlobalMouseWheelListener globalMouseWheelListener;
    private static LoadingWindow loadingWindow;
    private static LockManager lockManager;
    public static UpdateManager updateManager;

    public static ChatParser chatParser;

    private static AppInfo appInfo;
    private static AppState state = AppState.LOADING;
    private static AppState previousState = AppState.LOADING;
    private static boolean themesHaveBeenInitialized = false;
    private static boolean updateIsAvailable = false;
    private static boolean isRunningSetup = false;
    public static final ArrayList<SetupPhase> forcedSetupPhases = new ArrayList<>(); // no flag, used internally by previous command

    // Debug Flags - The commented flags can be used as program arguments (case-insensitive)
    // Suggested dev flags: -debug -noupdate -ui -options
    // Add -ea as a VM option (not program argument) to enable assertions
    public static boolean debug = false; // -debug: enables various developer windows and features
    public static boolean noUpdate = false; // -noupdate: disable update check
    public static boolean useLockFile = true; // -nolock: disable file locking
    public static boolean showOptionsOnLaunch = false; // -options: show the options menu at launch
    public static boolean debugUIAlwaysOnTop = false; // -ui: forces the UI to always be on top no matter what
    public static boolean forceSetup = false; // -setup: forces the setup wizard to run with all phases (can also do setup:SetupPhase to force a specific phase, ie 'setup:game_window')
    public static boolean chatInConsole = false; // TODO: This is broken, should fix or remove
    public static int debugUIBorders = 0; // No flag: Adds debug borders to UI elements. 0 for off, 1 or 2 for debugging
    public static boolean messageUITest = false; // No flag: Creates a theme testing window (WARNING: takes a bit to load)
    public static final boolean debugProfileLaunch = false; // No flag: prints some profiling info during app launch

    public static void main(String[] args) {
        parseLaunchArgs(args);

        // Lock file to prevent duplicate instances
        lockManager = new LockManager(SaveManager.getSaveDirectory().toString(), "app.lock");
        if (useLockFile) {
            if (!lockManager.tryAndLock()) {
                System.err.println("SlimTrade is already running. Terminating new instance.");
                System.exit(0);
            }
        }

        // Logger
        ZLogger.open(SaveManager.getSaveDirectory().toString(), args);
        ZLogger.log("SlimTrade launching... " + Arrays.toString(args));
        ZLogger.log("Platform: " + System.getProperty("os.name") + " [" + Platform.current + "]");
        ZLogger.cleanOldLogFiles();

        // Launch profiling
        if (debugProfileLaunch) ZLogger.log("Profiling launch actions....");
        Stopwatch.start();

        // This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

        // Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(App::closeProgram));

        // Reduce logging level for JNativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        // Load save files & app info
        SaveManager.init();
        // FIXME: is this correct spot for fresh? Or after UI init for callbacks? Or both? (maybe an alertBounds() function for after)
        POEWindow.forceGameBoundsRefresh();
        profileLaunch("Time to start update");

        // Update
        updateManager = new UpdateManager(References.AUTHOR, References.GITHUB_REPO, SaveManager.getSaveDirectory().toString(), getAppInfo(), getAppInfo().appVersion.isPreRelease);
        updateManager.continueUpdateProcess(args);
        if (!noUpdate) {
            if (updateManager.getCurrentUpdateAction() != UpdateAction.CLEAN && updateManager.isUpdateAvailable()) {
                if (SaveManager.settingsSaveFile.data.enableAutomaticUpdate) {
                    updateManager.runUpdateProcess();
                } else {
                    updateIsAvailable = true;
                }
            } else {
                updateManager.runPeriodicUpdateCheck();
            }
        }

        // Loading Window
        try {
            Stopwatch.start();
            SwingUtilities.invokeAndWait(() -> {
                initializeThemes();
                loadingWindow = new LoadingWindow(getAppInfo());
                loadingWindow.setVisible(true);
            });
            profileLaunch("ThemeManager");
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // Init Managers
        Stopwatch.start();
        CurrencyType.initIconList();
        AudioManager.init();
        profileLaunch("Managers Launched");

        // JNativeHook Setup
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        globalKeyboardListener = new GlobalKeyboardListener();
        globalMouseListener = new GlobalMouseListener();
        globalMouseWheelListener = new GlobalMouseWheelListener();
        GlobalScreen.addNativeKeyListener(globalKeyboardListener);
        GlobalScreen.addNativeMouseListener(globalMouseListener);
        GlobalScreen.addNativeMouseMotionListener(globalMouseListener);
        GlobalScreen.addNativeMouseWheelListener(globalMouseWheelListener);

        // UI
        try {
            Stopwatch.start();
            SwingUtilities.invokeAndWait(() -> {
                // Initialize GUI
                SystemTrayManager.init();
                FrameManager.createGUI();
            });
            profileLaunch("UI Creation");
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // Final Setup
        if (SetupManager.getSetupPhases().size() > 0) runSetupWizard();
        else ZUtil.invokeAndWait(App::launchApp);

        ZUtil.invokeAndWait(() -> {
            loadingWindow.setVisible(false);
            loadingWindow.dispose();
        });

        if (debugProfileLaunch) ZLogger.log("Profiling launch complete!\n");
        ZLogger.log("SlimTrade Launched");
    }

    private static void profileLaunch(String context) {
        if (!debugProfileLaunch) return;
        ZLogger.log("\t" + context + ": " + Stopwatch.getElapsedSeconds());
    }

    private static void runSetupWizard() {
        isRunningSetup = true;
        SwingUtilities.invokeLater(() -> {
            FrameManager.setupWindow.buildSetupCardPanel();
            FrameManager.setWindowVisibility(AppState.SETUP);
        });
    }

    public static void initializeThemes() {
        assert (SwingUtilities.isEventDispatchThread());
        if (themesHaveBeenInitialized) return;
        FontManager.loadFonts();
        ThemeManager.setTheme(SaveManager.settingsSaveFile.data.theme);
        ThemeManager.setFont(SaveManager.settingsSaveFile.data.preferredFontName);
        ThemeManager.setIconSize(SaveManager.settingsSaveFile.data.iconSize);
        ThemeManager.setFontSize(SaveManager.settingsSaveFile.data.fontSize);
        ThemeManager.checkFontChange();
        themesHaveBeenInitialized = true;
    }

    public static void launchApp() {
        assert SwingUtilities.isEventDispatchThread();
        POEWindow.forceGameBoundsRefresh();
        if (FrameManager.setupWindow != null) {
            FrameManager.setupWindow.dispose();
            FrameManager.setupWindow = null;
        }
        isRunningSetup = false;
        SaveManager.appStateSaveFile.revertChanges();
        SaveManager.settingsSaveFile.revertChanges();
        SaveManager.stashSaveFile.revertChanges();
        SaveManager.chatScannerSaveFile.revertChanges();
        SaveManager.overlaySaveFile.revertChanges();
        FrameManager.optionsWindow.reloadExampleTrades();

        FrameManager.applyAllDefaultSizesAndLocations();
        PinManager.applyAllPins();
        FrameManager.showAppFrames();
        SystemTrayManager.showDefault();

        initParser();
        HotkeyManager.loadHotkeys();
        App.setState(AppState.RUNNING);

        if (SaveManager.appStateSaveFile.data.tutorialVersion < TutorialWindow.TUTORIAL_VERSION) {
            SwingUtilities.invokeLater(() -> FrameManager.tutorialWindow.setVisible(true));
            SaveManager.appStateSaveFile.data.tutorialVersion = TutorialWindow.TUTORIAL_VERSION;
            SaveManager.appStateSaveFile.saveToDisk(false);
        }
        if (updateIsAvailable) FrameManager.displayUpdateAvailable();
        if (updateManager.getCurrentUpdateAction() == UpdateAction.CLEAN)
            SwingUtilities.invokeLater(() -> FrameManager.patchNotesWindow.setVisible(true));
    }

    public static void initParser() {
        if (chatParser != null) {
            chatParser.close();
            chatParser.removeAllListeners();
        }
        chatParser = new ChatParser();
        // History
        chatParser.addOnInitCallback(FrameManager.historyWindow);
        chatParser.addOnLoadedCallback(FrameManager.historyWindow);
        chatParser.addTradeListener(FrameManager.historyWindow);
        // Message Manager
        chatParser.addTradeListener(FrameManager.messageManager);
        chatParser.addChatScannerListener(FrameManager.messageManager);
        chatParser.addJoinedAreaListener(FrameManager.messageManager);
        // Menu Bar
        chatParser.addOnLoadedCallback(FrameManager.menuBarIcon);
        chatParser.addOnLoadedCallback(FrameManager.menuBarDialog);
        chatParser.addDndListener(FrameManager.menuBarIcon);
        chatParser.addDndListener(FrameManager.menuBarDialog);
        // Open
        chatParser.open(SaveManager.settingsSaveFile.data.clientPath);
    }

    public static AppInfo getAppInfo() {
        if (appInfo == null) appInfo = readAppInfo();
        return appInfo;
    }

    private static AppInfo readAppInfo() {
        Properties properties = new Properties();
        try {
            InputStream stream = new BufferedInputStream(Objects.requireNonNull(UpdateManager.class.getClassLoader().getResourceAsStream("project.properties")));
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            ZLogger.err("Properties not found! Create a 'project.properties' file in the resources folder, then add the lines 'version=${project.version}' and 'artifactId=${project.artifactId}'.");
            return null;
        }
        String name = properties.getProperty("name");
        String version = properties.getProperty("version");
        String url = properties.getProperty("url");
        return new AppInfo(name, new AppVersion(version), url);
    }

    private static void parseLaunchArgs(String[] args) {
        for (String arg : args) {
            arg = arg.toLowerCase();
            if (arg.equals("-noupdate")) noUpdate = true;
            if (arg.equals("-nolock")) useLockFile = false;
            if (arg.equals("-debug")) debug = true;
            if (arg.equals("-options")) showOptionsOnLaunch = true;
            if (arg.equals("-ui")) debugUIAlwaysOnTop = true;
            if (arg.equals("-setup")) forceSetup = true;
            if (arg.startsWith("-setup:")) {
                String targetPhase = arg.replace("-setup:", "").toLowerCase();
                for (SetupPhase phase : SetupPhase.values()) {
//                    System.out.println("PHASE: " +P);
                    if (phase.toString().toLowerCase().equals(targetPhase)) {
                        forcedSetupPhases.add(phase);
                        break;
                    }
                }
            }
        }
    }

    // FIXME: This
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

    public static boolean isRunningSetup() {
        return isRunningSetup;
    }

    private static void closeProgram() {
        try {
            GlobalScreen.unregisterNativeHook();
            lockManager.closeLock();
            ZLogger.log("SlimTrade Terminated");
            ZLogger.close();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

}