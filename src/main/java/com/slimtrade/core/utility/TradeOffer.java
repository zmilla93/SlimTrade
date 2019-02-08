package main.java.com.slimtrade.core.utility;

import java.io.Serializable;

import main.java.com.slimtrade.enums.MessageType;

public class TradeOffer implements Serializable{

	private static final long serialVersionUID = 1L;
	public final String date;
	public final String time;
	public final MessageType msgType;
	public final String guildName;
	public final String playerName;
	public final String itemName;
	public final Double itemCount;
	public final String priceTypeString;
	public final Double priceCount;
	public final String stashtabName;
	public final int stashtabX;
	public final int stashtabY;
	public final String bonusText;
	public final String sentMessage;
	
	public TradeOffer(String date, MessageType msgType, String playerName, String itemName, double itemCount, String priceTypeString, double priceCount){
		this.date = date;
		this.time = null;
		this.msgType = msgType;
		this.guildName = null;
		this.playerName = playerName;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.priceTypeString = priceTypeString;
		this.priceCount = priceCount;
		this.stashtabName = null;
		this.stashtabX = 0;
		this.stashtabY = 0;
		this.bonusText = null;
		this.sentMessage = null;
	}
	
	public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String itemName, double itemCount, String priceTypeString, double priceCount, String stashtabName, int stashtabX, int stashtabY, String bonusText, String sentMessage){
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
