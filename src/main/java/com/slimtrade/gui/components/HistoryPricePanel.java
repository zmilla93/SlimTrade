package com.slimtrade.gui.components;

import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.history.PoePrice;
import com.slimtrade.modules.colortheme.IconFactory;

import javax.swing.*;
import java.awt.*;

public class HistoryPricePanel extends JPanel {

    // FIXME : add parity with CurrencyPanel
    public HistoryPricePanel(PoePrice price) {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        CurrencyType currencyType = CurrencyType.getCurrencyImage(price.priceString);
        if (currencyType == null) {
            JLabel label = new JLabel("(" + price.quantity + ") " + price.priceString);
            Font font = label.getFont();
            label.setFont(font.deriveFont(Font.PLAIN, font.getSize()));
            add(label, gc);
        } else {
            JLabel quantityLabel = new JLabel("(" + price.quantity + ")");
            JLabel priceLabel = new JLabel();
            Font font = quantityLabel.getFont();
            quantityLabel.setFont(font.deriveFont(Font.PLAIN, font.getSize()));
            priceLabel.setIcon(IconFactory.getIcon(currencyType.getPath()));
            add(quantityLabel, gc);
            gc.gridx++;
            gc.insets.left = 2;
            add(priceLabel, gc);
        }
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg);
        for (Component c : getComponents()) {
            c.setForeground(fg);
        }
    }
}
