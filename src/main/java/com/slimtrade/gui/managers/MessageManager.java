package com.slimtrade.gui.managers;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.chatparser.IJoinedAreaListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.data.IgnoreItem;
import com.slimtrade.core.data.PlayerMessage;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;
import com.slimtrade.gui.messaging.*;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.colortheme.IThemeListener;
import com.slimtrade.modules.colortheme.IUIResizeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This window displays all popup notifications.
 *
 * @see NotificationPanel
 * @see TradeMessagePanel
 * @see com.slimtrade.gui.messaging.ChatScannerMessagePanel
 * @see UpdateMessagePanel
 */
public class MessageManager extends BasicDialog implements ITradeListener, IJoinedAreaListener, IThemeListener, IUIResizeListener {

    private final Container container;
    private final JPanel messageContainer;
    private final GridBagConstraints gc;
    private static final int MESSAGE_GAP = 1;
    private Point anchorPoint = new Point(800, 0);

    // Expanding
    private boolean expandUp = false;
    private boolean expanded;
    private final ExpandPanel expandPanel;

    // Opacity
    private boolean mouseHover = false;
    private final Timer fadeTimer;
    private float fadedOpacity;
    private float targetOpacity;
    private final Runnable fadeRunnable;
    private boolean fading;
    private static final float OPACITY_STEP = 0.02f;

    private static final Executor executor = Executors.newSingleThreadExecutor();

