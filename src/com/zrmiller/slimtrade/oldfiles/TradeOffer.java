package com.zrmiller.slimtrade.oldfiles;

import com.zrmiller.slimtrade.datatypes.MessageType;

public class TradeOffer {
	

	MessageType msgType;
	String guild;
	String name;
	String item;
	String price;
	float itemQuant;
	float priceQuant;
	String stashtabName;
	int stashtabX;
	int stashtabY;
	
	public TradeOffer(MessageType msgType, String guild, String name, String item, float itemQuant, String price, float priceQuant, String stashtabName, int stashtabX, int stashtabY){
		this.guild = fixGuild(guild);
		this.msgType = msgType;
		this.name = name;
		this.item = fixCurrency(item);
		this.itemQuant = itemQuant;
		this.price = fixCurrency(price);
		this.priceQuant = priceQuant;
		this.stashtabName = stashtabName;
		this.stashtabX = stashtabX;
		this.stashtabY = stashtabY;
	}
	
	private String fixGuild(String s){
		if(s==null){
			return "";
		}
		else{
			return s;
		}
	}
	
	private String fixCurrency(String s){
		String currency[] = {"chaos", "alch", "ex"};
		for(String c : currency){
			if(s.toLowerCase().matches(c + ".*")){
				return c;
			}
		}
		return s;
	}
	
	public void destroy(){
		this.destroy();
	}
}
