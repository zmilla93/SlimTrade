package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * A panel that simplifies working with {@link CardLayout}s.
 * Add cards using add(Component), then use showComponent(Component) to select which card is visible.
 * Does not currently support removing components (will cause bugs).
 */
// FIXME : Replace existing uses of CardLayout this this.
// FIXME : Add support for removing components. Could use a queue of int, track max id to fill queue, then requeue an int when it is removed.
public class CardPanel extends JPanel {

    private final CardLayout cardLayout = new CardLayout();
    private final HashMap<Component, String> cardMap = new HashMap<>();

    public CardPanel() {
        setLayout(cardLayout);
    }

    /**
     * Use this to show a component that was previously added.
     *
     * @param component Component to show
     */
    public void showCard(Component component) {
        if (!cardMap.containsKey(component)) return;
        String key = cardMap.get(component);
        cardLayout.show(this, key);
    }

    /**
     * This is the only version of add() that should ever be used!
     */
    @Override
    public Component add(Component comp) {
        String key = Integer.toString(getComponentCount());
        add(comp, key);
        cardMap.put(comp, key);
        return comp;
    }

}
