package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.saving.savefiles.SettingsSaveFile;
import com.slimtrade.core.saving.savefiles.StashSaveFile;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.saving.SaveFile;

import java.io.File;

public class SaveManager {

    // Save Directory
    private static String saveDirectory;
    private static final String folderWin = "SlimTrade-Rebuild";
    private static final String folderOther = ".slimtrade-rebuild";

    // Save Files
    private static final String settingsSaveName = "settings.json";
    private static final String stashSaveName = "stash.json";

    // Subfolder Names
    private static final String audioFolderName = "audio";
    private static final String imagesFolderName = "images";

    public static SaveFile<SettingsSaveFile> settingsSaveFile = new SaveFile<>(getSaveDirectory() + settingsSaveName, SettingsSaveFile.class);
    public static SaveFile<StashSaveFile> stashSaveFile = new SaveFile<>(getSaveDirectory() + stashSaveName, StashSaveFile.class);

    public static void init() {
        settingsSaveFile.loadFromDisk();
        stashSaveFile.loadFromDisk();

        SaveManager.settingsSaveFile.removeAllListeners();
        SaveManager.settingsSaveFile.addListener(new ISaveListener() {
            @Override
            public void onSave() {
                if (SaveManager.settingsSaveFile.data.fontSizeChanged) {
                    ColorManager.setFontSize(SaveManager.settingsSaveFile.data.textSize);
                    SaveManager.settingsSaveFile.data.fontSizeChanged = false;
                }
                if (SaveManager.settingsSaveFile.data.iconSizeChanged) {
                    ColorManager.setIconSize(SaveManager.settingsSaveFile.data.iconSize);
                    SaveManager.settingsSaveFile.data.iconSizeChanged = false;
                }
                SaveManager.settingsSaveFile.data.buildMacroCache();
            }

            @Override
            public void onLoad() {
                // Do Nothing
            }
        });
    }

    public static String getAudioDirectory() {
        return getSaveDirectory() + audioFolderName + File.separator;
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