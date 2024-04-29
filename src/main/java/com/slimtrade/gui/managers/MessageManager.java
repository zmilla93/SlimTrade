package com.slimtrade.gui.managers;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.chatparser.IJoinedAreaListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.data.PlayerMessage;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.hotkeys.HotkeyData;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;
import com.slimtrade.gui.components.CustomTabbedPane;
import com.slimtrade.gui.messaging.*;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;
import com.slimtrade.modules.updater.ZLogger;

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
 * @see ChatScannerMessagePanel
 * @see UpdateMessagePanel
 */
public class MessageManager extends BasicDialog implements ITradeListener, IJoinedAreaListener, IFontChangeListener, ISaveListener {

    private final GridBagConstraints gc;
    private static final int MESSAGE_GAP = 1;
    private final JPanel messageContainer;
    private final JPanel messageWrapperPanel;
    private final CustomTabbedPane tabbedPane = new CustomTabbedPane();
    private final CardLayout cardLayout = new CardLayout();
    private boolean currentlyUsingTabs;

    // Tabs
    private static final String DEFAULT_KEY = "DEFAULT";
    private static final String TAB_KEY = "TABS";

    // Expanding
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

    // Thread for handling opacity
    private static final Executor executor = Executors.newSingleThreadExecutor();

    public MessageManager() {
        setBackground(ThemeManager.TRANSPARENT);

        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(UIManager.getColor("Panel.background"));
        messageContainer = new JPanel(new GridBagLayout());
        messageContainer.setBackground(ThemeManager.TRANSPARENT);
        messageWrapperPanel = new JPanel(new BorderLayout());
        messageWrapperPanel.add(messageContainer, BorderLayout.CENTER);
        messageWrapperPanel.setBackground(ThemeManager.TRANSPARENT);

        contentPanel.add(messageWrapperPanel, DEFAULT_KEY);
        contentPanel.add(tabbedPane, TAB_KEY);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Init GridBagLayout
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = isExpandUp() ? new Insets(MESSAGE_GAP, 0, 0, 0) : new Insets(0, 0, MESSAGE_GAP, 0);
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;

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
                    //noinspection BusyWait
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        setMinimumSize(null);
        currentlyUsingTabs = SaveManager.settingsSaveFile.data.useMessageTabs;
        setDisplayMode(SaveManager.settingsSaveFile.data.useMessageTabs);
        refreshFadeData();
        refresh();
        addListeners();
        ThemeManager.addFontListener(this);
        SaveManager.settingsSaveFile.addListener(this);
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
        tabbedPane.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    int index = tabbedPane.indexAtLocation(e.getX(), e.getY());
                    if (index < 0) return;
                    removeMessage((NotificationPanel) tabbedPane.getComponentAt(index));
                }
            }
        });
    }

    // FIXME: Should merge this with addMessage
    public void addUpdateMessage(boolean playSound) {
        assert (SwingUtilities.isEventDispatchThread());
        if (playSound) AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.updateSound);
        UpdateMessagePanel panel = new UpdateMessagePanel();
        addMessageMutual(panel);
    }

    public void addMessage(TradeOffer tradeOffer) {
        addMessage(tradeOffer, true);
    }

    public void addMessage(TradeOffer tradeOffer, boolean playSound) {
        addMessage(tradeOffer, playSound, false);
    }

    public void addMessage(TradeOffer tradeOffer, boolean playSound, boolean force) {
        assert (SwingUtilities.isEventDispatchThread());
        setIgnoreRepaint(true);
        if (getMessageCount() > 20) return;
        if (!force) {
            if (!SaveManager.settingsSaveFile.data.enableIncomingTrades && tradeOffer.offerType == TradeOfferType.INCOMING_TRADE)
                return;
            if (!SaveManager.settingsSaveFile.data.enableOutgoingTrades && tradeOffer.offerType == TradeOfferType.OUTGOING_TRADE)
                return;
        }
        if (playSound) {
            switch (tradeOffer.offerType) {
                case INCOMING_TRADE:
                    SoundComponent sound = AudioManager.getPriceThresholdSound(tradeOffer.priceName, (int) Math.floor(tradeOffer.priceQuantity));
                    if (sound == null) {
                        AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.incomingSound);
                    } else {
                        AudioManager.playSoundComponent(sound);
                    }
                    break;
                case OUTGOING_TRADE:
                    AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.outgoingSound);
                    break;
                case CHAT_SCANNER_MESSAGE:
                    AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.chatScannerSound);
                    break;
            }
        }
        TradeMessagePanel panel = new TradeMessagePanel(tradeOffer);
        addMessageMutual(panel);
