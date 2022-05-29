package com.slimtrade.core.managers;

import com.slimtrade.core.saving.savefiles.*;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.saving.SaveFile;

import javax.swing.*;
import java.io.File;

public class SaveManager {

    // Save Directory
    private static String saveDirectory;
    private static final String folderWin = "SlimTrade-Rebuild";
    private static final String folderOther = ".slimtrade-rebuild";

    // Subfolder Names
    private static final String audioFolderName = "audio";
    private static final String imagesFolderName = "images";

    public static SaveFile<SettingsSaveFile> settingsSaveFile = new SaveFile<>(getSaveDirectory() + "settings.json", SettingsSaveFile.class);
    public static SaveFile<OverlaySaveFile> overlaySaveFile = new SaveFile<>(getSaveDirectory() + "overlay.json", OverlaySaveFile.class);
    public static SaveFile<StashSaveFile> stashSaveFile = new SaveFile<>(getSaveDirectory() + "stash.json", StashSaveFile.class);
    public static SaveFile<IgnoreSaveFile> ignoreSaveFile = new SaveFile<>(getSaveDirectory() + "ignore.json", IgnoreSaveFile.class);
    public static SaveFile<PinSaveFile> pinSaveFile = new SaveFile<>(getSaveDirectory() + "pins.json", PinSaveFile.class);

    public static void init() {

        SaveManager.settingsSaveFile.removeAllListeners();
        SaveManager.settingsSaveFile.addListener(new ISaveListener() {
            @Override
            public void onSave() {
                if (SaveManager.settingsSaveFile.data.fontSizeChanged) {
                    ColorManager.setFontSize(SaveManager.settingsSaveFile.data.fontSize);
                    SaveManager.settingsSaveFile.data.fontSizeChanged = false;
                }
                if (SaveManager.settingsSaveFile.data.iconSizeChanged) {
                    ColorManager.setIconSize(SaveManager.settingsSaveFile.data.iconSize);
                    SaveManager.settingsSaveFile.data.iconSizeChanged = false;
                }
                SaveManager.settingsSaveFile.data.buildMacroCache();
                assert SwingUtilities.isEventDispatchThread();
                // FIXME : force update ui only needs to run if colorblind mode was changed.
                FrameManager.messageManager.forceUpdateUI();
            }

            @Override
            public void onLoad() {
                SaveManager.settingsSaveFile.data.buildMacroCache();
            }
        });
        SaveManager.ignoreSaveFile.removeAllListeners();
        SaveManager.ignoreSaveFile.addListener(new ISaveListener() {
            @Override
            public void onSave() {
                SaveManager.ignoreSaveFile.data.buildCache();
            }

            @Override
            public void onLoad() {
                SaveManager.ignoreSaveFile.data.buildCache();
            }
        });
        stashSaveFile.addListener(new ISaveListener() {
            @Override
            public void onSave() {
                stashSaveFile.data.buildCache();
            }

            @Override
            public void onLoad() {
                stashSaveFile.data.buildCache();
            }
        });

        settingsSaveFile.loadFromDisk();
        overlaySaveFile.loadFromDisk();
        stashSaveFile.loadFromDisk();
        ignoreSaveFile.loadFromDisk();
        pinSaveFile.loadFromDisk();
    }

    public static String getAudioDirectory() {
        return getSaveDirectory() + audioFolderName + "/";
    }

    public static String getImagesDirectory() {
        return getSaveDirectory() + imagesFolderName + File.separator;
    }

    public static String getSaveDirectory() {
        if (saveDirectory == null) {
            String os = (System.getProperty("os.name")).toUpperCase();
            if (os.contains("WIN")) {
                saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin + File.separator;
            } else {
                saveDirectory = System.getProperty("user.home") + File.separator + folderOther + File.separator;
            }
        }
        return saveDirectory;
    }
}