package com.slimtrade.gui.managers;

import com.slimtrade.App;
import com.slimtrade.core.chatparser.ITradeListener;
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

public class MessageManager extends BasicDialog implements ITradeListener, IThemeListener {

    public Point anchorPoint = new Point(800, 0);
    boolean expandUp = false;

    private int MESSAGE_GAP = 1;
    private Container container;

    private GridBagConstraints gc;

    public MessageManager() {
        setBackground(new Color(0, 0, 0, 0));
        container = getContentPane();
        setLocationRelativeTo(null);

        container.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, MESSAGE_GAP, 0);
        setLocation(anchorPoint);
        pack();
        ColorManager.addListener(this);
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

    private void addComponent(JComponent component) {
        gc.gridy = container.getComponentCount();
        container.add(component, gc);
        revalidate();
        pack();
        adjustPosition();
        repaint();
    }

    public void removeMessage(NotificationPanel panel) {
        assert (SwingUtilities.isEventDispatchThread());
        panel.cleanup();
        container.remove(panel);
        pack();
        adjustPosition();
        container.revalidate();
        System.gc();
    }

    private void adjustPosition() {
        assert (SwingUtilities.isEventDispatchThread());
        Point p = new Point(anchorPoint);
        if (expandUp) {
            p.y -= getHeight();
        }
        setLocation(p);
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

}