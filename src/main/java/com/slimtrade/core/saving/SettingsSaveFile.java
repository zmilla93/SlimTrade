package com.slimtrade.core.saving;

import com.slimtrade.core.audio.SoundComponent;
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

}
