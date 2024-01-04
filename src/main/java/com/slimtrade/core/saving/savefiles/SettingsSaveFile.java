package com.slimtrade.core.saving.savefiles;

import com.slimtrade.App;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.data.PriceThresholdData;
import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.*;
import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.listening.IColorBlindChangeListener;
import com.slimtrade.gui.options.searching.StashSearchGroupData;
import com.slimtrade.gui.options.searching.StashSearchWindowMode;
import com.slimtrade.modules.theme.Theme;
import com.slimtrade.modules.updater.data.AppVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Class representation of settings.json
 */
public class SettingsSaveFile {

    // TODO : Validate appVersion on launch and update if needed
    public static final int VERSION = 1;
    public String appVersionString;
    private transient AppVersion appVersion;
    public int saveFileVersion = 1;

    // General
    public String characterName;
    public boolean showGuildName;
    public boolean initializedFolderOffset;
    public boolean folderOffset;
    public HotkeyData quickPasteHotkey;
    public ArrayList<CheatSheetData> cheatSheets = new ArrayList<>();

    // Message Popups
    public boolean collapseMessages;
    public int messageCountBeforeCollapse = SpinnerRange.MESSAGES_BEFORE_COLLAPSE.START;
    public boolean fadeMessages;
    public float secondsBeforeFading = SpinnerRangeFloat.SECONDS_BEFORE_FADE.START;
    public int fadedOpacity = SliderRange.FADED_OPACITY.START;

    // Display
    public Theme theme;
    public int fontSize = SpinnerRange.FONT_SIZE.START;
    public int iconSize = SpinnerRange.ICON_SIZE.START;
    public transient boolean fontSizeChanged;
    public transient boolean iconSizeChanged;
    public boolean colorBlindMode;
    private final transient ArrayList<IColorBlindChangeListener> colorBlindChangeListeners = new ArrayList<>();

    // History
    public HistoryOrder historyOrder = HistoryOrder.NEWEST_FIRST;
    public TimeFormat historyTimeFormat = TimeFormat.H12;
    public DateFormat historyDateFormat = DateFormat.MM_DD_YY;

    // Path of Exile
    public String clientPath;

    // Enable Features
    public boolean enableIncomingTrades = true;
    public boolean enableOutgoingTrades = true;
    public boolean enableItemHighlighter = true;
    public boolean enableMenuBar = true;
    public boolean enableAutomaticUpdate = true;
    public boolean hideWhenPOENotFocused = true;

    // Stash
    public ArrayList<StashTabData> stashTabs = new ArrayList<>();
    public boolean applyStashColorToMessage;

    // Audio
    public SoundComponent incomingSound = new SoundComponent(new Sound("Ping 1", Sound.SoundType.INBUILT), 50);
    public SoundComponent outgoingSound = new SoundComponent(new Sound("Ping 1", Sound.SoundType.INBUILT), 50);
    public SoundComponent itemIgnoredSound = new SoundComponent(new Sound("Blip 2", Sound.SoundType.INBUILT), 50);
    public SoundComponent chatScannerSound = new SoundComponent(new Sound("Ping 2", Sound.SoundType.INBUILT), 50);
    public SoundComponent playerJoinedAreaSound = new SoundComponent(new Sound("Blip 1", Sound.SoundType.INBUILT), 50);
    public SoundComponent updateSound = new SoundComponent(new Sound("Blip 3", Sound.SoundType.INBUILT), 50);
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

    // Searching
    public HotkeyData stashSearchHotkey;
    public StashSearchWindowMode stashSearchWindowMode = StashSearchWindowMode.COMBINED;
    public ArrayList<StashSearchGroupData> stashSearchData = new ArrayList<>();

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
            CurrencyType currency = CurrencyType.getCurrencyType(data.currencyType.ID);
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

    public void triggerColorBlindModeChange(boolean colorBlindMode) {
        for (IColorBlindChangeListener listener : colorBlindChangeListeners) {
            listener.onColorBlindChange(colorBlindMode);
        }
    }

    public void addColorBlindListener(IColorBlindChangeListener listener) {
        if (colorBlindChangeListeners.contains(listener)) return;
        colorBlindChangeListeners.add(listener);
    }

    public void removeColorBlindListener(IColorBlindChangeListener listener) {
        colorBlindChangeListeners.remove(listener);
    }

    public AppVersion appVersion() {
        if (appVersion == null) {
            appVersion = new AppVersion(appVersionString);
            if (!appVersion.valid) appVersion = new AppVersion(App.appInfo.appVersion.toString());
        }
        return appVersion;
    }

}
