package main.java.com.slimtrade.core.utility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Timer;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.audio.AudioManager;
import main.java.com.slimtrade.core.audio.SoundComponent;
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.FrameManager;

public class ChatParser {
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	int curLineCount = 0;
	int totalLineCount = 0;
	String curLine;
	// TODO: should these be trade or message variables? chat scanner?
	public int tradeHistoryIndex = 0;
	final public int MAX_TRADE_HISTORY = 50;
	public TradeOffer[] tradeHistory = new TradeOffer[MAX_TRADE_HISTORY];
	// public int messageQueue = 0;
	public String[] playerJoinedArea = new String[20];
	public int playerJoinedQueue = 0;
	private ActionListener updateAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Debugger.benchmarkStart();
			// update();
			procUpdate();
			// System.out.println(Debugger.benchmark());
		}
	};
	private Timer updateTimer = new Timer(500, updateAction);

	// REGEX
	private final static String tradeMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2}))?.*@(To|From) (<.+> )?(\\S+): ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
	// TODO : Remove optional flag for global chat - guild returns null until
	// then
	private final static String searchMessageMatchString = "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$]?(<.+> )?(\\S+): (.+)";
	private final static String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";

	private String[] searchTerms;
	private String[] ignoreTerms;
	private boolean chatScannerRunning = false;
	private String searchResponseLeft;
	private String searchResponseRight;

	private String clientLogPath;

	public ChatParser() {

	}

	// TODO : Move path to options
	public void init() {
		Main.debug.log("Launching chat parser...");
		int msgCount = 0;
		updateTimer.stop();
		if (Main.fileManager.validClientPath) {
			clientLogPath = Main.fileManager.clientPath;
		} else {
			Main.debug.log("[ERROR] No valid client file path found.");
			return;
		}
		try {
			// fileReader = new FileReader("C:/Program Files
			// (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
			fileReader = new FileReader(clientLogPath);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e1) {
			Main.debug.log("[ERROR] Chat parser failed to launch.");
			e1.printStackTrace();
		}
		// TODO : Init history
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
			Main.debug.log(msgCount + " whisper messages found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateTimer.start();
		Main.debug.log(totalLineCount + " total lines found.");
		Main.debug.log("Chat parser sucessfully launched.");
	}

	private void procUpdate() {
		try {
			fileReader = new FileReader(clientLogPath);
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void update() {
		System.out.println("Updating chat parser");
		long start = System.currentTimeMillis();
		// Debugger.benchmarkStart();
		try {
			fileReader = new FileReader(clientLogPath);
			bufferedReader = new BufferedReader(fileReader);
			curLineCount = 0;
			while ((curLine = bufferedReader.readLine()) != null) {
				curLineCount++;
				if (curLineCount > totalLineCount) {
					totalLineCount++;
					if (curLine.contains("@")) {
						System.out.println(curLine);
						Main.debug.log(curLine);
						TradeOffer trade = getTradeOffer(curLine);
						if (trade != null && !FrameManager.messageManager.isDuplicateTrade(trade)) {
							FrameManager.messageManager.addMessage(trade);
							FrameManager.historyWindow.addTrade(trade, true);
							switch (trade.messageType) {
							case CHAT_SCANNER:
								break;
							case INCOMING_TRADE:
								AudioManager.play(SoundComponent.INCOMING_MESSAGE);
								break;
							case OUTGOING_TRADE:
								AudioManager.play(SoundComponent.OUTGOING_MESSAGE);
								break;
							case UNKNOWN:
								break;
							default:
								break;
							}
						}
					} else if (chatScannerRunning) {
						TradeOffer trade = getSearchOffer(curLine);
						System.out.println(trade);
						if (trade != null) {
							AudioManager.play(SoundComponent.SCANNER_MESSAGE);
							FrameManager.messageManager.addMessage(trade);
						}
						// for (String s : searchTerms) {
						// if (curLine.toLowerCase().contains(s)) {
						// // FrameManager.messageManager.addMessage(trade);
						// //Add null set/check
						// TradeOffer trade = getSearchOffer(curLine);
						// if(trade != null){
						// FrameManager.messageManager.addMessage(trade);
						// }
						// return;
						// }
						// }
					} else {
						Matcher joinAreaMatcher = Pattern.compile(playerJoinedAreaString).matcher(curLine);
					}
				}
			}
			bufferedReader.close();
			fileReader.close();
		} catch (NumberFormatException | IOException e) {
			updateTimer.stop();
			Main.debug.log("[ERROR] Exception encountered while attempting to update parser.");
			Main.debug.log("Parser disabled.");
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

			// System.out.println("TRADE OFFER : " + trade.guildName +
			// trade.playerName);
			return trade;
		} else {
			return null;
		}
	}

	private TradeOffer getSearchOffer(String text) {
		Matcher matcher = Pattern.compile(searchMessageMatchString).matcher(text);
		System.out.println("Searching ::: " + text);
		TradeOffer trade = null;
		if (matcher.matches()) {
			// DEBUG
			System.out.println("\tMATCH FOUND");
			for (int i = 0; i <= matcher.groupCount(); i++) {
				System.out.println("\tGROUP #" + i + " : " + matcher.group(i));
			}
			System.out.println("");
			// DEBUG END
			trade = new TradeOffer(matcher.group(2), matcher.group(3), MessageType.CHAT_SCANNER, matcher.group(4), matcher.group(5), null, matcher.group(6), this.searchResponseLeft, this.searchResponseRight);

			if (this.ignoreTerms != null) {
				for (String s : this.ignoreTerms) {
					System.out.println("IGNORE : " + s);
					if (trade.searchMessage.contains(s)) {
						return null;
					}
				}
			}
			boolean found = false;
			System.out.println("MESSAGE ::: " + trade.searchMessage);
			for (String s : this.searchTerms) {
				System.out.println("\t" + s);
				if (trade.searchMessage.contains(s)) {
					found = true;
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

	public void setChatScannerRunning(boolean state) {
		chatScannerRunning = state;
	}

	public void setSearchTerms(String[] searchTerms) {
		this.searchTerms = searchTerms;
	}

	public void setIgnoreTerms(String[] ignoreTerms) {
		this.ignoreTerms = ignoreTerms;
	}

	public void setResponseText(String lmb, String rmb) {
		this.searchResponseLeft = lmb;
		this.searchResponseRight = rmb;
	}

}
