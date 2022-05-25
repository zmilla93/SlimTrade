package com.slimtrade.gui.chatscanner;

import com.slimtrade.gui.options.IncomingMacroPanel;
import com.slimtrade.gui.windows.CustomDialog;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatScannerWindow extends CustomDialog {

    //    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JList<ChatScannerEntry> scannerMessages = new JList<>();
    private final JButton newSearchButton = new JButton("New Entry");
    private final AdvancedButton scanButton = new AdvancedButton("Start Scanning");

    public ChatScannerWindow() {
        super("Chat Scanner");

        JLabel titleLabel = new JLabel();
//        tabbedPane.add(new ScannerSearchTermsPanel(), "Search Terms");
//        tabbedPane.add(new IncomingMacroPanel(), "Macros");

        // Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(newSearchButton, BorderLayout.NORTH);
        buttonPanel.add(scanButton, BorderLayout.SOUTH);

        // Sidebar
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(scannerMessages, BorderLayout.NORTH);
        sidebar.add(buttonPanel, BorderLayout.SOUTH);

        // Tabbed Panel
        IncomingMacroPanel p = new IncomingMacroPanel();
        p.reloadExampleTrade();

        // Container
//        Container contentPanel = getContentPane();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new ChatScannerCustomizerPanel(), BorderLayout.CENTER);
        contentPanel.add(new JSeparator(), BorderLayout.NORTH);
        contentPanel.add(sidebar, BorderLayout.WEST);

        // Finalize
        setTitle("Chat Scanner");
        pack();
        setSize(800, 600);

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scanButton.setActive(!scanButton.isActive());
                System.out.println(scanButton.isActive());
            }
        });


        // TEMP DATA
        scannerMessages.setListData(new ChatScannerEntry[]{
                new ChatScannerEntry("Cool Beans!", "asdfsdf", "Asdf", null),
                new ChatScannerEntry("Cool asdf!", "asdfsdf", "Asdf", null),
                new ChatScannerEntry("aaaa", "asdfsdf", "Asdf", null),
                new ChatScannerEntry("Cool Beans!", "asdfsdf", "Asdf", null),
        });

        addListeners();
    }

    private void addListeners() {
        ChatScannerWindow self = this;
        newSearchButton.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(self, "Enter a Title", "New Entry", JOptionPane.QUESTION_MESSAGE);
            System.out.println(title);
        });
    }

}
