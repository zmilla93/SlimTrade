package main.java.com.slimtrade.core.utility;

import java.awt.Dimension;
import java.awt.Toolkit;

import main.java.com.slimtrade.datatypes.CurrencyType;

public class TradeUtility {

	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static String getFixedCurrencyString(String inputString) {
		String[] currency = { "alch", "chaos", "ex" };
		String fixedString = inputString.replaceAll("\\s", "").replaceAll("(?i)(orb|of)", "");
		for (String s : currency) {
			if (fixedString.toLowerCase().matches(s + ".*")) {
				return s;
			}
		}
		return inputString;
	}

	public static String getFixedItemName(String item, double count, boolean paren) {
		String fixedNum = count == 0 ? "" : String.valueOf(count).toString().replaceAll("[.,]0", "");
		if (!fixedNum.equals("") && paren) {
			fixedNum = "(" + fixedNum + ")";
		}
		String fixedString = fixedNum.equals("") ? item : fixedNum + " " + item;
		return fixedString;
	}

	public static String getFixedDouble(double num) {
		String fixedDouble = String.valueOf(num).replaceAll("[.,]0", "");
		return fixedDouble;
	}

	// TODO : Could remove empty string check
	public static CurrencyType getCurrencyType(String inputString) {
		for (CurrencyType type : CurrencyType.values()) {
			for (String tag : type.getTags()) {
				if (tag == "") {
					break;
				}
				if (inputString.contains(tag)) {
					return type;
				}
			}
		}
		return null;
	}

	// TODO : check more stuff?
	public static boolean isDuplicateTrade(TradeOffer trade1, TradeOffer trade2) {
		final int checkCount = 6;
		int check = 0;
		if (trade1.msgType.equals(trade2.msgType)) {
			check++;
		}
		if (trade1.playerName.equals(trade2.playerName)) {
			check++;
		}
		if (trade1.itemName.equals(trade2.itemName)) {
			check++;
		}
		if (trade1.itemCount.equals(trade2.itemCount)) {
			check++;
		}
		if (trade1.priceTypeString.equals(trade2.priceTypeString)) {
			check++;
		}
		if (trade1.priceCount.equals(trade2.priceCount)) {
			check++;
		}
		if (check == checkCount) {
			return true;
		}
		return false;
	}

}
