package com.zrmiller.slimtrade;

public class TradeOffer {
	

	MessageType msgType;
	String name;
	String item;
	String price;
	float itemQuant;
	float priceQuant;
	String stashtabName;
	int stashtabX;
	int stashtabY;
	
	public TradeOffer(MessageType msgType, String name, String item, float itemQuant, String price, float priceQuant, String stashtabName, int stashtabX, int stashtabY){
		this.msgType = msgType;
		this.name = name;
		this.item = item;
		this.itemQuant = itemQuant;
		this.price = price;
		this.priceQuant = priceQuant;
		this.stashtabName = stashtabName;
		this.stashtabX = stashtabX;
		this.stashtabY = stashtabY;
	}
	
	public void destroy(){
		this.destroy();
	}
}
