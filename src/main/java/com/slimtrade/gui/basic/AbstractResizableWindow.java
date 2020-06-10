package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AbstractResizableWindow extends AbstractWindow implements IColorable {

    static final long serialVersionUID = 1L;
    protected JPanel pullRight = new JPanel();
    protected JPanel pullBottom = new JPanel();
    protected JPanel container = new JPanel();

    private int pullbarSize = 5;

    private int startingX;
    private int startingY;
    private int startingWidth;
    private int startingHeight;
    private boolean mousePressed = false;
//    private AbstractWindow local;

    private final int SLEEP_DURATION = 20;

    public AbstractResizableWindow(String title) {
        super(title, true);
        buildWindow();
    }

    public AbstractResizableWindow(String title, boolean makeCloseButton) {
        super(title, makeCloseButton);
        buildWindow();
    }

    public AbstractResizableWindow(String title, boolean makeCloseButton, boolean makePinButton) {
        super(title, makeCloseButton, makePinButton);
        buildWindow();
    }

    private void buildWindow() {
        // container.setOpaque(false);

        this.setMinimumSize(new Dimension(100, 100));
        pullRight.setPreferredSize(new Dimension(pullbarSize, 0));
        pullBottom.setPreferredSize(new Dimension(0, pullbarSize));
        center.setLayout(new BorderLayout());
        center.add(pullRight, BorderLayout.LINE_END);
        center.add(pullBottom, BorderLayout.PAGE_END);
        center.add(container, BorderLayout.CENTER);
        pullRight.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        pullBottom.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));

        pullRight.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startingX = e.getXOnScreen();
                startingY = e.getY();
                startingWidth = getWidth();
                startingHeight = getHeight();
                mousePressed = true;
                new Thread(runnerRight).start();
            }

            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
            }
        });

        pullBottom.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startingX = e.getXOnScreen();
                startingY = e.getYOnScreen();
                startingWidth = getWidth();
                startingHeight = getHeight();
                mousePressed = true;
                new Thread(runnerBottom).start();
            }

            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
            }
        });
    }

    public void autoResize() {
        Dimension size = container.getPreferredSize();
        size.width += this.BORDER_THICKNESS * 2 + this.pullbarSize;
        size.height += this.TITLEBAR_HEIGHT + this.BORDER_THICKNESS + this.pullbarSize;
        this.setPreferredSize(size);
    }

    protected Runnable runnerRight = new Runnable() {
        public void run() {
            while (mousePressed) {
                if (pinned) {
                    return;
                }
                int mouseX = MouseInfo.getPointerInfo().getLocation().x;
                int width = startingWidth - (startingX - mouseX);
                if (width % 2 != 0) width++;
                final int finalWidth = width;
                SwingUtilities.invokeLater(() -> {
                    setPreferredSize(new Dimension(finalWidth, startingHeight));
                    pack();
                    repaint();
                });
                try {
                    Thread.sleep(SLEEP_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    protected Runnable runnerBottom = new Runnable() {
        public void run() {
            while (mousePressed) {
                if (pinned) {
                    return;
                }
                int mouseY = MouseInfo.getPointerInfo().getLocation().y;
                int height = startingHeight - (startingY - mouseY);
                if (height % 2 != 0) height++;
                final int finalHeight = height;
                SwingUtilities.invokeLater(() -> {
                    setPreferredSize(new Dimension(startingWidth, finalHeight));
                    pack();
                    repaint();
                });
                try {
                    Thread.sleep(SLEEP_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    protected void updatePullbars() {
        if (pinned) {
            pullRight.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            pullBottom.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            pullRight.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
            pullBottom.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        }
    }

    @Override
    public void pinAction(MouseEvent e) {
        super.pinAction(e);
        updatePullbars();
    }

    @Override
    public void updateColor() {
        super.updateColor();
        pullRight.setBackground(ColorManager.PRIMARY);
        pullBottom.setBackground(ColorManager.PRIMARY);
    }

    @Override
    public void pack() {
        super.pack();
    }
}
