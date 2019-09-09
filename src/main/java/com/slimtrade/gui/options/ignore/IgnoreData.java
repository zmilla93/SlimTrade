package com.slimtrade.gui.options.ignore;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.slimtrade.gui.enums.MatchType;

public class IgnoreData {

	private String itemName;
	private MatchType matchType;
	private LocalDateTime expireTime;

	public IgnoreData(String itemName, MatchType type, int duration) {
		this.itemName = itemName;
		this.matchType = type;
		this.expireTime = LocalDateTime.now().plusMinutes(duration);
	}
	
	public IgnoreData(String itemName, MatchType type, LocalDateTime time) {
		this.itemName = itemName;
		this.matchType = type;
		this.expireTime = time;
	}

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

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	
	//Rounds to the nearest minute
	public int getRemainingTime(){
		return Math.round((((int)(LocalDateTime.now().until(expireTime, ChronoUnit.SECONDS))+5)/10)*10)/60;
	}

}
