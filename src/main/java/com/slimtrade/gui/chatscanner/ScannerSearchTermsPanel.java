package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;

public class ScannerSearchTermsPanel extends AbstractOptionPanel {

    private JLabel nameLabel = new JLabel();
    private JButton renameButton = new JButton("Rename");
    private int rows = 6;
    private int cols = 35;
    private final JTextArea searchTermsInput = new JTextArea(rows, cols);
    private JTextArea ignoreTermsInput = new JTextArea(rows, cols);

    public ScannerSearchTermsPanel() {
        JLabel searchLabel = new JLabel("Search Terms");
        JLabel ignoreLabel = new JLabel("Ignore Terms");

        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel ignorePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchTermsInput, BorderLayout.CENTER);
        ignorePanel.add(ignoreLabel, BorderLayout.NORTH);
        ignorePanel.add(ignoreTermsInput, BorderLayout.CENTER);

        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        inputPanel.add(searchPanel, gc);
        gc.gridy++;
        inputPanel.add(ignorePanel, gc);

        addHeader("Search Terms");
        addPanel(searchTermsInput);
        addVerticalStrut();
        addHeader("Ignore Terms");
        addPanel(ignoreTermsInput);
    }

}
