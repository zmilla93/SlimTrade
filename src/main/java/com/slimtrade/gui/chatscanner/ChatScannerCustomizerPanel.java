package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.gui.options.IncomingMacroPanel;

import javax.swing.*;
import java.awt.*;

public class ChatScannerCustomizerPanel extends AbstractOptionPanel {

    private final JLabel nameLabel = new JLabel("Trials");
    private final JButton renameButton = new JButton("Rename");
    private final JTabbedPane tabbedPane = new JTabbedPane();

    public ChatScannerCustomizerPanel() {
        tabbedPane.add(new ScannerSearchTermsPanel(), "Search Terms");
        tabbedPane.add(new IncomingMacroPanel(), "Macros");

        JPanel renamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();

        renamePanel.add(nameLabel, gc);
        gc.gridx++;
        renamePanel.add(renameButton, gc);

        addHeader("Trials");
//        addPanel(nameLabel);
        addPanel(renameButton);
//        addVerticalStrut();
        addPanel(tabbedPane);
    }

}
