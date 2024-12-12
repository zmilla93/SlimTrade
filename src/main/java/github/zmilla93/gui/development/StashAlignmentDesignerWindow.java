package github.zmilla93.gui.development;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.gui.windows.BasicDialog;
import github.zmilla93.modules.saving.ISaveListener;
import github.zmilla93.modules.theme.ThemeManager;

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
        SaveManager.stashSaveFile.addListener(this);
    }

    public StashAlignmentDesignerPanel getStashAlignmentDesigner() {
        return stashAlignmentDesignerPanel;
    }

    private void updateBounds() {
        Rectangle rect = SaveManager.stashSaveFile.data.gridRect;
        if (rect == null) return;
        setLocation(rect.getLocation());
        pack();
    }

    @Override
    public void onSave() {
        updateBounds();
    }

}
