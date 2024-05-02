package com.slimtrade.core.chatparser;

import com.slimtrade.core.data.PlayerMessage;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;

public interface IChatScannerListener {

    void onScannerMessage(ChatScannerEntry entry, PlayerMessage message, boolean loaded);

}
