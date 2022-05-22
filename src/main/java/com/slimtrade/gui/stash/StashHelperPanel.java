package com.slimtrade.gui.stash;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StashHelperPanel extends AdvancedButton {

    private JFrame highlighter = new JFrame();
    JLabel tabLabel;
    JLabel itemLabel;

    private TradeOffer tradeOffer;
    private StashTabColor stashTabColor;

    public StashHelperPanel(StashHelperContainer parent, TradeOffer offer) {
        // FIXME : default visibility to true and make sure no debug panels are being added
        setVisible(false);
        tradeOffer = offer;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new GridBagLayout());
        tabLabel = new JLabel(offer.stashTabName);
        itemLabel = new JLabel(offer.itemName);

        GridBagConstraints gc = ZUtil.getGC();
        add(tabLabel, gc);
        gc.gridy++;
        add(itemLabel, gc);
        gc.gridy++;

        stashTabColor = tradeOffer.getStashTabColor();

        if (stashTabColor != StashTabColor.ZERO) {
            setBackground(stashTabColor.getBackground());
            tabLabel.setForeground(stashTabColor.getForeground());
            itemLabel.setForeground(stashTabColor.getForeground());

        }
        createBorder(stashTabColor);
//        Border innerBorder = BorderFactory.createEmptyBorder(insetVertical, insetHorizontal, insetVertical, insetHorizontal);
//        Border outerBorder = BorderFactory.createLineBorder(stashTabColor.getForeground(), 2);
//        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
//        setBorder(compoundBorder);

        JButton self = this;
        addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    PoeInterface.findInStash(TradeUtil.cleanItemName(tradeOffer.itemName));
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setVisible(false);
                    parent.refresh();
                }
            }
        });

        FrameManager.stashHelperContainer.add(this);
    }

    private void createBorder(StashTabColor stashTabColor) {
        Color color = stashTabColor.getForeground();
        if (stashTabColor == StashTabColor.ZERO) color = UIManager.getColor("Label.foreground");
        int insetHorizontal = 6;
        int insetVertical = 4;
        Border innerBorder = BorderFactory.createEmptyBorder(insetVertical, insetHorizontal, insetVertical, insetHorizontal);
        Border outerBorder = BorderFactory.createLineBorder(color, 2);
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        setBorder(compoundBorder);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (stashTabColor == StashTabColor.ZERO) {
            createBorder(stashTabColor);
        }
    }
}
