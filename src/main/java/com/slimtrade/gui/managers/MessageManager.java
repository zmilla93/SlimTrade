package com.slimtrade.gui.managers;

import com.slimtrade.App;
import com.slimtrade.core.chatparser.IJoinedAreaListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.messaging.NotificationPanel;
import com.slimtrade.gui.messaging.TradeMessagePanel;
import com.slimtrade.gui.messaging.UpdateMessagePanel;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.colortheme.IThemeListener;

import javax.swing.*;
import java.awt.*;

public class MessageManager extends BasicDialog implements ITradeListener, IJoinedAreaListener, IThemeListener {

    public Point anchorPoint = new Point(800, 0);
    boolean expandUp = false;

    private static final int MESSAGE_GAP = 1;
    private final Container container;

    private GridBagConstraints gc;

    public MessageManager() {
        setBackground(new Color(0, 0, 0, 0));
        container = getContentPane();
        setLocationRelativeTo(null);

        container.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = expandUp ? new Insets(MESSAGE_GAP, 0, 0, 0) : new Insets(0, 0, MESSAGE_GAP, 0);
        setLocation(anchorPoint);
        pack();
        ColorManager.addListener(this);
        setAnchorPoint(SaveManager.overlaySaveFile.data.messageLocation);
        refreshOrder();
    }

    public void addMessage(TradeOffer tradeOffer) {
        addMessage(tradeOffer, true);
    }

    public void addUpdateMessage(boolean playSound) {
        assert (SwingUtilities.isEventDispatchThread());
        if (playSound)
            App.audioManager.playSoundPercent(SaveManager.settingsSaveFile.data.updateSound.sound, SaveManager.settingsSaveFile.data.updateSound.volume);
        UpdateMessagePanel panel = new UpdateMessagePanel();
        panel.startTimer();
        addComponent(panel);
    }

    public void addMessage(TradeOffer tradeOffer, boolean playSound) {
        assert (SwingUtilities.isEventDispatchThread());
        if (container.getComponentCount() > 20) return;
        if (!SaveManager.settingsSaveFile.data.enableIncomingMessages && tradeOffer.offerType == TradeOffer.TradeOfferType.INCOMING)
            return;
        if (!SaveManager.settingsSaveFile.data.enableOutgoingMessages && tradeOffer.offerType == TradeOffer.TradeOfferType.OUTGOING)
            return;
        if (playSound) {
            switch (tradeOffer.offerType) {
                case INCOMING:
                    App.audioManager.playSoundPercent(SaveManager.settingsSaveFile.data.incomingSound.sound, SaveManager.settingsSaveFile.data.incomingSound.volume);
                    break;
                case OUTGOING:
                    App.audioManager.playSoundPercent(SaveManager.settingsSaveFile.data.outgoingSound.sound, SaveManager.settingsSaveFile.data.outgoingSound.volume);
                    break;
                case CHAT_SCANNER:
                    App.audioManager.playSoundPercent(SaveManager.settingsSaveFile.data.chatScannerSound.sound, SaveManager.settingsSaveFile.data.chatScannerSound.volume);
                    break;
            }
        }
        TradeMessagePanel panel = new TradeMessagePanel(tradeOffer);
        panel.startTimer();
        addComponent(panel);
    }

    private void addComponent(Component component) {
        gc.gridy = expandUp ? 1000 - container.getComponentCount() : container.getComponentCount();
        setIgnoreRepaint(true);
        container.add(component, gc);
        revalidate();
        pack();
        moveToAnchor();
        setIgnoreRepaint(false);
        repaint();
    }

    public void removeMessage(NotificationPanel panel) {
        assert (SwingUtilities.isEventDispatchThread());
        setIgnoreRepaint(true);
        panel.cleanup();
        container.remove(panel);
        pack();
        moveToAnchor();
        revalidate();
        setIgnoreRepaint(false);
        repaint();
    }

    private void moveToAnchor() {
        assert (SwingUtilities.isEventDispatchThread());
        Point p = new Point(anchorPoint);
        if (expandUp && container.getComponentCount() > 0) {
            p.y -= getHeight() - container.getComponent(0).getHeight();
        }
        setLocation(p);
    }

    public void setAnchorPoint(Point point) {
        anchorPoint = point;
        moveToAnchor();
    }

    public void refreshOrder() {
        expandUp = SaveManager.overlaySaveFile.data.expandDirection == ExpandDirection.UPWARDS;
        Component[] components = container.getComponents();
        container.removeAll();
        for (Component comp : components) {
            addComponent(comp);
        }
    }

    /**
     * Closes all outgoing trades except for the panel being passed in.
     *
     * @param panel
     */
    public void quickCloseOutgoing(Component panel) {
        setIgnoreRepaint(true);
        for (int i = container.getComponentCount() - 1; i >= 0; i--) {
            Component comp = container.getComponent(i);
            if (comp instanceof TradeMessagePanel) {
                TradeOffer trade = ((TradeMessagePanel) comp).getTradeOffer();
                if (trade.offerType == TradeOffer.TradeOfferType.OUTGOING && comp != panel) {
                    container.remove(i);
                }
            }
        }
        refreshOrder();
        pack();
        moveToAnchor();
        setIgnoreRepaint(false);
        revalidate();
        repaint();
    }

    /**
     * Closes all trade offers with the same name and price as the trade offer passed in.
     *
     * @param targetOffer
     */
    public void quickCloseIncoming(TradeOffer targetOffer) {
        setIgnoreRepaint(true);
        for (int i = container.getComponentCount() - 1; i >= 0; i--) {
            Component comp = container.getComponent(i);
            if (comp instanceof TradeMessagePanel) {
                TradeOffer trade = ((TradeMessagePanel) comp).getTradeOffer();
                if (trade.offerType == TradeOffer.TradeOfferType.INCOMING
                        && trade.itemName.equals(targetOffer.itemName)
                        && trade.priceTypeString.equals(targetOffer.priceTypeString)
                        && trade.priceQuantity == targetOffer.priceQuantity) {
                    container.remove(i);
                }
            }
        }
        refreshOrder();
        pack();
        moveToAnchor();
        setIgnoreRepaint(false);
        revalidate();
        repaint();
    }

    // FIXME : This is needed to call updateUI on child components to force color changes.
    //  Would be nice to have a clear way to do this
    public void forceUpdateUI() {
        for (Component c : container.getComponents()) {
            if (c instanceof JComponent) {
                ColorManager.recursiveUpdateUI((JComponent) c);
            }
        }
    }

    @Override
    public void handleTrade(TradeOffer tradeOffer) {
        SwingUtilities.invokeLater(() -> addMessage(tradeOffer));
    }

    @Override
    public void onThemeChange() {
        revalidate();
        repaint();
    }

    @Override
    public void onJoinedArea(String playerName) {
        for (Component c : container.getComponents()) {
            if (c instanceof TradeMessagePanel) {
                TradeMessagePanel panel = (TradeMessagePanel) c;
                TradeOffer offer = panel.getTradeOffer();
                if (offer.offerType == TradeOffer.TradeOfferType.INCOMING && offer.playerName.equals(playerName)) {
                    App.audioManager.playSoundPercent(SaveManager.settingsSaveFile.data.playerJoinedAreaSound.sound, SaveManager.settingsSaveFile.data.playerJoinedAreaSound.volume);
                    panel.setPlayerJoinedArea();
                }
            }
        }
    }
}