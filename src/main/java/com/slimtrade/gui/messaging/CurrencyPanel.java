package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.CurrencyImage;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.components.IconLabel;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CurrencyPanel extends AdvancedButton {

    public static final int INSET = 2;
    private ArrayList<SaleItem> items;

    public CurrencyPanel() {
        super();
        updateUI();
    }

    public void setItem(SaleItem item) {
        ArrayList<SaleItem> list = new ArrayList();
        list.add(item);
        this.items = list;
        buildPanel();
    }

    public void setItems(ArrayList<SaleItem> items) {
        this.items = items;
        buildPanel();
    }

    private void buildPanel() {
        removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        for (SaleItem item : items) {
            CurrencyImage image = CurrencyImage.getCurrencyImage(item.itemName);
            if (image == null) {
                add(new JLabel(formatPrice(item.quantity) + " " + item.itemName), gc);
            } else {
                add(new JLabel(formatPrice(item.quantity)), gc);
                gc.insets.left = 4;
                add(new IconLabel(image.getPath()), gc);
            }
        }
    }

    private String formatPrice(double price) {
        return "(" + TradeOffer.cleanItemQuantity(price) + ")";
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (items != null) buildPanel();
        setBorder(BorderFactory.createEmptyBorder(INSET, INSET, INSET, INSET));
    }

}