    public MessageManager() {
        setBackground(ColorManager.TRANSPARENT);
        container = getContentPane();
        container.setLayout(new BorderLayout());
        messageContainer = new JPanel(new GridBagLayout());
        messageContainer.setBackground(ColorManager.TRANSPARENT);
        container.add(messageContainer, BorderLayout.CENTER);

        // Init GridBagLayout
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = expandUp ? new Insets(MESSAGE_GAP, 0, 0, 0) : new Insets(0, 0, MESSAGE_GAP, 0);

        // Expand Panel
        expandPanel = new ExpandPanel();
        fadeTimer = new Timer(0, e -> {
            fadeToOpacity(fadedOpacity);
            stopFadeTimer();
        });

        // Fade Runnable
        fadeRunnable = () -> {
            fading = true;
            while (fading && getOpacity() != targetOpacity) {
                float curOpacity = getOpacity();
                float step = targetOpacity > curOpacity ? OPACITY_STEP : -OPACITY_STEP;
                float newOpacity = curOpacity + step;
                if (Math.abs(targetOpacity - newOpacity) <= OPACITY_STEP) newOpacity = targetOpacity;
                final float finalOpacity = TradeUtil.floatWithinRange(newOpacity, 0, 1);
                SwingUtilities.invokeLater(() -> setOpacity(finalOpacity));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ColorManager.addThemeListener(this);
        ColorManager.addFontListener(this);
        setAnchorPoint(SaveManager.overlaySaveFile.data.messageLocation);
        refreshFadeData();
        refresh();
        addListeners();
    }

    private void addListeners() {
        expandPanel.getButton().setAllowedMouseButtons(1);
        expandPanel.getButton().addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                expanded = !expanded;
                if (expanded) expandMessages();
                else collapseMessages();
            }
        });

    }

    // FIXME: Should merge this with addMessage
    public void addUpdateMessage(boolean playSound) {
        assert (SwingUtilities.isEventDispatchThread());
        if (playSound)
            AudioManager.playSoundPercent(SaveManager.settingsSaveFile.data.updateSound.sound, SaveManager.settingsSaveFile.data.updateSound.volume);
        UpdateMessagePanel panel = new UpdateMessagePanel();
        addMessageMutual(panel);
    }

    public void addMessage(TradeOffer tradeOffer) {
        addMessage(tradeOffer, true);
    }

    public void addMessage(TradeOffer tradeOffer, boolean playSound) {
        assert (SwingUtilities.isEventDispatchThread());
        setIgnoreRepaint(true);
        if (messageContainer.getComponentCount() > 20) return;
        if (!SaveManager.settingsSaveFile.data.enableIncomingMessages && tradeOffer.offerType == TradeOfferType.INCOMING_TRADE)
            return;
        if (!SaveManager.settingsSaveFile.data.enableOutgoingMessages && tradeOffer.offerType == TradeOfferType.OUTGOING_TRADE)
            return;
        if (playSound) {
            switch (tradeOffer.offerType) {
                case INCOMING_TRADE:
                    SoundComponent sound = AudioManager.getPriceThresholdSound(tradeOffer.priceName, (int) Math.floor(tradeOffer.priceQuantity));
                    if (sound == null) {
                        AudioManager.playSoundPercent(SaveManager.settingsSaveFile.data.incomingSound.sound, SaveManager.settingsSaveFile.data.incomingSound.volume);
                    } else {
                        AudioManager.playSoundComponent(sound);
                    }
                    break;
                case OUTGOING_TRADE:
                    AudioManager.playSoundPercent(SaveManager.settingsSaveFile.data.outgoingSound.sound, SaveManager.settingsSaveFile.data.outgoingSound.volume);
                    break;
                case CHAT_SCANNER_MESSAGE:
                    AudioManager.playSoundPercent(SaveManager.settingsSaveFile.data.chatScannerSound.sound, SaveManager.settingsSaveFile.data.chatScannerSound.volume);
                    break;
            }
        }
        TradeMessagePanel panel = new TradeMessagePanel(tradeOffer);
        addMessageMutual(panel);
        panel.resizeStrut();
    }

    public void addScannerMessage(ChatScannerEntry entry, PlayerMessage playerMessage) {
        addScannerMessage(entry, playerMessage, true);
    }

    public void addScannerMessage(ChatScannerEntry entry, PlayerMessage playerMessage, boolean playSound) {
//        panel.startTimer();
        ChatScannerMessagePanel panel = new ChatScannerMessagePanel(entry, playerMessage);
        if (playSound) AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.chatScannerSound);
        addMessageMutual(panel);
    }

    private void addMessageMutual(Component component) {
        addComponent(component);
        if (SaveManager.settingsSaveFile.data.collapseMessages && !expanded && messageContainer.getComponentCount() >= SaveManager.settingsSaveFile.data.messagesBeforeCollapse) {
            component.setVisible(false);
            expandPanel.setVisible(true);
        }
        if (messageContainer.getComponentCount() == 1) {
            setOpacity(1);
            startFadeTimer();
        }
        revalidate();
        refresh();
    }

    private void recheckMessageVisibility() {
        for (int i = 0; i < messageContainer.getComponentCount(); i++) {
            Component c = messageContainer.getComponent(i);
            if (SaveManager.settingsSaveFile.data.collapseMessages && !expanded && i >= SaveManager.settingsSaveFile.data.messagesBeforeCollapse)
                c.setVisible(false);
            else c.setVisible(true);
        }
        if (SaveManager.settingsSaveFile.data.collapseMessages && messageContainer.getComponentCount() > SaveManager.settingsSaveFile.data.messagesBeforeCollapse) {
            expandPanel.setVisible(true);
        } else {
            expandPanel.setVisible(false);
            expanded = false;
        }
        updateExpandText();
    }

    private void addComponent(Component component) {
        gc.gridy = expandUp ? 1000 - messageContainer.getComponentCount() : messageContainer.getComponentCount();
        messageContainer.add(component, gc);
    }

    public void removeMessage(NotificationPanel panel) {
        assert (SwingUtilities.isEventDispatchThread());
        setIgnoreRepaint(true);
        panel.cleanup();
        messageContainer.remove(panel);
        refresh();
    }

    private void moveToAnchor() {
        assert (SwingUtilities.isEventDispatchThread());
        Point p = new Point(anchorPoint);
        if (expandUp && messageContainer.getComponentCount() > 0) {
            p.y -= getHeight() - messageContainer.getComponent(0).getHeight();
        }
        setLocation(p);
    }

    public void setAnchorPoint(Point point) {
        anchorPoint = point;
        moveToAnchor();
    }

    private void refreshOrder() {
        expandUp = SaveManager.overlaySaveFile.data.expandDirection == ExpandDirection.UPWARDS;
        Component[] components = messageContainer.getComponents();
        messageContainer.removeAll();
        for (Component comp : components) {
            addComponent(comp);
        }
        container.remove(expandPanel);
        if (expandUp) container.add(expandPanel, BorderLayout.NORTH);
        else container.add(expandPanel, BorderLayout.SOUTH);
//        refresh();
    }

    /**
     * Closes all outgoing trades except for the panel being passed in.
     *
     * @param panel
     */
    public void quickCloseOutgoing(Component panel) {
        setIgnoreRepaint(true);
        for (int i = messageContainer.getComponentCount() - 1; i >= 0; i--) {
            Component comp = messageContainer.getComponent(i);
            if (comp instanceof TradeMessagePanel) {
                TradeOffer trade = ((TradeMessagePanel) comp).getTradeOffer();
                if (trade.offerType == TradeOfferType.OUTGOING_TRADE && comp != panel) {
                    messageContainer.remove(i);
                }
            }
        }
        refresh();
    }

    /**
     * Closes all trade offers with the same name and price as the trade offer passed in.
     *
     * @param targetOffer
     */
    public void quickCloseIncoming(TradeOffer targetOffer) {
        setIgnoreRepaint(true);
        for (int i = messageContainer.getComponentCount() - 1; i >= 0; i--) {
            Component comp = messageContainer.getComponent(i);
            if (comp instanceof TradeMessagePanel) {
                TradeOffer trade = ((TradeMessagePanel) comp).getTradeOffer();
                if (trade.offerType == TradeOfferType.INCOMING_TRADE
                        && trade.itemName.equals(targetOffer.itemName)
                        && trade.priceName.equals(targetOffer.priceName)
                        && trade.priceQuantity == targetOffer.priceQuantity) {
                    messageContainer.remove(i);
                }
            }
        }
        refresh();
    }

    /**
     * Closes all incoming trades that match the given criteria.
     *
     * @param item
     */
    public void quickCloseIgnore(IgnoreItem item) {
        setIgnoreRepaint(true);
        for (int i = messageContainer.getComponentCount() - 1; i >= 0; i--) {
            Component comp = messageContainer.getComponent(i);
            if (comp instanceof TradeMessagePanel) {
                TradeOffer trade = ((TradeMessagePanel) comp).getTradeOffer();
                if (trade.offerType != TradeOfferType.INCOMING_TRADE) continue;
                if (item.matchType == MatchType.EXACT_MATCH) {
                    if (trade.itemName.equals(item.itemName)) {
                        messageContainer.remove(i);
                    }
                } else if (item.matchType == MatchType.CONTAINS_TEXT) {
                    if (trade.itemNameLower.contains(item.itemNameLower)) {
                        messageContainer.remove(i);
                    }
                }
            }
        }
        refresh();
    }

    private void expandMessages() {
        for (Component c : messageContainer.getComponents()) {
            c.setVisible(true);
        }
        updateExpandText();
        refresh();
    }

    private void collapseMessages() {
        for (int i = 0; i < messageContainer.getComponentCount(); i++) {
            if (i >= SaveManager.settingsSaveFile.data.messagesBeforeCollapse) {
                messageContainer.getComponent(i).setVisible(false);
            }
        }
        updateExpandText();
        refresh();
    }

    private void updateExpandText() {
        if (expanded) {
            expandPanel.setText("Collapse Messages");
        } else {
            int hiddenMessageCount = messageContainer.getComponentCount() - SaveManager.settingsSaveFile.data.messagesBeforeCollapse;
            String suffix = hiddenMessageCount > 1 ? "s" : "";
            expandPanel.setText("+" + hiddenMessageCount + " More Message" + suffix);
        }
    }

    private void startFadeTimer() {
        if (!SaveManager.settingsSaveFile.data.fadeMessages) return;
        fadeTimer.stop();
        fadeTimer.restart();
        fadeTimer.start();
    }

    private void stopFadeTimer() {
        fadeTimer.stop();
    }

    public void refreshFadeData() {
        fadeTimer.setInitialDelay(Math.round(SaveManager.settingsSaveFile.data.secondsBeforeFading * 1000));
        fadedOpacity = SaveManager.settingsSaveFile.data.fadedOpacity / 100f;
    }

    private void fadeToOpacity(float opacity) {
        targetOpacity = opacity;
        executor.execute(fadeRunnable);
    }

    // FIXME : This is needed to call updateUI on child components to force color changes.
    //  Would be nice to have a clear way to do this
    public void forceUpdateUI() {
        for (Component c : messageContainer.getComponents()) {
            if (c instanceof JComponent) {
                ColorManager.recursiveUpdateUI((JComponent) c);
            }
        }
    }

    /**
     * Makes sure the MessageManager is in the correct location, in the right order, and all messages have correct visibility.
     * It then revalidates and repaints the frame.
     */
    public void refresh() {
        assert (SwingUtilities.isEventDispatchThread());
        recheckMessageVisibility();
        refreshOrder();
        pack();
        moveToAnchor();
        setIgnoreRepaint(false);
        revalidate();
        repaint();
    }

    //
    // Interfaces
    //

    @Override
    public void handleTrade(TradeOffer tradeOffer) {
        SwingUtilities.invokeLater(() -> addMessage(tradeOffer));
    }

    @Override
    public void onThemeChange() {
        revalidate();
        repaint();
    }

    public void checkMouseHover(Point p) {
        // Increasing the bounds slightly prevents spam when message is on the edge of the screen.
        Rectangle bounds = getBounds();
        bounds.width += 4;
        bounds.height += 4;
        if (bounds.contains(p)) {
            if (!mouseHover) {
                mouseHover = true;
                fadeToOpacity(1);
                stopFadeTimer();
            }
        } else {
            if (mouseHover) {
                mouseHover = false;
                startFadeTimer();
            }
        }
    }

    @Override
    public void onJoinedArea(String playerName) {
        for (Component c : messageContainer.getComponents()) {
            if (c instanceof TradeMessagePanel) {
                TradeMessagePanel panel = (TradeMessagePanel) c;
                TradeOffer offer = panel.getTradeOffer();
                if (offer.offerType == TradeOfferType.INCOMING_TRADE && offer.playerName.equals(playerName)) {
                    AudioManager.playSoundPercent(SaveManager.settingsSaveFile.data.playerJoinedAreaSound.sound, SaveManager.settingsSaveFile.data.playerJoinedAreaSound.volume);
                    panel.setPlayerJoinedArea();
                }
            }
        }
    }

    @Override
    public void onFontSizeChanged() {
        // Do Nothing
    }

    @Override
    public void onIconSizeChanged() {
        for (Component c : messageContainer.getComponents()) {
            if (c instanceof NotificationPanel) {
                ((NotificationPanel) c).resizeStrut();
            }
        }
        pack();
    }
}