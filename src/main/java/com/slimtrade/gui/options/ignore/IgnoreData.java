package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.SaveSystem.SaveFile;
import com.slimtrade.gui.enums.MatchType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class IgnoreData {

	private String itemName;
	private MatchType matchType;
	private Date expireTime;
	private static Calendar calendar = Calendar.getInstance();

	public IgnoreData(String itemName, MatchType type, int duration) {
		this.itemName = itemName;
		this.matchType = type;
		Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, duration);
		this.expireTime = calendar.getTime();
	}
	
//	public IgnoreData(String itemName, MatchType type, LocalDateTime date) {
//		this.itemName = itemName;
//		this.matchType = type;
////		this.calendar = Calendar.getInstance();
////		calendar.setTime(date);
////		calendar.add(Calendar.MINUTE, 60);
//		this.expireTime = calendar.getTime();
//	}

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

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	//Rounds to the nearest minute
	public int getRemainingTime(){
	    Date now = new Date();
        int diff = SaveFile.dateDifference(now, expireTime);
		return diff;
	}

}
