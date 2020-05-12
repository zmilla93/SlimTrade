package com.slimtrade.core.observing;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.User32Custom;
import com.slimtrade.enums.WindowType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.enums.WindowState;
import com.sun.jna.Native;
import com.sun.jna.PointerType;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListener implements NativeMouseInputListener {

    private boolean isGameFocused;
    private boolean ignoreUntilNextFocusClick = false;

    public GlobalMouseListener() {
        super();
        isGameFocused = PoeInterface.isPoeFocused();
    }

    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    public void nativeMousePressed(NativeMouseEvent e) {
        boolean wasFocused = isGameFocused;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        PointerType hwnd;
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
//        System.out.println("window:" + curWindowTitle);
        if (curWindowTitle.equals(References.POE_WINDOW_TITLE) || App.forceUI) {
            ignoreUntilNextFocusClick = false;
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
        } else if (
                curWindowTitle.equals(References.APP_NAME + " - Options")
                || curWindowTitle.equals(References.APP_NAME + " - History")
                || curWindowTitle.equals(References.APP_NAME + " - Chat Scanner")
                || curWindowTitle.equals(References.APP_NAME + " - Stash Overlay")
                || curWindowTitle.equals(References.APP_NAME + " - Update")
                || curWindowTitle.equals(References.APP_NAME + " Window")) {
            ignoreUntilNextFocusClick = false;
            isGameFocused = true;
            FrameManager.showVisibleFrames();
            FrameManager.forceAllToTop();
        } else if (!ignoreUntilNextFocusClick) {
            isGameFocused = false;
            FrameManager.hideAllFrames();
            FrameManager.overlayManager.hideAll();
        }
        if(!wasFocused && isGameFocused && FrameManager.windowState == WindowState.NORMAL) {
            FrameManager.refreshMenuFrames();
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

    public void setIgnoreUntilNextFocusClick(boolean state) {
        ignoreUntilNextFocusClick = true;
    }

}
