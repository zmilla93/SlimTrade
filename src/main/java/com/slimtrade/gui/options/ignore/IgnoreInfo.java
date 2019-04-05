package main.java.com.slimtrade.gui.options.ignore;

import main.java.com.slimtrade.gui.enums.MatchType;

public class IgnoreInfo {

	private String itemName;
	private MatchType matchType;
	private int duration;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
