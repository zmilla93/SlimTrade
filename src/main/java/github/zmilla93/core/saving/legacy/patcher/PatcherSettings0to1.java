package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.data.CheatSheetData;
import github.zmilla93.core.data.IgnoreItemData;
import github.zmilla93.core.data.StashTabData;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.legacy.SaveFilePatcherManager;
import github.zmilla93.core.saving.legacy.savefiles.LegacySettingsSave0;
import github.zmilla93.core.saving.savefiles.SettingsSaveFile;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.gui.options.searching.StashSearchGroupData;
import github.zmilla93.gui.options.searching.StashSearchTermData;
import github.zmilla93.modules.saving.SaveFile;

import java.util.ArrayList;

public class PatcherSettings0to1 implements ISavePatcher {

    private String errorMessage;

    @Override
    public int getNewVersion() {
        return 1;
    }

    @Override
    public boolean requiresPatch() {
        return SaveManager.settingsSaveFile.fileExists() && SaveManager.settingsSaveFile.data.saveFileVersion < getNewVersion();
    }

    @Override
    public boolean patch() {
        SaveFile<LegacySettingsSave0> legacySaveFile = new SaveFile<>(SaveManager.settingsSaveFile.path, LegacySettingsSave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) {
            errorMessage = FAILED_TO_LOAD;
            return false;
        }
        SaveFilePatcherManager.copyMatchingFields(legacySaveFile.data, SaveManager.settingsSaveFile.data);
        handleFieldConversions(legacySaveFile.data, SaveManager.settingsSaveFile.data);
        return true;
    }

    @Override
    public void applyNewVersion() {
        /// Settings and ignore data were previously stored in the same file.
        SaveManager.ignoreSaveFile.data.saveFileVersion = getNewVersion();
        SaveManager.settingsSaveFile.data.saveFileVersion = getNewVersion();
        SaveManager.settingsSaveFile.saveToDisk(false);
        SaveManager.ignoreSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.settingsSaveFile.initData();
        SaveManager.ignoreSaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    private static void handleFieldConversions(LegacySettingsSave0 legacySaveFile, SettingsSaveFile saveFile) {
        saveFile.theme = legacySaveFile.theme.theme;
        saveFile.initializedFolderOffset = true;

        // Cheat Sheets
        ArrayList<CheatSheetData> cheatSheetData = new ArrayList<>();
        for (LegacySettingsSave0.LegacyCheatSheetData data : legacySaveFile.cheatSheetData) {
            CheatSheetData newData = data.toCheatSheetData();
            if (newData == null) continue;
            cheatSheetData.add(newData);
        }
        saveFile.cheatSheets = cheatSheetData;

        // History
        saveFile.historyDateFormat = legacySaveFile.historyDateFormat.format;
        saveFile.historyOrder = legacySaveFile.historyOrder.order;

        // Ignore Items
        ArrayList<IgnoreItemData> ignoreItemData = new ArrayList<>();
        for (LegacySettingsSave0.LegacyIgnoreData entry : legacySaveFile.ignoreData) {
            if (entry.duration != 0) continue;
            ignoreItemData.add(entry.toIgnoreItemData());
        }
        SaveManager.ignoreSaveFile.data.ignoreList = ignoreItemData;

        // Macro Buttons
        ArrayList<MacroButton> incomingMacros = new ArrayList<>();
        ArrayList<MacroButton> outgoingMacros = new ArrayList<>();
        for (LegacySettingsSave0.LegacyMacroButton macro : legacySaveFile.incomingMacros)
            incomingMacros.add(macro.toMacroButton());
        for (LegacySettingsSave0.LegacyMacroButton macro : legacySaveFile.outgoingMacros)
            outgoingMacros.add(macro.toMacroButton());
        saveFile.incomingMacroButtons = incomingMacros;
        saveFile.outgoingMacroButtons = outgoingMacros;

        // Sounds
        saveFile.incomingSound = legacySaveFile.incomingMessageSound.toSoundComponent();
        saveFile.outgoingSound = legacySaveFile.outgoingMessageSound.toSoundComponent();
        saveFile.chatScannerSound = legacySaveFile.scannerMessageSound.toSoundComponent();
        saveFile.playerJoinedAreaSound = legacySaveFile.playerJoinedSound.toSoundComponent();
        saveFile.updateSound = legacySaveFile.updateSound.toSoundComponent();

        // Stash Tabs
        ArrayList<StashTabData> stashTabData = new ArrayList<>();
        for (LegacySettingsSave0.LegacyStashTabData data : legacySaveFile.stashTabs)
            stashTabData.add(data.toStashTabData());
        saveFile.stashTabs = stashTabData;

        // Stash Search
        ArrayList<StashSearchGroupData> searchGroupList = new ArrayList<>();
        ArrayList<StashSearchTermData> termList = new ArrayList<>();
        for (LegacySettingsSave0.LegacyStashSearchData term : legacySaveFile.stashSearchData)
            termList.add(term.toTermData());
        searchGroupList.add(new StashSearchGroupData(1, "Stash", null, termList));
        saveFile.stashSearchData = searchGroupList;
    }

}
