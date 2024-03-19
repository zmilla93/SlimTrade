package com.slimtrade.core.data;

import java.util.ArrayList;

/**
 * A wrapper to allow SaleItem array to be used in a table cell renderer.
 */
public class SaleItemWrapper {

    public final ArrayList<SaleItem> items;

    public SaleItemWrapper(ArrayList<SaleItem> items) {
        this.items = items;
    }

}
