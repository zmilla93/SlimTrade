package com.zrmiler.slimtrade;

import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class TradeOffer {

	MessageType msgType;
	String playerName;
	String itemName;
	Double itemCount;
	CurrencyType priceType;
	Double currencyCount;
	
	public TradeOffer(MessageType msgType, String playerName, String itemName, Double itemCount, CurrencyType priceType, Double currencyCount){
		this.msgType = msgType;
		this.playerName = playerName;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.priceType = priceType;
		this.currencyCount = currencyCount;
	}
	
	
}
