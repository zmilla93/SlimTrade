package github.zmilla93.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * A JScrollPane with a better default scroll speed.
 */
// TODO : Should switch all existing JScrollPanes to this for better consistency.
public class CustomScrollPane extends JScrollPane {

    public static final int SCROLL_SPEED = 10;

    public CustomScrollPane() {
        setDefaults();
    }

    public CustomScrollPane(Component view) {
        super(view);
        setDefaults();
    }

    public CustomScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        setDefaults();
    }

    public CustomScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        setDefaults();
    }

    private void setDefaults() {
        getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
    }

}
