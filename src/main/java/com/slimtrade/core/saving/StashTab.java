package com.slimtrade.core.saving;

import com.slimtrade.enums.StashTabColor;
import com.slimtrade.enums.StashTabType;

public class StashTab {

    public String name;
    public StashTabType type;
    public StashTabColor color;

    public StashTab(String name, StashTabType type, StashTabColor color) {
        this.name = name;
        this.type = type;
        this.color = color;
    }

}
