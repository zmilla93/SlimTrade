package com.slimtrade.core.managers;

import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.saving.legacy.SaveFilePatcherManager;
import com.slimtrade.core.saving.savefiles.*;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.saving.SaveFile;
import com.slimtrade.modules.theme.ThemeManager;
import com.slimtrade.modules.updater.ZLogger;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class SaveManager {

    // Install folder names
    public static final String folderWin = "SlimTrade";
    public static final String folderOther = ".slimtrade";

    // Subfolder Names
    private static final String audioFolderName = "audio";
    private static final String imagesFolderName = "images";
    private static final String logFolderName = "logs";

    // Full paths
    private static String saveDirectory;
    private static String audioDirectory;
    private static String logsDirectory;
    private static String imagesDirectory;

    // Safe Files
    public static SaveFile<SettingsSaveFile> settingsSaveFile = new SaveFile<>(getSaveDirectory() + "settings.json", SettingsSaveFile.class);
    public static SaveFile<AppStateSaveFile> appStateSaveFile = new SaveFile<>(getSaveDirectory() + "app_state.json", AppStateSaveFile.class);
    public static SaveFile<OverlaySaveFile> overlaySaveFile = new SaveFile<>(getSaveDirectory() + "overlay.json", OverlaySaveFile.class);
    public static SaveFile<StashSaveFile> stashSaveFile = new SaveFile<>(getSaveDirectory() + "stash.json", StashSaveFile.class);
    public static SaveFile<IgnoreSaveFile> ignoreSaveFile = new SaveFile<>(getSaveDirectory() + "ignore.json", IgnoreSaveFile.class);
    public static SaveFile<PinSaveFile> pinSaveFile = new SaveFile<>(getSaveDirectory() + "pins.json", PinSaveFile.class);
    public static SaveFile<ChatScannerSaveFile> chatScannerSaveFile = new SaveFile<>(getSaveDirectory() + "scanner.json", ChatScannerSaveFile.class);
    public static SaveFile<PatchNotesSaveFile> patchNotesSaveFile = new SaveFile<>(getSaveDirectory() + "patch_notes.json", PatchNotesSaveFile.class);

    public static void init() {
        // Load all save files from disk
        settingsSaveFile.loadFromDisk();
        appStateSaveFile.loadFromDisk();
        overlaySaveFile.loadFromDisk();
        stashSaveFile.loadFromDisk();
        ignoreSaveFile.loadFromDisk();
        pinSaveFile.loadFromDisk();
        chatScannerSaveFile.loadFromDisk();
        patchNotesSaveFile.loadFromDisk();
        // Build Caches
        settingsSaveFile.data.buildMacroCache();
        ignoreSaveFile.data.buildCache();
        stashSaveFile.data.buildCache();
        // Finish
        addStaticListeners();
        SaveFilePatcherManager.handleSaveFilePatching();
    }

    private static void addStaticListeners() {
        // Only static listeners should be added here,
        // or when a save file needs to listen to itself since it is simpler than a custom class.
        SaveManager.settingsSaveFile.addListener(() -> {
            if (!FrameManager.hasBeenInitialized()) return;
            SwingUtilities.invokeLater(() -> {
                FrameManager.messageManager.refreshFadeData();
                FrameManager.stashHelperContainer.updateLocation();
                ThemeManager.checkFontChange();
            });
        });
        SaveManager.ignoreSaveFile.removeAllListeners();
        SaveManager.ignoreSaveFile.addListener(() -> {
            SaveManager.ignoreSaveFile.data.buildCache();
            if (!FrameManager.hasBeenInitialized()) return;
            assert SwingUtilities.isEventDispatchThread();
            for (IgnoreItemData ignoreItemData : SaveManager.ignoreSaveFile.data.ignoreList) {
                FrameManager.messageManager.quickCloseIgnore(ignoreItemData);
            }
        });
        stashSaveFile.addListener(() -> stashSaveFile.data.buildCache());
    }

    public static String getAudioDirectory() {
        if (audioDirectory == null)
            audioDirectory = validateDirectory(getSaveDirectory() + audioFolderName + File.separator);
        return audioDirectory;
    }

    public static String getImagesDirectory() {
        if (imagesDirectory == null)
            imagesDirectory = validateDirectory(getSaveDirectory() + imagesFolderName + File.separator);
        return imagesDirectory;
    }

    public static String getLogsDirectory() {
        if (logsDirectory == null)
            logsDirectory = getSaveDirectory() + logFolderName + File.separator;
        return logsDirectory;
    }

    public static String getSaveDirectory() {
        if (saveDirectory == null) {
            String os = (System.getProperty("os.name")).toUpperCase();
            if (os.contains("WIN")) {
                saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin + File.separator;
            } else {
                saveDirectory = System.getProperty("user.home") + File.separator + folderOther + File.separator;
            }
            validateDirectory(saveDirectory);
        }
        return saveDirectory;
    }

    public static String validateDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) ZLogger.err("Failed to validate directory: " + path);
        }
        return path;
    }

    public static ArrayList<String> getPotentialClients() {
        ArrayList<String> paths = new ArrayList<>();
        for (String path : getCommonDirectories()) {
            File file = new File(path);
            if (file.isFile()) paths.add(path);
        }
        return paths;
    }

    private static ArrayList<String> getCommonDirectories() {
        ArrayList<String> paths = new ArrayList<>();
        // Iterates A - Z
        for (int i = 65; i <= 90; i++) {
            char c = (char) i;
            // Stand Alone
            paths.add(c + ":/Grinding Gear Games/Path of Exile/logs/Client.txt");
            paths.add(c + ":/Program Files/Grinding Gear Games/Path of Exile/logs/Client.txt");
            paths.add(c + ":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt");
            // Steam
            paths.add(c + ":/Steam/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(c + ":/Program Files/Steam/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(c + ":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
            // Steam Library
            paths.add(c + ":/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(c + ":/Program Files/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(c + ":/Program Files (x86)/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
        }
        return paths;
    }

}