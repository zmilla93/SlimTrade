package com.zrmiller.slimtrade;

public class TradeOffer {
	

	String offerType;
	String name;
	String item;
	String price;
	float itemQuant;
	float priceQuant;
	
	public TradeOffer(String offerType, String name, String item, float itemQuant, String price, float priceQuant){
		this.offerType = offerType;
		this.name = name;
		this.item = item;
		this.itemQuant = itemQuant;
		this.price = price;
		this.priceQuant = priceQuant;
	}
	
	public void destroy(){
		this.destroy();
	}
}
