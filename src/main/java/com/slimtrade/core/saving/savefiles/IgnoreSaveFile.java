package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.data.IgnoreItem;
import com.slimtrade.core.enums.MatchType;

import java.util.ArrayList;
import java.util.HashMap;

public class IgnoreSaveFile extends BaseSaveFile {

    public ArrayList<IgnoreItem> ignoreList = new ArrayList<>();
    public transient HashMap<String, IgnoreItem> exactMatchIgnoreMap = new HashMap<>();
    public transient ArrayList<IgnoreItem> containsTextIgnoreList = new ArrayList<>();

    public void buildCache() {
        exactMatchIgnoreMap.clear();
        containsTextIgnoreList.clear();
        for (IgnoreItem item : ignoreList) {
            if (item.isExpired()) continue;
            if (item.matchType == MatchType.EXACT_MATCH) {
                exactMatchIgnoreMap.put(item.itemNameLower(), item);
            } else {
                containsTextIgnoreList.add(item);
            }
        }
    }

}
