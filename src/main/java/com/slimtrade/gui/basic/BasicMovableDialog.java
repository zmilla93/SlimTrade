package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.utility.TradeUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//TODO : REVIEW THIS CODE!!
public class BasicMovableDialog extends BasicDialog {

    private static final long serialVersionUID = 1L;

    protected int offsetX;
    protected int offsetY;
    protected boolean screenLock = false;
    protected boolean pinned = false;
    protected boolean mouseDown = false;
    private int borderOffset = 0;

    public BasicMovableDialog() {

    }

    public BasicMovableDialog(boolean createListeners) {
        if (createListeners) {
            createListeners((JPanel) this.getContentPane());
        }
    }

    public void createListeners(JPanel p) {
        p.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    offsetX = e.getX();
                    offsetY = e.getY();
                    mouseDown = true;
                    runWindowMover();
                }
            }
        });
        p.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                }
            }
        });
    }

    //TODO : Make this function cleaner, then use it for runWindowMover
    public void forceOntoScreen() {
        forceOntoScreen(this.getX(), this.getY());
    }

    public void forceOntoScreen(int x, int y) {
        int targetX = x;
        int targetY = y;
        if (targetX < 0) targetX = 0;
        if (targetX > TradeUtility.screenSize.width - getWidth())
            targetX = TradeUtility.screenSize.width - getWidth() - borderOffset * 2;
        if (targetY < 0) targetY = 0;
        if (targetY > TradeUtility.screenSize.height - getHeight())
            targetY = TradeUtility.screenSize.height - getHeight() - borderOffset * 2;
        moveWindow(new Point(targetX, targetY));
    }

    public void setScreenLock(boolean state) {
        screenLock = state;
        if (screenLock) {
            forceOntoScreen();
        }
    }

    public void setBorderOffset(int borderOffset) {
        this.borderOffset = borderOffset;
    }

    private void moveWindow(Point p) {
        this.setLocation(p);
    }

    public void unpin() {
        this.pinned = false;
    }

    private void runWindowMover() {
        if (pinned) {
            return;
        }
        new Thread(() -> {
            while (mouseDown) {
                int targetX = MouseInfo.getPointerInfo().getLocation().x - offsetX - borderOffset;
                int targetY = MouseInfo.getPointerInfo().getLocation().y - offsetY - borderOffset;
                if (screenLock || App.globalKeyboard.isShiftPressed()) {
                    if (targetX > TradeUtility.screenSize.width - getWidth() - borderOffset * 2)
                        targetX = TradeUtility.screenSize.width - getWidth() - borderOffset * 2;
                    if (targetX < 0) targetX = 0;
                    if (targetY > TradeUtility.screenSize.height - getHeight() - borderOffset * 2)
                        targetY = TradeUtility.screenSize.height - getHeight() - borderOffset * 2;
                    if (targetY < 0) targetY = 0;
                }
                int finalTargetX = targetX;
                int finalTargetY = targetY;
                moveWindow(new Point(finalTargetX, finalTargetY));
            }
        }).start();
    }


}
