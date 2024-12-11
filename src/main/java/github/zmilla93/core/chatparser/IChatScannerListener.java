package github.zmilla93.core.chatparser;

import github.zmilla93.core.data.PlayerMessage;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;

public interface IChatScannerListener {

    void onScannerMessage(ChatScannerEntry entry, PlayerMessage message, boolean loaded);

}
