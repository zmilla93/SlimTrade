package com.zrmiller.slimtrade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Timer;

import com.zrmiller.slimtrade.datatypes.MessageType;

public class ChatParser {
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	int curLineCount = 0;
	int totalLineCount = 0;
	String curLine;
	//TODO: should these be trade or message variables? chat scanner?
	public int tradeHistoryIndex = 0;
	final public int MAX_TRADE_HISTORY = 50;
	public TradeOffer[] tradeHistory = new TradeOffer[MAX_TRADE_HISTORY];
//	public int messageQueue = 0;
	public String[] playerJoinedArea = new String[20];
	public int playerJoinedQueue = 0;
	private ActionListener updateAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			update();
		}
	};
	private Timer updateTimer = new Timer(500, updateAction);
	
	//REGEX
	private final static String tradeMessageMatchString = ".+@(To|From) (<.+> )?(.+): ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+) (.+) in (\\w+)( [(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?[.]?([.]*))";
	private final static String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";
	
	private String clientLogPath;
	
	public ChatParser(){
		
	}
	
	//TODO : Move path to options
	public void init(){
		Main.debug.log("Launching chat parser...");
		int msgCount = 0;
		updateTimer.stop();
		if(Main.fileManager.validClientPath){
			clientLogPath = Main.fileManager.clientPath;
		}else{
			Main.debug.log("[ERROR] No valid client file path found.");
			return;
		}
		try {
//			fileReader = new FileReader("C:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
			fileReader = new FileReader(clientLogPath);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			Main.debug.log("[ERROR] Chat parser failed to launch.");
			e1.printStackTrace();
		}
		//TODO : Init history
		try {
			while((curLine = bufferedReader.readLine()) != null){
				if (curLine.contains("@")){
					TradeOffer trade = getTradeOffer(curLine);
					if(trade != null){
//						FrameManager.historyWindow.addTrade(trade);
					}
					msgCount++;
				}
				totalLineCount++;
			}
			Main.debug.log(msgCount + " whisper messages found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateTimer.start();
		Main.debug.log(totalLineCount + " total lines found.");
		Main.debug.log("Chat parser sucessfully launched.");
	}

	private void update(){
		try {
			fileReader = new FileReader(clientLogPath);
			bufferedReader = new BufferedReader(fileReader);
			curLineCount=0;
			while((curLine = bufferedReader.readLine()) != null){
				curLineCount++;
				if (curLineCount>totalLineCount){
					totalLineCount++;
					if(curLine.contains("@")){
						Main.debug.log(curLine);
						TradeOffer trade = getTradeOffer(curLine);
						if(trade != null && !FrameManager.messageManager.isDuplicateTrade(trade)){
							FrameManager.messageManager.addMessage(trade);
						}
					}else{
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
	}
	
	private TradeOffer getTradeOffer(String text){
		Matcher tradeMsgMatcher = Pattern.compile(tradeMessageMatchString).matcher(curLine);
		TradeOffer trade = null;
		if(tradeMsgMatcher.matches()){
			//TODO: could move int fixing to TradeOffer class
			Double f1 = 0.0; 
			Double f2 = 0.0;
			if(tradeMsgMatcher.group(7)!=null){
				f1 = Double.parseDouble(tradeMsgMatcher.group(7));
			}
			if(tradeMsgMatcher.group(10)!=null){
				f2 = Double.parseDouble(tradeMsgMatcher.group(10));
			}
			int i1 = 0;
			int i2 = 0;
			if(tradeMsgMatcher.group(15)!=null){
				i1 = Integer.parseInt(tradeMsgMatcher.group(17));
			}
			if(tradeMsgMatcher.group(17)!=null){
				i2 = Integer.parseInt(tradeMsgMatcher.group(19));
			}
			trade = new TradeOffer(getMsgType(tradeMsgMatcher.group(1)), tradeMsgMatcher.group(2),
					tradeMsgMatcher.group(3), tradeMsgMatcher.group(8), f1, TradeUtility.fixCurrencyString(tradeMsgMatcher.group(11)), f2, 
					tradeMsgMatcher.group(15), i1, i2, tradeMsgMatcher.group(4));
			return trade;
		}else{
			return null;
		}
	}
	
	private MessageType getMsgType(String s){
		MessageType type = MessageType.UNKNOWN;
		switch(s.toLowerCase()){
		case "to":
			type = MessageType.OUTGOING_TRADE;
			break;
		case "from":
			type = MessageType.INCOMING_TRADE;
			break;
		}
		return type;
	}
	
}
