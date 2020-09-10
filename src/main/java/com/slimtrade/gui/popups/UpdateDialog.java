package com.slimtrade.gui.popups;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.custom.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UpdateDialog extends JFrame implements IColorable {

    private JPanel borderPanel = new JPanel(new GridBagLayout());
    private JPanel container = new JPanel(new GridBagLayout());
    private JButton updateButton = new ConfirmButton("Update");

    private JLabel info1 = new CustomLabel("Update Available!");
    private JLabel info2 = new CustomLabel("Currently Running: v" + References.getAppVersion());
    private JLabel info3 = new CustomLabel("Latest Version: " + App.updateManager.getVersionTag());

    private final int BUFFER = 30;

    public UpdateDialog() {

        this.setTitle(References.APP_NAME + " - Update");
        this.setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/default/tagx64.png"))).getImage());
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
        container.add(updateButton, gc);

        // Border Panel
        gc.gridy = 0;
        gc.insets = new Insets(BUFFER, BUFFER, BUFFER, BUFFER);
//        this.getContentPane().setLayout(new GridBagLayout());
        borderPanel.add(container, gc);

        // Content Pane
        gc.insets = new Insets(0, 0, 0, 0);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(borderPanel, BorderLayout.CENTER);
        this.getContentPane().add(Box.createVerticalStrut(BUFFER), BorderLayout.NORTH);
        this.getContentPane().add(Box.createVerticalStrut(BUFFER), BorderLayout.SOUTH);
        this.getContentPane().add(Box.createHorizontalStrut(BUFFER), BorderLayout.EAST);
        this.getContentPane().add(Box.createHorizontalStrut(BUFFER), BorderLayout.WEST);

        // Finish
        this.pack();
        this.setMinimumSize(this.getSize());
        this.setSize(getWidth() + 60, getHeight() + 20);
        this.setAlwaysOnTop(true);
        ColorManager.recursiveColor(this);
        FrameManager.centerFrame(this);

        // Listener
        updateButton.addActionListener(e -> {
            App.updateManager.runProcess(App.launcherPath, "update");
        });

    }

    @Override
    public void updateColor() {
        // Colors
        this.getContentPane().setBackground(ColorManager.BACKGROUND);
        container.setBackground(ColorManager.LOW_CONTRAST_1);
        borderPanel.setBackground(ColorManager.LOW_CONTRAST_1);
        borderPanel.setBorder(ColorManager.BORDER_TEXT);
    }
}
