package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.saving.legacy.SaveFilePatcherManager;
import com.slimtrade.core.saving.savefiles.*;
import com.slimtrade.core.utility.Platform;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.saving.SaveFile;
import com.slimtrade.modules.theme.ThemeManager;
import com.slimtrade.modules.updater.ZLogger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class SaveManager {

    // Install folder names
    protected static Path persistentDataDirectory;
    public static final String saveFolderName = App.getAppInfo().appName;
    public static final String folderWin = "SlimTrade";
    public static final String folderOther = ".slimtrade";
    private static final String backupSuffix = "-Backup";

    // Subfolder Names
    private static final String logFolderName = "logs";

    // Full Paths
    private static Path saveDirectoryPath;
    private static Path backupDirectoryPath;
    private static final Path logsDirectoryPath = getSaveDirectoryPath().resolve("logs");
    private static final Path audioDirectory = getSaveDirectoryPath().resolve("audio");
    private static final Path imagesDirectoryPath = getSaveDirectoryPath().resolve("images");
    private static final Path ninjaDirectoryPath = getSaveDirectoryPath().resolve("ninja");

    // Full Paths (OLD)
    private static String saveDirectory;
    private static String logsDirectory;
    private static String ninjaDirectory;

    // Save Files
    public static SaveFile<SettingsSaveFile> settingsSaveFile = new SaveFile<>(getSaveDirectoryPath().resolve("settings.json").toString(), SettingsSaveFile.class);
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
                FrameManager.stashHelperContainerLegacy.updateLocation();
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

//    public static String getImagesDirectory() {
//        if (imagesDirectory == null)
//            imagesDirectory = validateDirectory(getSaveDirectory() + imagesFolderName + File.separator);
//        return imagesDirectory;
//    }

    public static String getLogsDirectory() {
        if (logsDirectory == null)
            logsDirectory = validateDirectory(getSaveDirectory() + logFolderName + File.separator);
        return logsDirectory;
    }

    public static String getNinjaDirectory() {
        if (ninjaDirectory == null)
            ninjaDirectory = validateDirectory(getSaveDirectory() + "ninja" + File.separator);
        return ninjaDirectory;
    }

    public static Path getAudioDirectory() {
        return validatePath(audioDirectory);
    }

    public static Path getImagesDirectoryPath() {
        return validatePath(imagesDirectoryPath);
    }

    public static Path getNinjaDirectoryPath() {
        return validatePath(ninjaDirectoryPath);
    }

    public static Path getLogsDirectoryPath() {
        return validatePath(logsDirectoryPath);
    }

    public static String getSaveDirectory() {
        if (saveDirectory == null) {
            if (Platform.current == Platform.WINDOWS)
                saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin + File.separator;
            else
                saveDirectory = System.getProperty("user.home") + File.separator + folderOther + File.separator;
            validateDirectory(saveDirectory);
        }
        return saveDirectory;
    }

    public static Path getSaveDirectoryPath() {
        if (saveDirectoryPath == null) {
            if (Platform.current == Platform.WINDOWS)
                saveDirectoryPath = getPersistentDataDirectory().resolve(saveFolderName);
            else saveDirectoryPath = getPersistentDataDirectory().resolve("." + saveFolderName.toLowerCase());
            validatePath(saveDirectoryPath);
        }
        return saveDirectoryPath;
    }

    public static Path getBackupDirectoryPath() {
        if (backupDirectoryPath == null) {
            if (Platform.current == Platform.WINDOWS)
                backupDirectoryPath = getPersistentDataDirectory().resolve(saveFolderName + backupSuffix);
            else
                backupDirectoryPath = getPersistentDataDirectory().resolve("." + saveFolderName.toLowerCase() + backupSuffix.toLowerCase());
            validatePath(backupDirectoryPath);
        }
        return backupDirectoryPath;
    }

    /**
     * Returns a directory to store a persistent app data folder based on the current {@link Platform}.
     */
    private static Path getPersistentDataDirectory() {
        if (persistentDataDirectory == null) {
            if (Platform.current == Platform.WINDOWS)
                persistentDataDirectory = Paths.get(System.getenv("LocalAppData"));
            else
                persistentDataDirectory = Paths.get(System.getProperty("user.home"));
        }
        return persistentDataDirectory;
    }

    /// Ensures that all directories in a path exist. Throws a fatal exception if validation fails.
    public static Path validatePath(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            ZLogger.err("Failed to validate path: " + path);
            throw new RuntimeException(e);
        }
        return path;
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
            char letter = (char) i;
            // Stand Alone
            paths.add(letter + ":/Grinding Gear Games/Path of Exile/logs/Client.txt");
            paths.add(letter + ":/Program Files/Grinding Gear Games/Path of Exile/logs/Client.txt");
            paths.add(letter + ":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt");
            // Steam
            paths.add(letter + ":/Steam/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(letter + ":/Program Files/Steam/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(letter + ":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
            // Steam Library
            paths.add(letter + ":/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(letter + ":/Program Files/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
            paths.add(letter + ":/Program Files (x86)/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
        }
        return paths;
    }

    public static void createBackup() {
        try {
            deleteDirectoryContents(getBackupDirectoryPath());
            copyFilesRecursively(getSaveDirectoryPath(), getBackupDirectoryPath());
            ZLogger.log("Created new backup.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadBackup() {
        try {
            deleteDirectoryContents(getSaveDirectoryPath());
            copyFilesRecursively(getBackupDirectoryPath(), getSaveDirectoryPath());
            // FIXME : Call loadFromDisk on all save files, then revert UI.
            ZLogger.log("Loaded backup.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFilesRecursively(Path source, Path destination) throws IOException {
        if (!Files.exists(destination)) Files.createDirectories(destination);
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = destination.resolve(source.relativize(dir));
                if (!Files.exists(targetDir)) Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetFile = destination.resolve(source.relativize(file));
                try {
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (FileSystemException ignore) {
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void deleteDirectoryContents(Path directory) throws IOException {
        if (!Files.exists(directory) || !Files.isDirectory(directory)) return;
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try {
                    Files.delete(file);
                } catch (FileSystemException ignore) {
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                try {
                    Files.delete(dir);
                } catch (FileSystemException ignore) {
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}