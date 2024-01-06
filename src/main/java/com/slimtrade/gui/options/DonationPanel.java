package com.slimtrade.gui.options;

import com.slimtrade.core.References;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class DonationPanel extends AbstractOptionPanel {

    private final JButton donateButton = new JButton("Donate with PayPal");

    @SuppressWarnings("SpellCheckingInspection")
    private static final String[] supporters = new String[]{
            "Alex 'LordPole' Pope",
            "Charles Cole",
            "Jason Foster",
            "JoshMike",
            "Oskar 'Ikkiz' Kallgren",
            "Sorin Ghita",
    };

    public DonationPanel() {
        JPanel supporterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        for (String name : supporters) {
            supporterPanel.add(new JLabel(name), gc);
            gc.gridy++;
        }

        // Setup panel
        addHeader("Donating");
        addComponent(new JLabel("If you enjoy using this app, please consider supporting me! Supporters will be added here."));
        addComponent(new JLabel("Optionally include a message with a username, or that you'd like to remain anonymous."));
        addVerticalStrutSmall();
        addComponent(donateButton);
        addVerticalStrut();
        addHeader("Thank You!");
        addComponent(supporterPanel);

        donateButton.addActionListener(e -> ZUtil.openLink(References.PAYPAL_URL));
    }

    public JButton getDonateButton() {
        return donateButton;
    }

}
