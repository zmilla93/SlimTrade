package com.slimtrade.core.SaveSystem;

import com.slimtrade.App;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.enums.ExpandDirection;
import com.slimtrade.gui.options.OrderType;
import com.slimtrade.gui.options.ignore.IgnoreData;
import com.slimtrade.gui.scanner.ScannerMessage;

import java.io.File;
import java.util.ArrayList;

public class SaveFile {

    // Overlay
    public int menubarX = 0;
    public int menubarY = 0;
    public int messageManagerX = 500;
    public int messageManagerY = 0;
    public ExpandDirection messageExpandDirection = ExpandDirection.DOWN;
    public MenubarButtonLocation menubarButtonLocation = MenubarButtonLocation.NW;

    //Stash
    public int stashX = 100;
    public int stashY = 100;
    public int stashWidth = 300;
    public int stashHeight = 300;

    // Basics
    public String characterName = "";
    public boolean showGuildName = false;
    public boolean closeOnKick = false;
    public boolean quickPasteTrades = false;
    public ColorTheme colorTheme = ColorTheme.LIGHT_THEME;

    // History
    public TimeStyle timeStyle = TimeStyle.H12;
    public DateStyle dateStyle = DateStyle.DDMMYY;
    public OrderType orderType = OrderType.NEW_FIRST;
    public int historyLimit = 50;

    //Audio
    public SoundElement incomingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement outgoingMessageSound = new SoundElement(Sound.PING1, 50);
    public SoundElement scannerMessageSound = new SoundElement(Sound.PING2, 50);
    public SoundElement buttonSound = new SoundElement(Sound.CLICK1, 50);

    // Client
    public String clientPath = null;
    public String clientDirectory = null;
    public boolean validClientPath = false;
    public int clientCount;

    // Custom Macros
    public ArrayList<StashTab> stashTabs = new ArrayList<>();
    public ArrayList<MacroButton> incomingMacroButtons = new ArrayList<>();
    public ArrayList<MacroButton> outgoingMacroButtons = new ArrayList<>();
    public ArrayList<IgnoreData> ignoreData = new ArrayList<>();
    public ArrayList<ScannerMessage> scannerMessages = new ArrayList<>();




}
