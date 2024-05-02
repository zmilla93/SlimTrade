package com.slimtrade.core.saving.savefiles;

import com.slimtrade.gui.chatscanner.ChatScannerEntry;

import java.util.ArrayList;

public class ChatScannerSaveFile extends AbstractSaveFile {

    public ArrayList<ChatScannerEntry> scannerEntries = new ArrayList<>();
    public transient ArrayList<ChatScannerEntry> activeSearches = new ArrayList<>();
    public int[] selectedIndexes;
    public boolean searching;

    @Override
    public int getTargetFileVersion() {
        return 2;
    }

}
