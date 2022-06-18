package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.gui.options.IncomingMacroPanel;

import javax.swing.*;
import java.awt.*;

public class ChatScannerCustomizerPanel extends JPanel {

    private final JButton renameButton = new JButton("Rename");
    private String title;
    private final ScannerSearchTermsPanel searchTermsPanel = new ScannerSearchTermsPanel();
    private final ChatScannerMacroPanel macroPanel = new ChatScannerMacroPanel();

    public ChatScannerCustomizerPanel(String title) {
        this(new ChatScannerEntry(title));
    }

    public ChatScannerCustomizerPanel(ChatScannerEntry entry) {
        setLayout(new BorderLayout());
        this.title = entry.title;
        ChatScannerOptionPanel customizerPanel = new ChatScannerOptionPanel();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(searchTermsPanel, "Search Terms");
        tabbedPane.add(macroPanel, "Macros");
        customizerPanel.addHeader(entry.title);
        customizerPanel.addPanel(renameButton);
        customizerPanel.addPanel(tabbedPane);
        add(customizerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        setData(entry);
    }

    public void setData(ChatScannerEntry entry) {
        macroPanel.reloadExampleTrade(entry);
        searchTermsPanel.setSearchTerms(entry.searchTermsRaw);
        searchTermsPanel.setIgnoreTerms(entry.ignoreTermsRaw);
        macroPanel.setMacros(entry.macros);
    }

    public ChatScannerEntry getData() {
        return new ChatScannerEntry(title, searchTermsPanel.getSearchTerms(), searchTermsPanel.getIgnoreTerms(), macroPanel.getMacros());
    }

    public void reloadExample(){
        macroPanel.reloadExampleTrade(getData());
    }

}
