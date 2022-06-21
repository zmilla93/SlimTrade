package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.data.PriceThresholdData;
import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.*;
import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.core.managers.QuickPasteManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.stashsorting.StashSortData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/***
 * Class representation of settings.json
 */
public class SettingsSaveFile {

    // General
    public boolean showGuildName;
    public boolean folderOffset;
    public String characterName;
    public QuickPasteManager.QuickPasteMode quickPasteMode = QuickPasteManager.QuickPasteMode.DISABLED;
    public HotkeyData quickPasteHotkey;
    public ArrayList<CheatSheetData> cheatSheets = new ArrayList<>();

    // Message Popups
    public boolean collapseMessages;
    public int messagesBeforeCollapse = SpinnerRange.MESSAGES_BEFORE_COLLAPSE.START;
    public boolean fadeMessages;
    public float secondsBeforeFading = SpinnerRangeFloat.SECONDS_BEFORE_FADE.START;
    public int fadedOpacity = SliderRange.FADED_OPACITY.START;

    // Display
    public int fontSize = SpinnerRange.FONT_SIZE.START;
    public int iconSize = SpinnerRange.ICON_SIZE.START;
    public transient boolean fontSizeChanged;
    public transient boolean iconSizeChanged;
    public ColorTheme colorTheme;
    public boolean colorBlindMode;

    // History
    public HistoryOrder historyOrder = HistoryOrder.NEWEST_FIRST;
    public TimeFormat historyTimeFormat = TimeFormat.H12;
    public DateFormat historyDateFormat = DateFormat.MM_DD_YY;

    // Path of Exile
    // FIXME:
    public String clientPath = "C:/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt";

    // Enable Features
    public boolean enableIncomingMessages = true;
    public boolean enableOutgoingMessages = true;
    public boolean enableItemHighlighter = true;
    public boolean enableMenuBar = true;
    public boolean enableAutomaticUpdate = true;
    // FIXME : Switch default to true before release
    public boolean hideWhenPOENotFocused = false;

    // Stash
    public ArrayList<StashTabData> stashTabs = new ArrayList<>();
    public boolean applyStashColorToMessage;

    // Audio
    public SoundComponent incomingSound;
    public SoundComponent outgoingSound;
    public SoundComponent itemIgnoredSound;
    public SoundComponent chatScannerSound;
    public SoundComponent playerJoinedAreaSound;
    public SoundComponent updateSound;
    public ArrayList<PriceThresholdData> priceThresholds = new ArrayList<>();
    public transient final HashMap<CurrencyType, ArrayList<PriceThresholdData>> priceThresholdMap = new HashMap<>();

    // Macros
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
    public HotkeyData changeCharacterHotkey;

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

    public SettingsSaveFile() {
        incomingMacroButtons.add(new MacroButton(CustomIcon.INVITE, "/invite {player}", "", ButtonRow.BOTTOM_ROW, null, false));
        incomingMacroButtons.add(new MacroButton(CustomIcon.CART, "/tradewith {player}", "", ButtonRow.BOTTOM_ROW, null, false));
        incomingMacroButtons.add(new MacroButton(CustomIcon.THUMB, "thanks", "", ButtonRow.BOTTOM_ROW, null, false));
        incomingMacroButtons.add(new MacroButton(CustomIcon.LEAVE, "/kick {player}", "", ButtonRow.BOTTOM_ROW, null, true));

        outgoingMacroButtons.add(new MacroButton(CustomIcon.REFRESH, "{message}", "", ButtonRow.TOP_ROW, null, false));
        outgoingMacroButtons.add(new MacroButton(CustomIcon.WARP, "/hideout {player}", "", ButtonRow.BOTTOM_ROW, null, false));
        outgoingMacroButtons.add(new MacroButton(CustomIcon.THUMB, "thanks", "", ButtonRow.BOTTOM_ROW, null, false));
        outgoingMacroButtons.add(new MacroButton(CustomIcon.LEAVE, "/kick {self}", "", ButtonRow.BOTTOM_ROW, null, false));
        outgoingMacroButtons.add(new MacroButton(CustomIcon.HOME, "/hideout", "", ButtonRow.BOTTOM_ROW, null, true));
    }

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

    public void buildThresholdMap() {
        priceThresholdMap.clear();
        for (PriceThresholdData data : priceThresholds) {
            CurrencyType currency = CurrencyType.getCurrencyImage(data.currencyType.ID);
            ArrayList<PriceThresholdData> thresholds = priceThresholdMap.get(currency);
            if (thresholds == null) {
                thresholds = new ArrayList<>();
            }
            thresholds.add(data);
            priceThresholdMap.put(currency, thresholds);
        }
        for (ArrayList<PriceThresholdData> thresholds : priceThresholdMap.values()) {
            thresholds.sort(Collections.reverseOrder());
        }
    }

}
