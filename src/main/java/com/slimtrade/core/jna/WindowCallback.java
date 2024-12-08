package com.slimtrade.core.jna;

import com.sun.jna.platform.win32.WinDef;

public interface WindowCallback {

    void onWindowFound(String windowTitle, WinDef.HWND window);

}
