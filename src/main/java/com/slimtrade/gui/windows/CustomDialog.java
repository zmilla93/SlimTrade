package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomDialog extends JDialog {

    private final int resizeSize = 8;
    private JPanel innerPanel = new JPanel(new BorderLayout());
    private JPanel outerPanel = new JPanel(new BorderLayout());

    // Resizers
    private JPanel resizerTop = new JPanel(new BorderLayout());
    private JPanel resizerBottom = new JPanel(new BorderLayout());
    private JPanel resizerLeft = new JPanel(new BorderLayout());
    private JPanel resizerRight = new JPanel(new BorderLayout());

    private final JPanel titleBarPanel = new JPanel(new BorderLayout());
    private final JLabel titleLabel = new JLabel();
    protected final JPanel contentPanel = new JPanel();
    private final Container container;
    private Point startLocation;
    private Point clickedWindowPoint;
    private Dimension startSize;

    // Buttons
    private JButton closeButton = new IconButton("/icons/default/closex64.png");

    public CustomDialog() {
        setUndecorated(true);
        container = getContentPane();
        container.setLayout(new BorderLayout());

        // Title Bar
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        titlePanel.add(titleLabel, gc);
        buttonPanel.add(closeButton, gc);
        titleBarPanel.add(titlePanel, BorderLayout.WEST);
        titleBarPanel.add(buttonPanel, BorderLayout.EAST);
        titleBarPanel.add(new JSeparator(), BorderLayout.SOUTH);

        // Inner Panel
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        JPanel contentBorderPanel = new JPanel(new BorderLayout());
        contentBorderPanel.add(contentPanel, BorderLayout.CENTER);
        innerPanel.add(titleBarPanel, BorderLayout.NORTH);
        innerPanel.add(contentPanel, BorderLayout.CENTER);
        innerPanel.setBackground(Color.ORANGE);

        // Resize Panels
        resizerTop.add(Box.createVerticalStrut(resizeSize), BorderLayout.CENTER);
        resizerBottom.add(Box.createVerticalStrut(resizeSize), BorderLayout.CENTER);
        resizerLeft.add(Box.createHorizontalStrut(resizeSize), BorderLayout.CENTER);
        resizerRight.add(Box.createHorizontalStrut(resizeSize), BorderLayout.CENTER);
        resizerTop.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        resizerBottom.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        resizerLeft.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        resizerRight.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        resizerTop.setBackground(ColorManager.TRANSPARENT_CLICKABLE);
        resizerBottom.setBackground(ColorManager.TRANSPARENT_CLICKABLE);
        resizerLeft.setBackground(ColorManager.TRANSPARENT_CLICKABLE);
        resizerRight.setBackground(ColorManager.TRANSPARENT_CLICKABLE);
//        resizeRight.setOpaque(false);

        // Outer Panel
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        outerPanel.add(resizerTop, BorderLayout.NORTH);
//        titleBarPanel.add(resizerTop, BorderLayout.NORTH);
        outerPanel.add(resizerBottom, BorderLayout.SOUTH);
        outerPanel.add(resizerLeft, BorderLayout.WEST);
        outerPanel.add(resizerRight, BorderLayout.EAST);
        outerPanel.setOpaque(false);
        innerPanel.setOpaque(false);

        // Container
//        container.add(titleBarPanel, BorderLayout.NORTH);
        setContentPane(outerPanel);
        setBackground(ColorManager.TRANSPARENT);

        addWindowMover();
        addWindowResizer();
        addListeners();
    }

    private void addListeners() {
        closeButton.addActionListener(e -> {
            if (getDefaultCloseOperation() == EXIT_ON_CLOSE) {
                System.exit(0);
            } else {
                setVisible(false);
            }
        });
    }

    private void addWindowMover() {
        titleBarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                clickedWindowPoint = e.getPoint();
                clickedWindowPoint.x += resizeSize;
                clickedWindowPoint.y += resizeSize;
            }
        });
        titleBarPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point p = MouseInfo.getPointerInfo().getLocation();
                p.x -= clickedWindowPoint.x;
                p.y -= clickedWindowPoint.y;
                setLocation(p);
            }
        });
    }

    private void addWindowResizer() {
        // Bottom

        // Left
        resizerLeft.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                clickedWindowPoint = MouseInfo.getPointerInfo().getLocation();
                startLocation = getLocation();
                startSize = getSize();
//                System.out.println("K");
            }
        });
        resizerLeft.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
//                System.out.println("RRR");
                super.mouseDragged(e);
//                Point p = MouseInfo.getPointerInfo().getLocation();

                Point p = MouseInfo.getPointerInfo().getLocation();
                int widthAdjust = clickedWindowPoint.x - p.x;
                setLocation(startLocation.x - widthAdjust, startLocation.y);
                setSize(new Dimension(startSize.width + widthAdjust, startSize.height));
            }
        });

        // Right
        resizerRight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                clickedWindowPoint = MouseInfo.getPointerInfo().getLocation();
                startSize = getSize();
//                System.out.println("K");
            }
        });
        resizerRight.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point p = MouseInfo.getPointerInfo().getLocation();
                int widthAdjust = clickedWindowPoint.x - p.x;
                setSize(new Dimension(startSize.width - widthAdjust, startSize.height));
            }
        });

    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        titleLabel.setText(title);
    }

}
