package com.slimtrade.core.observing;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.utility.User32Custom;
import com.slimtrade.enums.WindowType;
import com.slimtrade.gui.FrameManager;
import com.sun.jna.Native;
import com.sun.jna.PointerType;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListener implements NativeMouseInputListener {

    private boolean isGameFocused = false;

    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    public void nativeMousePressed(NativeMouseEvent e) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        PointerType hwnd = null;
        byte[] windowText = new byte[512];
        int i = 0;
        do {
            i++;
            hwnd = User32Custom.INSTANCE.GetForegroundWindow();
            if (hwnd != null) {
                break;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if(i>20) {
                    return;
                }
            }
        } while (true);
        User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        String curWindowTitle = Native.toString(windowText);
//        System.out.println("win:" + curWindowTitle);
        isGameFocused = false;
        if (curWindowTitle.equals(References.POE_WINDOW_TITLE) || App.forceUI) {
            isGameFocused = true;
            switch (FrameManager.windowState) {
                case NORMAL:
                    FrameManager.showVisibleFrames();
                    FrameManager.forceAllToTop();
                    break;
                case LAYOUT_MANAGER:
                    FrameManager.overlayManager.showAll();
                    FrameManager.overlayManager.allToFront();
                    break;
                case STASH_OVERLAY:
                    FrameManager.stashOverlayWindow.setVisible(true);
                    FrameManager.stashOverlayWindow.setAlwaysOnTop(false);
                    FrameManager.stashOverlayWindow.setAlwaysOnTop(true);
                    break;
            }
        } else if (curWindowTitle.equals("Open") || curWindowTitle.equals(References.APP_NAME + " - Options") || curWindowTitle.equals(References.APP_NAME + " - History") || curWindowTitle.equals(References.APP_NAME + " - Chat Scanner")) {
            isGameFocused = true;
            FrameManager.showVisibleFrames();
            FrameManager.forceAllToTop();
        } else {
            FrameManager.hideAllFrames();
            FrameManager.overlayManager.hideAll();
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

    public boolean isGameFocused() {
        return this.isGameFocused;
    }

}
