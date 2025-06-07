package github.zmilla93.gui.windows;

import github.zmilla93.App;
import github.zmilla93.core.References;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.MarkdownParser;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.BufferPanel;
import github.zmilla93.gui.components.CardPanel;
import github.zmilla93.gui.components.CustomScrollPane;
import github.zmilla93.gui.components.LimitCombo;
import github.zmilla93.gui.listening.IDefaultSizeAndLocation;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.updater.PatchNotesEntry;
import github.zmilla93.modules.updater.data.AppVersion;

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
    private final CardPanel cardPanel = new CardPanel();

    private static final String PREFIX = "**Enjoying the app? Consider supporting on [Patreon](" + References.PATREON_URL + ") or [PayPal](" + References.PAYPAL_URL + ")!**<br>";
    private static final String POSTFIX = "*Want to report a bug or give feedback? Post on [GitHub](" + References.GITHUB_ISSUES_URL + ") or join the [Discord](" + References.DISCORD_INVITE + ")!*";

    // Announcement Panel
    private boolean showAnnouncement = true;
    private JPanel announcementPanel = createAnnouncementPanel();
    private JPanel patchNotesPanel;
    private JButton toggleButton = new JButton();

    public PatchNotesWindow() {
        super("Patch Notes");
        pinButton.setVisible(false);

        // Combo
        ArrayList<PatchNotesEntry> entries = App.updateManager.getPatchNotes(App.getAppInfo().appVersion);
        if (entries != null && entries.size() > 0) {
            for (int i = 0; i < entries.size(); i++) {
                PatchNotesEntry entry = entries.get(i);
                entry.text = getCleanPatchNotes(entry.getAppVersion(), entry.text, i == 0);
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
        patchNotesPanel = new JPanel(new BorderLayout());
        patchNotesPanel.add(controlsPanel, BorderLayout.NORTH);
        patchNotesPanel.add(scrollPane, BorderLayout.CENTER);

        toggleButton = new JButton("Toggle");
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(cardPanel, BorderLayout.CENTER);
        wrapperPanel.add(new JButton("Flip"), BorderLayout.SOUTH);
        cardPanel.add(patchNotesPanel);
        cardPanel.add(announcementPanel);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(toggleButton, BorderLayout.SOUTH);

        // Finalize
        addListeners();
        updateSelectedPatchNotes();
        comboBox.requestFocus();

        showCurrentPanel();
        setMinimumSize(new Dimension(400, 400));
        pack();
    }

    private void togglePanel() {
        showAnnouncement = !showAnnouncement;
        showCurrentPanel();
    }

    private void showCurrentPanel() {
        if (showAnnouncement) {
            toggleButton.setText("Show Patch Notes");
            cardPanel.showCard(announcementPanel);
        } else {
            toggleButton.setText("Show Announcement");
            cardPanel.showCard(patchNotesPanel);
        }
    }

    private JPanel createAnnouncementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextPane textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setText("Hi there! ZMilla, author of SlimTrade here,<br><br>" +
                "My desktop PC died, money is tight, and I can't play POE for a bit. If everyone reading this donated just one dollar... (you know how this goes)<br><br>" +
                "Or don't! That's completely fine. Just understand that I don't want to feel obligated to work on SlimTrade when I can't play POE myself.<br><br>" +
                "<b>I will still maintain SlimTrade with bug fixes, but new features & QOL are on hold.</b><br><br>" +
                "Thanks for reading! Stay sane, exile.");
        panel.add(new BufferPanel(new JLabel("Annoying Announcement :^)"), 4), BorderLayout.NORTH);
        JButton donationButton = new JButton("Donate");
        panel.add(new CustomScrollPane(textArea), BorderLayout.CENTER);
        panel.add(donationButton, BorderLayout.SOUTH);
        donationButton.addActionListener(e -> openDonationWindow());
        return panel;
    }

    private void addListeners() {
        toggleButton.addActionListener(e -> togglePanel());
        comboBox.addActionListener(e -> updateSelectedPatchNotes());
        textPane.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                ZUtil.openLink(e.getURL().toString());
            }
        });
        githubButton.addActionListener(e -> ZUtil.openLink(References.GITHUB_URL));
        discordButton.addActionListener(e -> ZUtil.openLink(References.DISCORD_INVITE));
        donateButton.addActionListener(e -> {
            openDonationWindow();
        });
    }

    private void openDonationWindow() {
        FrameManager.optionsWindow.setVisible(true);
        FrameManager.optionsWindow.toFront();
        FrameManager.optionsWindow.showDonationPanel();
    }

    private void updateSelectedPatchNotes() {
        PatchNotesEntry entry = (PatchNotesEntry) comboBox.getSelectedItem();
        if (entry == null) return;
        textPane.setText(entry.text);
        textPane.setCaretPosition(0);
    }

    private String getCleanPatchNotes(AppVersion version, String body, boolean addExtraInfo) {
        String[] lines = body.split("(\\n|\\\\r\\\\n)");
        StringBuilder builder = new StringBuilder();
        builder.append("<h1>SlimTrade ").append(version).append("</h1>");
        if (addExtraInfo) builder.append(MarkdownParser.getHtmlFromMarkdown(PREFIX));
        for (String s : lines) {
            if (s.toLowerCase().contains("how to install")) {
                break;
            }
            builder.append(MarkdownParser.getHtmlFromMarkdown(s));
        }
        if (addExtraInfo) builder.append(MarkdownParser.getHtmlFromMarkdown(POSTFIX));
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
        POEWindow.centerWindow(this);
    }

}
