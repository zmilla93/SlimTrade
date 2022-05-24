package com.slimtrade.gui.windows;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.IconLabel;

import java.awt.*;
import java.io.File;

public class CheatSheetWindow extends CustomDialog {

    protected boolean valid = true;

    public static CheatSheetWindow createCheatSheet(CheatSheetData data) {
        File file = new File(SaveManager.getImagesDirectory() + data.fileName);
        if (file.exists() && file.isFile()) {
            CheatSheetWindow window = new CheatSheetWindow(data);
            if (window.valid) {
                return window;
            }
            window.dispose();
        }
        return null;
    }

    private CheatSheetWindow(CheatSheetData data) {
        super(data.title, true);
        setFocusable(false);
        setFocusableWindowState(false);
        IconLabel label = new IconLabel(SaveManager.getImagesDirectory() + data.fileName, true);
        if (label.getIcon() == null) valid = false;
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(label, BorderLayout.CENTER);
        pack();
    }

}
