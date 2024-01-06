package com.slimtrade.core.data;

import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.utility.ZUtil;

import java.util.concurrent.TimeUnit;

public class IgnoreItemData {

    public final String itemName;
    private transient String itemNameLower;
    public final MatchType matchType;
    public final int initialDuration;
    public final long expirationTime;
    private transient boolean expired = false;
    private static final int leniency = 1000 * 30;

    public IgnoreItemData(String itemName, MatchType matchType, int duration) {
        this.itemName = ZUtil.cleanString(itemName);
        this.itemNameLower = itemName.toLowerCase();
        this.matchType = matchType;
        this.initialDuration = duration;
        if (duration == 0) {
            expirationTime = 0;
        } else {
            expirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(duration);
        }
    }

    public String itemNameLower() {
        if (itemNameLower == null) itemNameLower = itemName.toLowerCase();
        return itemNameLower;
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
