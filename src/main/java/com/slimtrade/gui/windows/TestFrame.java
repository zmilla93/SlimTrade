package com.slimtrade.gui.windows;

import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.messaging.NotificationPanel;
import com.slimtrade.gui.messaging.TradeMessagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TestFrame extends JFrame {

    JPanel contentPanel = new JPanel();

    public TestFrame() {
        setContentPane(contentPanel);
        setFocusable(false);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);

//        ColorManager.addFrame(this);
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(new IconButton(DefaultIcon.TAG, 30));


        NotificationPanel panel = new NotificationPanel();
        contentPanel.add(panel);
        NotificationPanel trade = new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false);
        contentPanel.add(trade);
        BufferedImage img = null;
        CurrencyType currency = CurrencyType.getCurrencyType("Orbe exalté");
        CurrencyType exalt = CurrencyType.getCurrencyType("Orbe exalté");
        try {
            assert currency != null;
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource(currency.getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(CurrencyType.getIconPath("Chaos Orb"));
        JLabel currencyIcon = new JLabel();
        IconButton exaltButton = new IconButton(() -> {
            assert exalt != null;
            return exalt.getPath();
        });
        currencyIcon.setIcon(icon);
        if (img != null) currencyIcon.setIcon(new ImageIcon(img));
        contentPanel.add(currencyIcon);
        contentPanel.add(exaltButton);
//        contentPanel.add(new IconLabel(exalt.getPath()));
//        contentPanel.add(new IconLabel(exalt.getPath(), 25));
//        contentPanel.add(new IconLabel(exalt.getPath(), 40));
//        contentPanel.add(new IconLabel(exalt.getPath()));
//        contentPanel.add(new IconLabel(exalt.getPath()));

        JPanel fontPanel = new JPanel();
        fontPanel.setLayout(new BoxLayout(fontPanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane(fontPanel);
        scrollPane.setPreferredSize(new Dimension(200, 500));
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (String s : ge.getAvailableFontFamilyNames()) {
            Font font = new Font(s, Font.PLAIN, 12);
            if (!font.canDisplay('a')) continue;
            JLabel label = new JLabel(font.getName());
            label.setFont(font);
            fontPanel.add(label);
        }
        contentPanel.add(scrollPane);


        pack();
//        setSize(500, 500);
        setVisible(true);
//        setVisible(true);

    }


}
