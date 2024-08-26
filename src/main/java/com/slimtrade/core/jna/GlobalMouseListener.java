package com.slimtrade.core.jna;

import com.slimtrade.App;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.VisibilityManager;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.util.ArrayList;

public class GlobalMouseListener implements NativeMouseInputListener {

    private final ArrayList<NativeMouseAdapter> mouseAdapters = new ArrayList<>();

    public void addMouseAdapter(NativeMouseAdapter adapter) {
        mouseAdapters.add(adapter);
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        for (NativeMouseAdapter adapter : mouseAdapters) adapter.nativeMouseClicked(nativeMouseEvent);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        for (NativeMouseAdapter adapter : mouseAdapters) adapter.nativeMousePressed(nativeMouseEvent);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        for (NativeMouseAdapter adapter : mouseAdapters) adapter.nativeMouseReleased(nativeMouseEvent);
        if (POEInterface.isGameFocused(true)) {
            VisibilityManager.showOverlay();
        } else {
            VisibilityManager.hideOverlay();
        }
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        for (NativeMouseAdapter adapter : mouseAdapters) adapter.nativeMouseMoved(nativeMouseEvent);
        if (App.getState() != AppState.RUNNING) return;
        Point point = nativeMouseEvent.getPoint();
        FrameManager.messageManager.checkMouseHover(point);
        FrameManager.checkMenubarVisibility(point);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        for (NativeMouseAdapter adapter : mouseAdapters) adapter.nativeMouseDragged(nativeMouseEvent);
    }

}
