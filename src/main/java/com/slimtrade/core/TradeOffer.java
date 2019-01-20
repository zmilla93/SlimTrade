package main.java.com.slimtrade.core;

import java.io.Serializable;

import main.java.com.slimtrade.datatypes.MessageType;

public class TradeOffer implements Serializable{

	private static final long serialVersionUID = 1L;
	public String date;
	public String time;
	public MessageType msgType;
	public String guildName;
	public String playerName;
	public String itemName;
	public Double itemCount;
	public String priceTypeString;
	public Double priceCount;
	public String stashtabName;
	public int stashtabX;
	public int stashtabY;
	public String bonusText;
	public String sentMessage;
	
	public TradeOffer(String date, MessageType msgType, String playerName, String itemName, String priceTypeString){
		this.date = date;
		this.time = null;
		this.msgType = msgType;
		this.guildName = null;
		this.playerName = playerName;
		this.itemName = itemName;
		this.itemCount = 0.0;
		this.priceTypeString = priceTypeString;
		this.priceCount = 0.0;
		this.stashtabName = null;
		this.stashtabX = 0;
		this.stashtabY = 0;
		this.bonusText = null;
		this.sentMessage = null;
	}
	
	public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String itemName, Double itemCount, String priceTypeString, Double priceCount, String stashtabName, int stashtabX, int stashtabY, String bonusText, String sentMessage){
		this.date = date;
		this.time = time;
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
		this.bonusText = bonusText;
		this.sentMessage = sentMessage;
	}
	
	
}
