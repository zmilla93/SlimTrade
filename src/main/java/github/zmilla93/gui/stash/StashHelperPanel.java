package github.zmilla93.gui.stash;

import github.zmilla93.core.data.SaleItem;
import github.zmilla93.core.enums.StashTabColor;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.core.utility.TradeUtil;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.CurrencyLabelFactory;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.components.AdvancedButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A helper panel that displays an item name and stash tab name. Use StashHelperBulkWrapper for bulk trades.
 * Displays a {@link StashHighlighterFrame} when hovered. Searches item name in stash when clicked.
 */
public class StashHelperPanel extends AdvancedButton {

    private final TradeOffer tradeOffer;
    private StashHighlighterFrame highlighterFrame;
    private StashTabColor stashTabColor;

    private final String searchTerm;
    private int index = -1;

    public StashHelperPanel(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
        searchTerm = TradeUtil.cleanItemName(tradeOffer.itemName);
        buildPanel();
    }

    public StashHelperPanel(TradeOffer tradeOffer, int index) {
        this.tradeOffer = tradeOffer;
        this.index = index;
        SaleItem saleItem = tradeOffer.getItems().get(index);
        searchTerm = TradeUtil.cleanItemName(saleItem.itemName);
//        PriceLabel.applyBulkItemToComponent(priceLabel, tradeOffer, index);
        buildPanel();
    }

    private void buildPanel() {
        assert (SwingUtilities.isEventDispatchThread());
        // FIXME : default visibility to true and make sure no debug panels are being added
        setVisible(false);
        if (tradeOffer.stashTabName != null) {
            if (tradeOffer.game.isPoe1()) highlighterFrame = new StashHighlighterFramePoe1(tradeOffer);
            else highlighterFrame = new StashHighlighterFramePoe2(tradeOffer);
        }

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new GridBagLayout());
        JLabel stashTabLabel = new JLabel(tradeOffer.stashTabName);
        JPanel itemPanel = new JPanel();
        itemPanel.setOpaque(false);
        if (tradeOffer.isBulkTrade) {
//            CurrencyLabelFactory.applyBulkItemToComponent(itemPanel, tradeOffer, index);
            CurrencyLabelFactory.applyItemToComponent(itemPanel, tradeOffer.game, tradeOffer.getItems().get(index).toArrayList());
        } else {
            CurrencyLabelFactory.applyItemToComponent(itemPanel, tradeOffer.game, tradeOffer.getItems());
        }

        GridBagConstraints gc = ZUtil.getGC();
        add(stashTabLabel, gc);
        gc.gridy++;
        add(itemPanel, gc);
        gc.gridy++;
        stashTabColor = this.tradeOffer.getStashTabColor();
        if (stashTabColor != StashTabColor.ZERO) {
            setBackground(stashTabColor.getBackground());
            setBackgroundHover(ThemeManager.lighter(stashTabColor.getBackground()));
            stashTabLabel.setForeground(stashTabColor.getForeground());
            CurrencyLabelFactory.applyColorToLabel(itemPanel, stashTabColor.getForeground());
        }
        createBorder(stashTabColor);
        revalidate();
        addListeners();
        if (!this.tradeOffer.isBulkTrade) {
            if (tradeOffer.game.isPoe1()) FrameManager.stashHelperContainerPoe1.addHelper(this);
            else FrameManager.stashHelperContainerPoe2.addHelper(this);
        }
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

    public StashHighlighterFrame getHighlighterFrame() {
        return highlighterFrame;
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
        /// Hide first to trigger a repack on parent window
        setVisible(false);
        ZUtil.removeFromParent(this);
        if (!tradeOffer.isBulkTrade) {
            if (tradeOffer.game.isPoe1()) FrameManager.stashHelperContainerPoe1.remove(this);
            else FrameManager.stashHelperContainerPoe2.remove(this);
        }
        if (highlighterFrame != null) {
            highlighterFrame.dispose();
            highlighterFrame = null;
        }
    }

    private void updateParent() {
        Container parent = getTopLevelAncestor();
        if (parent == null) return;
        if (parent instanceof StashHelperContainer) {
            ((StashHelperContainer) parent).updateLocation();
        }
    }

    // Repack the parent window anytime the visibility of this panel changes
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        updateParent();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (stashTabColor == StashTabColor.ZERO) {
            createBorder(stashTabColor);
        }
    }

}
