package com.slimtrade.core.data;

import com.slimtrade.core.enums.MatchType;

import java.util.concurrent.TimeUnit;

public class IgnoreItem {

    public final String itemName;
    public final MatchType matchType;
    public final long expirationTime;
    public final boolean indefinite;

    public IgnoreItem(String itemName, MatchType matchType, int durationInMinutes) {
        this.itemName = itemName;
        this.matchType = matchType;
        if (durationInMinutes == 0) {
            indefinite = true;
            expirationTime = 0;
        } else {
            indefinite = false;
            expirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(durationInMinutes);
        }
    }

    public IgnoreItem(String itemName, MatchType matchType, long expirationTime, boolean indefinite) {
        this.itemName = itemName;
        this.matchType = matchType;
        this.expirationTime = expirationTime;
        this.indefinite = indefinite;
    }

    public boolean isExpired() {
        return expirationTime < System.currentTimeMillis();
    }

    public int getRemainingMinutes() {
        long remaining = expirationTime - System.currentTimeMillis();
        return (int) Math.ceil(remaining / (1000f * 60));
    }

}
