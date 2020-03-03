package com.slimtrade.gui.stash_search;

import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.basic.BasicMovableDialog;

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
