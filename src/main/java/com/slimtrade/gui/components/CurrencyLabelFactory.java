package com.slimtrade.gui.components;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.history.PoePrice;
import com.slimtrade.modules.theme.ThemeManager;

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

    // Factory Functions

    public static Container applyItemToComponent(Container container, TradeOffer tradeOffer) {
        ArrayList<SaleItem> saleItems = tradeOffer.getItems();
        setupContainer(container);
        for (SaleItem item : saleItems) {
            addDataToContainer(container, item.itemName, item.quantity);
        }
        return container;
    }

    public static Container applyItemToComponent(Container container, ArrayList<SaleItem> saleItems) {
        setupContainer(container);
        for (SaleItem item : saleItems) {
            addDataToContainer(container, item.itemName, item.quantity, true);
        }
        return container;
    }

    public static Container applyBulkItemToComponent(Container container, TradeOffer tradeOffer, int itemIndex) {
        setupContainer(container);
        SaleItem saleItem = tradeOffer.getItems().get(itemIndex);
        addDataToContainer(container, saleItem.itemName, saleItem.quantity);
        return container;
    }

    public static Container applyPriceToComponent(Container container, String currencyName, double quantity) {
        setupContainer(container);
        addDataToContainer(container, currencyName, quantity);
        return container;
    }

    public static Container applyPOEPriceToComponent(Container container, PoePrice poePrice) {
        setupContainer(container);
        addDataToContainer(container, poePrice.priceString, poePrice.quantity, true);
        return container;
    }

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

    // Internal Stuff
    // Setup - Call this first!

    private static Container setupContainer(Container container) {
        container.setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        return container;
    }

    // Building the container itself

    private static Container addDataToContainer(Container container, String itemName, double quantity) {
        return addDataToContainer(container, itemName, quantity, false);
    }

    private static Container addDataToContainer(Container container, String itemName, double quantity, boolean plain) {
        CurrencyType currencyType = CurrencyType.getCurrencyType(itemName);
        JLabel textLabel = textLabel(itemName, quantity, currencyType);
        if (plain)
            textLabel.setFont(textLabel.getFont().deriveFont(Font.PLAIN, textLabel.getFont().getSize()));
        container.add(textLabel, gc);
        gc.gridx++;
        if (currencyType != null) {
            gc.insets.left = ITEM_CURRENCY_INSET;
            JLabel iconLabel = iconLabel(currencyType);
            container.add(iconLabel, gc);
            gc.gridx++;
        }
        gc.insets.left = MULTIPLE_PANEL_INSET;
        return container;
    }

    // Components

    private static JLabel textLabel(String itemName, double quantity, CurrencyType currencyType) {
        if (currencyType != null && quantity == 0) quantity = 1;
        String prefix = quantity > 0 ? "(" + ZUtil.formatNumber(quantity) + ")" : "";
        String suffix = currencyType == null ? " " + itemName : "";
        return new JLabel(prefix + suffix);
    }

    private static JLabel iconLabel(CurrencyType currencyType) {
        if (currencyType != null) {
            JLabel currencyLabel = new JLabel();
            currencyLabel.setIcon(ThemeManager.getIcon(currencyType.getPath()));
            return currencyLabel;
        }
        return null;
    }

}
