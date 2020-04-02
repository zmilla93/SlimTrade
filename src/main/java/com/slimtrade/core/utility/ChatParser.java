package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.debug.Debugger;
import com.slimtrade.enums.LangRegex;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
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
    //    private static final String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";
//    private static String searchMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$](<.+> )?(\\S+): (.+)";
    private static final Pattern SEARCH_PATTERN = Pattern.compile(References.REGEX_PREFIX_SCANNER + "(?<scannerMessage>.+))");
    private static final Pattern JOINED_PATTERN = Pattern.compile(".+ : (.+) has joined the area(.)");
    private ArrayList<IgnoreData> whisperIgnoreData = new ArrayList<IgnoreData>();
    private boolean chatScannerRunning = false;
    private String[] searchIgnoreTerms;
    private String[] searchTerms;
    private String searchName;

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
                            if ((!App.saveManager.saveFile.enableIncomingTrades && trade.messageType == MessageType.INCOMING_TRADE)
                                    || (!App.saveManager.saveFile.enableOutgoingTrades && trade.messageType == MessageType.OUTGOING_TRADE)) {
                                // Ignore disabled trades
                            } else {
                                FrameManager.messageManager.addMessage(trade);
                            }
                            FrameManager.historyWindow.addTrade(trade, true);
                        }
                    } else if (chatScannerRunning) {
                        TradeOffer trade = getSearchOffer(curLine);
                        if(trade != null) {
                            FrameManager.messageManager.addMessage(trade);
                        }
                        // TODO : Chat Scanner
//                        boolean ignore = false;
//                        String curLower = curLine.toLowerCase();
////                        for (String s : searchIgnoreTerms) {
////                            if (curLower.contains(s)) {
////                                ignore = true;
////                                break;
////                            }
////                        }
//                        if (!ignore) {
//                            for (String searchTerm : searchTerms) {
//                                if (curLower.contains(searchTerm)) {
////                                        public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String searchName, String searchMessage) {
//
////                                    trade.date = date;
////                                    trade = new TradeOffer(matcher.group(2), matcher.group(3), MessageType.CHAT_SCANNER, matcher.group(4), matcher.group(5), this.searchName, matcher.group(6));
//
//                                }
//                            }
//                        }
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


//    App.chatParser.setSearchName(selectedMessage.name);
//    App.chatParser.setSearchTerms(selectedMessage.searchTermsArray);
//     App.chatParser.setSearchIgnoreTerms(selectedMessage.ignoreTermsArray);


    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setSearchTerms(String[] terms) {
        this.searchTerms = terms;
    }

    public void setSearchIgnoreTerms(String[] terms) {
        this.searchIgnoreTerms = terms;
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
        trade.date = matcher.group("date").replaceAll("/", "-");
        trade.time = matcher.group("time");
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
        return Double.parseDouble(text);
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

    public void setChatScannerRunning(boolean state) {
        this.chatScannerRunning = state;
    }
}
