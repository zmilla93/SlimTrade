package main.java.com.slimtrade;

import java.awt.Dimension;
import java.awt.Toolkit;

public class TradeUtility {
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static String fixCurrencyString(String inputString){
		String[] currency = {"alch", "chaos", "ex"};
		String fixedString = inputString.replaceAll("\\s", "").replaceAll("(?i)(orb|of)", "");
		for(String s : currency){
			if(fixedString.toLowerCase().matches(s + ".*")){
				return s;
			}
		}
		return inputString;
	}
	
	public static boolean isDuplicateTrade(TradeOffer trade1, TradeOffer trade2){
		final int checkCount = 6;
		int check = 0;
		if(trade1.msgType.equals(trade2.msgType)){
			check++;
		}
		if(trade1.playerName.equals(trade2.playerName)){
			check++;
		}
		if(trade1.itemName.equals(trade2.itemName)){
			check++;
		}
		if(trade1.itemCount.equals(trade2.itemCount)){
			check++;
		}
		if(trade1.priceTypeString.equals(trade2.priceTypeString)){
			check++;
		}
		if(trade1.priceCount.equals(trade2.priceCount)){
			check++;
		}
		if(check == checkCount){
			return true;
		}
		return false;
	}
	
}
