package com.zrmiller.slimtrade;

import java.io.Serializable;

import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class TradeOffer implements Serializable{

	private static final long serialVersionUID = 1L;
	public MessageType msgType;
	public String playerName;
	public String itemName;
	public Double itemCount;
	public CurrencyType priceType;
	public Double currencyCount;
	public String stashtabName;
	public int stashtabX;
	public int stashtabY;
	
	public TradeOffer(MessageType msgType, String playerName, String itemName, Double itemCount, CurrencyType priceType, Double currencyCount, String stashtabName, int stashtabX, int stashtabY){
		this.msgType = msgType;
		this.playerName = playerName;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.priceType = priceType;
		this.currencyCount = currencyCount;
		this.stashtabName = stashtabName;
		this.stashtabX = stashtabX;
		this.stashtabY = stashtabY;
	}
	
	
}
