package com.slimtrade.gui.windows;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.IconLabel;

import java.awt.*;
import java.io.File;

public class CheatSheetWindow extends CustomDialog {

    public static CheatSheetWindow createCheatSheet(CheatSheetData data) {
        File file = new File(SaveManager.getImagesDirectory() + data.fileName);
        if (file.exists() && file.isFile()) {
            CheatSheetWindow window = new CheatSheetWindow(data);
            window.setVisible(true);
            return window;
        }
        return null;
    }

    private CheatSheetWindow(CheatSheetData data) {
        super(data.fileName, true);
        IconLabel label = new IconLabel(SaveManager.getImagesDirectory() + data.fileName, true);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(label, BorderLayout.CENTER);
        pack();
    }

}
