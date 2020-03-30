package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.components.PanelWrapper;
import com.slimtrade.gui.enums.ExpandDirection;
import com.slimtrade.gui.enums.WindowState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageDialogManager {

    private Point anchorPoint;
    public static final Dimension DEFAULT_SIZE = new Dimension(400, 40);
    private int sizeIncrease = 0;
    private ExpandDirection expandDirection;

    private final int BUFFER_SIZE = 2;
    private final int MAX_MESSAGE_COUNT = 20;
    private static final CopyOnWriteArrayList<PanelWrapper> wrapperList = new CopyOnWriteArrayList<PanelWrapper>();
    public Dimension messageSize;

    public MessageDialogManager() {
        expandDirection = App.saveManager.overlaySaveFile.messageExpandDirection;
        int x = App.saveManager.overlaySaveFile.messageX;
        int y = App.saveManager.overlaySaveFile.messageY;
        anchorPoint = new Point(x, y);
        messageSize = new Dimension(DEFAULT_SIZE.width + App.saveManager.overlaySaveFile.messageSizeIncrease, DEFAULT_SIZE.height + App.saveManager.overlaySaveFile.messageSizeIncrease);
    }

    public void addMessage(TradeOffer trade) {
        addMessage(trade, true);
    }

    public void addMessage(TradeOffer trade, boolean playSound) {
        if (wrapperList.size() >= MAX_MESSAGE_COUNT || isDuplicateTrade(trade)) {
            return;
        }
        if (playSound) {
            trade.playSound();
        }
        final MessagePanel panel = new MessagePanel(trade, messageSize);
        final PanelWrapper wrapper = new PanelWrapper(panel, "SlimTrade Message Window");
        wrapperList.add(wrapper);
        refreshPanelLocations();
        panel.getCloseButton().addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    removeMessage(wrapperList.indexOf(wrapper));
                    refreshPanelLocations();
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    closeSimilarTrades(wrapperList.indexOf(wrapper));
                }
            }
        });
        App.eventManager.recursiveColor(wrapper);
        wrapper.setShow(true);
        if (!App.globalMouse.isGameFocused() || FrameManager.windowState != WindowState.NORMAL) {
            wrapper.setVisible(false);
        }
        FrameManager.showVisibleFrames();
        FrameManager.forceAllToTop();
    }

    public void refreshPanelLocations() {
        Point targetPoint = new Point(anchorPoint);
        for (PanelWrapper w : wrapperList) {
            w.setLocation(targetPoint);
            w.setAlwaysOnTop(false);
            w.setAlwaysOnTop(true);
            if (expandDirection == ExpandDirection.DOWN) {
                targetPoint.y += w.getHeight() + BUFFER_SIZE;
            } else {
                targetPoint.y -= w.getHeight() + BUFFER_SIZE;
            }
        }
    }

    private boolean isDuplicateTrade(TradeOffer trade) {
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msgPanel = (MessagePanel) wrapper.getPanel();
            TradeOffer tradeB = msgPanel.getTrade();
            if (TradeUtility.isDuplicateTrade(trade, tradeB)) {
                return true;
            }
        }
        return false;
    }

    public void closeTrade(TradeOffer tradeA) {
        int i = 0;
        final ArrayList<Integer> indexesToDelete = new ArrayList<Integer>();
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msg = (MessagePanel) wrapper.getPanel();
            TradeOffer tradeB = msg.trade;
            if (tradeA.equals(tradeB)) {
                this.removeMessage(i);
                break;
            }
            i++;
        }
        //TODO : Optimize
