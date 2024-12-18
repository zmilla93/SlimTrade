package github.zmilla93.gui.stash;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.poe.POEWindowListener;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.windows.BasicDialog;
import github.zmilla93.modules.saving.ISaveListener;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.IThemeListener;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that holds StashHelper panels (or StashHelperBulkWrappers for bulk trades)
 *
 * @see StashHelperPanel
 * @see StashHelperBulkWrapper
 */
public abstract class StashHelperContainer extends BasicDialog implements POEWindowListener, IThemeListener, ISaveListener {

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
        POEWindow.addListener(this);
    }

    public abstract void updateLocation();

    public Component addHelper(TradeOffer offer) {
        Component panel = new StashHelperPanel(offer);
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(panel, gc);
        pack();
        return panel;
    }

    public void addHelper(Component component) {
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(component, gc);
        pack();
        updateLocation();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public void onThemeChange() {
        pack();
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

    @Override
    public void onGameBoundsChange() {
        updateLocation();
    }

}
