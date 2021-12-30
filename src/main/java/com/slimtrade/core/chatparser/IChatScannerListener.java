package com.slimtrade.core.chatparser;

import com.slimtrade.core.trading.ChatScan;

public interface IChatScannerListener {

    void handle(ChatScan message);

}
