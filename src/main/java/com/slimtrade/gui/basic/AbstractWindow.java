package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.panels.BufferPanel;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractWindow extends BasicMovableDialog implements IColorable {

    private static final long serialVersionUID = 1L;
    public final int TITLEBAR_HEIGHT = 20;
    public final int BORDER_THICKNESS = 1;

    private JPanel titlebarPanel = new JPanel();
    private JPanel buttonPanel = new JPanel(new FlowLayout(0, 0, 0));
    protected JPanel center = new JPanel();
    private JLabel titleLabel;

    protected IconButton closeButton;
    protected IconButton pinButton;
    private boolean pinned = false;
    private ImageIcon pinIcon1;
    private ImageIcon pinIcon2;
    protected Container contentPane = this.getContentPane();

    private Color borderColor = ColorManager.PRIMARY;

    private GridBagConstraints gc = new GridBagConstraints();

    public AbstractWindow(String title) {
        this(title, true, false);
    }

    public AbstractWindow(String title, boolean makeCloseButton) {
        this(title, makeCloseButton, false);
    }

    public AbstractWindow(String title, boolean makeCloseButton, boolean makePinButton) {
        String fullTitle = "SlimTrade - " + title;
        this.setTitle(fullTitle);

        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(borderColor);

        titlebarPanel.setLayout(new BorderLayout());
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        //TODO : Container color
        center.setBackground(borderColor);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.ipadx = 0;
        gc.ipady = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        titlebarPanel.add(new BufferPanel(5, 0), BorderLayout.WEST);
        titleLabel = new CustomLabel(fullTitle);
        gc.anchor = GridBagConstraints.LINE_START;
        titlebarPanel.add(titleLabel, BorderLayout.CENTER);
        gc.insets = new Insets(0, 0, 0, 0);
        gc.gridx++;

        if (makePinButton) {
            pinButton = new IconButton(DefaultIcons.PIN2, TITLEBAR_HEIGHT);
            pinButton = new IconButton(DefaultIcons.PIN1, TITLEBAR_HEIGHT);
            buttonPanel.add(pinButton);
            pinButton.addActionListener(e -> {
                pinned = !pinned;
                if (pinned) {
                    pinButton.setIcon(pinIcon2);
                } else {
                    pinButton.setIcon(pinIcon1);
                }
                pinButton.repaint();
            });
        }

        if (makeCloseButton) {
            closeButton = new IconButton(DefaultIcons.CLOSE, TITLEBAR_HEIGHT);
            buttonPanel.add(closeButton);
            closeButton.addActionListener(e -> {
                setShow(false);
                FrameManager.refreshMenuFrames();
            });
        }
        titlebarPanel.add(buttonPanel, BorderLayout.EAST);
        contentPane.add(titlebarPanel, BorderLayout.NORTH);
        contentPane.add(center, BorderLayout.CENTER);

        this.setLocation(0, 0);
        titlebarPanel.setPreferredSize(new Dimension(50, TITLEBAR_HEIGHT));
        this.pack();
        if (closeButton != null && closeButton.getLocation().x % 2 != 0) {
            gc.insets = new Insets(0, 1, 0, 0);
            titlebarPanel.add(closeButton, gc);
        }
        this.createListeners(titlebarPanel);
    }

    public void setTitleText(String title) {
        this.setTitle(title);
        titleLabel.setText(title);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        pinIcon1 = new ImageIcon(DefaultIcons.PIN1.getColorImage(ColorManager.TEXT));
        pinIcon2 = new ImageIcon(DefaultIcons.PIN2.getColorImage(ColorManager.TEXT));
        if (pinButton != null) {
            if (pinned) {
                pinButton.setIcon(pinIcon2);
                pinButton.setCachedImage(DefaultIcons.PIN2);
            } else {
                pinButton.setIcon(pinIcon1);
                pinButton.setCachedImage(DefaultIcons.PIN1);
            }
        }
        titlebarPanel.setBackground(ColorManager.PRIMARY);
        titleLabel.setForeground(ColorManager.TEXT);
        center.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, ColorManager.PRIMARY));
    }
}
