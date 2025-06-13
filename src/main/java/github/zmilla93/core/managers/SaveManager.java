package github.zmilla93.core.managers;

import github.zmilla93.App;
import github.zmilla93.core.data.IgnoreItemData;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.saving.legacy.SaveFilePatcherManager;
import github.zmilla93.core.saving.savefiles.*;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.saving.SaveFile;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.updater.ZLogger;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

// FIXME : Should switch to using instances to allow SaveManager to become a library.
public class SaveManager {

    // Install folder names
    public static final String saveFolderName = App.getAppInfo().appName;
    private static final String backupSuffix = "-Backup";

    // Save Directories - Use getters to ensure directories exist!
    private static Path persistentDataDirectory; // LocalAppData on Windows, user.home on Mac/Linux
    private static Path saveDirectory; // Save stuff here!
    private static Path backupDirectory; // Used for making backups of saveDirectory
    private static final Path audioDirectory = getSaveDirectory().resolve("audio");
    private static final Path imagesDirectory = getSaveDirectory().resolve("images");
    private static final Path ninjaDirectory = getSaveDirectory().resolve("ninja");
    private static final Path logsDirectory = getSaveDirectory().resolve("logs");

    // Save Files
    public static SaveFile<SettingsSaveFile> settingsSaveFile = new SaveFile<>(getSaveDirectory().resolve("settings.json"), SettingsSaveFile.class);
    public static SaveFile<AppStateSaveFile> appStateSaveFile = new SaveFile<>(getSaveDirectory().resolve("app_state.json"), AppStateSaveFile.class);
    public static SaveFile<OverlaySaveFile> overlaySaveFile = new SaveFile<>(getSaveDirectory().resolve("overlay.json"), OverlaySaveFile.class);
    public static SaveFile<StashSaveFile> stashSaveFile = new SaveFile<>(getSaveDirectory().resolve("stash.json"), StashSaveFile.class);
    public static SaveFile<IgnoreSaveFile> ignoreSaveFile = new SaveFile<>(getSaveDirectory().resolve("ignore.json"), IgnoreSaveFile.class);
    public static SaveFile<PinSaveFile> pinSaveFile = new SaveFile<>(getSaveDirectory().resolve("pins.json"), PinSaveFile.class);
    public static SaveFile<ChatScannerSaveFile> chatScannerSaveFile = new SaveFile<>(getSaveDirectory().resolve("scanner.json"), ChatScannerSaveFile.class);
    public static SaveFile<PatchNotesSaveFile> patchNotesSaveFile = new SaveFile<>(getSaveDirectory().resolve("patch_notes.json"), PatchNotesSaveFile.class);
    public static SaveFile<LinuxSaveFile> linuxSaveFile = new SaveFile<>(getSaveDirectory().resolve("linux.json"), LinuxSaveFile.class);

    public static boolean isNewInstall = false;

    public static void init() {
        // Load all save files from disk
        settingsSaveFile.loadFromDisk();
        if (!settingsSaveFile.loadedExistingData()) {
            System.out.println("new install!");
            isNewInstall = true;
        } else {
            System.out.println("existing save found");
        }
        appStateSaveFile.loadFromDisk();
        overlaySaveFile.loadFromDisk();
        stashSaveFile.loadFromDisk();
        ignoreSaveFile.loadFromDisk();
        pinSaveFile.loadFromDisk();
        chatScannerSaveFile.loadFromDisk();
        patchNotesSaveFile.loadFromDisk();
        linuxSaveFile.loadFromDisk();
        // Build Caches
        settingsSaveFile.data.buildMacroCache();
        ignoreSaveFile.data.buildCache();
        stashSaveFile.data.buildCache();
        // Add Listeners
        // FIXME: Should listeners be last?
        addStaticListeners();
        // Handle save file patching
        SaveFilePatcherManager.handleSaveFilePatching();
    }

