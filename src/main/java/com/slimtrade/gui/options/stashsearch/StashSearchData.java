package com.slimtrade.gui.options.stashsearch;

import com.slimtrade.enums.StashTabColor;
import com.slimtrade.gui.components.IRemovablePanelData;

public class StashSearchData implements IRemovablePanelData {

    public StashSearchData() {

    }

    public StashSearchData(String searchName, String searchTerms, StashTabColor color) {
        this.searchName = searchName;
        this.searchTerms = searchTerms;
        this.color = color;
    }

    public String searchName;
    public String searchTerms;
    public StashTabColor color;

}
