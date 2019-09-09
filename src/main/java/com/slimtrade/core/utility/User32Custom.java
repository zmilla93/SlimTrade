package com.slimtrade.core.utility;

import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;

public interface User32Custom extends StdCallLibrary {
	User32Custom INSTANCE = (User32Custom) Native.loadLibrary("user32", User32Custom.class);

	HWND GetForegroundWindow();

	int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
}