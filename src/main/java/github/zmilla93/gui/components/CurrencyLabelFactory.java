package github.zmilla93.gui.components;

import github.zmilla93.core.data.SaleItem;
import github.zmilla93.core.enums.CurrencyType;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Factory for adding currency icons and labels to a component.
 */
public class CurrencyLabelFactory extends JPanel {

    private static final int MULTIPLE_PANEL_INSET = 4;
    private static final int ITEM_CURRENCY_INSET = 0;
    private static GridBagConstraints gc;

    ///////////////////////
    // Factory Functions //
    ///////////////////////

    public static Container applyItemToComponent(Container container, Game game, ArrayList<SaleItem> saleItems) {
        return applyItemToComponent(container, game, saleItems, false);
    }

    public static Container applyItemToComponent(Container container, Game game, ArrayList<SaleItem> saleItems, boolean forceText) {
        setupContainer(container);
        for (int i = 0; i < saleItems.size(); i++) {
            SaleItem item = saleItems.get(i);
            addDataToContainer(container, game, item.itemName, item.quantity, forceText);
            if (i < saleItems.size() - 1) addComma(container);
        }
        return container;
    }

    /**
     * Recursively applies a foreground color to all labels within a container.
     */
    /// FIXME : Move this to ThemeColor perhaps?
    public static Container applyColorToLabel(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setForeground(color);
            }
            if (component instanceof Container) {
                Container nestedContainer = (Container) component;
                applyColorToLabel(nestedContainer, color);
            }
        }
        return container;
    }

    ///////////////
    // Container //
    ///////////////

    // Setup - Call this first!
    private static Container setupContainer(Container container) {
        container.setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        return container;
    }

    private static Container addDataToContainer(Container container, Game game, String itemName, double quantity, boolean forceText) {
        CurrencyType currencyType = CurrencyType.getCurrencyType(itemName, game);
        JLabel textLabel = textLabel(itemName, quantity, currencyType, forceText);
        container.add(textLabel, gc);
        gc.gridx++;
        if (currencyType != null) {
            gc.insets.left = ITEM_CURRENCY_INSET;
            JLabel iconLabel = iconLabel(currencyType);
            container.add(iconLabel, gc);
            gc.gridx++;
        }
        gc.insets.left = 0;
//        gc.insets.left = MULTIPLE_PANEL_INSET;
        return container;
    }

    private static Container addComma(Container container) {
        JLabel label = new JLabel(", ");
        container.add(label, gc);
        gc.gridx++;
        return container;
    }

    ////////////////
    // Components //
    ////////////////

    private static JLabel textLabel(String itemName, double quantity, CurrencyType currencyType, boolean forceText) {
        if (currencyType != null && quantity == 0) quantity = 1;
        String prefix = quantity > 0 ? "(" + ZUtil.formatNumber(quantity) + ")" : "";
        String suffix = currencyType == null || forceText ? " " + itemName : "";
        if (currencyType != null && forceText) suffix += " ";
        return new JLabel(prefix + suffix);
    }

    private static JLabel iconLabel(CurrencyType currencyType) {
        if (currencyType != null) {
            JLabel currencyLabel = new JLabel();
            currencyLabel.setIcon(ThemeManager.getIcon(currencyType.path()));
            return currencyLabel;
        }
        return null;
    }

}
