package github.zmilla93.core.jna;

import github.zmilla93.App;
import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.managers.VisibilityManager;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.util.ArrayList;

/**
 * A mouse listener for global events.
 */
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

    // FIXME : Should move app logic to an adapter so that this class is general purpose.
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
