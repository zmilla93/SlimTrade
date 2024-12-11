package github.zmilla93.modules.listening;

import javax.swing.*;
import java.util.ArrayList;

public class ListenManagerPanel<T> extends JPanel {

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
