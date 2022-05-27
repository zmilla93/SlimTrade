package com.slimtrade.gui.stash;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StashHelperPanel extends AdvancedButton {

    private StashHelperContainer parent;
    private TradeOffer tradeOffer;
    private StashHighlighterFrame highlighterFrame;
    private StashTabColor stashTabColor;

    private JLabel tabLabel;
    private JLabel itemLabel;

    protected StashHelperPanel(StashHelperContainer parent, TradeOffer offer, StashHighlighterFrame highlighterFrame) {
        assert (SwingUtilities.isEventDispatchThread());
        this.parent = parent;
        // FIXME : default visibility to true and make sure no debug panels are being added
        setVisible(false);
        tradeOffer = offer;
        this.highlighterFrame = highlighterFrame;
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


        FrameManager.stashHelperContainer.add(this);
        addListeners();
    }

    private void addListeners() {
        addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    POEInterface.searchInStash(TradeUtil.cleanItemName(tradeOffer.itemName));
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setVisible(false);
                    if (highlighterFrame != null) highlighterFrame.setVisible(false);
                    parent.refresh();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (highlighterFrame != null) {
                    highlighterFrame.setVisible(true);
                    highlighterFrame.stopTimer();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (highlighterFrame != null) {
                    highlighterFrame.startTimer();
                }
            }
        });
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

    public void cleanup() {
        highlighterFrame = null;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (stashTabColor == StashTabColor.ZERO) {
            createBorder(stashTabColor);
        }
    }

}
