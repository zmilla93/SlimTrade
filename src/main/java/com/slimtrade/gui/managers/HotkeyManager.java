package com.slimtrade.gui.managers;

import com.slimtrade.App;
import com.slimtrade.core.data.*;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.HashMap;

public class HotkeyManager {

    private static HashMap<Integer, HotkeyActionMeh> hotkeyMapOld;
    private static HashMap<Integer, IHotkeyAction> hotkeyMap;

    public static void loadHotkeys() {
        hotkeyMap = new HashMap<>();
        // SlimTrade
        registerHotkey(App.saveManager.settingsSaveFile.optionsHotkey, new AppHotkey(AppHotkey.AppWindow.OPTIONS));
        registerHotkey(App.saveManager.settingsSaveFile.historyHotkey, new AppHotkey(AppHotkey.AppWindow.HISTORY));
        // POE
        registerHotkey(App.saveManager.settingsSaveFile.delveHotkey, new PoeHotkey("/delve"));
        registerHotkey(App.saveManager.settingsSaveFile.doNotDisturbHotkey, new PoeHotkey("/dnd"));
        registerHotkey(App.saveManager.settingsSaveFile.exitToMenuHotkey, new PoeHotkey("/exit"));
        registerHotkey(App.saveManager.settingsSaveFile.guildHideoutHotkey, new PoeHotkey("/guild"));
        registerHotkey(App.saveManager.settingsSaveFile.hideoutHotkey, new PoeHotkey("/hideout"));
        if (App.saveManager.settingsSaveFile.characterName != null)
            registerHotkey(App.saveManager.settingsSaveFile.leavePartyHotkey, new PoeHotkey("/kick " + App.saveManager.settingsSaveFile.characterName));
        registerHotkey(App.saveManager.settingsSaveFile.menagerieHotkey, new PoeHotkey("/menagerie"));
        registerHotkey(App.saveManager.settingsSaveFile.metamorphHotkey, new PoeHotkey("/metamorph"));
        registerHotkey(App.saveManager.settingsSaveFile.remainingMonstersHotkey, new PoeHotkey("/remaining"));
    }

    private static void addHotkey(KeystrokeData keystroke, HotkeyActionMeh action) {
        if (keystroke == null) return;
        hotkeyMapOld.put(keystroke.keyCode, action);
    }

    private static void registerHotkey(KeystrokeData keystroke, IHotkeyAction action) {
        if (keystroke == null) return;
        hotkeyMap.put(keystroke.keyCode, action);
    }

    public static void processHotkey(NativeKeyEvent e) {
        if (hotkeyMap == null) return;
        IHotkeyAction hotkeyAction = hotkeyMap.get(e.getKeyCode());
        if (hotkeyAction == null) return;
        hotkeyAction.execute();
    }

}
