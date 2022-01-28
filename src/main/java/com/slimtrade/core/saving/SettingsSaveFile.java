package com.slimtrade.core.saving;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.data.KeystrokeData;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.core.utility.MacroButton;

/***
 * Class representation of settings.json
 */
public class SettingsSaveFile {

    public boolean showGuildName;
    public boolean folderOffset;
    public boolean colorBlindMode;
    public String characterName;
    public ColorTheme colorTheme;

    public SoundComponent incomingSound;
    public SoundComponent outgoingSound;
    public SoundComponent chatScannerSound;
    public SoundComponent playerJoinedAreaSound;
    public SoundComponent updateSound;

    public MacroButton[] incomingMacroButtons;
    public MacroButton[] outgoingMacroButtons;

    // SlimTrade Hotkeys
    public KeystrokeData optionsHotkey;
    public KeystrokeData historyHotkey;
    public KeystrokeData chatScannerHotkey;
    public KeystrokeData closeTradeHotkey;

    // POE Hotkeys
    public KeystrokeData delveHotkey;
    public KeystrokeData doNotDisturbHotkey;
    public KeystrokeData exitToMenuHotkey;
    public KeystrokeData guildHideoutHotkey;
    public KeystrokeData hideoutHotkey;
    public KeystrokeData leavePartyHotkey;
    public KeystrokeData menagerieHotkey;
    public KeystrokeData metamorphHotkey;
    public KeystrokeData remainingMonstersHotkey;

    // Link Hotkeys

}
