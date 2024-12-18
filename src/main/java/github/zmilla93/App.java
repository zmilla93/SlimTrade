package github.zmilla93;

import github.zmilla93.core.References;
import github.zmilla93.core.chatparser.ChatParser;
import github.zmilla93.core.chatparser.ChatParserManager;
import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.enums.CurrencyType;
import github.zmilla93.core.enums.SetupPhase;
import github.zmilla93.core.jna.GlobalKeyboardListener;
import github.zmilla93.core.jna.GlobalMouseListener;
import github.zmilla93.core.jna.GlobalMouseWheelListener;
import github.zmilla93.core.managers.*;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.FrameManager;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static GlobalKeyboardListener globalKeyboardListener;
    public static GlobalMouseListener globalMouseListener;
    public static GlobalMouseWheelListener globalMouseWheelListener;
    private static LoadingWindow loadingWindow;
    private static LockManager lockManager;
    public static UpdateManager updateManager;

    public static ChatParser chatParserPoe1;
    public static ChatParser chatParserPoe2;

    private static AppInfo appInfo;
    private static AppState state = AppState.LOADING;
    private static boolean themesHaveBeenInitialized = false;
    private static boolean updateIsAvailable = false;
    private static boolean isRunningSetup = false;
    public static final ArrayList<SetupPhase> forcedSetupPhases = new ArrayList<>(); // no flag, used internally by previous command

    // TODO : Add a flag to set which option panel shows at launch
    // Debug Flags - The commented flags can be used as program arguments (case-insensitive)
    // Suggested dev flags: -debug -noupdate -ui -options
    // Add -ea as a VM option (not program argument) to enable assertions
    public static boolean debug = false; // -d, -debug: enables various developer windows and features
    public static boolean noUpdate = false; // -nu, -noupdate: disable update check
    public static boolean useLockFile = true; // -nl, -nolock: disable file locking
    public static boolean showOptionsOnLaunch = false; // -o, -options: show the options menu at launch
    public static boolean showHistoryOnLaunch = false; // -h, -history: show the history window at launch
    public static boolean debugUIAlwaysOnTop = false; // -ui: forces the UI to always be on top no matter what
    public static boolean themeDebugWindows = false; // -uim: shows UIManager inspection windows
    public static boolean forceSetup = false; // -s, -setup: forces the setup wizard to run with all phases (can also do setup:SetupPhase to force a specific phase, ie 'setup:game_window')
    public static boolean chatInConsole = false; // TODO: This is broken, should fix or remove
    public static int debugUIBorders = 0; // No flag: Adds debug borders to UI elements. 0 for off, 1 or 2 for debugging
    public static boolean messageUITest = false; // No flag: Creates a theme testing window (WARNING: takes a bit to load)
    public static final boolean debugProfileLaunch = false; // No flag: prints some profiling info during app launch
    public static String debugOptionPanelName = null; // No flag: prints some profiling info during app launch

    public static void main(String[] args) {
        parseLaunchArgs(args);

        /// Lock file to prevent duplicate instances
        lockManager = new LockManager(SaveManager.getSaveDirectory(), "app.lock");
        if (useLockFile) {
            if (!lockManager.tryAndLock()) {
                System.err.println("SlimTrade is already running. Terminating new instance.");
                System.exit(0);
            }
        }

        /// Logger
        ZLogger.open(SaveManager.getSaveDirectory(), args);
        String argString = args.length == 0 ? "" : Arrays.toString(args);
        String debugString = debug ? " [DEBUG]" : "";
        ZLogger.log("SlimTrade " + getAppInfo().appVersion + " started... " + argString + debugString);
        ZLogger.log("Current platform: " + System.getProperty("os.name") + " [" + Platform.current.name() + "]");
        ZLogger.cleanOldLogFiles();

        /// Launch profiling
        if (debugProfileLaunch) ZLogger.log("Profiling launch actions....");
        Stopwatch.start();

        /// This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

        /// Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(App::closeProgram));

        /// Reduce logging level for JNativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        /// Load save files & app info
        SaveManager.init();
        // FIXME: is this correct spot for fresh? Or after UI init for callbacks? Or both? (maybe an alertBounds() function for after)
        POEWindow.forceGameBoundsRefresh();
        profileLaunch("Time to start update");

        /// Update
        updateManager = new UpdateManager(References.AUTHOR, References.GITHUB_REPO, SaveManager.getSaveDirectory(), getAppInfo(), getAppInfo().appVersion.isPreRelease);
        updateManager.continueUpdateProcess(args);
        if (!noUpdate) {
            if (updateManager.getCurrentUpdateAction() != UpdateAction.CLEAN && updateManager.isUpdateAvailable()) {
                if (SaveManager.settingsSaveFile.data.enableAutomaticUpdate) {
                    updateManager.runUpdateProcess();
                } else {
                    updateIsAvailable = true;
                }
            } else {
                if (getAppInfo().appVersion.isPreRelease) updateManager.runPeriodicUpdateCheck(2, TimeUnit.HOURS);
                else updateManager.runPeriodicUpdateCheck(1, TimeUnit.DAYS);
            }
        }

        /// Loading Window
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

        /// Init Managers
        Stopwatch.start();
        CurrencyType.initIconList();
        AudioManager.init();
        profileLaunch("Managers Launched");

        /// JNativeHook Setup
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

        /// UI
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

        /// Final Setup
        if (!SetupManager.getSetupPhases().isEmpty()) runSetupWizard();
        else ZUtil.invokeAndWait(App::launchApp);

        ZUtil.invokeAndWait(() -> {
            loadingWindow.setVisible(false);
            loadingWindow.dispose();
        });

        if (debugProfileLaunch) ZLogger.log("Profiling launch complete!\n");
        ZLogger.log("SlimTrade startup complete!");
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
        ThemeManager.setColorblindMode(SaveManager.settingsSaveFile.data.colorBlindMode);
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

        if (SaveManager.appStateSaveFile.data.tutorialVersion < TutorialWindow.TUTORIAL_VERSION) {
            SwingUtilities.invokeLater(() -> FrameManager.tutorialWindow.setVisible(true));
            SaveManager.appStateSaveFile.data.tutorialVersion = TutorialWindow.TUTORIAL_VERSION;
            SaveManager.appStateSaveFile.saveToDisk(false);
        }
        if (updateIsAvailable) FrameManager.displayUpdateAvailable(updateManager.getLatestReleaseTag());
        if (updateManager.getCurrentUpdateAction() == UpdateAction.CLEAN)
            SwingUtilities.invokeLater(() -> FrameManager.patchNotesWindow.setVisible(true));

        HotkeyManager.loadHotkeys();
        App.setState(AppState.RUNNING);
        ChatParserManager.initChatParsers();
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
            if (arg.equals("-nu") || arg.equals("-noupdate")) noUpdate = true;
            if (arg.equals("-nl") || arg.equals("-nolock")) useLockFile = false;
            if (arg.equals("-d") || arg.equals("-debug")) debug = true;
            if (arg.equals("-o") || arg.equals("-options")) showOptionsOnLaunch = true;
            if (arg.equals("-h") || arg.equals("-history")) showHistoryOnLaunch = true;
            if (arg.startsWith("-o:") || arg.startsWith("-options:")) {
                App.debugOptionPanelName = arg.replace("-options:", "").replaceFirst("-o:", "").toLowerCase();
                showOptionsOnLaunch = true;
            }
            if (arg.equals("-ui")) debugUIAlwaysOnTop = true;
            if (arg.equals("-uim")) themeDebugWindows = true;
            if (arg.equals("-s") || arg.equals("-setup")) forceSetup = true;
            if (arg.startsWith("-setup:") || arg.startsWith("-s:")) {
                String targetPhase = arg.replace("-setup:", "").replaceFirst("-s:", "").toLowerCase();
                for (SetupPhase phase : SetupPhase.values()) {
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
        App.state = state;
    }

    public static AppState getState() {
        return App.state;
    }

    public static boolean isRunningSetup() {
        return isRunningSetup;
    }

    public static void unlock() {
        lockManager.closeLock();
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