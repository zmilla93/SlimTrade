package com.slimtrade.gui.stash;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StashHelperPanel extends JButton {

    private JFrame highlighter = new JFrame();

    public StashHelperPanel(TradeOffer offer, StashTabColor color){
        setLayout(new GridBagLayout());
        JLabel tabLabel = new JLabel(offer.stashtabName);
        JLabel itemLabel = new JLabel(offer.itemName);
        JPanel innerPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gc = ZUtil.getGC();
        add(tabLabel, gc);
        gc.gridy++;
        add(itemLabel, gc);
        setBackground(color.getBackground());
        tabLabel.setForeground(color.getForeground());
        itemLabel.setForeground(color.getForeground());

        int insetHorizontal = 6;
        int insetVertical = 4;
        Border innerBorder = BorderFactory.createEmptyBorder(insetVertical, insetHorizontal, insetVertical, insetHorizontal);
        Border outerBorder = BorderFactory.createLineBorder(color.getForeground());
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        setBorder(compoundBorder);
    }

}
