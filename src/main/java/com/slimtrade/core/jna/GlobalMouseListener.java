package com.slimtrade.core.jna;

import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.VisibilityManager;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListener implements NativeMouseInputListener {

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        if (POEInterface.isGameFocused(true)) {
            VisibilityManager.showOverlay();
        } else {
            VisibilityManager.hideOverlay();
        }
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        FrameManager.messageManager.checkMouseHover(nativeMouseEvent.getPoint());
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }
}
