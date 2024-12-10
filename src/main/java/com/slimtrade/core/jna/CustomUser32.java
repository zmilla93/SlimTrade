package com.slimtrade.core.jna;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

/**
 * An extension for {@link com.sun.jna.platform.win32.User32} that
 * exposing Windows specific functions from user32.dll.
 */
public interface CustomUser32 extends StdCallLibrary {

    CustomUser32 INSTANCE = Native.load("user32", CustomUser32.class);

    /// Returns true is the window is minimized.
    boolean IsIconic(WinDef.HWND hWnd);

    /// Converts a window relative point to a screen relative
    boolean ClientToScreen(WinDef.HWND hWnd, WinDef.POINT point);

}
