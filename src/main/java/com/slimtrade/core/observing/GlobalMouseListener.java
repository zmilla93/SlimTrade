package com.slimtrade.core.observing;

import com.slimtrade.App;
import com.slimtrade.enums.WindowType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.core.References;
import com.slimtrade.gui.enums.WindowState;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.sun.jna.Native;
import com.sun.jna.PointerType;

import com.slimtrade.core.utility.User32Custom;

import java.awt.*;

public class GlobalMouseListener implements NativeMouseInputListener {


    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    //TODO : This throws an error if mouse is clicked during loading
    public void nativeMousePressed(NativeMouseEvent e) {
//		new Thread(refreshRunner).start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        PointerType hwnd = null;
        byte[] windowText = new byte[512];
        do {
            hwnd = User32Custom.INSTANCE.GetForegroundWindow();
            if (hwnd != null) {
                break;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } while (true);
        User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        String curWindowTitle = Native.toString(windowText);

//        System.out.println("Click : " +  click);
//        click++;
        System.out.println("window : " +  curWindowTitle);
//        System.out.println("STATE : " + FrameManager.windowState);
//		if (curWindowTitle.equals(References.POE_WINDOW_TITLE) || curWindowTitle.startsWith(References.APP_NAME) || App.debugMode) {
//            FrameManager.showVisibleFrames();
//			FrameManager.forceAllToTop();
//		}else{
//		    FrameManager.hideAllFrames();
//        }
        // TODO : CLEAN UP

        if (curWindowTitle.equals(References.POE_WINDOW_TITLE)
                || App.debugMode) {
            if (FrameManager.windowState == WindowState.NORMAL) {
//                System.out.println("P1");
                FrameManager.showVisibleFrames();
//                System.out.println("P2");
                FrameManager.forceAllToTop();
//                System.out.println("P3");
            } else if (FrameManager.windowState == WindowState.LAYOUT_MANAGER) {
                FrameManager.overlayManager.showAll();
                FrameManager.overlayManager.allToFront();
            } else if (FrameManager.windowState == WindowState.STASH_OVERLAY) {
                FrameManager.stashOverlayWindow.setVisible(true);
                FrameManager.stashOverlayWindow.setAlwaysOnTop(false);
                FrameManager.stashOverlayWindow.setAlwaysOnTop(true);
            }
        } else if (curWindowTitle.equals("Open") || curWindowTitle.equals(References.APP_NAME + " - Options") || curWindowTitle.equals(References.APP_NAME + " - History") || curWindowTitle.equals(References.APP_NAME + " - Chat Scanner")) {
//            FrameManager.optionsWindow.setAlwaysOnTop(false);
//            FrameManager.optionsWindow.setAlwaysOnTop(true);
            FrameManager.showVisibleFrames();
            FrameManager.forceAllToTop();
        } else {
            FrameManager.hideAllFrames();
            FrameManager.overlayManager.hideAll();
//            FrameManager.stashOverlayWindow.setVisible(false);
//            FrameManager.stashOverlayWindow.setAlwaysOnTop(false);
        }


    }

    public void nativeMouseReleased(NativeMouseEvent e) {

    }

    public void nativeMouseDragged(NativeMouseEvent e) {

    }

    public void nativeMouseMoved(NativeMouseEvent e) {

    }

    private WindowType getWindowType(String win) {
        if (win.equals("Path of Exile")) {
            return WindowType.POE;
        } else if (win.contains("SlimTrade")) {
            return WindowType.SLIMTRADE;
        } else {
            return WindowType.OTHER;
        }
    }

}
