package com.slimtrade.modules.listening;

import java.util.ArrayList;

public abstract class ListenManager<T> {

    protected final ArrayList<T> listeners = new ArrayList<>();

    public void addListener(T listener) {
        listeners.add(listener);
    }

    public void removeListener(T listener) {
        listeners.remove(listener);
    }

    public void removeAllListeners() {
        listeners.clear();
    }

}
