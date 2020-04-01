package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.debug.Debugger;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.ParserRegex;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.enums.MatchType;
import com.slimtrade.gui.options.ignore.IgnoreData;

import javax.swing.*;
import java.awt.event.ActionEvent;
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
                ParserRegex r = validateTradeMessage(curLine);
                if (curLine.contains("@") && r != null) {
                    TradeOffer trade = getTradeOffer(curLine, r);
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
                    ParserRegex r = validateTradeMessage(curLine);
                    if (curLine.contains("@") && r != null) {
                        App.debugger.log(curLine);
                        TradeOffer trade = getTradeOffer(curLine, r);
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

    public ParserRegex validateTradeMessage(String message) {
        for (ParserRegex r : ParserRegex.values()) {
            if (message.contains(r.getContains())) {
                return r;
            }
        }
        return null;
    }

    private TradeOffer getTradeOffer(String text, ParserRegex p) {
        if (!text.contains(p.getContains())) {
            return null;
        }
        System.out.println("TEST:::" + text.contains(p.getContains()) + " | " + text);
//        if (text.contains("listed for") || text.contains("for my")) {
//            tradeMsgMatcher = Pattern.compile(tradeMatchString).matcher(text);
//        } else {
//            tradeMsgMatcher = Pattern.compile(unpricedTradeMatchString).matcher(text);
//        }
        Matcher matcher = p.getPattern().matcher(text);
        TradeOffer trade;
        if (matcher.matches()) {
            System.out.println("PARSING : " + text);
            for (int i = 0; i < matcher.groupCount(); i++) {
                System.out.println("G" + i + "|" + matcher.group(i));
            }
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
            double d1 = 0.0;
            double d2 = 0.0;
            // Item Count
            if (matcher.group(11) != null) {
                d1 = Double.parseDouble(matcher.group(11));
            }
            // Price Count
            if (matcher.group(14) != null) {
                d2 = Double.parseDouble(matcher.group(14));
            }
            int i1 = 0;
            int i2 = 0;
            // Stashtab X
            if (matcher.group(22) != null) {
                i1 = Integer.parseInt(matcher.group(22));
            }
            // Stashtab Y
            if (matcher.group(24) != null) {
                i2 = Integer.parseInt(matcher.group(24));
            }
            trade = new TradeOffer(matcher.group(2).replaceAll("/", "-"), matcher.group(3), getMsgType(matcher.group(4)), matcher.group(5), matcher.group(6), matcher.group(12), d1, matcher.group(15), d2, matcher.group(20), i1, i2, matcher.group(25), matcher.group(7));
            if (trade.messageType == MessageType.INCOMING_TRADE && this.whisperIgnoreData != null) {
                for (IgnoreData d : this.whisperIgnoreData) {
                    if (d.getMatchType() == MatchType.CONTAINS) {
                        if (trade.itemName.toLowerCase().contains(d.getItemName().toLowerCase())) {
                            return null;
                        }
                    } else if (d.getMatchType() == MatchType.EXACT) {
                        if (trade.itemName.toLowerCase().equals(d.getItemName().toLowerCase())) {
                            return null;
                        }
                    }
                }
            }
            return trade;
        } else {
            return null;
        }
    }

    private MessageType getMsgType(String s) {
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
