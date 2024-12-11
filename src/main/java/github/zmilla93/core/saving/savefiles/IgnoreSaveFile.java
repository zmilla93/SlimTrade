package github.zmilla93.core.saving.savefiles;

import github.zmilla93.core.data.IgnoreItemData;
import github.zmilla93.core.enums.MatchType;

import java.util.ArrayList;
import java.util.HashMap;

public class IgnoreSaveFile extends AbstractSaveFile {

    public ArrayList<IgnoreItemData> ignoreList = new ArrayList<>();
    public transient HashMap<String, IgnoreItemData> exactMatchIgnoreMap = new HashMap<>();
    public transient ArrayList<IgnoreItemData> containsTextIgnoreList = new ArrayList<>();

    public void buildCache() {
        exactMatchIgnoreMap.clear();
        containsTextIgnoreList.clear();
        for (IgnoreItemData item : ignoreList) {
            if (item.isExpired()) continue;
            if (item.matchType == MatchType.EXACT_MATCH) {
                exactMatchIgnoreMap.put(item.itemNameLower(), item);
            } else {
                containsTextIgnoreList.add(item);
            }
        }
    }

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
