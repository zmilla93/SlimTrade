package com.slimtrade.core.jna;

import com.sun.jna.platform.win32.WinDef;

/**
 * Stores a JNA native window handle & title.
 */
public class NativeWindow {

    public final String title;
    public final WinDef.HWND handle;

    public NativeWindow(String title, WinDef.HWND handle) {
        this.title = title;
        this.handle = handle;
    }

}
