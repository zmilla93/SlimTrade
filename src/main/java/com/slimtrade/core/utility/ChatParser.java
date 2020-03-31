package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.debug.Debugger;
import com.slimtrade.enums.MessageType;
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
    private InputStreamReader reader;
    private BufferedReader bufferedReader;
    private int curLineCount = 0;
    private int totalLineCount;
    private String curLine;
//    final public int MAX_TRADE_HISTORY = 25;
//    public TradeOffer[] tradeHistory = new TradeOffer[MAX_TRADE_HISTORY];
    private ActionListener updateAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            procUpdate();
        }
    };
    private Timer updateTimer = new Timer(500, updateAction);

    // REGEX
//    private static final String tradeMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2}))?.*@(To|From) (<.+> )?(\\S+): ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
    private static final String tradeMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2}))?.*@(To|From) (<.+> )?(.+): (((Hi, )?(I would|I'd) like to buy your|wtb) ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\\?\\\")?((.+)\\\\?\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
    private static final String unpricedTradeMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2}))?.*@(To|From) (<.+> )?(.+): (((Hi, )?(I would|I'd) like to buy your|wtb) ([\\d.]+)? ?(.+) (NULL)?(NULL)?(NULL)?in (\\w+( \\w+)?) ?([(]stash tab \\\\?\\\")?((.+)\\\\?\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
    private static String searchMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$](<.+> )?(\\S+): (.+)";
    // Allows for local chat
//	private final static String searchMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$]?(<.+> )?(\\S+): (.+)";
    private static final String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";

    private String[] searchTerms;
    private String[] searchIgnoreTerms;
    private ArrayList<IgnoreData> whisperIgnoreData = new ArrayList<IgnoreData>();
    private boolean chatScannerRunning = false;
    private String searchName;
    private String clientPath;

    // TODO : Move path to options
    public void init() {
        Debugger.benchmarkStart();
        System.out.println("Launching chat parser...");
        FrameManager.historyWindow.clearHistory();
        if (App.debugMode) {
            searchMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$]?(<.+> )?(\\S+): (.+)";
        }
        int msgCount = 0;
        updateTimer.stop();
        File file = new File(App.saveManager.saveFile.clientPath);
        if (file.exists() && file.isFile()) {
            clientPath = App.saveManager.saveFile.clientPath;
        } else {
            App.debugger.log("[ERROR] No valid client file path found.");
            return;
        }
        try {
            // fileReader = new FileReader("C:/Program Files
            // (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
//			fileReader = new FileReader(clientPath);
            reader = new InputStreamReader(new FileInputStream(clientPath), StandardCharsets.UTF_8);
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
				if (curLine.contains("@")) {
					TradeOffer trade = getTradeOffer(curLine);
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
            reader = new InputStreamReader(new FileInputStream(clientPath), StandardCharsets.UTF_8);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {
        long start = System.currentTimeMillis();
        // Debugger.benchmarkStart();
        try {
            reader = new InputStreamReader(new FileInputStream(clientPath), StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(reader);
            curLineCount = 0;
            while ((curLine = bufferedReader.readLine()) != null) {
                curLineCount++;
                if (curLineCount > totalLineCount) {
                    totalLineCount++;
                    if (curLine.contains("@")) {
                        App.debugger.log(curLine);
                        TradeOffer trade = getTradeOffer(curLine);
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
                        TradeOffer trade = getSearchOffer(curLine);
                        if (trade != null) {
                            FrameManager.messageManager.addMessage(trade);
                        }
                    } else {
                        Matcher joinAreaMatcher = Pattern.compile(playerJoinedAreaString).matcher(curLine);
                        if (joinAreaMatcher.matches()) {
                            for (int i = 0; i < joinAreaMatcher.groupCount(); i++) {
                                if (joinAreaMatcher.groupCount() > 1) {
                                    FrameManager.messageManager.setPlayerJoinedArea(joinAreaMatcher.group(1));
                                    AudioManager.play(App.saveManager.saveFile.playerJoinedSound);
                                }
                            }
                        }
                    }
                }
            }
            bufferedReader.close();
            reader.close();
        } catch (NumberFormatException | IOException e) {
//			updateTimer.stop();
            App.debugger.log("[Chat Parser] Exception encountered while attempting to update parser.");
//			App.debugger.log("Parser disabled.");
//			e.printStackTrace();
        }
        // long end = System.currentTimeMillis();
        // System.out.println("PARSER UPDATE TIME : " + (end-start));
        // System.out.println(end-start-Debugger.benchmark());
    }

    private TradeOffer getTradeOffer(String text) {
        Matcher tradeMsgMatcher;
        if(text.contains("listed for") || text.contains("for my")) {
            tradeMsgMatcher = Pattern.compile(tradeMatchString).matcher(text);
        } else {
            tradeMsgMatcher = Pattern.compile(unpricedTradeMatchString).matcher(text);
        }
        TradeOffer trade;
        if (tradeMsgMatcher.matches()) {
            // DEBUG
            // System.out.println("NEW TRADE OFFER");
            // for (int i = 0; i < 24; i++) {
            // System.out.println("GROUP #" + i + " : " +
            // tradeMsgMatcher.group(i));
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
            if (tradeMsgMatcher.group(11) != null) {
                d1 = Double.parseDouble(tradeMsgMatcher.group(11));
            }
            // Price Count
            if (tradeMsgMatcher.group(14) != null) {
                d2 = Double.parseDouble(tradeMsgMatcher.group(14));
            }
            int i1 = 0;
            int i2 = 0;
            // Stashtab X
            if (tradeMsgMatcher.group(22) != null) {
                i1 = Integer.parseInt(tradeMsgMatcher.group(22));
            }
            // Stashtab Y
            if (tradeMsgMatcher.group(24) != null) {
                i2 = Integer.parseInt(tradeMsgMatcher.group(24));
            }
            trade = new TradeOffer(tradeMsgMatcher.group(2).replaceAll("/", "-"), tradeMsgMatcher.group(3), getMsgType(tradeMsgMatcher.group(4)), tradeMsgMatcher.group(5), tradeMsgMatcher.group(6), tradeMsgMatcher.group(12), d1, tradeMsgMatcher.group(15), d2, tradeMsgMatcher.group(20), i1, i2, tradeMsgMatcher.group(25), tradeMsgMatcher.group(7));
//			if(trade.itemName)
            // System.out.println("TRADE OFFER : " + trade.guildName +
            // trade.playerName);
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

    private TradeOffer getSearchOffer(String text) {
        Matcher matcher = Pattern.compile(searchMessageMatchString).matcher(text);
//		System.out.println("Searching ::: " + text);
        TradeOffer trade = null;
        if (matcher.matches()) {
            // DEBUG
//			System.out.println("\tMATCH FOUND");
//			for (int i = 0; i <= matcher.groupCount(); i++) {
//				System.out.println("\tGROUP #" + i + " : " + matcher.group(i));
//			}
//			System.out.println("");
            // DEBUG END
            trade = new TradeOffer(matcher.group(2), matcher.group(3), MessageType.CHAT_SCANNER, matcher.group(4), matcher.group(5), this.searchName, matcher.group(6));
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

    public void setClientPath(String path) {
        this.clientPath = path;
    }

    public void setChatScannerRunning(boolean state) {
        chatScannerRunning = state;
    }

    public void setSearchName(String name) {
        searchName = name;
    }

    public void setSearchTerms(String[] searchTerms) {
        this.searchTerms = searchTerms;
    }

    public void setSearchIgnoreTerms(String[] ignoreTerms) {
        this.searchIgnoreTerms = ignoreTerms;
    }

    public void setWhisperIgnoreTerms(ArrayList<IgnoreData> ignoreData) {
        this.whisperIgnoreData = ignoreData;
    }

//	public void setResponseText(String lmb, String rmb) {
//		this.searchResponseLeft = lmb;
//		this.searchResponseRight = rmb;
//	}

}
