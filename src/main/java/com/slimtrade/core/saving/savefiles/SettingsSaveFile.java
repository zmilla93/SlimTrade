package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.core.utility.MacroButton;

/***
 * Class representation of settings.json
 */
public class SettingsSaveFile {

    // General
    public boolean showGuildName;
    public boolean folderOffset;
    public boolean colorBlindMode;
    public String characterName;
    public ColorTheme colorTheme;
    public QuickPasteManager.QuickPasteMode quickPasteMode;
    public HotkeyData quickPasteHotkey;

    // Path of Exile
    public String clientPath;

    public SoundComponent incomingSound;
    public SoundComponent outgoingSound;
    public SoundComponent chatScannerSound;
    public SoundComponent playerJoinedAreaSound;
    public SoundComponent updateSound;

    public MacroButton[] incomingMacroButtons;
    public MacroButton[] outgoingMacroButtons;

    // SlimTrade Hotkeys
    public HotkeyData optionsHotkey;
    public HotkeyData historyHotkey;
    public HotkeyData chatScannerHotkey;
    public HotkeyData closeTradeHotkey;

    // POE Hotkeys
    public HotkeyData delveHotkey;
    public HotkeyData doNotDisturbHotkey;
    public HotkeyData exitToMenuHotkey;
    public HotkeyData guildHideoutHotkey;
    public HotkeyData hideoutHotkey;
    public HotkeyData leavePartyHotkey;
    public HotkeyData menagerieHotkey;
    public HotkeyData metamorphHotkey;
    public HotkeyData remainingMonstersHotkey;

    // Link Hotkeys

}
