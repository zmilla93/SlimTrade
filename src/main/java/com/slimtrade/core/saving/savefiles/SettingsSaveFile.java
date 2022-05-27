package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.*;
import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.stashsorting.StashSortData;

import java.util.ArrayList;

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
    public ArrayList<CheatSheetData> cheatSheets = new ArrayList<>();

    // History
    public HistoryOrder historyOrder = HistoryOrder.NEWEST_FIRST;
    public TimeFormat historyTimeFormat = TimeFormat.H12;
    public DateFormat historyDateFormat = DateFormat.MM_DD_YY;

    // Path of Exile
    public String clientPath;

    // Enable Features
    public boolean enableIncomingMessages;
    public boolean enableOutgoingMessages;
    public boolean enableItemHighlighter;
    public boolean enableMenuBar;
    public boolean enableAutomaticUpdate;
    public boolean hideWhenPOENotFocused;

    // Stash
    public ArrayList<StashTabData> stashTabs = new ArrayList<>();

    // Display
    public int fontSize = SpinnerRange.FONT_SIZE.START;
    public int iconSize = SpinnerRange.ICON_SIZE.START;
    public transient boolean fontSizeChanged;
    public transient boolean iconSizeChanged;

    // Audio
    public SoundComponent incomingSound;
    public SoundComponent outgoingSound;
    public SoundComponent itemIgnoredSound;
    public SoundComponent chatScannerSound;
    public SoundComponent playerJoinedAreaSound;
    public SoundComponent updateSound;

    // Macros
    public boolean applyStashColorToMessage;
    public ArrayList<MacroButton> incomingMacroButtons = new ArrayList<>();
    public ArrayList<MacroButton> outgoingMacroButtons = new ArrayList<>();
    public transient ArrayList<MacroButton> incomingTopMacros = new ArrayList<>();
    public transient ArrayList<MacroButton> incomingBottomMacros = new ArrayList<>();
    public transient ArrayList<MacroButton> outgoingTopMacros = new ArrayList<>();
    public transient ArrayList<MacroButton> outgoingBottomMacros = new ArrayList<>();

    // SlimTrade Hotkeys
    public HotkeyData optionsHotkey;
    public HotkeyData historyHotkey;
    public HotkeyData chatScannerHotkey;
    public HotkeyData stashSortHotkey;
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

    public ArrayList<StashSortData> stashSortData = new ArrayList<>();

    // Internal
    public transient boolean clientFileRotated;

    // Macro Generators
    public void buildMacroCache() {
        incomingTopMacros.clear();
        incomingBottomMacros.clear();
        outgoingTopMacros.clear();
        outgoingBottomMacros.clear();
        for (MacroButton button : incomingMacroButtons) {
            if (button.row == ButtonRow.TOP_ROW) {
                incomingTopMacros.add(button);
            } else {
                incomingBottomMacros.add(button);
            }
        }
        for (MacroButton button : outgoingMacroButtons) {
            if (button.row == ButtonRow.TOP_ROW) {
                outgoingTopMacros.add(button);
            } else {
                outgoingBottomMacros.add(button);
            }
        }
    }

}
