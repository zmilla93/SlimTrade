package com.slimtrade.core.data;

public class ListPanel  <T> {

    public final String title;
    public final T component;

    public ListPanel(String title, T component) {
        this.title = title;
        this.component = component;
    }

    @Override
    public String toString() {
        return title;
    }

}
