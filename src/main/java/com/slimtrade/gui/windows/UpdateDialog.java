package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.buttons.ConfirmButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UpdateDialog extends JFrame {

    private JPanel borderPanel = new JPanel(FrameManager.gridBag);
    private JPanel container = new JPanel(FrameManager.gridBag);
    private JButton viewUpdateButton = new ConfirmButton("View on Github");

    private JLabel info1 = new CustomLabel("Update Available!");
    private JLabel info2 = new CustomLabel("Currently Running: " + References.APP_VERSION);
    private JLabel info3 = new CustomLabel("Latest Version: " + App.updateChecker.getLatestRelease());

    private final int BUFFER = 30;

    public UpdateDialog() {

        this.setTitle(References.APP_NAME + " - Update");
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("icons/default/tagx64.png")).getImage());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        // Container
        gc.insets.bottom = 15;
        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 5;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.bottom = 20;
        container.add(info3, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        gc.fill = GridBagConstraints.BOTH;
        container.add(viewUpdateButton, gc);

        // Border Panel
        gc.gridy = 0;
        gc.insets = new Insets(BUFFER, BUFFER, BUFFER, BUFFER);
//        this.getContentPane().setLayout(FrameManager.gridBag);
        borderPanel.add(container, gc);

        // Content Pane
        gc.insets = new Insets(0, 0, 0, 0);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(borderPanel, BorderLayout.CENTER);
        this.getContentPane().add(Box.createVerticalStrut(BUFFER), BorderLayout.NORTH);
        this.getContentPane().add(Box.createVerticalStrut(BUFFER), BorderLayout.SOUTH);
        this.getContentPane().add(Box.createHorizontalStrut(BUFFER), BorderLayout.EAST);
        this.getContentPane().add(Box.createHorizontalStrut(BUFFER), BorderLayout.WEST);

        // Colors
        this.getContentPane().setBackground(ColorManager.BACKGROUND);
        container.setBackground(ColorManager.LOW_CONTRAST_1);
        borderPanel.setBackground(ColorManager.LOW_CONTRAST_1);
        borderPanel.setBorder(ColorManager.BORDER_TEXT);

        // Finish
        this.pack();
        this.setMinimumSize(this.getSize());
        this.setSize(getWidth() + 60, getHeight() + 20);
        this.setAlwaysOnTop(true);
        App.eventManager.recursiveColor(this);
        FrameManager.centerFrame(this);

        // Listener
        viewUpdateButton.addActionListener(e -> {
            try {
                URI uri = new URI("https://github.com/zmilla93/SlimTrade/releases/latest");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException err) {
                err.printStackTrace();
            }
        });

    }

}
