package com.slimtrade.gui.windows;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.TutorialPanel;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.messaging.TradeMessagePanel;

import javax.swing.*;
import java.awt.*;

public class TutorialWindow extends CustomDialog {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final JButton previousButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");
    private final JLabel pageLabel = new JLabel("Page 1/1");

    private int activePanel = 1;

    public TutorialWindow() {
        super("Tutorial");
        pinButton.setVisible(false);

        contentPanel.setLayout(new BorderLayout());
        JPanel borderPanel = new JPanel(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(borderPanel, 10);

        // Tutorial Panels
        cardPanel.add(createTradeMessagePanel(), "1");
        cardPanel.add(createStashHelperPanel(), "2");
        cardPanel.add(createTradeHistoryPanel(), "3");
        cardPanel.add(createChatScannerPanel(), "4");
        cardPanel.add(createGettingStartedPanel(), "5");
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        // Button Panel
        JPanel pageLabelWrapper = new JPanel(new GridBagLayout());
        pageLabelWrapper.add(pageLabel);
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(previousButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        buttonPanel.add(pageLabelWrapper, BorderLayout.CENTER);

        // Content Panel
        borderPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(borderPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setMinimumSize(null);
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        addListeners();
        updatePageLabel();
    }

    private void addListeners() {
        previousButton.addActionListener(e -> {
            if (activePanel <= 1) return;
            activePanel--;
            cardLayout.show(cardPanel, Integer.toString(activePanel));
            updatePageLabel();
        });
        nextButton.addActionListener(e -> {
            if (activePanel >= cardPanel.getComponentCount()) return;
            activePanel++;
            cardLayout.show(cardPanel, Integer.toString(activePanel));
            updatePageLabel();
        });
        closeButton.addActionListener(e -> {
            FrameManager.optionsWindow.setVisible(true);
            FrameManager.optionsWindow.toFront();
        });
    }

    private void updatePageLabel() {
        pageLabel.setText("Page " + activePanel + "/" + cardPanel.getComponentCount());
    }

    ///////////////////////
    //  Tutorial Panels  //
    ///////////////////////

    private JPanel createFeatureOverviewPanel() {
        TutorialPanel panel = new TutorialPanel();
        panel.addHeader("Feature Overview");
        panel.addLabel("-Streamlined trading UI");
        panel.addLabel("-Incredibly customizable");
        return panel;
    }

    private JPanel createTradeMessagePanel() {
        TutorialPanel panel = new TutorialPanel();
        panel.addHeader("Trade Messages");
        panel.addLabel("Popups are created when a trade message is sent or received.");
        panel.addLabel("Buttons can be customized in the options window.");
        panel.addLabel("Incoming trades are green, outgoing trades are red.");
        panel.addVerticalStrut();
        panel.addComponent(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false));
        panel.addComponent(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE), false));
        return panel;
    }

    private JPanel createStashHelperPanel() {
        TutorialPanel panel = new TutorialPanel();
        panel.addHeader("Stash Helper");
        panel.addLabel("Incoming trades cause an info window to appear above your stash.");
        panel.addLabel("Hover to outline an item, left click to search the name, or right click to close.");
        panel.addLabel("Stash tab names can be assigned a color or marked as quad in the options.");
        return panel;
    }

    private JPanel createTradeHistoryPanel() {
        TutorialPanel panel = new TutorialPanel();
        panel.addHeader("Trade History");
        panel.addLabel("The history window can be used to reopen recent trade popups.");
        panel.addLabel("History is built from the client file, so trades will show up even if SlimTrade wasn't running.");
        return panel;
    }

    private JPanel createChatScannerPanel() {
        TutorialPanel panel = new TutorialPanel();
        panel.addLabel("The chat scanner can be used to search for custom phrases in chat.");
        panel.addLabel("Multiple search presets can be made with custom responses for each entry.");
        panel.addComponent(new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.CHAT_SCANNER_MESSAGE), false));
        return panel;
    }

    private JPanel createGettingStartedPanel() {
        TutorialPanel panel = new TutorialPanel();
        panel.addHeader("Getting Started");
        panel.addLabel("Access SlimTrade from the menubar button, or from the system tray.");
        panel.addLabel("Open the options menu to start customizing!");
        return panel;
    }

}
