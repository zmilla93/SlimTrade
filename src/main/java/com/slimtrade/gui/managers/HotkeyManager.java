package com.slimtrade.gui.managers;

import com.slimtrade.App;
import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.hotkeys.*;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.options.searching.StashSearchGroupData;
import com.slimtrade.gui.options.searching.StashSearchWindowMode;
import com.slimtrade.gui.windows.CheatSheetWindow;
import org.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.util.HashMap;

/**
 * To add a new hotkey type, implement IHotkeyAction then register with a HotkeyData object in loadHotkeys.
 * Notification Panel hotkeys are handled in MessageManager, but keystroke data is still sent from here.
 */
public class HotkeyManager {

    private static final HashMap<HotkeyData, IHotkeyAction> hotkeyMap = new HashMap<>();

    public static void loadHotkeys() {
        hotkeyMap.clear();
        // SlimTrade
        registerHotkey(SaveManager.settingsSaveFile.data.optionsHotkey, new WindowHotkey(FrameManager.optionsWindow));
        registerHotkey(SaveManager.settingsSaveFile.data.historyHotkey, new WindowHotkey(FrameManager.historyWindow));
        registerHotkey(SaveManager.settingsSaveFile.data.chatScannerHotkey, new WindowHotkey(FrameManager.chatScannerWindow));
        registerHotkey(SaveManager.settingsSaveFile.data.changeCharacterHotkey, TradeUtil::changeCharacterName);
        registerHotkey(SaveManager.settingsSaveFile.data.closeTradeHotkey, () -> SwingUtilities.invokeLater(() -> FrameManager.messageManager.closeOldestTrade()));
        // POE
        registerHotkey(SaveManager.settingsSaveFile.data.delveHotkey, new PoeCommandHotkey("/delve"));
        registerHotkey(SaveManager.settingsSaveFile.data.doNotDisturbHotkey, new PoeCommandHotkey("/dnd"));
        registerHotkey(SaveManager.settingsSaveFile.data.exitToMenuHotkey, new PoeCommandHotkey("/exit"));
        registerHotkey(SaveManager.settingsSaveFile.data.guildHideoutHotkey, new PoeCommandHotkey("/guild"));
        registerHotkey(SaveManager.settingsSaveFile.data.hideoutHotkey, new PoeCommandHotkey("/hideout"));
        if (SaveManager.settingsSaveFile.data.characterName != null)
            registerHotkey(SaveManager.settingsSaveFile.data.leavePartyHotkey, new PoeCommandHotkey("/kick " + SaveManager.settingsSaveFile.data.characterName));
        registerHotkey(SaveManager.settingsSaveFile.data.menagerieHotkey, new PoeCommandHotkey("/menagerie"));
        registerHotkey(SaveManager.settingsSaveFile.data.necropolisHotkey, new PoeCommandHotkey("/necropolis"));
        registerHotkey(SaveManager.settingsSaveFile.data.remainingMonstersHotkey, new PoeCommandHotkey("/remaining"));
        // Stash Searching
        if (SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSearchWindowMode.COMBINED) {
            registerHotkey(SaveManager.settingsSaveFile.data.stashSearchHotkey, new WindowHotkey(FrameManager.combinedSearchWindow));
        } else if (SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSearchWindowMode.SEPARATE) {
            for (StashSearchGroupData data : SaveManager.settingsSaveFile.data.stashSearchData) {
                registerHotkey(data.hotkeyData, new SearchWindowHotkey(data.id));
            }
        }
        // Cheat Sheets
        for (CheatSheetData cheatSheetData : SaveManager.settingsSaveFile.data.cheatSheets) {
            CheatSheetWindow window = FrameManager.cheatSheetWindows.get(cheatSheetData.title());
            registerHotkey(cheatSheetData.hotkeyData, new WindowHotkey(window));
        }
    }

    private static void registerHotkey(HotkeyData hotkeyData, IHotkeyAction action) {
        if (hotkeyData == null) return;
        // FIXME : Should inform user of duplicate hotkeys
        if (hotkeyMap.containsKey(hotkeyData)) return;
        hotkeyMap.put(hotkeyData, action);
    }

    public static void processHotkey(NativeKeyEvent e) {
        if (App.getState() != AppState.RUNNING) return;
        if (!POEInterface.isGameFocused(true)) return;
        HotkeyData data = new HotkeyData(e.getKeyCode(), e.getModifiers());
        IHotkeyAction hotkeyAction = hotkeyMap.get(data);
        if (hotkeyAction == null) {
            FrameManager.messageManager.checkHotkey(data);
        } else {
            hotkeyAction.execute();
        }
    }

}
