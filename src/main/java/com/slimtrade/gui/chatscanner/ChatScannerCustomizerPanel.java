package com.slimtrade.gui.chatscanner;

import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;

public class ChatScannerCustomizerPanel extends JPanel implements Comparable<ChatScannerCustomizerPanel> {

    private final JButton renameButton = new JButton("Rename");
    private final JButton deleteButton = new JButton("Delete");
    private final JLabel headerLabel;
    private final ScannerSearchTermsPanel searchTermsPanel = new ScannerSearchTermsPanel();
    private final ChatScannerMacroPanel macroPanel = new ChatScannerMacroPanel();
    private final JCheckBox chatCheckbox = new JCheckBox("Global & Trade Chat");
    private final JCheckBox whisperCheckbox = new JCheckBox("Whispers");
    private final JCheckBox metaCheckbox = new JCheckBox("Meta Info (zone info, level ups, debug info, etc)");

    public ChatScannerCustomizerPanel(String title) {
        this(new ChatScannerEntry(title));
    }

    public ChatScannerCustomizerPanel(ChatScannerEntry entry) {
        setLayout(new BorderLayout());
        ChatScannerOptionPanel customizerPanel = new ChatScannerOptionPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(searchTermsPanel, "Search Terms");
        tabbedPane.add(macroPanel, "Macros");
        chatCheckbox.setSelected(true);
        whisperCheckbox.setSelected(true);

        headerLabel = customizerPanel.addHeader(entry.title).label;
        customizerPanel.addComponent(new ComponentPair(renameButton, deleteButton));
        customizerPanel.addVerticalStrut();

        customizerPanel.addHeader("Scanner Target");
        customizerPanel.addComponent(chatCheckbox);
        customizerPanel.addComponent(whisperCheckbox);
        customizerPanel.addComponent(metaCheckbox);

        customizerPanel.addComponent(tabbedPane);
        add(customizerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        setData(entry);
        addListeners();
    }

    private void addListeners() {
        renameButton.addActionListener(e -> FrameManager.chatScannerWindow.showRenamePanel());
        deleteButton.addActionListener(e -> FrameManager.chatScannerWindow.showDeletePanel());
    }

    public void setData(ChatScannerEntry entry) {
        macroPanel.reloadExampleTrade(entry);
        searchTermsPanel.setSearchTerms(entry.searchTermsRaw);
        searchTermsPanel.setIgnoreTerms(entry.ignoreTermsRaw);
        macroPanel.setMacros(entry.macros);
        chatCheckbox.setSelected(entry.allowGlobalAndTradeChat);
        whisperCheckbox.setSelected(entry.allowWhispers);
        metaCheckbox.setSelected(entry.allowMetaText);
    }

    public ChatScannerEntry getData() {
        return new ChatScannerEntry(headerLabel.getText(), searchTermsPanel.getSearchTerms(), searchTermsPanel.getIgnoreTerms(), macroPanel.getMacros(),
                chatCheckbox.isSelected(), whisperCheckbox.isSelected(), metaCheckbox.isSelected());
    }

    public void setTitle(String title) {
        headerLabel.setText(title);
    }

    public String getTitle() {
        return headerLabel.getText();
    }

    public void reloadExample() {
        macroPanel.reloadExampleTrade(getData());
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int compareTo(ChatScannerCustomizerPanel other) {
        return getTitle().toLowerCase().compareTo(other.getTitle().toLowerCase());
    }

}
