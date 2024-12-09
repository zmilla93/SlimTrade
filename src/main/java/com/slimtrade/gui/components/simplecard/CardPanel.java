package com.slimtrade.gui.components.simplecard;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * A panel that simplifies working with {@link CardLayout}s.
 */
public class CardPanel extends JPanel {

    private final CardLayout cardLayout = new CardLayout();
    private final HashMap<Component, String> cardMap = new HashMap<>();

    public CardPanel() {
        setLayout(cardLayout);
    }

    public void showCard(Component component) {
        if (!cardMap.containsKey(component)) return;
        String key = cardMap.get(component);
        cardLayout.show(this, key);
    }

    @Override
    public Component add(Component comp) {
        String key = Integer.toString(getComponentCount());
        add(comp, key);
        cardMap.put(comp, key);
        return comp;
    }

}
