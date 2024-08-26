package com.slimtrade.gui.development;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.ThemeManager;

import java.awt.*;

/**
 * Used during development for recording location of stash UI elements.
 */
public class StashAlignmentDesignerWindow extends BasicDialog implements ISaveListener {

    private final StashAlignmentDesignerPanel stashAlignmentDesignerPanel = new StashAlignmentDesignerPanel();

    public StashAlignmentDesignerWindow() {
        setLayout(new BorderLayout());
        add(stashAlignmentDesignerPanel, BorderLayout.CENTER);
        pack();
        updateBounds();
        setBackground(ThemeManager.TRANSPARENT);
        setVisible(true);
        SaveManager.stashSaveFile.addListener(this);
    }

    public StashAlignmentDesignerPanel getStashAlignmentDesigner() {
        return stashAlignmentDesignerPanel;
    }

    private void updateBounds() {
        Rectangle rect = SaveManager.stashSaveFile.data.gridRect;
        setLocation(rect.getLocation());
        pack();
    }

    @Override
    public void onSave() {
        updateBounds();
    }

}