//        int max = wrapperList.size() - 1;
//        for (i = max; i >= 0; i--) {
//            if (indexesToDelete.contains(i)) {
//                removeMessage(i);
//            }
//        }
        //TODO : End Optimize
        refreshPanelLocations();
    }

    public void closeTradesByItem(String itemA) {
        int i = 0;
        final ArrayList<Integer> indexesToDelete = new ArrayList<Integer>();
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msg = (MessagePanel) wrapper.getPanel();
            TradeOffer trade = msg.trade;
            String itemB = trade.itemName;
            if (trade.messageType == MessageType.INCOMING_TRADE && itemA.equals(itemB)) {
                indexesToDelete.add(i);
            }
            i++;
        }
        //TODO : Optimize
        int max = wrapperList.size() - 1;
        for (i = max; i >= 0; i--) {
            if (indexesToDelete.contains(i)) {
                removeMessage(i);
            }
        }
        //TODO : End Optimize
        refreshPanelLocations();
    }

    private void closeSimilarTrades(int index) {
        MessagePanel msg = (MessagePanel) wrapperList.get(index).getPanel();
        TradeOffer tradeA = msg.getTrade();
//		ArrayList<int> toBeDeleted = new ArrayList<int>();
        final ArrayList<Integer> indexesToDelete = new ArrayList<Integer>();
        int i = 0;
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msgB = (MessagePanel) wrapper.getPanel();
            TradeOffer tradeB = msgB.getTrade();
            if (msg != null && msg instanceof MessagePanel) {
                if (i != index) {
                    try {
                        int checkCount = 0;
                        int check = 0;
                        if (tradeA.messageType == MessageType.INCOMING_TRADE) {
                            checkCount = 4;
                            if (tradeA.priceType.equals(tradeB.priceType)) {
                                check++;
                            }
                            if (tradeA.priceCount.equals(tradeB.priceCount)) {
                                check++;
                            }
                            if (TradeUtility.cleanItemName(tradeA.itemName).equals(TradeUtility.cleanItemName(tradeB.itemName))) {
                                check++;
                            }
                        } else if (tradeA.messageType == MessageType.OUTGOING_TRADE) {
                            checkCount = 1;
                        } else if (tradeA.messageType == MessageType.CHAT_SCANNER) {
                            checkCount = 2;
                            if (tradeA.searchName.equals(tradeB.searchName)) {
                                check++;
                            }
                        }
                        if (tradeA.messageType.equals(tradeB.messageType)) {
                            check++;
                        }
                        if (check == checkCount) {
                            indexesToDelete.add(i);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
            i++;
        }
        if (tradeA.messageType == MessageType.INCOMING_TRADE) {
            indexesToDelete.add(index);
        }
        //TODO : Optimize
        int max = wrapperList.size() - 1;
        for (i = max; i >= 0; i--) {
            if (indexesToDelete.contains(i)) {
                removeMessage(i);
            }
        }
        //TODO : End Optimize
        refreshPanelLocations();
    }

    private void removeMessage(int index) {
        MessagePanel msgPanel = (MessagePanel) wrapperList.get(index).getPanel();
        if (msgPanel.getMessageType() == MessageType.INCOMING_TRADE) {
            if (msgPanel.getStashHelper() != null) {
                // TODO :
                if (msgPanel.getStashHelper().itemHighlighter != null) {
                    msgPanel.getStashHelper().itemHighlighter.dispose();
                }
                FrameManager.stashHelperContainer.remove(msgPanel.getStashHelper());
                FrameManager.stashHelperContainer.pack();
            }
        }
        wrapperList.get(index).dispose();
        wrapperList.remove(index);
    }

    public void setExpandDirection(ExpandDirection dir) {
        this.expandDirection = dir;
    }

    public void setAnchorPoint(Point point) {
        this.anchorPoint = point;
    }

    public Point getAnchorPoint() {
        return this.anchorPoint;
    }

    public static CopyOnWriteArrayList<PanelWrapper> getDialogList() {
        return MessageDialogManager.wrapperList;
    }

    public void setPlayerJoinedArea(String username) {
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel panel = (MessagePanel) wrapper.getPanel();
            if (panel.getTrade().playerName.equals(username)) {
                if (panel.getTrade().messageType == MessageType.INCOMING_TRADE) {
                    panel.pricePanel.setBackground(ColorManager.PLAYER_JOINED_INCOMING);
                    panel.borderPanel.setBackground(ColorManager.PLAYER_JOINED_INCOMING);
                    panel.namePanel.setTextColor(ColorManager.PLAYER_JOINED_INCOMING);
                }
            }
        }
    }

    public void updateMessageColors() {
        for (PanelWrapper w : wrapperList) {
            App.eventManager.recursiveColor(w);
        }
    }

    public void setMessageIncrease(int sizeIncrease) {
        messageSize = new Dimension(DEFAULT_SIZE.width + sizeIncrease, DEFAULT_SIZE.height + sizeIncrease);
        int tempMax = 50;
        if (sizeIncrease > tempMax) sizeIncrease = tempMax;
        this.sizeIncrease = sizeIncrease;
        for (PanelWrapper p : wrapperList) {
            if (p.getPanel() instanceof MessagePanel) {
                ((MessagePanel) p.getPanel()).resizeFrames(new Dimension(DEFAULT_SIZE.width + sizeIncrease, DEFAULT_SIZE.height + sizeIncrease));
                ((MessagePanel) p.getPanel()).getCloseButton().addMouseListener(new AdvancedMouseAdapter() {
                    public void click(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            removeMessage(wrapperList.indexOf(p));
                            refreshPanelLocations();
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            closeSimilarTrades(wrapperList.indexOf(p));
                        }
                    }
                });
            }
            p.pack();

        }
        this.refreshPanelLocations();
    }

}
