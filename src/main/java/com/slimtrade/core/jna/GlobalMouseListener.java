package com.slimtrade.core.jna;

import com.slimtrade.App;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.VisibilityManager;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;

public class GlobalMouseListener implements NativeMouseInputListener {

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        // Do Nothing
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        // Do Nothing
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
        if (App.getState() != AppState.RUNNING) return;
        Point point = nativeMouseEvent.getPoint();
        FrameManager.messageManager.checkMouseHover(point);
        FrameManager.checkMenubarVisibility(point);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        // Do Nothing
    }
}
