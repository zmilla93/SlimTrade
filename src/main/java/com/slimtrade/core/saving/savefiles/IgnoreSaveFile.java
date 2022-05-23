package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.data.IgnoreItem;
import com.slimtrade.core.enums.MatchType;

import java.util.ArrayList;
import java.util.HashMap;

public class IgnoreSaveFile {

    public ArrayList<IgnoreItem> ignoreList = new ArrayList<>();
    public transient HashMap<String, IgnoreItem> exactIgnoreMap = new HashMap<>();
    public transient ArrayList<IgnoreItem> containsIgnoreList = new ArrayList<>();

    public void buildCache() {
        exactIgnoreMap.clear();
        containsIgnoreList.clear();
        for (IgnoreItem item : ignoreList) {
            if (item.isExpired()) continue;
            if (item.matchType == MatchType.EXACT_MATCH) {
                exactIgnoreMap.put(item.itemNameLower, item);
            } else {
                containsIgnoreList.add(item);
            }
        }
    }

}