//        panel.resizeStrut();
    }

    public void addScannerMessage(ChatScannerEntry entry, PlayerMessage playerMessage) {
        addScannerMessage(entry, playerMessage, true);
    }

    public void addScannerMessage(ChatScannerEntry entry, PlayerMessage playerMessage, boolean playSound) {
        ChatScannerMessagePanel panel = new ChatScannerMessagePanel(entry, playerMessage);
        if (playSound) AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.chatScannerSound);
        addMessageMutual(panel);
    }

    private void addMessageMutual(NotificationPanel panel) {
        addPanel(panel);
        if (getMessageCount() == 1) {
            setOpacity(1);
            startFadeTimer();
        }
        revalidate();
        refresh();
    }

    private void recheckMessageCollapse() {
        if (SaveManager.settingsSaveFile.data.useMessageTabs) return;
        for (int i = 0; i < messageContainer.getComponentCount(); i++) {
            Component comp = messageContainer.getComponent(i);
            if (SaveManager.settingsSaveFile.data.collapseMessages && !expanded && i >= SaveManager.settingsSaveFile.data.messageCountBeforeCollapse)
                comp.setVisible(false);
            else comp.setVisible(true);
        }
        if (SaveManager.settingsSaveFile.data.collapseMessages && messageContainer.getComponentCount() > SaveManager.settingsSaveFile.data.messageCountBeforeCollapse) {
            expandPanel.setVisible(true);
        } else {
            expandPanel.setVisible(false);
            expanded = false;
        }
        updateExpandText();
    }

    private void addPanel(NotificationPanel panel) {
        if (SaveManager.settingsSaveFile.data.useMessageTabs) {
            tabbedPane.addTab(Integer.toString(tabbedPane.getTabCount() + 1), panel);
        } else {
            gc.gridy = isExpandUp() ? 9999 - messageContainer.getComponentCount() : messageContainer.getComponentCount();
            messageContainer.add(panel, gc);
        }
    }

    public void removeMessage(NotificationPanel panel) {
        assert (SwingUtilities.isEventDispatchThread());
        setIgnoreRepaint(true);
        panel.cleanup();
        messageContainer.remove(panel);
        tabbedPane.remove(panel);
        refresh();
    }

    private void refreshOrder() {
        if (SaveManager.settingsSaveFile.data.useMessageTabs) return;
        Component[] components = messageContainer.getComponents();
        messageContainer.removeAll();
        for (Component comp : components) {
            addPanel((NotificationPanel) comp);
        }
        messageWrapperPanel.remove(expandPanel);
        if (isExpandUp()) messageWrapperPanel.add(expandPanel, BorderLayout.NORTH);
        else messageWrapperPanel.add(expandPanel, BorderLayout.SOUTH);
    }

    /**
     * Closes all outgoing trades except for the panel being passed in.
     */
    public void quickCloseOutgoing(NotificationPanel panel) {
        setIgnoreRepaint(true);
        for (int i = getMessageCount() - 1; i >= 0; i--) {
            Component comp = getMessageComponent(i);
            if (!(comp instanceof TradeMessagePanel)) continue;
            TradeMessagePanel otherPanel = (TradeMessagePanel) comp;
            TradeOffer trade = otherPanel.getTradeOffer();
            if (trade.offerType == TradeOfferType.OUTGOING_TRADE && comp != panel) removeMessage(otherPanel);
        }
        refresh();
    }

    /**
     * Closes all trade offers with the same name and price as the trade offer passed in.
     */
    public void quickCloseIncoming(TradeOffer targetOffer) {
        setIgnoreRepaint(true);
        for (int i = getMessageCount() - 1; i >= 0; i--) {
            Component comp = getMessageComponent(i);
            if (!(comp instanceof TradeMessagePanel)) continue;
            TradeMessagePanel panel = (TradeMessagePanel) comp;
            TradeOffer trade = panel.getTradeOffer();
            if (trade.offerType == TradeOfferType.INCOMING_TRADE
                    && trade.itemName.equals(targetOffer.itemName)
                    && trade.priceName.equals(targetOffer.priceName)
                    && trade.priceQuantity == targetOffer.priceQuantity) {
                removeMessage(panel);
            }
        }
        refresh();
    }

    /**
     * Closes all incoming trades that match the given criteria.
     */
    public void quickCloseIgnore(IgnoreItemData item) {
        setIgnoreRepaint(true);
        for (int i = getMessageCount() - 1; i >= 0; i--) {
            Component comp = getMessageComponent(i);
            if (!(comp instanceof TradeMessagePanel)) continue;
            TradeMessagePanel panel = (TradeMessagePanel) comp;
            TradeOffer trade = ((TradeMessagePanel) comp).getTradeOffer();
            if (trade.offerType != TradeOfferType.INCOMING_TRADE) continue;
            if (item.matchType == MatchType.EXACT_MATCH) {
                if (trade.itemName.equals(item.itemName)) removeMessage(panel);
            } else if (item.matchType == MatchType.CONTAINS_TEXT) {
                if (trade.itemNameLower.contains(item.itemNameLower())) removeMessage(panel);
            }
        }
        refresh();
    }

    public void closeOldestTrade() {
        int componentCount = getMessageCount();
        if (componentCount < 1) return;
        NotificationPanel panel = getMessageComponent(0);
        removeMessage(panel);
    }

    private void expandMessages() {
        for (Component comp : messageContainer.getComponents()) {
            comp.setVisible(true);
        }
        updateExpandText();
        refresh();
    }

    private void collapseMessages() {
        for (int i = 0; i < messageContainer.getComponentCount(); i++) {
            if (i >= SaveManager.settingsSaveFile.data.messageCountBeforeCollapse) {
                messageContainer.getComponent(i).setVisible(false);
            }
        }
        updateExpandText();
        refresh();
    }

    public void changeMessageTab(int change) {
        assert SwingUtilities.isEventDispatchThread();
        if (change != -1 && change != 1) {
            ZLogger.err("Changing message tab expects either -1 or 1.");
            return;
        }
        if (!SaveManager.settingsSaveFile.data.useMessageTabs) return;
        int selectedTab = tabbedPane.getSelectedIndex();
        if (selectedTab < 0) return;
        int nextTab = selectedTab + change;
        if (nextTab < 0 || nextTab >= tabbedPane.getTabCount()) return;
        tabbedPane.setSelectedIndex(nextTab);
    }

    private void updateExpandText() {
        if (expanded) {
            expandPanel.setText("Collapse Messages");
        } else {
            int hiddenMessageCount = messageContainer.getComponentCount() - SaveManager.settingsSaveFile.data.messageCountBeforeCollapse;
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

    private Component[] getMessageComponents() {
        return SaveManager.settingsSaveFile.data.useMessageTabs ? tabbedPane.getTabbedComponents() : messageContainer.getComponents();
    }

    private NotificationPanel getMessageComponent(int index) {
        return (NotificationPanel) (SaveManager.settingsSaveFile.data.useMessageTabs ? tabbedPane.getComponentAt(index) : messageContainer.getComponent(index));
    }

    private int getMessageCount() {
        return SaveManager.settingsSaveFile.data.useMessageTabs ? tabbedPane.getTabCount() : messageContainer.getComponentCount();
    }

    /**
     * Makes sure the MessageManager is in the correct location, in the right order, and all messages have correct visibility.
     */
    public void refresh() {
        assert (SwingUtilities.isEventDispatchThread());
        for (Component component : messageContainer.getComponents()) {
            ((NotificationPanel) component).updateSize();
        }
        if (SaveManager.settingsSaveFile.data.useMessageTabs) {
            setVisible(tabbedPane.getTabCount() != 0);
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setTitleAt(i, Integer.toString(i + 1));
                Component comp = tabbedPane.getComponentAt(i);
                ((NotificationPanel) comp).updateSize();
            }
        } else {
            setVisible(true);
        }
        expandPanel.updateSize();
        recheckMessageCollapse();
        refreshOrder();
        revalidate();
        pack();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.messageLocation, SaveManager.overlaySaveFile.data.messageExpandDirection);
        repaint();
    }

    public void checkMouseHover(Point p) {
        if (getBufferedBounds().contains(p)) {
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

    public void checkHotkey(HotkeyData hotkeyData) {
        if (getMessageCount() == 0) return;
        if (SaveManager.settingsSaveFile.data.useMessageTabs) {
            NotificationPanel panel = (NotificationPanel) tabbedPane.getSelectedComponent();
            panel.checkHotkeys(hotkeyData);
        } else {
            NotificationPanel panel = (NotificationPanel) messageContainer.getComponent(0);
            panel.checkHotkeys(hotkeyData);
        }
    }

    private boolean isExpandUp() {
        return SaveManager.overlaySaveFile.data.messageExpandDirection == ExpandDirection.UPWARDS;
    }

    private void setDisplayMode(boolean useTabs) {
        Component[] components;
        if (currentlyUsingTabs) components = tabbedPane.getTabbedComponents();
        else components = messageContainer.getComponents();
        messageContainer.removeAll();
        tabbedPane.removeAll();

        for (Component comp : components) {
            addPanel((NotificationPanel) comp);
        }

        contentPanel.setOpaque(useTabs);
        if (useTabs) {
            cardLayout.show(contentPanel, TAB_KEY);
        } else {
            cardLayout.show(contentPanel, DEFAULT_KEY);
        }
        refresh();
        revalidate();
        repaint();
        currentlyUsingTabs = SaveManager.settingsSaveFile.data.useMessageTabs;
    }

    @Override
    public void handleTrade(TradeOffer tradeOffer) {
        SwingUtilities.invokeLater(() -> addMessage(tradeOffer));
    }

    @Override
    public void onJoinedArea(String playerName) {
        for (Component comp : getMessageComponents()) {
            if (!(comp instanceof TradeMessagePanel)) continue;
            TradeMessagePanel panel = ((TradeMessagePanel) comp);
            TradeOffer offer = panel.getTradeOffer();
            if (offer.offerType == TradeOfferType.INCOMING_TRADE && offer.playerName.equals(playerName)) {
                AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.playerJoinedAreaSound);
                panel.setPlayerJoinedArea();
            }
        }
    }

    @Override
    public void onFontChanged() {
        for (Component comp : getMessageComponents()) {
//            ((NotificationPanel) comp).resizeStrut();
        }
        refresh();
    }

    @Override
    public void onSave() {
        setDisplayMode(SaveManager.settingsSaveFile.data.useMessageTabs);
    }

}