package com.slimtrade.gui.messaging;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.CurrencyImage;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.components.IconLabel;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CurrencyButton extends AdvancedButton {

    public static final int INSET = 2;
    private ArrayList<SaleItem> items;
    private Color textColor;

    public CurrencyButton() {
        super();
        updateUI();
    }

    public void setItem(SaleItem item) {
        ArrayList<SaleItem> list = new ArrayList<>(1);
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
        for (int i = 0; i < items.size(); i++) {
            SaleItem item = items.get(i);
            CurrencyImage image = CurrencyImage.getCurrencyImage(item.itemName);
            if (image == null) {
                String prefix = item.quantity <= 1 ? "" : formatPrice(item.quantity) + " ";
                add(new JLabel(prefix + item.itemName), gc);
            } else {
                if (item.quantity < 1) item.quantity = 1;
                add(new JLabel(formatPrice(item.quantity)), gc);
                gc.insets.left = 1;
                add(new IconLabel(image.getPath()), gc);
                gc.insets.left = 6;
            }
        }
    }

    private String formatPrice(double price) {
        return "(" + TradeOffer.cleanItemQuantity(price) + ")";
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        textColor = fg;
        for (Component c : getComponents()) {
            c.setForeground(fg);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (items != null) buildPanel();
        if (textColor != null) {
            for (Component c : getComponents()) {
                c.setForeground(textColor);
            }
        }
        setBorder(BorderFactory.createEmptyBorder(INSET, INSET, INSET, INSET));
    }

}
