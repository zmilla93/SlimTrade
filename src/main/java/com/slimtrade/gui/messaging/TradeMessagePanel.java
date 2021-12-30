package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.basic.ColorPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TradeMessagePanel extends NotificationPanel {
    static int i = 7;

    private TradeOffer tradeOffer;

    JPanel playerPanel = new ColorPanel("ComboBox.background");
    JPanel pricePanel = new JPanel(new GridBagLayout());


    public TradeMessagePanel(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;

        // Player Panel

        Random rng = new Random();
        playerPanel.setLayout(new GridBagLayout());
        playerPanel.add(new JLabel(tradeOffer.playerName + ThreadLocalRandom.current().nextInt(0, 100)));
//        playerPanel.setBackground(UIManager.getColor("Menu.background"));
//        System.out.println(UIManager.get("Menu.background"));
//        System.out.println(UIManager.get("Menu.foreground"));
//        playerPanel.setBackground(UIManager.getColor("Menu.foreground"));

        // Price Panel
        pricePanel.add(new JLabel("(" + tradeOffer.priceQuantity + ")" + tradeOffer.priceTypeString));
        pricePanel.setBackground(ColorManager.GREEN_SALE);

//        panel2.add(new JLabel("c" + i));

        // Timer Panel
        JPanel timerPanel = new JPanel(new GridBagLayout());
        JLabel timerLabel = new JLabel("10s");
        timerPanel.add(timerLabel);

        // Item Panel
        JPanel itemPanel = new JPanel(new GridBagLayout());
        JLabel itemLabel = new JLabel(tradeOffer.itemName);
        itemPanel.add(itemLabel);

        // TODO : This loop is inefficient to do everytime. Should either do some caching or change save format
        ArrayList<MacroButton> topButtons = new ArrayList<>();
        ArrayList<MacroButton> bottomButtons = new ArrayList<>();
        if (tradeOffer.offerType == TradeOffer.TradeOfferType.INCOMING) {
            borderColor = ColorManager.GREEN_SALE;
            for (MacroButton button : App.saveManager.settingsSaveFile.incomingMacroButtons) {
                if (button.row == ButtonRow.TOP_ROW)
                    topButtons.add(button);
                else
                    bottomButtons.add(button);
            }
        } else if (tradeOffer.offerType == TradeOffer.TradeOfferType.OUTGOING) {
            borderColor = ColorManager.RED_SALE;
            for (MacroButton button : App.saveManager.settingsSaveFile.outgoingMacroButtons) {
                if (button.row == ButtonRow.TOP_ROW)
                    topButtons.add(button);
                else
                    bottomButtons.add(button);
            }
        }
        topMacros = topButtons.toArray(topMacros);
        bottomMacros = bottomButtons.toArray(bottomMacros);

        i++;
        addTopPanel(playerPanel, 0.8f);
        addTopPanel(pricePanel, 0.2f);

        addBottomPanel(timerPanel, 0.05f);
        addBottomPanel(itemPanel, 0.95f);

        buildPanel();

        updateUI();
    }


    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    @Override
    public void updateUI() {
        super.updateUI();
//        Color color = UIManager.getColor("Menu.foreground");
//        if (playerPanel != null && color != null) {
//            if (tradeOffer != null)
//                System.out.println("trd:" + tradeOffer.playerName);
//            playerPanel.setBackground(color);
    }

}
