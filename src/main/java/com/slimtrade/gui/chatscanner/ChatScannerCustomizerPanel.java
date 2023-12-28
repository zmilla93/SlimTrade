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

    public ChatScannerCustomizerPanel(String title) {
        this(new ChatScannerEntry(title));
    }

    public ChatScannerCustomizerPanel(ChatScannerEntry entry) {
        setLayout(new BorderLayout());
        ChatScannerOptionPanel customizerPanel = new ChatScannerOptionPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(searchTermsPanel, "Search Terms");
        tabbedPane.add(macroPanel, "Macros");
        headerLabel = customizerPanel.addHeader(entry.title).label;
        customizerPanel.addComponent(new ComponentPair(renameButton, deleteButton));
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
    }

    public ChatScannerEntry getData() {
        return new ChatScannerEntry(headerLabel.getText(), searchTermsPanel.getSearchTerms(), searchTermsPanel.getIgnoreTerms(), macroPanel.getMacros());
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
