package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.saving.StashTab;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.StashTabColor;
import com.slimtrade.enums.StashTabType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.panels.PricePanel;
import com.slimtrade.gui.scanner.ScannerMessage;
import com.slimtrade.gui.stash.helper.StashHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MessagePanel extends AbstractMessagePanel implements IColorable {

    private static final long serialVersionUID = 1L;
    private JPanel topPanel = new JPanel(gb);
    private JPanel bottomPanel = new JPanel(gb);

    protected JPanel buttonPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    protected JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

    public StashHelper stashHelper;

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

        if (trade.guildName != null && App.saveManager.settingsSaveFile.showGuildName) {
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
                pricePanel = new PricePanel(trade.priceTypeString, trade.priceQuantity, true);
                //TODO : PRICE PANEL
                break;
            case NOTIFICATION:
                itemPanel.setText(trade.itemName);
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
        if (messageType != MessageType.NOTIFICATION) {
            this.startTimer();
        }
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
        totalWidth = messageWidth + (BORDER_SIZE * 4);
        totalHeight = messageHeight + (BORDER_SIZE * 4);
    }

    private void refreshButtons(boolean makeListeners) {
        // Remove Buttons
        buttonPanelTop.removeAll();
        buttonPanelBottom.removeAll();
        MacroButton[] macros = null;
        switch (messageType) {
            case INCOMING_TRADE:
                macros = App.saveManager.settingsSaveFile.incomingMacros;
                break;
            case OUTGOING_TRADE:
                macros = App.saveManager.settingsSaveFile.outgoingMacros;
                break;
            case CHAT_SCANNER:
                for (ScannerMessage msg : App.saveManager.scannerSaveFile.messages) {
                    if (msg.name == trade.searchName) {
                        macros = msg.macroButtons;
                        break;
                    }
                }
                break;
            case UNKNOWN:
                break;
        }
        // Preset Macros
        if (makeListeners) {
            namePanel.addMouseListener(new AdvancedMouseAdapter() {
                @Override
                public void click(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
//                        PoeInterface.runCommand(new String[]{"/whois {player}"}, trade.playerName, "", "");
                        PoeInterface.runCommand(new String[]{"/whois {player}"}, trade.playerName, "", "", "", null);
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        PoeInterface.paste("@" + trade.playerName + " ", false);
//                        PoeInterface.runCommand("", trade.playerName, TradeUtility.getFixedItemName(trade.itemName, trade.itemQuantity, true), (trade.priceQuantity.toString().replace(".0", "") + " " + trade.priceTypeString));
                    }
                }
            });
            if (messageType == MessageType.INCOMING_TRADE) {
                itemPanel.addMouseListener(new AdvancedMouseAdapter() {
                    @Override
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            stashHelper.setVisible(true);
                            FrameManager.stashHelperContainer.pack();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            FrameManager.ignoreItemWindow.setItem(trade.itemName);
                            FrameManager.ignoreItemWindow.pack();
                            FrameManager.centerFrame(FrameManager.ignoreItemWindow);
                            ColorManager.recursiveColor(FrameManager.ignoreItemWindow);
                            FrameManager.ignoreItemWindow.setVisible(true);
                        }
                    }
                });
            }
        }
        // Custom Macros
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
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                PoeInterface.runCommand(b.getCommandsLeft(), trade.playerName, TradeUtility.getFixedItemName(trade.itemName, trade.itemQuantity, true), (trade.priceQuantity.toString().replace(".0", "") + " " + trade.priceTypeString), trade.sentMessage, trade.messageType);
                            } else if (e.getButton() == MouseEvent.BUTTON3) {
                                PoeInterface.runCommand(b.getCommandsRight(), trade.playerName, TradeUtility.getFixedItemName(trade.itemName, trade.itemQuantity, true), (trade.priceQuantity.toString().replace(".0", "") + " " + trade.priceTypeString), trade.sentMessage, trade.messageType);
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
        borderPanel.setPreferredSize(new Dimension(messageWidth + BORDER_SIZE * 2, messageHeight + BORDER_SIZE * 2));
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

        if (trade.messageType == MessageType.NOTIFICATION) {
            namePanel.setPreferredSize(new Dimension(nameWidth + priceWidth, rowHeight));
            pricePanel.setPreferredSize(new Dimension(0, rowHeight));
            itemPanel.setPreferredSize(new Dimension(itemWidth + timerWidth, rowHeight));
            timerPanel.setPreferredSize(new Dimension(0, rowHeight));
        } else {
            namePanel.setPreferredSize(new Dimension(nameWidth, rowHeight));
            pricePanel.setPreferredSize(new Dimension(priceWidth, rowHeight));
            itemPanel.setPreferredSize(new Dimension(itemWidth, rowHeight));
            timerPanel.setPreferredSize(new Dimension(timerWidth, rowHeight));
        }


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
        namePanel.borderDefault = ColorManager.MESSAGE_NAME_BG;
        if (messageType != MessageType.NOTIFICATION) {
            namePanel.backgroundHover = ColorManager.PRIMARY;
            namePanel.borderHover = ColorManager.TEXT;
            namePanel.borderClick = ColorManager.TEXT;
        }
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
                    for (StashTab tab : App.saveManager.settingsSaveFile.stashTabs) {
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
            case NOTIFICATION:
                borderPanel.setBackground(ColorManager.PURPLE_UPDATE);
        }
    }

}
