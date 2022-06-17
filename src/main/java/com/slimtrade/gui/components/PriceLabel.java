package com.slimtrade.gui.components;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PriceLabel extends JPanel {

    private final String itemName;
    private final double quantity;
    private static final int MULTIPLE_PANEL_INSET = 4;


    public PriceLabel(TradeOffer tradeOffer) {
        this(tradeOffer, false);
    }

    public PriceLabel(TradeOffer tradeOffer, boolean useItem) {
        if (useItem) {
            itemName = tradeOffer.itemName;
            quantity = tradeOffer.itemQuantity;
        } else {
            itemName = tradeOffer.priceTypeString;
            quantity = tradeOffer.priceQuantity;
        }
        buildPanel();
    }

    public PriceLabel(TradeOffer tradeOffer, int index) {
        SaleItem saleItem = tradeOffer.getItems().get(index);
        itemName = saleItem.itemName;
        quantity = saleItem.quantity;
        buildPanel();
    }

    private void buildPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        CurrencyType currencyType = CurrencyType.getCurrencyImage(itemName);

        // Item Name / Quantity
        String suffix = currencyType == null ? " " + itemName : "";
        JLabel itemLabel = new JLabel("(" + ZUtil.formatNumber(quantity) + ")" + suffix);
        add(itemLabel, gc);
        gc.gridx++;
        // Currency Icon
        if (currencyType != null) {
            JLabel currencyLabel = new JLabel();
            currencyLabel.setIcon(ColorManager.getIcon(currencyType.getPath()));
            add(currencyLabel, gc);
            gc.gridx++;
        }

    }

    public static JPanel buildPriceLabel(TradeOffer offer) {
        if (offer.isBulkTrade) {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gc = ZUtil.getGC();
            ArrayList<SaleItem> items = offer.getItems();
            for (int i = 0; i < items.size(); i++) {
                PriceLabel label = new PriceLabel(offer, i);
                panel.add(label, gc);
                gc.gridx++;
                gc.insets.left = MULTIPLE_PANEL_INSET;
            }
            return panel;
        } else {
            return new PriceLabel(offer);
        }
    }

}
