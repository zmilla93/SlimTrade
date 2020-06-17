package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.saving.SaveFile;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.HideableDialog;
import com.slimtrade.gui.components.PanelWrapper;
import com.slimtrade.gui.enums.ExpandDirection;
import com.slimtrade.gui.enums.WindowState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageDialogManager {

    private Point anchorPoint;
    public static final Dimension DEFAULT_SIZE = new Dimension(400, 40);
//    private int sizeIncrease = 0;
//    private ExpandDirection expandDirection;

    private final int BUFFER_SIZE = 2;
    private final int MAX_MESSAGE_COUNT = 20;
    private static final CopyOnWriteArrayList<PanelWrapper> wrapperList = new CopyOnWriteArrayList<PanelWrapper>();
    public Dimension messageSize;
    private final ExpandPanel expandPanel = new ExpandPanel();
    private boolean expanded = false;
    private Rectangle bounds = new Rectangle(0, 0, 0, 0);

    private SaveFile saveFile = App.saveManager.saveFile;

    // Opacity Variables
    private float targetOpacity = 1f;
    private float opacity = 1f;
    private final float OPACITY_STEP = 0.02F;
    private Timer fadeTimer;
    private boolean faded = false;
    private boolean fading = false;
    private ExecutorService fadeExecutor = Executors.newSingleThreadExecutor();
    private Runnable fadeTask = () -> {
        fading = true;
        while (FrameManager.messageManager.isFading() && FrameManager.messageManager.messageCount() > 0) {
            SwingUtilities.invokeLater(() -> FrameManager.messageManager.stepOpacity());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fading = false;
    };

    public MessageDialogManager() {
        int x = App.saveManager.overlaySaveFile.messageX;
        int y = App.saveManager.overlaySaveFile.messageY;
        anchorPoint = new Point(x, y);
        messageSize = new Dimension(DEFAULT_SIZE.width + App.saveManager.overlaySaveFile.messageSizeIncrease, DEFAULT_SIZE.height + App.saveManager.overlaySaveFile.messageSizeIncrease);
        expandPanel.setSize(getTotalMessageSize().width, expandPanel.getHeight());
        expandPanel.getLabelPanel().addMouseListener(new AdvancedMouseAdapter() {
            @Override
            public void click(MouseEvent e) {
                expanded = !expanded;
                refreshPanelLocations();
            }
        });

    }

    public Rectangle getBounds() {
        return this.bounds;
    }


    // Add Messages

    public void addMessage(TradeOffer trade) {
        addMessage(trade, true);
    }

    public void addMessage(TradeOffer trade, boolean playSound) {
        // TODO : enable dupe check
        // Ignore Duplicates
//        if (wrapperList.size() >= MAX_MESSAGE_COUNT || isDuplicateTrade(trade)) {
//            return;
//        }
        if (wrapperList.size() >= MAX_MESSAGE_COUNT) {
            return;
        }
        if (playSound) {
            trade.playSound();
        }
        // Init Panel
        final MessagePanel panel = new MessagePanel(trade, messageSize);
        final PanelWrapper wrapper = new PanelWrapper(panel, "SlimTrade Message Window");
        wrapperList.add(wrapper);
        addCloseButtonListener(wrapper);
        ColorManager.recursiveColor(wrapper);
        wrapper.setShow(true);

        // Hide message if game isn't focused
        if (!App.globalMouse.isGameFocused() || FrameManager.windowState != WindowState.NORMAL) {
            wrapper.setVisible(false);
        }

        // Hide message if collapsed
        if (saveFile.collapseExcessiveMessages && wrapperList.size() > saveFile.messageCountBeforeCollapse) {
            wrapper.setShow(false);
        }

        // Run Fade Timer
        if (saveFile.fadeAfterDuration && wrapperList.size() == 1) {
            if (saveFile.secondsBeforeFading == 0) {
                setFaded(true);
                opacity = (float) saveFile.fadeOpacityPercent / 100;
                setOpacity((float) saveFile.fadeOpacityPercent / 100);
            } else {
                runOpacityTimer();
            }
        } else {
            wrapper.setOpacity(opacity);
        }
        refreshPanelLocations();
        if (App.globalMouse.isGameFocused() && FrameManager.windowState == WindowState.NORMAL) {
            FrameManager.showVisibleFrames();
            FrameManager.forceAllToTop();
        }
    }

    // Opacity Stuff
    public int messageCount() {
        return wrapperList.size();
    }

    public boolean isFading() {
        if (faded && opacity > targetOpacity) {
            return true;
        } else return !faded && opacity < targetOpacity;
    }

    public void setTargetOpacity(float opacity) {
        this.targetOpacity = TradeUtility.floatWithinRange(opacity, 0.1f, 1f);
        fade();
    }

    public void setFaded(boolean state) {
        this.faded = state;
    }

    public void runOpacityTimer() {
        fadeTimer = new Timer((int) (App.saveManager.saveFile.secondsBeforeFading * 1000), e -> {
            setFaded(true);
            float opacity = (float) App.saveManager.saveFile.fadeOpacityPercent / 100;
            setTargetOpacity(opacity);
        });
        fadeTimer.setRepeats(false);
        fadeTimer.start();
    }

    public void stopTimer() {
        if (fadeTimer != null) {
            fadeTimer.stop();
        }
    }

    private void setOpacity(float opacity) {
        for (PanelWrapper w : wrapperList) {
            w.setOpacity(opacity);
        }
        expandPanel.setOpacity(opacity);
    }

    public void stepOpacity() {
        if (faded && opacity > targetOpacity) {
            opacity = TradeUtility.floatWithinRange(opacity - OPACITY_STEP, 0f, 1f);
            setOpacity(opacity);
        } else if (!faded && opacity < targetOpacity) {
            opacity = TradeUtility.floatWithinRange(opacity + OPACITY_STEP, 0f, 1f);
            setOpacity(opacity);
        }
    }

    // Redraw all panels

    public void refreshPanelLocations() {
        Point targetPoint = new Point(anchorPoint);
        int i = 0;
        int shownMessages = 0;
        // Set location for all messages
        for (PanelWrapper w : wrapperList) {
            w.setLocation(targetPoint);
            w.setAlwaysOnTop(false);
            w.setAlwaysOnTop(true);
            if (saveFile.collapseExcessiveMessages && i >= saveFile.messageCountBeforeCollapse && !expanded) {
                w.setShow(false);
            } else {
                if (App.saveManager.overlaySaveFile.messageExpandDirection == ExpandDirection.DOWN) {
                    targetPoint.y += w.getHeight() + BUFFER_SIZE;
                } else {
                    targetPoint.y -= w.getHeight() + BUFFER_SIZE;
                }
                w.setShow(true);
                shownMessages++;
            }
            i++;
        }
        // Set location of collapse bar
        if (App.saveManager.overlaySaveFile.messageExpandDirection == ExpandDirection.UP) {
            targetPoint.y += getTotalMessageSize().height;
            targetPoint.y -= expandPanel.getHeight();
        }
        expandPanel.setLocation(targetPoint);
        if (saveFile.collapseExcessiveMessages && wrapperList.size() > saveFile.messageCountBeforeCollapse) {
            expandPanel.updateMessage(wrapperList.size(), expanded);
            expandPanel.setShow(true);
        } else {
            expandPanel.setShow(false);
            expanded = false;
        }

        // Calculate Bounds
        if (wrapperList.size() == 0) {
            bounds = new Rectangle(0, 0, 0, 0);
            return;
        }
        int x;
        int y;
        int width = getTotalMessageSize().width;
        int height;
        if (App.saveManager.overlaySaveFile.messageExpandDirection == ExpandDirection.DOWN) {
            height = shownMessages * getTotalMessageSize().height + ((shownMessages - 1) * BUFFER_SIZE);
            if (expandPanel.visible) {
                height += expandPanel.getHeight() + BUFFER_SIZE;
            }
            bounds = new Rectangle(anchorPoint.x, anchorPoint.y, width, height);
        } else {
            int tempY = ((shownMessages - 1) * getTotalMessageSize().height + ((shownMessages - 1) * BUFFER_SIZE));
            height = (shownMessages * getTotalMessageSize().height + ((shownMessages - 1) * BUFFER_SIZE));
            if (expandPanel.visible) {
                height += expandPanel.getHeight() + BUFFER_SIZE;
                tempY += (expandPanel.getHeight() + BUFFER_SIZE);
            }
            bounds = new Rectangle(anchorPoint.x, anchorPoint.y - tempY, width, height);
        }
    }

    private boolean isDuplicateTrade(TradeOffer tradeA) {
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msgPanel = wrapper.getPanel();
            TradeOffer tradeB = msgPanel.getTrade();
            if (TradeUtility.isMatchingTrades(tradeA, tradeB)) {
                return true;
            }
        }
        return false;
    }

    public void closeTrade(TradeOffer tradeA) {
        int i = 0;
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msg = wrapper.getPanel();
            TradeOffer tradeB = msg.trade;
            if (tradeA.equals(tradeB)) {
                this.removeMessage(i);
                break;
            }
            i++;
        }
        refreshPanelLocations();
    }

    public void closeTradesByItem(String itemA) {
        int i = 0;
        final ArrayList<Integer> indexesToDelete = new ArrayList<>();
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msg = wrapper.getPanel();
            TradeOffer trade = msg.trade;
            String itemB = trade.itemName;
            if (trade.messageType == MessageType.INCOMING_TRADE && itemA.equals(itemB)) {
                indexesToDelete.add(i);
            }
            i++;
        }
        int max = wrapperList.size() - 1;
        for (i = max; i >= 0; i--) {
            if (indexesToDelete.contains(i)) {
                removeMessage(i);
            }
        }
        refreshPanelLocations();
    }

    private void closeSimilarTrades(int index) {
        MessagePanel msg = wrapperList.get(index).getPanel();
        TradeOffer tradeA = msg.getTrade();
        final ArrayList<Integer> indexesToDelete = new ArrayList<>();
        int i = 0;
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel msgB = wrapper.getPanel();
            TradeOffer tradeB = msgB.getTrade();
            if (tradeA.messageType != tradeB.messageType) {
                i++;
                continue;
            }
            if (i != index) {
                // Incoming Messages
                if (tradeA.messageType == MessageType.INCOMING_TRADE) {
                    int check = 0;
                    int totalCheckCount = 3;
                    if (tradeA.priceTypeString == null || tradeB.priceTypeString == null) {
                        if (tradeA.priceTypeString == null && tradeB.priceTypeString == null) {
                            check++;
                        }
                    } else if ((tradeA.poePriceType == null && tradeB.poePriceType == null) ||
                            ((tradeA.poePriceType != null && tradeB.poePriceType != null) && tradeA.poePriceType.equals(tradeB.poePriceType)))
                    {
                        check++;
                    }
                    if (tradeA.priceQuantity.equals(tradeB.priceQuantity)) {
                        check++;
                    }
                    if (TradeUtility.cleanItemName(tradeA.itemName).equals(TradeUtility.cleanItemName(tradeB.itemName))) {
                        check++;
                    }
                    if (check == totalCheckCount) {
                        indexesToDelete.add(i);
                    }
                }
                // Outgoing Messages
                else if (tradeA.messageType == MessageType.OUTGOING_TRADE) {
                    indexesToDelete.add(i);
                }
                // Chat Scanner Messages
                else if (tradeA.messageType == MessageType.CHAT_SCANNER) {
                    if (tradeA.searchName.equals(tradeB.searchName)) {
                        indexesToDelete.add(i);
                    }
                }
            }
            i++;
        }
        if (tradeA.messageType == MessageType.INCOMING_TRADE) {
            indexesToDelete.add(index);
        }
        for (i = wrapperList.size() - 1; i >= 0; i--) {
            if (indexesToDelete.contains(i)) {
                removeMessage(i);
            }
        }
        refreshPanelLocations();
    }

    private void removeMessage(int index) {
        MessagePanel msgPanel = wrapperList.get(index).getPanel();
        if (msgPanel.getMessageType() == MessageType.INCOMING_TRADE) {
            if (msgPanel.getStashHelper() != null) {
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

//    public void setExpandDirection(ExpandDirection dir) {
//        this.expandDirection = dir;
//    }

    public void setAnchorPoint(Point point) {
        this.anchorPoint = point;
    }

    public void forceAllToTop() {
        for (HideableDialog d : wrapperList) {
            if (d.isVisible()) {
                d.setAlwaysOnTop(false);
                d.setAlwaysOnTop(true);
            }
        }
        if (expandPanel.isVisible()) {
            expandPanel.setAlwaysOnTop(false);
            expandPanel.setAlwaysOnTop(true);
        }
    }

    public void showAll() {
        for (HideableDialog d : wrapperList) {
            if (d.visible) {
                d.setVisible(true);
            }
        }
        if (saveFile.collapseExcessiveMessages && wrapperList.size() > saveFile.messageCountBeforeCollapse && expandPanel.visible) {
            expandPanel.setVisible(true);
            expandPanel.repaint();
        }
    }

    public void hideAll() {
        for (HideableDialog d : wrapperList) {
            d.setVisible(false);
        }
        expandPanel.setVisible(false);
    }


    public void setPlayerJoinedArea(String username) {
        for (PanelWrapper wrapper : wrapperList) {
            MessagePanel panel = wrapper.getPanel();
            if (panel.getTrade().playerName.equals(username)) {
                if (panel.getTrade().messageType == MessageType.INCOMING_TRADE) {
                    panel.pricePanel.setBackground(ColorManager.PLAYER_JOINED_INCOMING);
                    panel.borderPanel.setBackground(ColorManager.PLAYER_JOINED_INCOMING);
                    panel.namePanel.setTextColor(ColorManager.PLAYER_JOINED_INCOMING);
                    panel.itemPanel.setTextColor(ColorManager.PLAYER_JOINED_INCOMING);
                    panel.timerPanel.setTextColor(ColorManager.PLAYER_JOINED_INCOMING);
                    AudioManager.play(saveFile.playerJoinedSound);
                }
            }
        }
    }

    public void updateMessageColors() {
        for (PanelWrapper w : wrapperList) {
            ColorManager.recursiveColor(w);
        }
        ColorManager.recursiveColor(expandPanel);
    }

    public void setMessageIncrease(int sizeIncrease) {
        expandPanel.setSize(getTotalMessageSize().width, expandPanel.getHeight());
        messageSize = new Dimension(DEFAULT_SIZE.width + sizeIncrease, DEFAULT_SIZE.height + sizeIncrease);
        int tempMax = 80;
        if (sizeIncrease > tempMax) sizeIncrease = tempMax;
        for (PanelWrapper w : wrapperList) {
            MessagePanel p = w.getPanel();
            p.resizeFrames(new Dimension(DEFAULT_SIZE.width + sizeIncrease, DEFAULT_SIZE.height + sizeIncrease));
            addCloseButtonListener(w);
            w.pack();
        }
        this.refreshPanelLocations();
    }

    /**
     * Returns message size, excluding border size. Use this for creating new message panels.
     *
     * @return Message Size EXCLUDING border
     */
    public static Dimension getMessageSize() {
        return new Dimension(DEFAULT_SIZE.width + App.saveManager.overlaySaveFile.messageSizeIncrease, DEFAULT_SIZE.height + App.saveManager.overlaySaveFile.messageSizeIncrease);
    }

    /**
     * Returns message size, including border size. Use this for calculations.
     *
     * @return Message size INCLUDING border
     */
    public static Dimension getTotalMessageSize() {
        return new Dimension(DEFAULT_SIZE.width + App.saveManager.overlaySaveFile.messageSizeIncrease + AbstractMessagePanel.BORDER_SIZE * 4, DEFAULT_SIZE.height + App.saveManager.overlaySaveFile.messageSizeIncrease + AbstractMessagePanel.BORDER_SIZE * 4);
    }

    public TradeOffer getFirstTrade() {
        if (wrapperList.size() > 0) {
            return wrapperList.get(0).getPanel().trade;
        }
        return null;
    }

    public void showStashHelper(String message, MessageType type) {
        if (type != MessageType.INCOMING_TRADE) {
            return;
        }
        for (PanelWrapper w : wrapperList) {
            TradeOffer trade = w.getPanel().trade;
            if (trade.messageType == MessageType.INCOMING_TRADE && trade.sentMessage.equals(message)) {
                w.getPanel().stashHelper.setVisible(true);
                FrameManager.stashHelperContainer.pack();
                break;
            }
        }
    }

    private void addCloseButtonListener(PanelWrapper wrapper) {
        wrapper.panel.getCloseButton().addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    removeMessage(wrapperList.indexOf(wrapper));
                    refreshPanelLocations();
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    closeSimilarTrades(wrapperList.indexOf(wrapper));
                }
            }
        });
    }

    private void fade() {
        if (!fading) {
            fadeExecutor.execute(fadeTask);
        }
    }

}
