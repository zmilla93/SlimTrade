package com.zrmiller.slimtrade;

import java.io.Serializable;

import com.zrmiller.slimtrade.datatypes.CurrencyType;
import com.zrmiller.slimtrade.datatypes.MessageType;

public class TradeOffer implements Serializable{

	private static final long serialVersionUID = 1L;
	public MessageType msgType;
	public String guildName;
	public String playerName;
	public String itemName;
	public Double itemCount;
	public String priceTypeString;
	public CurrencyType priceType;
	public Double priceCount;
	public String stashtabName;
	public int stashtabX;
	public int stashtabY;
	public String sentMessage = "";
	
	public TradeOffer(MessageType msgType, String guildName, String playerName, String itemName, Double itemCount, CurrencyType priceType, Double priceCount, String stashtabName, int stashtabX, int stashtabY, String sentMessage){
		this.msgType = msgType;
		this.guildName = guildName;
		this.playerName = playerName;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.priceType = priceType;
		this.priceCount = priceCount;
		this.stashtabName = stashtabName;
		this.stashtabX = stashtabX;
		this.stashtabY = stashtabY;
		this.sentMessage = sentMessage;
	}
	
	public TradeOffer(MessageType msgType, String guildName, String playerName, String itemName, Double itemCount, String priceTypeString, Double priceCount, String stashtabName, int stashtabX, int stashtabY, String sentMessage){
		this.msgType = msgType;
		this.guildName = guildName;
		this.playerName = playerName;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.priceTypeString = priceTypeString;
		this.priceCount = priceCount;
		this.stashtabName = stashtabName;
		this.stashtabX = stashtabX;
		this.stashtabY = stashtabY;
		this.sentMessage = sentMessage;
	}
	
	
}
