package com.slimtrade.gui.popups;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.updating.ReleaseData;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomScrollPane;
import com.slimtrade.gui.custom.CustomTextPane;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class PatchNotesWindow extends JFrame implements IColorable {

    private final JPanel container = new JPanel(new GridBagLayout());
    private final JTextPane textPane = new CustomTextPane();
    private final JScrollPane scrollPane = new CustomScrollPane(container);
    private final JPanel bufferPanel = new JPanel(new GridBagLayout());
    private final JLabel comboLabel = new CustomLabel("Version Selection");
    private final JComboBox<String> comboBox = new CustomCombo<>();

    private ReleaseData currentData;

    public PatchNotesWindow() {
        setTitle("SlimTrade - Patch Notes");
        setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/default/tagx64.png"))).getImage());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setSize(400, 400);
        textPane.setEditable(false);
        textPane.setOpaque(false);
        textPane.setContentType("text/html");
        textPane.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                TradeUtility.openLink(e.getURL());
            }
        });
        ArrayList<ReleaseData> releases = App.updateManager.getReleaseData();
        PatchNotesWindow local = this;
        comboBox.addActionListener(e -> {
            for (ReleaseData data : releases) {
                if (comboBox.getSelectedItem() != null && comboBox.getSelectedItem().equals(data.tag)) {
                    currentData = data;
                    textPane.setText(data.getColorPatchNotes(ColorManager.TEXT));
                    revalidate();
                    pack();
                    FrameManager.fitWindowToScreen(local);
                }
            }
        });

        // Components
        JPanel innerPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JPanel comboPanel = new JPanel(new GridBagLayout());
        innerPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        comboPanel.setOpaque(false);
        JButton githubButton = new BasicButton("Github");
        JButton discordButton = new BasicButton("Discord");
        JButton donateButton = new ConfirmButton("Donate");
        githubButton.addActionListener(e -> {
            TradeUtility.openLink(References.GITHUB);
        });
        discordButton.addActionListener(e -> {
            TradeUtility.openLink(References.DISCORD);
        });
        donateButton.addActionListener(e -> {
            TradeUtility.openLink(References.PAYPAL);
        });

        // Build UI
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        int i = 10;
        gc.insets = new Insets(i, i, i, i);
        bufferPanel.add(textPane, gc);
        gc.insets = new Insets(0, 0, 0, 0);

        // Button Panel
        buttonPanel.add(githubButton, gc);
        gc.insets.left = 20;
        gc.gridx++;
        buttonPanel.add(discordButton, gc);
        gc.gridx++;
        buttonPanel.add(donateButton, gc);
        gc.gridx = 0;
        gc.insets.left = 0;

        // Combo Panel
        comboPanel.add(comboLabel, gc);
        gc.gridx++;
        gc.insets.left = 20;
        comboPanel.add(comboBox, gc);
        gc.gridx = 0;
        gc.insets.left = 0;

        // Full UI
        innerPanel.add(buttonPanel, gc);
        gc.insets.top = 20;
        gc.gridy++;
        innerPanel.add(comboPanel, gc);
        gc.gridy++;
        innerPanel.add(bufferPanel, gc);
        i = 40;
        gc.gridy = 0;
        gc.insets = new Insets(i, i, i, i);
        container.add(innerPanel, gc);
        setMaximumSize(TradeUtility.screenSize);

//        releases = App.updateManager.getReleaseData();
        for (ReleaseData data : releases) {
            System.out.println("REL : " + data.tag);
            if (data.tag.toLowerCase().contains("pre")) {
                continue;
            }
            comboBox.addItem(data.tag);
        }

        int index = 8;
//        currentData = releases.get(index);
//        textPane.setText(releases.get(index).getColorPatchNotes(ColorManager.TEXT));
        textPane.setCaretPosition(0);
        revalidate();
        pack();
        FrameManager.fitWindowToScreen(this);
    }

    @Override
    public void updateColor() {
        if (currentData != null) {
            textPane.setText(currentData.getColorPatchNotes(ColorManager.TEXT));
        }
        getContentPane().setBackground(ColorManager.LOW_CONTRAST_1);
        container.setBackground(ColorManager.LOW_CONTRAST_1);
        bufferPanel.setBackground(ColorManager.BACKGROUND);
        bufferPanel.setBorder(ColorManager.BORDER_TEXT);
        textPane.setForeground(Color.ORANGE);
    }
}
