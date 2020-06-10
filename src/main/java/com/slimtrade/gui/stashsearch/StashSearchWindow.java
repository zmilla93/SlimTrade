package com.slimtrade.gui.stashsearch;

import com.slimtrade.gui.basic.AbstractResizableWindow;

public class StashSearchWindow extends AbstractResizableWindow {

    public StashSearchWindow() {
        super("Stash Searcher", true);
//        this.setSize(400, 400);
        this.pack();
        this.updateColor();
    }

    // Todo : call on stash update
    public void updateLocation() {

    }

}
