package com.slimtrade.core.legacy;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.savefiles.SettingsSaveFile;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.options.searching.StashSearchGroupData;
import com.slimtrade.gui.options.searching.StashSearchTermData;
import com.slimtrade.modules.saving.SaveFile;

import java.util.ArrayList;

public class SettingsSaveFilePatcher0to1 {

    public static boolean requiresConversion() {
        return SaveManager.settingsSaveFile.saveFileVersion() < 1;
    }

    public static boolean patch() {
        if (!requiresConversion()) return false;
        String filePath = SaveManager.settingsSaveFile.path;
        SaveFile<LegacySettingsSaveFile_0> legacySaveFile = new SaveFile<>(filePath, LegacySettingsSaveFile_0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) return false;

        SaveFilePatcherManager.copyMatchingFields(legacySaveFile.data, SaveManager.settingsSaveFile.data);
        handleFieldConversions(legacySaveFile.data, SaveManager.settingsSaveFile.data);
        SaveManager.settingsSaveFile.data.saveFileVersion = 1;
        // FIXME : Save to disk. Currently cannot save here due to listeners being null.
        return true;
    }

    private static void handleFieldConversions(LegacySettingsSaveFile_0 legacySaveFile, SettingsSaveFile saveFile) {
        saveFile.theme = legacySaveFile.theme.theme;

        // Cheat Sheets
        ArrayList<CheatSheetData> cheatSheetData = new ArrayList<>();
        for (LegacySettingsSaveFile_0.LegacyCheatSheetData data : legacySaveFile.cheatSheetData) {
            CheatSheetData newData = data.toCheatSheetData();
            if (newData == null) continue;
            cheatSheetData.add(newData);
        }
        saveFile.cheatSheets = cheatSheetData;

        // History
        saveFile.historyDateFormat = legacySaveFile.historyDateFormat.format;
        saveFile.historyOrder = legacySaveFile.historyOrder.order;

        // Macro Buttons
        ArrayList<MacroButton> incomingMacros = new ArrayList<>();
        ArrayList<MacroButton> outgoingMacros = new ArrayList<>();
        for (LegacySettingsSaveFile_0.LegacyMacroButton macro : legacySaveFile.incomingMacros)
            incomingMacros.add(macro.toMacroButton());
        for (LegacySettingsSaveFile_0.LegacyMacroButton macro : legacySaveFile.outgoingMacros)
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
        for (LegacySettingsSaveFile_0.LegacyStashTabData data : legacySaveFile.stashTabs)
            stashTabData.add(data.toStashTabData());
        saveFile.stashTabs = stashTabData;

        // Stash Search
        ArrayList<StashSearchGroupData> searchGroupList = new ArrayList<>();
        ArrayList<StashSearchTermData> termList = new ArrayList<>();
        for (LegacySettingsSaveFile_0.LegacyStashSearchData term : legacySaveFile.stashSearchData)
            termList.add(term.toTermData());
        searchGroupList.add(new StashSearchGroupData(1, "Stash", null, termList));
        saveFile.stashSearchData = searchGroupList;
    }

}