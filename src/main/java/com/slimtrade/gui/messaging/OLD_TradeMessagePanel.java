package com.slimtrade.gui.messaging;

import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.basic.ColorLabel;
import com.slimtrade.gui.buttons.NotificationButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stash.StashHelperPanel;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class OLD_TradeMessagePanel extends OLD_NotificationPanel {

    private StashHelperPanel helperPanel;
    private JButton playerPanel = new NotificationButton();
    private JPanel pricePanel = new JPanel(new GridBagLayout());
    private JLabel priceLabel;
    private JButton itemPanel;

    public OLD_TradeMessagePanel(TradeOffer tradeOffer) {
        this(tradeOffer, true);
    }

    public OLD_TradeMessagePanel(TradeOffer tradeOffer, boolean createListeners) {
        super();
        this.tradeOffer = tradeOffer;
        helperPanel = FrameManager.stashHelperContainer.addHelper(tradeOffer);

        priceLabel = new ColorLabel("(" + tradeOffer.priceQuantity + ")" + tradeOffer.priceTypeString, "TextArea.background");
        itemPanel = new AdvancedButton(tradeOffer.itemName);

//        playerPanel.setBorder(null);
        // Player Panel
        Random rng = new Random();
        playerPanel.setLayout(new GridBagLayout());
        playerPanel.add(new JLabel(tradeOffer.playerName));

        // Price Panel

        pricePanel.add(priceLabel);

        // Item Panel

//        JPanel itemPanel = new JPanel(new GridBagLayout());
//        JLabel itemLabel = new JLabel(tradeOffer.itemName);
//        itemPanel.add(itemLabel);

        // TODO : This loop is inefficient to do everytime. Should either do some caching or change save format
        ArrayList<MacroButton> topButtons = new ArrayList<>();
        ArrayList<MacroButton> bottomButtons = new ArrayList<>();
        if (tradeOffer.offerType == TradeOffer.TradeOfferType.INCOMING) {
            borderColor = ColorManager.GREEN_SALE;
            pricePanel.setBackground(ColorManager.GREEN_SALE);
            if (SaveManager.settingsSaveFile.data.incomingMacroButtons != null) {
                for (MacroButton button : SaveManager.settingsSaveFile.data.incomingMacroButtons) {
                    if (button.row == ButtonRow.TOP_ROW)
                        topButtons.add(button);
                    else
                        bottomButtons.add(button);
                }
            }
        } else if (tradeOffer.offerType == TradeOffer.TradeOfferType.OUTGOING) {
            borderColor = ColorManager.RED_SALE;
            pricePanel.setBackground(ColorManager.RED_SALE);
            if (SaveManager.settingsSaveFile.data.outgoingMacroButtons != null) {
                for (MacroButton button : SaveManager.settingsSaveFile.data.outgoingMacroButtons) {
                    if (button.row == ButtonRow.TOP_ROW)
                        topButtons.add(button);
                    else
                        bottomButtons.add(button);
                }
            }

        }
        topMacros = topButtons.toArray(topMacros);
        bottomMacros = bottomButtons.toArray(bottomMacros);
        addTopPanel(playerPanel, 0.8f);
        addTopPanel(pricePanel, 0.2f);
        addBottomPanel(getTimerPanel(), 0.05f);
        addBottomPanel(itemPanel, 0.95f);
        buildPanel(createListeners);
        addListeners();
    }

    private void addListeners() {
        itemPanel.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    helperPanel.setVisible(true);
                    FrameManager.stashHelperContainer.refresh();
                }
            }
        });
//        getCloseButton().addMouseListener(new AdvancedMouseListener() {
//            @Override
//            public void click(MouseEvent e) {
//                if(e.getButton() == MouseEvent.BUTTON1){
//                    FrameManager.stashGridWindow.remove(helperPanel);
//                }
//            }
//        });
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    @Override
    public void cleanup() {
        super.cleanup();
        FrameManager.stashHelperContainer.remove(helperPanel);
        FrameManager.stashHelperContainer.refresh();
    }
}
