package com.slimtrade.core.jna;

import org.jetbrains.annotations.Nullable;

public interface WindowCallback {

    void onWindowFound(@Nullable NativePoeWindow window);

}
