package com.zrmiller.slimtrade;

public class TradeUtility {

	public static String fixCurrencyString(String inputString){
		
		String[] currency = {"alch", "chaos", "ex"};
		String fixedString = inputString.replaceAll("\\s", "").replaceAll("(?i)(orbof)", "");
		for(String s : currency){
			if(fixedString.toLowerCase().matches(s + ".*")){
				return s;
			}
		}
		return fixedString;
	}
	
}
