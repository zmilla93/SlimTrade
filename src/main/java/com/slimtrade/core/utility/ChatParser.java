package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.enums.LangRegex;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.enums.MatchType;
import com.slimtrade.gui.options.ignore.IgnoreData;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatParser {

    // Internal
    private InputStreamReader reader;
    private BufferedReader bufferedReader;
    private int curLineCount = 0;
    private int totalLineCount;
    private String curLine;
    private ActionListener updateAction = e -> procUpdate();
    private Timer updateTimer = new Timer(500, updateAction);
    private static final Pattern SEARCH_PATTERN = Pattern.compile(References.REGEX_SCANNER_PREFIX + "(?<scannerMessage>.+))");
    private static final Pattern JOINED_PATTERN = Pattern.compile(".+ : (.+) has joined the area(.)");
    private ArrayList<IgnoreData> whisperIgnoreData = new ArrayList<IgnoreData>();
    private boolean chatScannerRunning = false;
    private String[] searchIgnoreTerms;
    private String[] searchTerms;
    private String searchName;

    // Debugging
    final int MAX_PRINT = 3;
    int procCount = 0;
    int updateCount = 0;

    public void init() {
        FrameManager.historyWindow.clearHistory();
        int msgCount = 0;
        updateTimer.stop();
        try {
            reader = new InputStreamReader(new FileInputStream(App.saveManager.saveFile.clientPath), StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(reader);
        } catch (FileNotFoundException e) {
            App.debugger.log("[ERROR] Chat parser failed to launch.");
            App.logger.log(Level.SEVERE, "Chat parser failed to launch");
            return;
        }
        // TODO : Init history
        totalLineCount = 0;
        try {
            while ((curLine = bufferedReader.readLine()) != null) {
                LangRegex lang;
                if (curLine.contains("@") && (lang = getLang(curLine)) != null) {
                    TradeOffer trade = getTradeOffer(curLine, lang);
                    if (trade != null) {
                        FrameManager.historyWindow.addTrade(trade, false);
                    }
                    msgCount++;
                }
                totalLineCount++;
            }
            FrameManager.historyWindow.buildHistory();
            App.debugger.log(msgCount + " whisper messages found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateTimer.start();
        App.debugger.log(totalLineCount + " total lines found.");
        App.debugger.log("Chat parser successfully launched.");
    }

    private void procUpdate() {
//        if(procCount < MAX_PRINT) {
//            System.out.println("Parser proc...");
//            procCount++;
//        }else if(procCount == MAX_PRINT) {
//            System.out.println("Silencing proc");
//            procCount++;
//        }
        try {
            reader = new InputStreamReader(new FileInputStream(App.saveManager.saveFile.clientPath), StandardCharsets.UTF_8);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
//        if(updateCount < MAX_PRINT) {
//            System.out.println("Parser update...");
//            updateCount++;
//        } else if(updateCount == MAX_PRINT) {
//            System.out.println("Silencing update");
//            updateCount++;
//        }
        try {
            reader = new InputStreamReader(new FileInputStream(App.saveManager.saveFile.clientPath), StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(reader);
            curLineCount = 0;
            while ((curLine = bufferedReader.readLine()) != null) {
                curLineCount++;
                if (curLineCount > totalLineCount) {
                    totalLineCount++;
                    LangRegex lang;
                    if (curLine.contains("@") && (lang = getLang(curLine)) != null) {
                        TradeOffer trade = getTradeOffer(curLine, lang);
                        if (trade != null) {
                            if ((App.saveManager.saveFile.enableIncomingTrades || trade.messageType != MessageType.INCOMING_TRADE)
                                    && (App.saveManager.saveFile.enableOutgoingTrades || trade.messageType != MessageType.OUTGOING_TRADE)) {
                                boolean ignore = false;
                                if(trade.messageType == MessageType.INCOMING_TRADE) {
                                    for (IgnoreData data : App.saveManager.saveFile.ignoreData) {
                                        if ((data.matchType == MatchType.CONTAINS && trade.itemName.contains(data.itemName))
                                                || (data.matchType == MatchType.EXACT && trade.itemName.matches(data.itemName))) {
                                            ignore = true;
                                            break;
                                        }
                                    }
                                }
                                if (!ignore) {
                                    FrameManager.messageManager.addMessage(trade);
                                    FrameManager.historyWindow.addTrade(trade, true);
                                }
                            }
                        }
                    } else if (chatScannerRunning) {
                        TradeOffer trade = getSearchOffer(curLine);
                        if (trade != null) {
                            FrameManager.messageManager.addMessage(trade);
                        }
                    }
                    Matcher joinAreaMatcher = JOINED_PATTERN.matcher(curLine);
                    if (joinAreaMatcher.matches()) {
                        if (joinAreaMatcher.groupCount() > 1) {
                            FrameManager.messageManager.setPlayerJoinedArea(joinAreaMatcher.group(1));
                        }
                    }
                }
            }
            bufferedReader.close();
            reader.close();
        } catch (NumberFormatException | IOException e) {
            App.debugger.log("[Chat Parser] Exception encountered while attempting to update parser.");
        }
    }

    public LangRegex getLang(String text) {
        // Languages only support one contain text so 'wtb is checked separately to support legacy sites
        if (text.contains("wtb")) {
            return LangRegex.ENGLISH;
        }
        for (LangRegex l : LangRegex.values()) {
            if (text.contains(l.CONTAINS_TEXT)) {
                return l;
            }
        }
        return null;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setSearchTerms(String[] terms) {
        this.searchTerms = terms;
    }

    public void setSearchIgnoreTerms(String[] terms) {
        this.searchIgnoreTerms = terms;
    }

    public boolean validateQuickPaste(String text, LangRegex lang) {
        Matcher matcher = null;
        boolean found = false;
        for (Pattern p : lang.QUICK_PASTE_PATTERNS) {
            matcher = p.matcher(text);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
//        if (!found) {
//            return null;
//        }
//        TradeOffer trade = new TradeOffer();
//        trade.date = matcher.group("date").replaceAll("/", "-");
//        trade.time = matcher.group("time");
//        trade.time = cleanResult(matcher, "time");
//        trade.messageType = getMessageType(matcher.group("messageType"));
//        trade.guildName = matcher.group("guildName");
//        trade.playerName = matcher.group("playerName");
//        trade.itemName = matcher.group("itemName");
//        trade.itemQuantity = cleanDouble(cleanResult(matcher, "itemQuantity"));
//        trade.priceTypeString = cleanResult(matcher, "priceType");
//        trade.priceQuantity = cleanDouble(cleanResult(matcher, "priceQuantity"));
//        trade.stashtabName = cleanResult(matcher, "stashtabName");
//        trade.stashtabX = cleanInt(cleanResult(matcher, "stashX"));
//        trade.stashtabY = cleanInt(cleanResult(matcher, "stashY"));
//        trade.bonusText = cleanResult(matcher, "bonusText");
//        trade.sentMessage = matcher.group("message");
//        return trade;
    }

    public TradeOffer getTradeOffer(String text, LangRegex lang) {
        Matcher matcher = null;
        boolean found = false;
        for (Pattern p : lang.CLIENT_PATTERNS) {
            matcher = p.matcher(text);
            if (matcher.matches()) {
                found = true;
                break;
            }
        }
        if (!found) {
            return null;
        }
        TradeOffer trade = new TradeOffer();
        trade.date = matcher.group("date").replaceAll("/", "-");
        trade.time = matcher.group("time");
        trade.time = cleanResult(matcher, "time");
        trade.messageType = getMessageType(matcher.group("messageType"));
        trade.guildName = matcher.group("guildName");
        trade.playerName = matcher.group("playerName");
        trade.itemName = matcher.group("itemName");
        trade.itemQuantity = cleanDouble(cleanResult(matcher, "itemQuantity"));
        trade.priceTypeString = cleanResult(matcher, "priceType");
        trade.priceQuantity = cleanDouble(cleanResult(matcher, "priceQuantity"));
        trade.stashtabName = cleanResult(matcher, "stashtabName");
        trade.stashtabX = cleanInt(cleanResult(matcher, "stashX"));
        trade.stashtabY = cleanInt(cleanResult(matcher, "stashY"));
        trade.bonusText = cleanResult(matcher, "bonusText");
        trade.sentMessage = matcher.group("message");
        if (trade.messageType == MessageType.UNKNOWN) {
            return null;
        }
        return trade;
    }

    private TradeOffer getSearchOffer(String text) {
        Matcher matcher = SEARCH_PATTERN.matcher(text);
        if (matcher.matches()) {
            TradeOffer trade = new TradeOffer();
            trade.date = matcher.group("date");
            trade.time = matcher.group("time");
            trade.messageType = MessageType.CHAT_SCANNER;
            trade.guildName = matcher.group("guildName");
            trade.playerName = matcher.group("playerName");
            trade.searchName = this.searchName;
            trade.searchMessage = matcher.group("scannerMessage");
            String chatMessage = trade.searchMessage.toLowerCase();
            if (this.searchIgnoreTerms != null) {
                for (String s : this.searchIgnoreTerms) {
                    if (chatMessage.contains(s)) {
                        return null;
                    }
                }
            }
            boolean found = false;
            for (String s : this.searchTerms) {
                if (!s.equals("")) {
                    if (chatMessage.contains(s)) {
                        found = true;
                    }
                }
            }
            if (found) {
                return trade;
            }
        }
        return null;
    }

    private <T> T cleanResult(Matcher matcher, String text) {
        try {
            return (T) matcher.group(text);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private int cleanInt(String text) {
        if (text == null) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    private double cleanDouble(String text) {
        if (text == null) {
            return 0;
        }
        text = text.replaceAll(",", ".");
        return Double.parseDouble(text);
    }

    private MessageType getMessageType(String s) {
        MessageType type = MessageType.UNKNOWN;
        switch (s.toLowerCase()) {
            case "to":
            case "à":       // French
            case "an":      // German
            case "para":    // Portuguese & Spanish
            case "кому":    // Russian
            case "ถึง":      // Thai
                type = MessageType.OUTGOING_TRADE;
                break;
            case "from":
            case "de":      // French, Portuguese & Spanish
            case "von":     // German
            case "от кого": // Russian
            case "จาก":     // Thai
                type = MessageType.INCOMING_TRADE;
                break;
        }
        return type;
    }

    public void setWhisperIgnoreTerms(ArrayList<IgnoreData> ignoreData) {
        this.whisperIgnoreData = ignoreData;
    }

    public void setChatScannerRunning(boolean state) {
        this.chatScannerRunning = state;
    }
}
