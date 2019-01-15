package com.zrmiller.slimtrade;

import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary {
    User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
    HWND GetForegroundWindow();  // add this
    int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
 }