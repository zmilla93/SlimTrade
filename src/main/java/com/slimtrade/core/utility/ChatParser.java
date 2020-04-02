package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.debug.Debugger;
import com.slimtrade.enums.LangRegex;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.ParserRegex;
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
    private static final String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";
    private ArrayList<IgnoreData> whisperIgnoreData = new ArrayList<IgnoreData>();
    private boolean chatScannerRunning = false;
    private String[] searchIgnoreTerms;
    private String[] searchTerms;
    private String searchName;

    private final String CONTAINS_ENG_1 = "like to buy";
    private final String CONTAINS_ENG_2 = "wtb";

    private final String REG_PREFIX = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*@(?<messageType>To|From) (<.+> )?(.+): ";

    // English
    private final Pattern ENG_PAT_1 = Pattern.compile(REG_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtab>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?");
    private final Pattern ENG_PAT_2 = Pattern.compile(REG_PREFIX + "Hi, I'd like to buy your ((?<itemQuantity>\\d+(.\\d+)?) (?<itemName>.+)) for my ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) in (?<league>.+).");
    private final Pattern ENG_PAT_3 = Pattern.compile(REG_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtab>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?");
    private final Pattern ENG_PAT_4 = Pattern.compile(REG_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) in (?<league>.+)\\.?(?<bonusText>.+)?");
    private final Pattern ENG_PAT_5 = Pattern.compile(REG_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+)\\.?(?<bonusText>.+)?");
    private final Pattern[] ENGLISH_PATTERNS = {ENG_PAT_1, ENG_PAT_1, ENG_PAT_3, ENG_PAT_4, ENG_PAT_5};

    // Russian
    private final Pattern RUS_PAT = Pattern.compile(REG_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+)\\.?(?<bonusText>.+)?");


    // TODO : Move path to options
    public void init() {
        Debugger.benchmarkStart();
        System.out.println("Launching chat parser...");
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
        try {
            reader = new InputStreamReader(new FileInputStream(App.saveManager.saveFile.clientPath), StandardCharsets.UTF_8);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        long start = System.currentTimeMillis();
        try {
            reader = new InputStreamReader(new FileInputStream(App.saveManager.saveFile.clientPath), StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(reader);
            curLineCount = 0;
            while ((curLine = bufferedReader.readLine()) != null) {
                curLineCount++;
                if (curLineCount > totalLineCount) {
                    totalLineCount++;
//                    ParserRegex r = validateTradeMessage(curLine);
                    LangRegex lang;
                    if (curLine.contains("@") && (lang = getLang(curLine)) != null) {
                        App.debugger.log(curLine);
                        TradeOffer trade = getTradeOffer(curLine, lang);
                        if (trade != null) {
                            if ((!App.saveManager.saveFile.enableIncomingTrades && trade.messageType == MessageType.INCOMING_TRADE)
                                    || (!App.saveManager.saveFile.enableOutgoingTrades && trade.messageType == MessageType.OUTGOING_TRADE)) {
                                // Ignore trades if option has been disabled, but still add them to history
                            } else {
                                FrameManager.messageManager.addMessage(trade);
                            }
                            FrameManager.historyWindow.addTrade(trade, true);
                        }
                    } else if (chatScannerRunning) {
                        // TODO : Chat Scanner
//                        TradeOffer trade = getSearchOffer(curLine);
//                        if (trade != null) {
//                            FrameManager.messageManager.addMessage(trade);
//                        }
                    } else {
                        Matcher joinAreaMatcher = Pattern.compile(playerJoinedAreaString).matcher(curLine);
                        if (joinAreaMatcher.matches()) {
                            for (int i = 0; i < joinAreaMatcher.groupCount(); i++) {
                                if (joinAreaMatcher.groupCount() > 1) {
                                    FrameManager.messageManager.setPlayerJoinedArea(joinAreaMatcher.group(1));
                                }
                            }
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

    public ParserRegex validateTradeMessage(String message) {
        for (ParserRegex r : ParserRegex.values()) {
            if (message.contains(r.getContains())) {
                return r;
            }
        }
        return null;
    }

    private TradeOffer getTradeOffer(String text, LangRegex lang) {
        Matcher matcher = null;
        boolean found = false;
        for (Pattern p : lang.PATTERNS) {
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
//            public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String itemName, double itemCount, String priceTypeString, double priceCount,
//        String stashtabName, int stashtabX, int stashtabY, String bonusText, String sentMessage) {
        trade.date = matcher.group("date").replaceAll("/", "-");
        trade.time = matcher.group("time");
        trade.messageType = getMessageType(matcher.group("messageType"));
        trade.guildName = matcher.group("guildName");
        trade.playerName = matcher.group("playerName");
        trade.itemName = matcher.group("itemName");
        trade.itemCount = cleanDouble(cleanResult(matcher, "itemCount"));
        trade.priceTypeString = cleanResult(matcher, "priceType");
        trade.priceCount = cleanDouble(cleanResult(matcher, "priceQuantity"));
        trade.stashtabName = cleanResult(matcher, "stashtabName");
        trade.stashtabX = cleanInt(cleanResult(matcher, "stashX"));;
        trade.stashtabY = cleanInt(cleanResult(matcher, "stashY"));;
        trade.bonusText = cleanResult(matcher, "bonusText");
        trade.sentMessage = "";
        System.out.println("ITEM : " + trade.itemName);
        System.out.println("X : " + trade.stashtabX);
        System.out.println("Y : " + trade.stashtabY);
//        if (matcher != null && matcher.matches()) {
//        System.out.println("PARSING : " + text);
//        for (int i = 0; i < matcher.groupCount(); i++) {
//            System.out.println("G" + i + "|" + matcher.group(i));
//        }
        // DEBUG
        // System.out.println("NEW TRADE OFFER");
        // for (int i = 0; i < 24; i++) {
        // System.out.println("GROUP #" + i + " : " +
        // matcher.group(i));
        // }
        // System.out.println("");
        // DEBUG END

        // date, time, MessageType msgType, guildName, playerName
        // itemName, Double itemCount, priceTypeString, Double priceCount
        // stashtabName, int stashtabX, int stashtabY, bonusText,
        // sentMessage
//        double d1 = 0.0;
//        double d2 = 0.0;
//        // Item Count
//        if (matcher.group(11) != null) {
//            d1 = Double.parseDouble(matcher.group(11));
//        }
//        // Price Count
//        if (matcher.group(14) != null) {
//            d2 = Double.parseDouble(matcher.group(14));
//        }
//        int i1 = 0;
//        int i2 = 0;
//        // Stashtab X
//        if (matcher.group(22) != null) {
//            i1 = Integer.parseInt(matcher.group(22));
//        }
//        // Stashtab Y
//        if (matcher.group(24) != null) {
//            i2 =
//            Integer.parseInt(matcher.group(24));
//        }
//        trade = new TradeOffer(matcher.group(2).replaceAll("/", "-"), matcher.group(3), getMessageType(matcher.group(4)), matcher.group(5), matcher.group(6), matcher.group(12), d1, matcher.group(15), d2, matcher.group(20), i1, i2, matcher.group(25), matcher.group(7));
//        if (trade.messageType == MessageType.INCOMING_TRADE && this.whisperIgnoreData != null) {
//            for (IgnoreData d : this.whisperIgnoreData) {
//                if (d.getMatchType() == MatchType.CONTAINS) {
//                    if (trade.itemName.toLowerCase().contains(d.getItemName().toLowerCase())) {
//                        return null;
//                    }
//                } else if (d.getMatchType() == MatchType.EXACT) {
//                    if (trade.itemName.toLowerCase().equals(d.getItemName().toLowerCase())) {
//                        return null;
//                    }
//                }
//            }
//        }
        return trade;
//        } else {
//            return null;
//        }
    }

    private <T> T cleanResult(Matcher matcher, String text) {
        try {
            T result;
            result = (T) matcher.group(text);
            return result;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private int cleanInt(String text) {
        if(text == null) {
            return 0;
        }
        int i = Integer.parseInt(text);
        return i;
    }

    private double cleanDouble(String text) {
        if(text == null) {
            return 0;
        }
        double d = Double.parseDouble(text);
        return d;
    }

    private MessageType getMessageType(String s) {
        MessageType type = MessageType.UNKNOWN;
        switch (s.toLowerCase()) {
            case "to":
                type = MessageType.OUTGOING_TRADE;
                break;
            case "from":
                type = MessageType.INCOMING_TRADE;
                break;
        }
        return type;
    }

    public void setWhisperIgnoreTerms(ArrayList<IgnoreData> ignoreData) {
        this.whisperIgnoreData = ignoreData;
    }

}
