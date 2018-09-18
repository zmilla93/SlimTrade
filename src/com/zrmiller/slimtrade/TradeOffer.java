package com.zrmiller.slimtrade;

public class TradeOffer {
	

	String offerType;
	String playerName;
	String item;
	String price;
	float itemQuant;
	float priceQuant;
	
	public TradeOffer(String offerType, String playerName, String item, float itemQuant, String price, float priceQuant){
		this.offerType = offerType;
		this.playerName = playerName;
		this.item = item;
		this.itemQuant = itemQuant;
		this.price = price;
		this.priceQuant = priceQuant;
	}
	
	public void destroy(){
		this.destroy();
	}
}
