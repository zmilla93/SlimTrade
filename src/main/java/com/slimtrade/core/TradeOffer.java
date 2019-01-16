package main.java.com.slimtrade.core;

import java.io.Serializable;

import main.java.com.slimtrade.datatypes.MessageType;

public class TradeOffer implements Serializable{

	private static final long serialVersionUID = 1L;
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
	public String sentMessage = "";
	
	public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String itemName, Double itemCount, String priceTypeString, Double priceCount, String stashtabName, int stashtabX, int stashtabY, String sentMessage){
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
