package github.zmilla93.core.saving.savefiles;

import github.zmilla93.gui.chatscanner.ChatScannerEntry;

import java.util.ArrayList;

public class ChatScannerSaveFile extends AbstractSaveFile {

    public ArrayList<ChatScannerEntry> scannerEntries = new ArrayList<>();
    public transient ArrayList<ChatScannerEntry> activeSearches = new ArrayList<>();
    public int[] selectedIndexes;
    public boolean searching;

    @Override
    public int getCurrentTargetVersion() {
        return 2;
    }
}
