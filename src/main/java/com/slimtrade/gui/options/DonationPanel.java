package com.slimtrade.gui.options;

import com.slimtrade.core.References;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class DonationPanel extends AbstractOptionPanel {

    private final JButton paypalButton = new JButton("PayPal");
    private final JButton patreonButton = new JButton("Patreon");

    @SuppressWarnings("SpellCheckingInspection")
    private static final String[] supporters = new String[]{
            "C. Cole",
            "J. Foster",
            "JoshMike",
            "LordPole",
            "Oskar 'Ikkiz' Kallgren",
            "S. Ghita",
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
        addComponent(createButtonPanel());
        addVerticalStrut();
        addHeader("Thank You!");
        addComponent(supporterPanel);

        addListeners();
    }

    private void addListeners() {
        paypalButton.addActionListener(e -> ZUtil.openLink(References.PAYPAL_URL));
        patreonButton.addActionListener(e -> ZUtil.openLink(References.PATREON_URL));
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        panel.add(patreonButton);
        gc.gridx++;
        gc.insets.left = 10;
        panel.add(paypalButton);
        return panel;
    }

    public JButton getButtonToFocus() {
        return patreonButton;
    }

}
