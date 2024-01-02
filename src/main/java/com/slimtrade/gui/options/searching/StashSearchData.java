package com.slimtrade.gui.options.searching;

@Deprecated
public class StashSearchData {

    public final String title;
    public final String searchTerm;
    public final int colorIndex;

    public StashSearchData(String tag, String search, int index) {
        title = tag;
        searchTerm = search;
        colorIndex = index;
    }

}
