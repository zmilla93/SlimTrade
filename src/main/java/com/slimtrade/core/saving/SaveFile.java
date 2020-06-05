package com.slimtrade.core.saving;

import com.slimtrade.core.References;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.CustomIcons;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.options.ignore.IgnoreData;

import java.util.ArrayList;
import java.util.Date;

public class SaveFile {

    //Version
    public String versionNumber = References.getAppVersion();

    // Basics
    public String characterName = "";
    public boolean showGuildName = false;
    public boolean colorBlindMode = false;
    public ColorTheme colorTheme = ColorTheme.SOLARIZED_LIGHT;
    public QuickPasteSetting quickPasteSetting = QuickPasteSetting.DISABLED;
    public HotkeyData quickPasteHotkey = null;

    // Messaging
    public boolean collapseExcessiveMessages = false;
    public int messageCountBeforeCollapse = 3;
    public boolean fadeAfterDuration = false;
    public double secondsBeforeFading = 3;
    public int fadeOpacityPercent = 50;

    // History
    public TimeStyle timeStyle = TimeStyle.H12;
    public DateStyle dateStyle = DateStyle.DDMMYY;
    public OrderType orderType = OrderType.NEW_FIRST;
    public int historyLimit = 25;

    // Disable Features
    public boolean enableIncomingTrades = true;
    public boolean enableOutgoingTrades = true;
    public boolean enableItemHighlighter = true;
    public boolean enableMenubar = true;

    // Audio
    public SoundElement incomingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement outgoingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement scannerMessageSound = new SoundElement(Sound.PING2, 50);
    public SoundElement playerJoinedSound = new SoundElement(Sound.BLIP1, 50);
//    public SoundElement buttonSound = new SoundElement(Sound.CLICK1, 50);

    // Client
    public String clientPath = null;


    public ArrayList<StashTab> stashTabs = new ArrayList<>();
    public ArrayList<IgnoreData> ignoreData = new ArrayList<>();
    // Custom Macros
    public MacroButton[] incomingMacros = {
            new MacroButton(ButtonRow.TOP, "Hi, are you still interested in my {item} listed for {price}?", "", CustomIcons.REFRESH, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/invite {player}", "", CustomIcons.INVITE, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/tradewith {player}", "", CustomIcons.CART, null, false),
            new MacroButton(ButtonRow.BOTTOM, "thanks", "", CustomIcons.THUMB, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/kick {player}", "", CustomIcons.LEAVE, null, true),
    };
    public MacroButton[] outgoingMacros = {
            new MacroButton(ButtonRow.TOP, "{message}", "", CustomIcons.REFRESH, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/hideout {player}", "", CustomIcons.WARP, null, false),
            new MacroButton(ButtonRow.BOTTOM, "thanks", "", CustomIcons.THUMB, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/kick {self}", "", CustomIcons.LEAVE, null, false),
            new MacroButton(ButtonRow.BOTTOM, "/hideout", "", CustomIcons.HOME, null, true),
    };

    // Legacy for 0.2.4 and earlier
    public String thankIncomingLMB = "Thanks!", thankIncomingRMB;
    public String thankOutgoingLMB = "Thanks!", thankOutgoingRMB;
    public ArrayList<MacroButton> incomingMacroButtons = new ArrayList<>();
    public ArrayList<MacroButton> outgoingMacroButtons = new ArrayList<>();
    // End Legacy

    // Hotkeys
    public HotkeyData closeTradeHotkey = null;
    //    public HotkeyData closeIncomingHotkey = null;
//    public HotkeyData closeOutoingHotkey = null;
    public HotkeyData remainingHotkey = null;
    public HotkeyData hideoutHotkey = null;
    public HotkeyData leavePartyHotkey = null;
    public HotkeyData betrayalHotkey = null;

    // TODO : Should move this to somewhere more logical
    public static int dateDifference(Date d1, Date d2) {
        try {
            long diff = d2.getTime() - d1.getTime();
            if (diff < 0) {
                return -1;
            }

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;

//            System.out.print(diffDays + " days, ");
//            System.out.print(diffHours + " hours, ");
//            System.out.print(diffMinutes + " minutes, ");
//            System.out.print(diffSeconds + " seconds.");

            diffMinutes += diffHours * 60;
            if (diffSeconds > 30) {
                diffMinutes++;
            }
            return (int) diffMinutes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}