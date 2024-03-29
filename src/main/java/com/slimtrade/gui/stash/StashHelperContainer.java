package com.slimtrade.gui.stash;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.IThemeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that holds StashHelper panels (or StashHelperBulkWrappers for bulk trades)
 *
 * @see StashHelperPanel
 * @see StashHelperBulkWrapper
 */
public class StashHelperContainer extends BasicDialog implements IThemeListener, ISaveListener {

    private static final int DEFAULT_OFFSET = 30;
    private static final int FOLDER_OFFSET = 75;
    public static final int INSET = 4;
    private final GridBagConstraints gc = ZUtil.getGC();

    public StashHelperContainer() {
        contentPanel.setLayout(new GridBagLayout());
        gc.anchor = GridBagConstraints.SOUTH;
        gc.insets.right = INSET;

        setBackground(ThemeManager.TRANSPARENT);
        setVisible(true);
        updateLocation();
        ThemeManager.addThemeListener(this);
        SaveManager.stashSaveFile.addListener(this);
    }

    public void updateLocation() {
        if (SaveManager.stashSaveFile.data.gridRect == null) return;
        Point target = SaveManager.stashSaveFile.data.gridRect.getLocation();
        int offset = SaveManager.settingsSaveFile.data.folderOffset ? FOLDER_OFFSET : DEFAULT_OFFSET;
        target.y -= getHeight() + offset;
        setLocation(target);
    }

    public Component addHelper(TradeOffer offer) {
        Component panel = new StashHelperPanel(offer);
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(panel, gc);
        refresh();
        return panel;
    }

    public void addHelper(Component component) {
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(component, gc);
        refresh();
    }

    public void refresh() {
        revalidate();
        repaint();
        pack();
        updateLocation();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public void onThemeChange() {
        refresh();
    }

    @Override
    public void onSave() {
        // Update stash helper locations if stash location is changed.
        for (Component component : contentPanel.getComponents()) {
            if (component instanceof StashHelperPanel) {
                StashHighlighterFrame highlighterFrame = ((StashHelperPanel) component).getHighlighterFrame();
                if (highlighterFrame != null) highlighterFrame.updateSizeAndLocation();
            } else if (component instanceof StashHelperBulkWrapper) {
                for (StashHelperPanel helperPanel : ((StashHelperBulkWrapper) component).getHelperPanels()) {
                    StashHighlighterFrame highlighterFrame = helperPanel.getHighlighterFrame();
                    if (highlighterFrame != null) highlighterFrame.updateSizeAndLocation();
                }
            }
        }
    }

}
