package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.saving.StashTab;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.StashTabColor;
import com.slimtrade.enums.StashTabType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.ColorPanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.panels.PricePanel;
import com.slimtrade.gui.stash.helper.StashHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MessagePanel extends AbstractMessagePanel implements IColorable {

    private static final long serialVersionUID = 1L;
    private JPanel topPanel = new ColorPanel(gb);
    private JPanel bottomPanel = new ColorPanel(gb);

    protected JPanel buttonPanelTop = new ColorPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    protected JPanel buttonPanelBottom = new ColorPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

    private StashHelper stashHelper;

    // private IconButton saveToHistoryButton;
    // private IconButton waitButton;
//    private IconButton refreshButton;
//    private IconButton inviteButton;
//    private IconButton warpButton;
//    private IconButton tradeButton;
//    private IconButton thankButton;

//    private IconButton homeButton;
//    private IconButton replyButton;

    private ArrayList<IconButton> customButtonsTop = new ArrayList<IconButton>();
    private ArrayList<IconButton> customButtonsBottom = new ArrayList<IconButton>();

    // TODO Listeners?
    public MessagePanel(TradeOffer trade, Dimension size) {
        super(trade);
        buildPanel(trade, size, true);
    }

    public MessagePanel(TradeOffer trade, Dimension size, boolean makeListeners) {
        super(trade);
        buildPanel(trade, size, makeListeners);
    }

    private void buildPanel(TradeOffer trade, Dimension size, boolean makeListeners) {
        // TODO : move size stuff to super
        this.trade = trade;
        this.setMessageType(trade.messageType);

        if (trade.guildName != null && App.saveManager.saveFile.showGuildName) {
            namePanel.setText(trade.guildName + " " + trade.playerName);
        } else {
            namePanel.setText(trade.playerName);
        }

        switch (messageType) {
            case CHAT_SCANNER:
                itemPanel.setText(trade.searchMessage);
                pricePanel.setText(trade.searchName);
                break;
            case INCOMING_TRADE:
            case OUTGOING_TRADE:
                itemPanel.setText(TradeUtility.getFixedItemName(trade.itemName, trade.itemQuantity, true));
//                pricePanel.removeListener();
                pricePanel = new PricePanel(trade.priceTypeString, trade.priceCount, true);
                //TODO : PRICE PANEL
                break;
            case UNKNOWN:
                break;
            default:
                break;
        }

        calculateSizes(size);
        refreshButtons(makeListeners);
        resizeFrames();

        timerPanel.setLayout(new BorderLayout());
        timerPanel.add(timerLabel, BorderLayout.CENTER);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        container.add(topPanel, gc);
        gc.gridy = 1;
        container.add(bottomPanel, gc);
        gc.gridy = 0;
        borderPanel.add(container, gc);
        this.add(borderPanel, gc);
        // TOP PANEL
        topPanel.add(namePanel, gc);
        gc.gridx++;
        topPanel.add(pricePanel, gc);
        gc.gridx++;
        topPanel.add(buttonPanelTop, gc);

        // BOTTOM PANEL
        gc.gridx = 0;
        gc.gridy = 0;
        bottomPanel.add(timerPanel, gc);
        gc.gridx++;
        bottomPanel.add(itemPanel, gc);
        gc.gridx++;
        bottomPanel.add(buttonPanelBottom, gc);

        // Finalize
        this.startTimer();
        this.updateColor();
    }

    private void calculateSizes(Dimension size) {
        if (size.width % 2 != 0) {
            size.width++;
        }
        if (size.height % 2 != 0) {
            size.height++;
        }
        messageWidth = size.width;
        messageHeight = size.height;
        rowHeight = messageHeight / 2;
        totalWidth = messageWidth + (borderSize * 4);
        totalHeight = messageHeight + (borderSize * 4);
    }

    private void refreshButtons(boolean makeListeners) {
        // Remove Buttons
        buttonPanelTop.removeAll();
        buttonPanelBottom.removeAll();
        MacroButton[] macros = null;
        switch (messageType) {
            case INCOMING_TRADE:
                macros = App.saveManager.saveFile.incomingMacros;
                break;
            case OUTGOING_TRADE:
                macros = App.saveManager.saveFile.outgoingMacros;
                break;
            case CHAT_SCANNER:
                break;
            case UNKNOWN:
                break;
        }
        // Add New Buttons
        buttonCountTop = 1;
        buttonCountBottom = 0;
        if (macros != null) {
            for (MacroButton b : macros) {
                IconButton button = new IconButton(b.image, rowHeight);
                if (b.row == ButtonRow.TOP) {
                    buttonPanelTop.add(button);
                    buttonCountTop++;
                } else if (b.row == ButtonRow.BOTTOM) {
                    buttonPanelBottom.add(button);
                    buttonCountBottom++;
                }
                if (makeListeners) {
                    button.addMouseListener(new AdvancedMouseAdapter() {
                        @Override
                        public void click(MouseEvent e) {
                            System.out.println(e.getButton());
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                PoeInterface.runCommand(b.getCommandsLeft(), trade.playerName, TradeUtility.getFixedItemName(trade.itemName, trade.itemQuantity, true), (trade.priceCount.toString().replace(".0", "") + " " + trade.priceTypeString));
                                System.out.println(Arrays.toString(b.getCommandsLeft()));
                            } else if (e.getButton() == MouseEvent.BUTTON3) {
                                System.out.println(Arrays.toString(b.getCommandsRight()));
                            }
                            if (b.closeOnClick) {
                                FrameManager.messageManager.closeTrade(trade);
                            }
                        }
                    });
                }
            }
        }
        // Close Button
        this.setCloseButton(rowHeight);
        buttonPanelTop.add(closeButton);
    }

    public void resizeFrames(Dimension size) {
        calculateSizes(size);
        refreshButtons(true);
        resizeFrames();
        updateColor();
    }

    public void resizeFrames(Dimension size, boolean makeListeners) {
        calculateSizes(size);
        refreshButtons(makeListeners);
        resizeFrames();
    }

    protected void resizeFrames() {
        this.setPreferredSize(new Dimension(totalWidth, totalHeight));
        this.setMinimumSize(new Dimension(totalWidth, totalHeight));
        borderPanel.setPreferredSize(new Dimension(messageWidth + borderSize * 2, messageHeight + borderSize * 2));
        container.setPreferredSize(new Dimension(messageWidth, messageHeight));
        Dimension s = new Dimension(messageWidth, rowHeight);
        topPanel.setPreferredSize(s);
        bottomPanel.setPreferredSize(s);
        Dimension buttonSizeTop = new Dimension(rowHeight * buttonCountTop, rowHeight);
        Dimension buttonSizeBottom = new Dimension(rowHeight * buttonCountBottom, rowHeight);
        buttonPanelTop.setPreferredSize(buttonSizeTop);
        buttonPanelTop.setMinimumSize(buttonSizeTop);
        buttonPanelBottom.setPreferredSize(buttonSizeBottom);
        buttonPanelBottom.setMaximumSize(buttonSizeBottom);
        int nameWidth = (int) ((messageWidth - buttonSizeTop.width) * 0.7);
        int priceWidth = messageWidth - nameWidth - buttonSizeTop.width;
        int timerWidth = (int) (messageWidth * timerWeight);
        int itemWidth = messageWidth - timerWidth - buttonSizeBottom.width;

        namePanel.setPreferredSize(new Dimension(nameWidth, rowHeight));
        pricePanel.setPreferredSize(new Dimension(priceWidth, rowHeight));
        timerPanel.setPreferredSize(new Dimension(timerWidth, rowHeight));
        itemPanel.setPreferredSize(new Dimension(itemWidth, rowHeight));
    }

    public JButton getCloseButton() {
        return this.closeButton;
    }

    public TradeOffer getTrade() {
        return trade;
    }

    public void setTrade(TradeOffer trade) {
        this.trade = trade;
    }

    public StashHelper getStashHelper() {
        return stashHelper;
    }

    public void setStashHelper(StashHelper stashHelper) {
        this.stashHelper = stashHelper;
    }

    @Override
    public void updateColor() {
        super.updateColor();
        Color color;
        Color colorText;
        //MUTUAL COLORS
        this.setBackground(ColorManager.MESSAGE_BORDER);
        // Name Panel
        namePanel.setBackgroundColor(ColorManager.MESSAGE_NAME_BG);
        namePanel.backgroundHover = ColorManager.PRIMARY;
        namePanel.borderDefault = ColorManager.MESSAGE_NAME_BG;
        namePanel.borderHover = ColorManager.TEXT;
        namePanel.borderClick = ColorManager.TEXT;
        namePanel.setTextColor(ColorManager.TEXT);
        itemPanel.setBackgroundColor(ColorManager.MESSAGE_ITEM_BG);
        itemPanel.setBorderColor(ColorManager.MESSAGE_ITEM_BG);
        itemPanel.setTextColor(ColorManager.TEXT);
        pricePanel.setTextColor(ColorManager.MESSAGE_PRICE_TEXT);
        timerPanel.setTextColor(ColorManager.TEXT);
        timerPanel.setBackgroundColor(ColorManager.MESSAGE_TIMER_BG);
        timerPanel.setBorderColor(ColorManager.MESSAGE_TIMER_BG);
        switch (trade.messageType) {
            case CHAT_SCANNER:
                // TODO : Custom tooltip
                itemPanel.getLabel().setToolTipText(trade.searchMessage);
                borderPanel.setBackground(ColorManager.SCANNER_BACKGROUND);
                pricePanel.setBackground(ColorManager.SCANNER_BACKGROUND);
                break;
            case INCOMING_TRADE:
                color = StashTabColor.TWENTYSIX.getBackground();
                colorText = StashTabColor.TWENTYSIX.getForeground();
                itemPanel.backgroundHover = ColorManager.PRIMARY;
                itemPanel.borderHover = ColorManager.TEXT;
                itemPanel.borderClick = ColorManager.TEXT;
                if (trade.stashtabName != null && !trade.stashtabName.equals("")) {
                    int i = 0;
                    for (StashTab tab : App.saveManager.saveFile.stashTabs) {
                        if (tab.name.toLowerCase().equals(trade.stashtabName.toLowerCase())) {

                            if (tab.color != StashTabColor.ZERO) {
                                StashTabColor stashColor = tab.color;
                                color = stashColor.getBackground();
                                colorText = stashColor.getForeground();
                            }
                            if (tab.type == StashTabType.QUAD) {
                                trade.stashType = StashTabType.QUAD;
                            }
                            break;
                        }
                    }
                }
                stashHelper = new StashHelper(trade, color, colorText);
                stashHelper.setVisible(false);
                FrameManager.stashHelperContainer.add(stashHelper);
                borderPanel.setBackground(ColorManager.GREEN_SALE);
                pricePanel.setBackground(ColorManager.GREEN_SALE);

                break;
            case OUTGOING_TRADE:
                borderPanel.setBackground(ColorManager.RED_SALE);
                pricePanel.setBackground(ColorManager.RED_SALE);
                break;
        }
    }

}
