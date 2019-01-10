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
	public int messageQueue = 0;
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
	private final static String tradeMessageMatchString = ".+@(To|From) (<.+> )?([A-z_]+): ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+) (.+) in (\\w+)( [(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?[.]?([.]*))";
	private final static String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";
	
	private String clientLogPath;
	
	public ChatParser(){
		
	}
	
	//TODO : Move path to options
	public void init(){
//		File dir = new File("D:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
//		File f = new File("D:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
//		
//		FrameManager.debug.log("Directory Exists : " + dir.exists());
//		FrameManager.debug.log("Log File Exists : " + f.exists());
		if(FrameManager.fileManager.validClientPath){
			clientLogPath = FrameManager.fileManager.clientPath;
		}else{
			FrameManager.debug.log("[ERROR] No valid client file path found");
			return;
		}
		try {
//			fileReader = new FileReader("C:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
			fileReader = new FileReader(clientLogPath);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			FrameManager.debug.log("CHAT PARSER FAILED TO LAUNCH");
			e1.printStackTrace();
		}
		//TODO : Init history
		try {
			while((curLine = bufferedReader.readLine()) != null){
				totalLineCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateTimer.start();
	}

	public boolean update(){
		boolean update = false;
		try {
			fileReader = new FileReader(clientLogPath);
			bufferedReader = new BufferedReader(fileReader);
			curLineCount=0;
			while((curLine = bufferedReader.readLine()) != null){
				curLineCount++;
				if (curLineCount>totalLineCount){
					FrameManager.debug.log(curLine);
					totalLineCount++;
					Matcher tradeMsgMatcher = Pattern.compile(tradeMessageMatchString).matcher(curLine);
					Matcher joinAreaMatcher = Pattern.compile(playerJoinedAreaString).matcher(curLine);
					if(tradeMsgMatcher.matches()){
						update = true;
						messageQueue++;
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
						TradeOffer trade = new TradeOffer(getMsgType(tradeMsgMatcher.group(1)), tradeMsgMatcher.group(2),
								tradeMsgMatcher.group(3), tradeMsgMatcher.group(8), f1, TradeUtility.fixCurrencyString(tradeMsgMatcher.group(11)), f2, 
								tradeMsgMatcher.group(15), i1, i2, tradeMsgMatcher.group(4));
						FrameManager.messageManager.addMessage(trade);
						FrameManager.forceAllToTop();
					}else if(joinAreaMatcher.matches()){
					}
				}
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		return update;
	}
	
	public MessageType getMsgType(String s){
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
	
//	public int getFixedIndex(int unsafeIndex){
//		int r = this.tradeHistoryIndex+unsafeIndex;
//		if (r<0){r=r+this.MAX_TRADE_HISTORY;}
//		if (r>this.MAX_TRADE_HISTORY){r=0;}
//		return r;
//	}
	
	public void refresh(){
		this.totalLineCount=0;
	}
}