    private static void addStaticListeners() {
        // Only static listeners should be added here,
        // or when a save file needs to listen to itself since it is simpler than a custom class.
        SaveManager.settingsSaveFile.addListener(() -> {
            if (!FrameManager.hasBeenInitialized()) return;
            SwingUtilities.invokeLater(() -> {
                FrameManager.messageManager.refreshFadeData();
                FrameManager.stashHelperContainerPoe1.updateLocation();
                FrameManager.stashHelperContainerPoe2.updateLocation();
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

    public static Path getAudioDirectory() {
        return validatePath(audioDirectory);
    }

    public static Path getImagesDirectory() {
        return validatePath(imagesDirectory);
    }

    public static Path getNinjaDirectory() {
        return validatePath(ninjaDirectory);
    }

    public static Path getLogsDirectory() {
        return validatePath(logsDirectory);
    }

    public static Path getSaveDirectory() {
        if (saveDirectory == null) {
            if (Platform.current == Platform.WINDOWS)
                saveDirectory = getPersistentDataDirectory().resolve(saveFolderName);
            else
                saveDirectory = getPersistentDataDirectory().resolve("." + saveFolderName.toLowerCase());
        }
        return validatePath(saveDirectory);
    }

    public static Path getBackupDirectory() {
        if (backupDirectory == null) {
            if (Platform.current == Platform.WINDOWS)
                backupDirectory = getPersistentDataDirectory().resolve(saveFolderName + backupSuffix);
            else
                backupDirectory = getPersistentDataDirectory().resolve("." + saveFolderName.toLowerCase() + backupSuffix.toLowerCase());
        }
        return validatePath(backupDirectory);
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
        return validatePath(persistentDataDirectory);
    }

    /// Ensures that all directories in a path exist. Throws a fatal exception if validation fails.
    public static Path validatePath(Path path) {
        if (path.toFile().exists()) return path;
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            ZLogger.err("Failed to validate path: " + path);
            throw new RuntimeException(e);
        }
        return path;
    }

    public static Path[] getValidGameDirectories(Game game) {
        ArrayList<Path> paths = getPotentialGameDirectories(game);
        for (int i = paths.size() - 1; i >= 0; i--) {
            Path path = paths.get(i);
            if (!path.toFile().exists()) paths.remove(i);
        }
        return paths.toArray(new Path[0]);
    }

    private static ArrayList<Path> getPotentialGameDirectories(Game game) {
        ArrayList<Path> paths = new ArrayList<>();
        // Iterates A - Z
        for (int i = 65; i <= 90; i++) {
            char letter = (char) i;
            // Stand Alone
            paths.add(Paths.get(letter + ":/Grinding Gear Games/" + game));
            paths.add(Paths.get(letter + ":/Program Files/Grinding Gear Games/" + game));
            paths.add(Paths.get(letter + ":/Program Files (x86)/Grinding Gear Games/" + game));
            // Steam
            paths.add(Paths.get(letter + ":/Steam/steamapps/common/" + game));
            paths.add(Paths.get(letter + ":/Program Files/Steam/steamapps/common/" + game));
            paths.add(Paths.get(letter + ":/Program Files (x86)/Steam/steamapps/common/" + game));
            // Steam Library
            paths.add(Paths.get(letter + ":/SteamLibrary/steamapps/common/" + game));
            paths.add(Paths.get(letter + ":/Program Files/SteamLibrary/steamapps/common/" + game));
            paths.add(Paths.get(letter + ":/Program Files (x86)/SteamLibrary/steamapps/common/" + game));
        }
        return paths;
    }

    public static void createBackup() {
        try {
            deleteDirectoryContents(getBackupDirectory());
            copyFilesRecursively(getSaveDirectory(), getBackupDirectory());
            ZLogger.log("Created new backup.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadBackup() {
        try {
            deleteDirectoryContents(getSaveDirectory());
            copyFilesRecursively(getBackupDirectory(), getSaveDirectory());
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