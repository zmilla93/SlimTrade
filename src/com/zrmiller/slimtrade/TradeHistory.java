package com.zrmiller.slimtrade;

import java.util.ArrayList;

public class TradeHistory {

	
//	TradeOffer[] tradeIncoming = new TradeOffer[50];
//	TradeOffer[] tradeOutgoing = new TradeOffer[50];
	ArrayList<TradeOffer> tradesIncoming = new ArrayList<TradeOffer>();
	ArrayList<TradeOffer> tradesOutgoing = new ArrayList<TradeOffer>();
	
	public TradeHistory(){
		
	}
	
	public void addTrade(TradeOffer trade){
		switch(trade.msgType){
		case INCOMING_TRADE:
			tradesIncoming.add(trade);
			break;
		case OUTGOING_TRADE:
			tradesOutgoing.add(trade);
			break;
		default:
			break;
		}
	}
	
}
