package com.slimtrade.core.saving.legacy.patcher;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.legacy.ISavePatcher;
import com.slimtrade.core.saving.legacy.savefiles.LegacyOverlaySave0;
import com.slimtrade.core.saving.savefiles.OverlaySaveFile;
import com.slimtrade.modules.saving.SaveFile;

import java.awt.*;

public class PatcherOverlay0to1 implements ISavePatcher {

    @Override
    public boolean requiresPatch() {
        return SaveManager.overlaySaveFile.data.saveFileVersion < 1;
    }

    @Override
    public boolean patch() {
        SaveFile<LegacyOverlaySave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "overlay.json", LegacyOverlaySave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) return false;
        LegacyOverlaySave0 legacyData = legacySaveFile.data;
        OverlaySaveFile data = SaveManager.overlaySaveFile.data;

        // Messages
        // FIXME: Can't import message location until new message location is reworked
//        data.messageLocation = new Point(legacyData.messageX, legacyData.messageY);
//        data.messageExpandDirection = legacyData.messageExpandDirection.expandDirection;

        // Menubar
        Point menubarLocation = new Point(legacyData.menubarX, legacyData.menubarY);
        if (legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.NE
                || legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.SE)
            menubarLocation.x += legacyData.menubarWidth;
        if (legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.SW
                || legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.SE)
            menubarLocation.y += legacyData.menubarHeight;
        data.menubarLocation = menubarLocation;
        data.menubarAnchor = legacyData.menubarButtonLocation.anchor;

        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.overlaySaveFile.data.saveFileVersion = 1;
        SaveManager.overlaySaveFile.saveToDisk();
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.overlaySaveFile.initData();
    }

}
