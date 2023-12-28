package com.slimtrade.gui.windows;

import com.slimtrade.core.data.PatchNotesEntry;
import com.slimtrade.core.utility.MarkdownParser;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.CustomScrollPane;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.util.ArrayList;

public class PatchNotesWindow extends CustomDialog {

    private final JComboBox<PatchNotesEntry> comboBox = new JComboBox<>();
    private final JTextPane textPane = new JTextPane();

    private final JButton githubButton = new JButton("GitHub");
    private final JButton discordButton = new JButton("Discord");
    private final JButton donateButton = new JButton("Donate");

    public PatchNotesWindow() {
        super("Patch Notes");
        pinButton.setVisible(false);

        // Combo
        // FIXME : Currently reading from temp local file
        ArrayList<PatchNotesEntry> entries = PatchNotesEntry.readLocalPatchNotes("D:\\Test\\slimtrade_patch_notes.json");
        for (PatchNotesEntry entry : entries) comboBox.addItem(entry);

        // Text Pane
        textPane.setEditable(false);
        textPane.setContentType("text/html");
        JScrollPane scrollPane = new CustomScrollPane(textPane);

        // Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel githubWrapperPanel = new JPanel(new GridBagLayout());
        githubWrapperPanel.add(githubButton);
        buttonPanel.add(discordButton, BorderLayout.WEST);
        buttonPanel.add(githubButton, BorderLayout.CENTER);
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
        setMinimumSize(new Dimension(600, 600));
        pack();
        setMinimumSize(new Dimension(400, 400));
        setLocationRelativeTo(null);
    }

    private void addListeners() {
        comboBox.addActionListener(e -> updateSelectedPatchNotes());
        textPane.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                ZUtil.openLink(e.getURL().toString());
            }
        });
        // TODO : Add button listeners
    }

    private void updateSelectedPatchNotes() {
        PatchNotesEntry entry = (PatchNotesEntry) comboBox.getSelectedItem();
        if (entry == null) return;
        textPane.setText(getCleanPatchNotes(entry.version, entry.text));
        textPane.setCaretPosition(0);
    }

    private String getCleanPatchNotes(String tag, String body) {
        String[] lines = body.split("(\\n|\\\\r\\\\n)");
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>SlimTrade ").append(tag).append("</h1>");
        for (String s : lines) {
            if (s.toLowerCase().contains("how to install")) {
                break;
            }
            builder.append(MarkdownParser.getHtmlFromMarkdown(s));
        }
        return builder.toString();
    }

}
