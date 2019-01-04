package com.zrmiller.slimtrade;

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
	
}
