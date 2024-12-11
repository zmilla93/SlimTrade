package github.zmilla93.core.jna;

import github.zmilla93.core.hotkeys.HotkeyData;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Jna and Awt both work with keycodes, but unfortunately don't use the same ones.
 * This class handles converting from one to the other for common keys.
 */
public class JnaAwtEvent {

    public static final int INVALID_HOTKEY = 0;
    private static final HashMap<Integer, Integer> keyMap = new HashMap<>();

    private JnaAwtEvent() {
        /// Static class
    }

    static {
        // Common Keys
        register(NativeKeyEvent.VC_ENTER, KeyEvent.VK_ENTER);
        register(NativeKeyEvent.VC_SLASH, KeyEvent.VK_SLASH);
        register(NativeKeyEvent.VC_SPACE, KeyEvent.VK_SPACE);
        register(NativeKeyEvent.VC_BACK_SLASH, KeyEvent.VK_BACK_SLASH);
        register(NativeKeyEvent.VC_BACKQUOTE, KeyEvent.VK_BACK_QUOTE);
        register(NativeKeyEvent.VC_TAB, KeyEvent.VK_TAB);

        // F1 - F12
        register(NativeKeyEvent.VC_F1, KeyEvent.VK_F1);
        register(NativeKeyEvent.VC_F2, KeyEvent.VK_F2);
        register(NativeKeyEvent.VC_F3, KeyEvent.VK_F3);
        register(NativeKeyEvent.VC_F4, KeyEvent.VK_F4);
        register(NativeKeyEvent.VC_F5, KeyEvent.VK_F5);
        register(NativeKeyEvent.VC_F6, KeyEvent.VK_F6);
        register(NativeKeyEvent.VC_F7, KeyEvent.VK_F7);
        register(NativeKeyEvent.VC_F8, KeyEvent.VK_F8);
        register(NativeKeyEvent.VC_F9, KeyEvent.VK_F9);
        register(NativeKeyEvent.VC_F10, KeyEvent.VK_F10);
        register(NativeKeyEvent.VC_F11, KeyEvent.VK_F11);
        register(NativeKeyEvent.VC_F12, KeyEvent.VK_F12);

        // Alphabet
        register(NativeKeyEvent.VC_A, KeyEvent.VK_A);
        register(NativeKeyEvent.VC_B, KeyEvent.VK_B);
        register(NativeKeyEvent.VC_C, KeyEvent.VK_C);
        register(NativeKeyEvent.VC_D, KeyEvent.VK_D);
        register(NativeKeyEvent.VC_E, KeyEvent.VK_E);
        register(NativeKeyEvent.VC_F, KeyEvent.VK_F);
        register(NativeKeyEvent.VC_G, KeyEvent.VK_G);
        register(NativeKeyEvent.VC_H, KeyEvent.VK_H);
        register(NativeKeyEvent.VC_I, KeyEvent.VK_I);
        register(NativeKeyEvent.VC_J, KeyEvent.VK_J);
        register(NativeKeyEvent.VC_K, KeyEvent.VK_K);
        register(NativeKeyEvent.VC_L, KeyEvent.VK_L);
        register(NativeKeyEvent.VC_M, KeyEvent.VK_M);
        register(NativeKeyEvent.VC_N, KeyEvent.VK_N);
        register(NativeKeyEvent.VC_O, KeyEvent.VK_O);
        register(NativeKeyEvent.VC_P, KeyEvent.VK_P);
        register(NativeKeyEvent.VC_Q, KeyEvent.VK_Q);
        register(NativeKeyEvent.VC_R, KeyEvent.VK_R);
        register(NativeKeyEvent.VC_S, KeyEvent.VK_S);
        register(NativeKeyEvent.VC_T, KeyEvent.VK_T);
        register(NativeKeyEvent.VC_U, KeyEvent.VK_U);
        register(NativeKeyEvent.VC_V, KeyEvent.VK_V);
        register(NativeKeyEvent.VC_W, KeyEvent.VK_W);
        register(NativeKeyEvent.VC_X, KeyEvent.VK_X);
        register(NativeKeyEvent.VC_Y, KeyEvent.VK_Y);
        register(NativeKeyEvent.VC_Z, KeyEvent.VK_Z);
    }

    private static void register(int jnaEvent, int awtEvent) {
        keyMap.put(jnaEvent, awtEvent);
    }

    public static int hotkeyToEvent(HotkeyData data) {
        if (data == null) return INVALID_HOTKEY;
        if (!keyMap.containsKey(data.keyCode)) return INVALID_HOTKEY;
        return keyMap.get(data.keyCode);
    }

}
