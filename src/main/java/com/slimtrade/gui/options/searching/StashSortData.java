package com.slimtrade.gui.options.searching;

@Deprecated
public class StashSortData {

    public final String title;
    public final String searchTerm;
    public final int colorIndex;

    public StashSortData(String tag, String search, int index) {
        title = tag;
        searchTerm = search;
        colorIndex = index;
    }

}
