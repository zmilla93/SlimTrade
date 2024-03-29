package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.data.PatchNotesEntry;
import com.slimtrade.core.utility.MarkdownParser;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.CustomScrollPane;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.listening.IDefaultSizeAndLocation;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.updater.data.AppVersion;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.util.ArrayList;

public class PatchNotesWindow extends CustomDialog implements IDefaultSizeAndLocation {

    private final JComboBox<PatchNotesEntry> comboBox = new LimitCombo<>();
    private final JTextPane textPane = new JTextPane();

    private final JButton githubButton = new JButton("GitHub");
    private final JButton discordButton = new JButton("Discord");
    private final JButton donateButton = new JButton("Donate");

    private static final String preamble = "[This project now has a Patreon!](https://www.patreon.com/SlimTrade) If you enjoy my work, please consider supporting.\n";

    public PatchNotesWindow() {
        super("Patch Notes");
        pinButton.setVisible(false);

        // Combo
        ArrayList<PatchNotesEntry> entries = App.updateManager.getPatchNotes(App.appInfo.appVersion);
        if (entries != null && entries.size() > 0) {
            entries.get(0).text = preamble + entries.get(0).text;
            for (PatchNotesEntry entry : entries) {
                comboBox.addItem(entry);
            }
        }

        // Text Pane
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        JScrollPane scrollPane = new CustomScrollPane(textPane);

        // Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel githubWrapperPanel = new JPanel(new GridBagLayout());
        githubWrapperPanel.add(githubButton);
        buttonPanel.add(githubButton, BorderLayout.WEST);
        buttonPanel.add(discordButton, BorderLayout.CENTER);
        buttonPanel.add(donateButton, BorderLayout.EAST);

        JPanel buttonWrapperPanel = new JPanel(new BorderLayout());
        buttonWrapperPanel.add(buttonPanel, BorderLayout.EAST);

        // Controls
        JPanel controlsPanel = new JPanel(new BorderLayout());
        controlsPanel.add(buttonWrapperPanel, BorderLayout.WEST);
        controlsPanel.add(comboBox, BorderLayout.EAST);

        // Build Panel
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(controlsPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Finalize
        addListeners();
        updateSelectedPatchNotes();
        comboBox.requestFocus();

        setMinimumSize(new Dimension(400, 400));
        pack();
    }

    private void addListeners() {
        comboBox.addActionListener(e -> updateSelectedPatchNotes());
        textPane.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                ZUtil.openLink(e.getURL().toString());
            }
        });
        githubButton.addActionListener(e -> ZUtil.openLink(References.GITHUB_URL));
        discordButton.addActionListener(e -> ZUtil.openLink(References.DISCORD_INVITE));
        donateButton.addActionListener(e -> {
            FrameManager.optionsWindow.setVisible(true);
            FrameManager.optionsWindow.toFront();
            FrameManager.optionsWindow.showDonationPanel();
        });
    }

    private void updateSelectedPatchNotes() {
        PatchNotesEntry entry = (PatchNotesEntry) comboBox.getSelectedItem();
        if (entry == null) return;
        textPane.setText(getCleanPatchNotes(entry.getAppVersion(), entry.text));
        textPane.setCaretPosition(0);
    }

    private String getCleanPatchNotes(AppVersion version, String body) {
        String[] lines = body.split("(\\n|\\\\r\\\\n)");
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>SlimTrade ").append(version).append("</h1>");
        for (String s : lines) {
            if (s.toLowerCase().contains("how to install")) {
                break;
            }
            builder.append(MarkdownParser.getHtmlFromMarkdown(s));
        }
        return builder.toString();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) getRootPane().requestFocus();
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        setSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
    }

}
