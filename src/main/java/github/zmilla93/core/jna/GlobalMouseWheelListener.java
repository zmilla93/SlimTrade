package github.zmilla93.core.jna;

import github.zmilla93.modules.listening.ListenManager;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

// FIXME: Comment and remove ListenManager?
public class GlobalMouseWheelListener extends ListenManager<NativeMouseWheelListener> implements NativeMouseWheelListener {

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeMouseWheelEvent) {
        for (NativeMouseWheelListener listener : listeners) listener.nativeMouseWheelMoved(nativeMouseWheelEvent);
    }

}
