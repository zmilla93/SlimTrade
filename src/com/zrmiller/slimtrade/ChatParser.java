package com.zrmiller.slimtrade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zrmiller.slimtrade.datatypes.MessageType;

public class ChatParser {
	FileReader fr;
	BufferedReader br;
	int curLineCount = 0;
	int totalLineCount = 0;
	String curLine;
	private Pattern msgPattern;
	private Matcher msg;
	public int tradeHistoryIndex = 0;
	final public int MAX_TRADE_HISTORY = 50;
	public TradeOffer[] tradeHistory = new TradeOffer[MAX_TRADE_HISTORY];
	
	
	private final static String tradeMessageMatchString = ".+@(To|From) (<.+> )?(.+): (Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+) (\\w+) in (\\w+)( [(]stash tab \")?((.+)\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?";

	public ChatParser(){
		this.msgPattern = Pattern.compile(tradeMessageMatchString);
		//String t = "ASDFASDASDF@To ShootyMcArrowShooter: Hi, I would like to buy your Queen of the Forest Destiny Leather listed for 40 chaos in Delve (stash tab \"b/o 5 234SADFDS_$#%_D\"; position: left 1, top 10)";
		//System.out.println("T: " + Pattern.matches(msg, t));
	}
	
	public void init() throws IOException{
		fr = new FileReader("C:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
		br = new BufferedReader(fr);
		br.mark(9999999);
		while((curLine = br.readLine()) != null){
			
		}
	}

	public int update() throws IOException{
		fr = new FileReader("C:/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
		br = new BufferedReader(fr);
		br.mark(9999999);
		curLineCount=0;
		int update = 0;
		while((curLine = br.readLine()) != null){
			curLineCount++;
			if (curLineCount>totalLineCount){
				//System.out.println("PARSING : " + curLine);
				totalLineCount++;
				msgPattern = Pattern.compile(tradeMessageMatchString);
				msg = msgPattern.matcher(curLine);
				if(msg.matches()){
					//System.out.println("MATCH");
					//System.out.println("PARSING ::: " + msg.group(1));
					update++;
					//TODO: could move int fixing to TradeOffer class
					float f1 = 0; 
					float f2 = 0;
					if(msg.group(6)!=null){
						f1 = Float.parseFloat(msg.group(6));
					}
					if(msg.group(9)!=null){
						f2 = Float.parseFloat(msg.group(9));
					}
					int i1 = 0;
					int i2 = 0;
					if(msg.group(14)!=null){
						i1 = Integer.parseInt(msg.group(16));
					}
					if(msg.group(16)!=null){
						i2 = Integer.parseInt(msg.group(18));
					}
					tradeHistory[tradeHistoryIndex] = new TradeOffer(getMsgType(msg.group(1)), msg.group(2),  msg.group(3), msg.group(7), f1, msg.group(10), f2, msg.group(14), i1, i2);
					if (tradeHistoryIndex<MAX_TRADE_HISTORY-1) tradeHistoryIndex++; else tradeHistoryIndex=0;
				}else{
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
