package com.slimtrade.gui.managers;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.hotkeys.*;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.windows.CheatSheetWindow;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.HashMap;

/**
 * To add a new hotkey type, implement IHotkeyAction then register with a HotkeyData object in loadHotkeys.
 */
public class HotkeyManager {

    private static HashMap<HotkeyData, IHotkeyAction> hotkeyMap = new HashMap<>();

    public static void loadHotkeys() {
        hotkeyMap.clear();
        // SlimTrade
        registerHotkey(SaveManager.settingsSaveFile.data.optionsHotkey, new AppHotkey(AppHotkey.AppWindow.OPTIONS));
        registerHotkey(SaveManager.settingsSaveFile.data.historyHotkey, new AppHotkey(AppHotkey.AppWindow.HISTORY));
        registerHotkey(SaveManager.settingsSaveFile.data.stashSortHotkey, new AppHotkey(AppHotkey.AppWindow.STASH_SORT_WINDOW));
        // POE
        registerHotkey(SaveManager.settingsSaveFile.data.delveHotkey, new PoeHotkey("/delve"));
        registerHotkey(SaveManager.settingsSaveFile.data.doNotDisturbHotkey, new PoeHotkey("/dnd"));
        registerHotkey(SaveManager.settingsSaveFile.data.exitToMenuHotkey, new PoeHotkey("/exit"));
        registerHotkey(SaveManager.settingsSaveFile.data.guildHideoutHotkey, new PoeHotkey("/guild"));
        registerHotkey(SaveManager.settingsSaveFile.data.hideoutHotkey, new PoeHotkey("/hideout"));
        if (SaveManager.settingsSaveFile.data.characterName != null)
            registerHotkey(SaveManager.settingsSaveFile.data.leavePartyHotkey, new PoeHotkey("/kick " + SaveManager.settingsSaveFile.data.characterName));
        registerHotkey(SaveManager.settingsSaveFile.data.menagerieHotkey, new PoeHotkey("/menagerie"));
        registerHotkey(SaveManager.settingsSaveFile.data.metamorphHotkey, new PoeHotkey("/metamorph"));
        registerHotkey(SaveManager.settingsSaveFile.data.remainingMonstersHotkey, new PoeHotkey("/remaining"));
        // Quick Paste
        if (SaveManager.settingsSaveFile.data.quickPasteMode == QuickPasteManager.QuickPasteMode.HOTKEY)
            registerHotkey(SaveManager.settingsSaveFile.data.quickPasteHotkey, new QuickPasteHotkey());
        // Cheat Sheets
        for (CheatSheetData cheatSheetData : SaveManager.settingsSaveFile.data.cheatSheets) {
            CheatSheetWindow window = FrameManager.cheatSheetWindows.get(cheatSheetData.title);
            registerHotkey(cheatSheetData.hotkeyData, new CheatSheetHotkey(window));
        }
    }

    private static void registerHotkey(HotkeyData hotkeyData, IHotkeyAction action) {
        if (hotkeyData == null) return;
        if (hotkeyMap.containsKey(hotkeyData)) return;   // Duplicate hotkeys are ignored
        hotkeyMap.put(hotkeyData, action);
    }

    public static void processHotkey(NativeKeyEvent e) {
        if (hotkeyMap == null) return;
        HotkeyData data = new HotkeyData(e.getKeyCode(), e.getModifiers());
        IHotkeyAction hotkeyAction = hotkeyMap.get(data);
        if (hotkeyAction == null) return;
        hotkeyAction.execute();
    }

}
