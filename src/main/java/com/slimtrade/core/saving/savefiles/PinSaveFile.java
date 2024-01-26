package com.slimtrade.core.saving.savefiles;

import com.slimtrade.gui.pinning.PinData;

import java.util.ArrayList;

public class PinSaveFile extends AbstractSaveFile {

    public ArrayList<PinData> appWindows = new ArrayList<>();
    public ArrayList<PinData> cheatSheetWindows = new ArrayList<>();
    public PinData combinedSearchWindow;
    public ArrayList<PinData> searchWindows = new ArrayList<>();

    @Override
    public int getTargetFileVersion() {
        return 1;
    }
}
