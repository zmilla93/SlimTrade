package com.slimtrade.gui.windows;

import com.slimtrade.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class AbstractWindow extends JFrame {

    protected Container container;

    private boolean screenLock;
    private Rectangle screenBounds;

    public AbstractWindow(String title) {
        super(title);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        this.container = getContentPane();

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                if (App.globalKeyboardListener.isShiftPressed()) {
                    if(!screenLock){
                        screenLock = true;
                        startScreenLock();
                    }
                } else {
                    screenLock = false;
                }

                if(screenLock){
                    if(screenBounds.contains(getLocation())){
                        setLocation(screenBounds.getLocation());
                    }
                }
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    @Override
    public void setLocation(Point p) {
        super.setLocation(p);
        System.out.println("LOC");
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        System.out.println("LOC");
    }

    private void startScreenLock() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for (GraphicsDevice device : devices) {
            Rectangle bounds = device.getDefaultConfiguration().getBounds();
            if(bounds.contains(MouseInfo.getPointerInfo().getLocation())){
                System.out.println("ME!!!");
                screenBounds = bounds;
                return;
            }
            System.out.println("Mouse pos " + getMousePosition());
        }
        System.out.println();
    }

}
