package com.slimtrade.gui.stash;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.CurrencyLabelFactory;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StashHelperPanel extends AdvancedButton {

    private final TradeOffer tradeOffer;
    private StashHighlighterFrame highlighterFrame;
    private StashTabColor stashTabColor;

    private final String searchTerm;
    //    private final String itemName;
//    private SaleItem saleItem;
//    private JLabel priceLabel = new JLabel();
    private int index = -1;

    public StashHelperPanel(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
//        itemName = tradeOffer.itemName;
        searchTerm = TradeUtil.cleanItemName(tradeOffer.itemName);
        buildPanel();
    }

    public StashHelperPanel(TradeOffer tradeOffer, int index) {
        this.tradeOffer = tradeOffer;
        this.index = index;
        SaleItem saleItem = tradeOffer.getItems().get(index);
//        CurrencyType currency = CurrencyType.getCurrencyImage(saleItem.itemName);
//        String suffix = currency == null ? " " + saleItem.itemName : "";
//        itemName = "(" + ZUtil.formatNumber(saleItem.quantity) + ")" + suffix;
//        if (currency != null)
        searchTerm = TradeUtil.cleanItemName(saleItem.itemName);
//        PriceLabel.applyBulkItemToComponent(priceLabel, tradeOffer, index);
        buildPanel();
    }

    private void buildPanel() {
        assert (SwingUtilities.isEventDispatchThread());
        // FIXME : default visibility to true and make sure no debug panels are being added
        setVisible(false);
        if (tradeOffer.stashTabName != null)
            highlighterFrame = new StashHighlighterFrame(tradeOffer);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new GridBagLayout());
        JLabel stashTabLabel = new JLabel(tradeOffer.stashTabName);
        JPanel itemPanel = new JPanel();
        itemPanel.setOpaque(false);
        if (tradeOffer.isBulkTrade) {
            CurrencyLabelFactory.applyBulkItemToComponent(itemPanel, tradeOffer, index);
        } else {
            CurrencyLabelFactory.applyItemToComponent(itemPanel, tradeOffer);
        }

        GridBagConstraints gc = ZUtil.getGC();
        add(stashTabLabel, gc);
        gc.gridy++;
        add(itemPanel, gc);
        gc.gridy++;
        stashTabColor = this.tradeOffer.getStashTabColor();
        if (stashTabColor != StashTabColor.ZERO) {
            setBackground(stashTabColor.getBackground());
            stashTabLabel.setForeground(stashTabColor.getForeground());
            CurrencyLabelFactory.applyColorToLabel(itemPanel, stashTabColor.getForeground());
        }
        createBorder(stashTabColor);
        addListeners();
        if (!this.tradeOffer.isBulkTrade) FrameManager.stashHelperContainer.addHelper(this);
    }

    private void addListeners() {
        addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    POEInterface.searchInStash(searchTerm);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setVisible(false);
                    if (highlighterFrame != null) highlighterFrame.setVisible(false);
                    FrameManager.stashHelperContainer.refresh();
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
        if (!tradeOffer.isBulkTrade)
            FrameManager.stashHelperContainer.getContentPanel().remove(this);
        if (highlighterFrame != null) {
            highlighterFrame.dispose();
            highlighterFrame = null;
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (stashTabColor == StashTabColor.ZERO) {
            createBorder(stashTabColor);
        }
    }

}
