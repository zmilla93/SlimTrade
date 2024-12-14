package github.zmilla93.core.data;

import github.zmilla93.core.poe.Game;

import java.util.ArrayList;

/**
 * A wrapper to allow SaleItem array to be used in a table cell renderer.
 */
public class SaleItemWrapper {

    public final Game game;
    public final ArrayList<SaleItem> items;

    public SaleItemWrapper(Game game, ArrayList<SaleItem> items) {
        this.game = game;
        this.items = items;
    }

}
