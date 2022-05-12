package com.slimtrade.gui.managers;

import com.slimtrade.core.hotkeys.*;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.managers.SaveManager;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.HashMap;

public class HotkeyManager {

    private static HashMap<HotkeyData, IHotkeyAction> hotkeyMap;

    public static void loadHotkeys() {
        hotkeyMap = new HashMap<>();
        // SlimTrade
        registerHotkey(SaveManager.settingsSaveFile.data.optionsHotkey, new AppHotkey(AppHotkey.AppWindow.OPTIONS));
        registerHotkey(SaveManager.settingsSaveFile.data.historyHotkey, new AppHotkey(AppHotkey.AppWindow.HISTORY));
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
    }

    private static void registerHotkey(HotkeyData hotkeyData, IHotkeyAction action) {
        if (hotkeyData == null) return;
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
