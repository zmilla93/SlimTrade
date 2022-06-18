package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class ChatScannerSearchingPanel extends JPanel {

    public ChatScannerSearchingPanel() {
        setLayout(new BorderLayout());
//        JPanel borderPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        panel.add(new JLabel("Scanning in progress..."), gc);
        gc.gridy++;
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        panel.add(progressBar, gc);
//        borderPanel.add(panel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
    }

}
