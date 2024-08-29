package com.slimtrade.core.jna;

import com.slimtrade.modules.listening.ListenManager;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class GlobalMouseWheelListener extends ListenManager<NativeMouseWheelListener> implements NativeMouseWheelListener {

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeMouseWheelEvent) {
        for (NativeMouseWheelListener listener : listeners) listener.nativeMouseWheelMoved(nativeMouseWheelEvent);
    }

}
