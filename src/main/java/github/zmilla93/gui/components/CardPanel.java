package github.zmilla93.gui.components;

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
    private final HashMap<Component, Integer> cardMap = new HashMap<>();
    private int currentCardIndex = 0;
    private boolean allowWrap;

    public CardPanel() {
        setLayout(cardLayout);
    }

    public CardPanel(Component... components) {
        this();
        for (Component component : components)
            add(component);
    }

    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    /**
     * Use this to show a component that was previously added.
     *
     * @param component Component to show
     */
    public void showCard(Component component) {
        if (!cardMap.containsKey(component)) return;
        int key = cardMap.get(component);
        currentCardIndex = key;
        cardLayout.show(this, Integer.toString(key));
    }

    /**
     * Sets if previous() and next() will cause the card panel to wrap between the first and last panels.
     */
    public void setAllowWrapAround(boolean allow) {
        this.allowWrap = allow;
    }

    public void previous() {
        boolean wrap = currentCardIndex == 0;
        if (wrap && !allowWrap) return;
        cardLayout.previous(this);
        if (wrap) currentCardIndex = getComponentCount() - 1;
        else currentCardIndex--;
    }

    public void next() {
        boolean wrap = currentCardIndex == getComponentCount() - 1;
        if (wrap && !allowWrap) return;
        cardLayout.next(this);
        if (wrap) currentCardIndex = 0;
        else currentCardIndex++;
    }

    /**
     * An add(Component) alias to make it easier to identify the correct add method.
     */
    public Component addCard(Component comp) {
        return add(comp);
    }

    /**
     * This is the only version of add() that should ever be used!
     */
    @Override
    public Component add(Component comp) {
        int key = getComponentCount();
        add(comp, Integer.toString(key));
        cardMap.put(comp, key);
        return comp;
    }

}
