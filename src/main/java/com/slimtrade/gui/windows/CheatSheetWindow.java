package com.slimtrade.gui.windows;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.managers.SaveManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CheatSheetWindow extends CustomDialog {

    protected boolean valid = true;

    public static CheatSheetWindow createCheatSheet(CheatSheetData data) {
        File file = new File(SaveManager.getImagesDirectory() + data.fileName);
        if (file.exists() && file.isFile()) {
            CheatSheetWindow window = new CheatSheetWindow(data);
            if (window.valid) return window;
            window.dispose();
        }
        return null;
    }

    private CheatSheetWindow(CheatSheetData data) {
        super(data.title, true);
        setMinimumSize(new Dimension(0, 0));
        setFocusable(false);
        setFocusableWindowState(false);
        ImageIcon icon = new ImageIcon(SaveManager.getImagesDirectory() + data.fileName);
        JLabel label = new JLabel();
        label.setIcon(icon);
        if (label.getIcon() == null) valid = false;
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(label, BorderLayout.CENTER);
        pack();
        setMinimumSize(getSize());
    }

}
