package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.audio.AudioManager;
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
//	private FileReader fileReader;
	private InputStreamReader reader;
	private BufferedReader bufferedReader;
	int curLineCount = 0;
	int totalLineCount;
	String curLine;
	// TODO: should these be trade or message variables? chat scanner?
//	public int tradeHistoryIndex = 0;
	final public int MAX_TRADE_HISTORY = 50;
	public TradeOffer[] tradeHistory = new TradeOffer[MAX_TRADE_HISTORY];
	// public int messageQueue = 0;
//	public String[] playerJoinedArea = new String[20];
//	public int playerJoinedQueue = 0;
	private ActionListener updateAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			procUpdate();
		}
	};
	private Timer updateTimer = new Timer(500, updateAction);

	// REGEX
	private final static String tradeMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2}))?.*@(To|From) (<.+> )?(\\S+): ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
	// TODO : Remove optional flag for global chat - guild returns null until
	private final static String searchMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$](<.+> )?(\\S+): (.+)";
	// Allows for local chat
//	private final static String searchMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$]?(<.+> )?(\\S+): (.+)";
	private final static String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";

	private String[] searchTerms;
	private String[] searchIgnoreTerms;
	private ArrayList<IgnoreData> whisperIgnoreData = new ArrayList<IgnoreData>();
	private boolean chatScannerRunning = false;
	private String searchName;
//	private String searchResponseLeft;
//	private String searchResponseRight;

	private String clientPath;

	public ChatParser() {

	}

	// TODO : Move path to options
	public void init() {
		App.debugger.log("Launching chat parser...");
		int msgCount = 0;
		updateTimer.stop();
		if (App.saveManager.saveFile.validClientPath) {
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
//			e.printStackTrace();
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
		App.debugger.log("Chat parser sucessfully launched.");
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
							FrameManager.messageManager.addMessage(trade);
							FrameManager.historyWindow.addTrade(trade, true);
						}
					} else if (chatScannerRunning) {
						TradeOffer trade = getSearchOffer(curLine);
						System.out.println(trade);
						if (trade != null) {
							AudioManager.play(App.saveManager.saveFile.scannerMessageSound);
							FrameManager.messageManager.addMessage(trade);
						}
					} else {
						Matcher joinAreaMatcher = Pattern.compile(playerJoinedAreaString).matcher(curLine);
						if(joinAreaMatcher.matches()){
                            for(int i = 0; i<joinAreaMatcher.groupCount(); i++){
                                if(joinAreaMatcher.groupCount()>1){
                                    FrameManager.messageManager.setPlayerJoinedArea(joinAreaMatcher.group(1));
                                }
                            }
                        }
						//TODO : Player joined indicator
//                        System.out.println("PLAYER JOINED!");
//
					}
				}
			}
			bufferedReader.close();
			reader.close();
		} catch (NumberFormatException | IOException e) {
			updateTimer.stop();
			App.debugger.log("[ERROR] Exception encountered while attempting to update parser.");
			App.debugger.log("Parser disabled.");
			e.printStackTrace();
		}
		// long end = System.currentTimeMillis();
		// System.out.println("PARSER UPDATE TIME : " + (end-start));
		// System.out.println(end-start-Debugger.benchmark());
	}

	private TradeOffer getTradeOffer(String text) {
		Matcher tradeMsgMatcher = Pattern.compile(tradeMessageMatchString).matcher(text);
		TradeOffer trade = null;
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
			if (tradeMsgMatcher.group(10) != null) {
				d1 = Double.parseDouble(tradeMsgMatcher.group(10));
			}
			// Price Count
			if (tradeMsgMatcher.group(13) != null) {
				d2 = Double.parseDouble(tradeMsgMatcher.group(13));
			}
			int i1 = 0;
			int i2 = 0;
			// Stashtab X
			if (tradeMsgMatcher.group(21) != null) {
				i1 = Integer.parseInt(tradeMsgMatcher.group(21));
			}
			// Stashtab Y
			if (tradeMsgMatcher.group(23) != null) {
				i2 = Integer.parseInt(tradeMsgMatcher.group(23));
			}
			trade = new TradeOffer(tradeMsgMatcher.group(2).replaceAll("/", "-"), tradeMsgMatcher.group(3), getMsgType(tradeMsgMatcher.group(4)), tradeMsgMatcher.group(5), tradeMsgMatcher.group(6), tradeMsgMatcher.group(11), d1, tradeMsgMatcher.group(14), d2, tradeMsgMatcher.group(19), i1, i2, tradeMsgMatcher.group(24), tradeMsgMatcher.group(7));
//			if(trade.itemName)
			// System.out.println("TRADE OFFER : " + trade.guildName +
			// trade.playerName);
			if(trade.messageType == MessageType.INCOMING_TRADE && this.whisperIgnoreData!=null){
				for(IgnoreData d : this.whisperIgnoreData){
					if(d.getMatchType() == MatchType.CONTAINS){
						if(trade.itemName.toLowerCase().contains(d.getItemName().toLowerCase())){
							return null;
						}
					}else if(d.getMatchType() == MatchType.EXACT){
						if(trade.itemName.toLowerCase().equals(d.getItemName().toLowerCase())){
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
			//TODO (OPT) : This loops the same thing twice, and calls toLowerCase more than needed
			if (this.searchIgnoreTerms != null) {
				for (String s : this.searchIgnoreTerms) {
					if (trade.searchMessage.toLowerCase().contains(s.toLowerCase())) {
						return null;
					}
				}
			}
			boolean found = false;
			for (String s : this.searchTerms) {
				if(!s.equals("")){
					if (trade.searchMessage.toLowerCase().contains(s.toLowerCase())) {
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
	
	public void setClientPath(String path){
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
