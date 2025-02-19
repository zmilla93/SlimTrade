package github.zmilla93.core.saving.savefiles;

import github.zmilla93.gui.pinning.PinData;
import github.zmilla93.modules.saving.AbstractSaveFile;

import java.util.ArrayList;

public class PinSaveFile extends AbstractSaveFile {

    public ArrayList<PinData> appWindows = new ArrayList<>();
    public ArrayList<PinData> cheatSheetWindows = new ArrayList<>();
    public PinData combinedSearchWindow;
    public ArrayList<PinData> searchWindows = new ArrayList<>();

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
