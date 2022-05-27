package com.slimtrade.gui.components;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.CurrencyImage;
import com.slimtrade.core.trading.TradeOffer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CurrencyComponent {

    public static void addCurrencyToPanel(JComponent component, SaleItem saleItem) {
        ArrayList<SaleItem> list = new ArrayList<>(1);
        list.add(saleItem);
    }

    public static void addCurrencyToPanel(JComponent component, ArrayList<SaleItem> items) {
        buildPanel(component, items);
    }

    private static void buildPanel(JComponent component, ArrayList<SaleItem> items) {
        component.removeAll();
        component.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        for (int i = 0; i < items.size(); i++) {
            SaleItem item = items.get(i);
            CurrencyImage image = CurrencyImage.getCurrencyImage(item.itemName);
            if (image == null) {
                String prefix = item.quantity <= 1 ? "" : formatPrice(item.quantity) + " ";
                component.add(new PlainLabel(prefix + item.itemName), gc);
            } else {
                if (item.quantity < 1) item.quantity = 1;
                JLabel quantityLabel = new PlainLabel(formatPrice(item.quantity));
                component.add(quantityLabel, gc);
                gc.insets.left = 1;
                component.add(new IconLabel(image.getPath()), gc);
                gc.insets.left = 6;
            }
        }
    }

    private static String formatPrice(double price) {
        return "(" + TradeOffer.cleanItemQuantity(price) + ")";
    }


}
