package com.zrmiller.slimtrade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class ChatParser {
	FileReader fileReader;
	BufferedReader bufferedReader;
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
	
	//REGEX
	private final static String tradeMessageMatchString = ".+@(To|From) (<.+> )?([A-z_]+): (Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+) (\\w+) in (\\w+)( [(]stash tab \")?((.+)\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?";
	private final static String playerJoinedAreaString = ".+ : (.+) has joined the area(.)";
	
	public ChatParser(){
		
	}
	
	//TODO : Move path to options
	public void init() throws IOException{
//		fileReader = new FileReader("C:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
		//TODO : Init history
		while((curLine = bufferedReader.readLine()) != null){
			
		}
	}

	public boolean update() throws IOException{
		fileReader = new FileReader("C:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
		bufferedReader = new BufferedReader(fileReader);
		curLineCount=0;
		boolean update = false;
		while((curLine = bufferedReader.readLine()) != null){
			curLineCount++;
			if (curLineCount>totalLineCount){
				totalLineCount++;
				Matcher tradeMsgMatcher = Pattern.compile(tradeMessageMatchString).matcher(curLine);
				Matcher joinAreaMatcher = Pattern.compile(playerJoinedAreaString).matcher(curLine);
				if(tradeMsgMatcher.matches()){
					update = true;
					messageQueue++;
					//TODO: could move int fixing to TradeOffer class
					Double f1 = 0.0; 
					Double f2 = 0.0;
					if(tradeMsgMatcher.group(6)!=null){
						f1 = Double.parseDouble(tradeMsgMatcher.group(6));
					}
					if(tradeMsgMatcher.group(9)!=null){
						f2 = Double.parseDouble(tradeMsgMatcher.group(9));
					}
					int i1 = 0;
					int i2 = 0;
					if(tradeMsgMatcher.group(14)!=null){
						i1 = Integer.parseInt(tradeMsgMatcher.group(16));
					}
					if(tradeMsgMatcher.group(16)!=null){
						i2 = Integer.parseInt(tradeMsgMatcher.group(18));
					}
					TradeOffer trade = new TradeOffer(getMsgType(tradeMsgMatcher.group(1)), tradeMsgMatcher.group(2),
							tradeMsgMatcher.group(3), tradeMsgMatcher.group(7), f1, tradeMsgMatcher.group(10), f2, 
							tradeMsgMatcher.group(14), i1, i2);
//					System.out.println("===TRADE OFFER===");
//					System.out.println(tradeMsgMatcher.group(1));
//					System.out.println(tradeMsgMatcher.group(2));
//					System.out.println(tradeMsgMatcher.group(3));
//					System.out.println(tradeMsgMatcher.group(7));
//					System.out.println(f1);
//					System.out.println(tradeMsgMatcher.group(10));
//					System.out.println(f2);
//					System.out.println(tradeMsgMatcher.group(14));
//					System.out.println(i1);
//					System.out.println(i2);
//					System.out.println(curLine);					
				}
				else if(joinAreaMatcher.matches()){
					//update = true;
					//playerJoinedArea[playerJoinedQueue] = joinAreaMatcher.group(1);
					//playerJoinedQueue++;
					//System.out.println("PLAYER JOINED : " + joinAreaMatcher.group(1));
				}
			}
		}
		return update;
	}
	
	public MessageType getMsgType(String s){
		MessageType t = MessageType.UNKNOWN;
		switch(s.toLowerCase()){
		case "to":
			t = MessageType.OUTGOING_TRADE;
			break;
		case "from":
			t = MessageType.INCOMING_TRADE;
			break;
		}
		return t;
	}
	
	public int getFixedIndex(int unsafeIndex){
		int r = this.tradeHistoryIndex+unsafeIndex;
		if (r<0){r=r+this.MAX_TRADE_HISTORY;}
		if (r>this.MAX_TRADE_HISTORY){r=0;}
		return r;
	}
	
	public void refresh(){
		this.totalLineCount=0;
	}
}
