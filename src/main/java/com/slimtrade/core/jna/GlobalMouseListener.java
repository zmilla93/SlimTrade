package com.slimtrade.core.jna;

import com.slimtrade.gui.managers.FrameManager;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import java.awt.*;

public class GlobalMouseListener implements NativeMouseMotionListener {
    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        Point p = nativeMouseEvent.getPoint();
        if (FrameManager.menubarDialog.contains(p) || FrameManager.menubarIcon.contains(p)) {
            FrameManager.menubarDialog.setVisible(true);
            FrameManager.menubarIcon.setVisible(false);
        } else {
            FrameManager.menubarIcon.setVisible(true);
            FrameManager.menubarDialog.setVisible(false);
        }
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }
}
