package com.slimtrade.core.SaveSystem;

import com.slimtrade.core.References;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.options.ignore.IgnoreData;

import java.util.ArrayList;
import java.util.Date;

public class SaveFile {

    //Version
    public String versionNumber = References.APP_VERSION;

    // Basics
    public String characterName = "";
    public boolean showGuildName = false;
    public boolean closeOnKick = false;
    public boolean quickPasteTrades = false;
    public ColorTheme colorTheme = ColorTheme.SOLARIZED_LIGHT;

    // History
    public TimeStyle timeStyle = TimeStyle.H12;
    public DateStyle dateStyle = DateStyle.DDMMYY;
    public OrderType orderType = OrderType.NEW_FIRST;
    public int historyLimit = 25;

    //Audio
    public SoundElement incomingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement outgoingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement scannerMessageSound = new SoundElement(Sound.PING2, 50);
    public SoundElement buttonSound = new SoundElement(Sound.CLICK1, 50);

    // Client
    public String clientPath = null;
//    public boolean validClientPath = false;
//    public int clientCount;

    // Custom Macros
    public ArrayList<StashTab> stashTabs = new ArrayList<>();
    public String thankIncomingLMB = "Thanks!", thankIncomingRMB;
    public String thankOutgoingLMB = "Thanks!", thankOutgoingRMB;
    public ArrayList<MacroButton> incomingMacroButtons = new ArrayList<>();
    public ArrayList<MacroButton> outgoingMacroButtons = new ArrayList<>();
    public ArrayList<IgnoreData> ignoreData = new ArrayList<>();

    public static int dateDifference(Date d1, Date d2) {
        try {
            long diff = d2.getTime() - d1.getTime();
            if (diff < 0) {
                return -1;
            }

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);

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