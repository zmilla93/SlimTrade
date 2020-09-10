package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.saving.SettingsSaveFile;
import com.slimtrade.gui.enums.MatchType;

import java.util.Calendar;
import java.util.Date;

public class IgnoreData {

    public String itemName;
    public MatchType matchType;
    public Date expireTime;
    public int duration;
    public boolean indefinite = false;
    public static Calendar calendar = Calendar.getInstance();

    public IgnoreData(String itemName, MatchType type, int duration) {
        this.itemName = itemName;
        this.matchType = type;
        this.duration = duration;
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, duration);
        this.expireTime = calendar.getTime();
        if (duration == 0) {
            indefinite = true;
        }
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

    public boolean getIndefinite() {
        return indefinite;
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
    public int getRemainingTime() {
//	    return duration;
        Date now = new Date();
        int diff = SettingsSaveFile.dateDifference(now, expireTime);
        return diff;
    }

}
