package com.slimtrade.core.data;

import com.slimtrade.core.enums.MatchType;

import java.util.concurrent.TimeUnit;

public class IgnoreItem {

    public final String itemName;
    public final String itemNameLower;
    public final MatchType matchType;
    public final int initialDuration;
    public final long expirationTime;
    private transient boolean expired = false;
    private transient final int leniency = 1000 * 30;

    public IgnoreItem(String itemName, MatchType matchType, int duration) {
        this.itemName = itemName;
        this.itemNameLower = itemName.toLowerCase();
        this.matchType = matchType;
        this.initialDuration = duration;
        if (duration == 0) {
            expirationTime = 0;
        } else {
            expirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(duration);
        }
    }

    public boolean isExpired() {
        if (isInfinite()) return false;
        if (!expired)
            expired = expirationTime <= System.currentTimeMillis() + leniency;
        return expired;
    }

    public int getRemainingMinutes() {
        long remaining = expirationTime - System.currentTimeMillis();
        return (int) Math.ceil(remaining / (1000f * 60));
    }

    public boolean isInfinite() {
        return initialDuration == 0;
    }

}
