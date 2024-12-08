package com.slimtrade.gui.windows;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.listening.IDefaultSizeAndLocation;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CheatSheetWindow extends CustomDialog implements IDefaultSizeAndLocation {

    protected boolean valid = true;

    public static CheatSheetWindow createCheatSheet(CheatSheetData data) {
        File file = SaveManager.getImagesDirectory().resolve(data.fileName).toFile();
        if (file.exists() && file.isFile()) {
            CheatSheetWindow window = new CheatSheetWindow(data);
            if (window.valid) return window;
            window.dispose();
        }
        return null;
    }

    private CheatSheetWindow(CheatSheetData data) {
        super(data.title(), true, false);
        pinRespectsSize = false;
        setResizable(false);
        setMinimumSize(new Dimension(0, 0));
        setFocusable(false);
        setFocusableWindowState(false);
        ImageIcon icon = new ImageIcon(SaveManager.getImagesDirectory().resolve(data.fileName).toString());
        JLabel label = new JLabel();
        label.setIcon(icon);
        if (label.getIcon() == null) valid = false;
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(label, BorderLayout.CENTER);
        pack();
        setMinimumSize(getSize());
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        setLocation(-RESIZER_PANEL_SIZE, -RESIZER_PANEL_SIZE);
        pack();
    }

}
