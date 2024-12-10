package com.slimtrade.core.jna;

import com.sun.jna.platform.win32.WinDef;
import org.jetbrains.annotations.Nullable;

public interface WindowCallback {

    void onWindowFound(@Nullable WinDef.HWND handle);

}
