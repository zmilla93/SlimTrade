package com.slimtrade.gui.options;

import com.slimtrade.core.References;
import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.BasicIconLabel;
import com.slimtrade.modules.updater.ZLogger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DonationPanel extends AbstractOptionPanel {

    private final JButton paypalButton = new JButton("PayPal");
    private final JButton patreonButton = new JButton("Patreon");
    private final JPanel supporterPanel = new JPanel(new GridBagLayout());
    private final ArrayList<Supporter> supporters;

    public DonationPanel() {
        supporters = parseSupporters();

        // Setup panel
        addHeader("Donating");
        addComponent(new JLabel("If you enjoy using this app, please consider supporting me! Supporters will be added here."));
        addComponent(new JLabel("Contact me if you want your display named changed, or if you'd like to remain anonymous."));
        addVerticalStrutSmall();
        addComponent(createButtonPanel());
        addVerticalStrut();
        addHeader("Thank You!");
        addComponent(supporterPanel);

        buildSupporterPanel();

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

    private void buildSupporterPanel() {
        supporterPanel.removeAll();
        GridBagConstraints gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        for (Supporter supporter : supporters) {
            JLabel label = new BasicIconLabel(CurrencyType.getCurrencyType(supporter.currencyType));
            label.setText(supporter.name);
            supporterPanel.add(label, gc);
            gc.gridy++;
        }
    }

    private ArrayList<Supporter> parseSupporters() {
        ArrayList<Supporter> supporters = new ArrayList<>();
        BufferedReader reader = ZUtil.getBufferedReader("/text/supporters.txt");
        String currentCurrency = null;
        try {
            while (reader.ready()) {
                String line = reader.readLine().trim();
                if (line.startsWith("=")) continue;
                else if (line.startsWith("#")) currentCurrency = getCurrencyType(line);
                else if (line.length() > 0) {
                    if (currentCurrency == null) ZLogger.err("[Donation Panel] Currency not set!");
                    supporters.add(new Supporter(line, currentCurrency));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(supporters);
        return supporters;
    }

    private String getCurrencyType(String line) {
        if (line.contains("T1")) return "Orb of Alchemy";
        else if (line.contains("T2")) return "Chaos Orb";
        else if (line.contains("T3")) return "Divine Orb";
        else if (line.contains("PayPal")) return "Regal Orb";
        else if (line.contains("Testers")) return "Blessed Orb";
        ZLogger.err("[Donation Panel] Invalid currency line: " + line);
        return null;
    }

    public JButton getButtonToFocus() {
        return patreonButton;
    }

    private static class Supporter implements Comparable<Supporter> {
        public final String name;
        public final String currencyType;

        public Supporter(String name, String currencyType) {
            this.name = name;
            this.currencyType = currencyType;
        }

        @Override
        public int compareTo(@NotNull DonationPanel.Supporter other) {
            return name.compareTo(other.name);
        }

        @Override
        public String toString() {
            return name + " : " + currencyType;
        }
    }

}
