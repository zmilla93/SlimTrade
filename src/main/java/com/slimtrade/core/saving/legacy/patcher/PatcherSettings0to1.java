package com.slimtrade.core.saving.legacy.patcher;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.legacy.ISavePatcher;
import com.slimtrade.core.saving.legacy.SaveFilePatcherManager;
import com.slimtrade.core.saving.legacy.savefiles.LegacySettingsSave0;
import com.slimtrade.core.saving.savefiles.SettingsSaveFile;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.options.searching.StashSearchGroupData;
import com.slimtrade.gui.options.searching.StashSearchTermData;
import com.slimtrade.modules.saving.SaveFile;

import java.util.ArrayList;

public class PatcherSettings0to1 implements ISavePatcher {

    @Override
    public boolean requiresPatch() {
        return SaveManager.settingsSaveFile.saveFileVersion() < 1;
    }

    @Override
    public boolean patch() {
        String filePath = SaveManager.settingsSaveFile.path;
        SaveFile<LegacySettingsSave0> legacySaveFile = new SaveFile<>(filePath, LegacySettingsSave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) return false;

        SaveFilePatcherManager.copyMatchingFields(legacySaveFile.data, SaveManager.settingsSaveFile.data);
        handleFieldConversions(legacySaveFile.data, SaveManager.settingsSaveFile.data);
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.settingsSaveFile.data.saveFileVersion = 1;
        SaveManager.settingsSaveFile.saveToDisk(false);
        SaveManager.ignoreSaveFile.data.saveFileVersion = 1;
        SaveManager.ignoreSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.settingsSaveFile.initData();
        SaveManager.ignoreSaveFile.initData();
    }

    private static void handleFieldConversions(LegacySettingsSave0 legacySaveFile, SettingsSaveFile saveFile) {
        saveFile.theme = legacySaveFile.theme.theme;

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
