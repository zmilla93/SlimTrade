package com.slimtrade.core.saving.savefiles;

import com.slimtrade.gui.chatscanner.ChatScannerEntry;

import java.util.ArrayList;

public class ChatScannerSaveFile {

    public ArrayList<ChatScannerEntry> scannerEntries = new ArrayList<>();
    public transient boolean searching;
    public transient ArrayList<ChatScannerEntry> activeSearches = new ArrayList<>();
}
