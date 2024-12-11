package github.zmilla93.gui.windows;

import github.zmilla93.App;
import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.stash.GridPanel;
import github.zmilla93.modules.saving.ISavable;
import github.zmilla93.modules.theme.IFontChangeListener;
import github.zmilla93.modules.theme.IThemeListener;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class StashGridWindow extends CustomDialog implements ISavable, IThemeListener, IFontChangeListener {

    private final JButton gridButton = new JButton("Grid");
    //    private final JButton alignButton = new JButton("Auto Align");
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    private final GridPanel gridPanel = new GridPanel();

    private final int INSET_HORIZONTAL = 8;
    private final int INSET_VERTICAL = 8;

    /**
     * Used to mark the location of POE's stash.
     * <p>
     * IMPORTANT : The location of gridPanel has to be calculated manually,
     * so moving it in the hierarchy would require updating setBoundsUsingGrid().
     */
    public StashGridWindow() {
        super("Stash Overlay");
        setFocusable(false);
        setFocusableWindowState(false);
        closeButton.setVisible(false);
        pinButton.setVisible(false);

        // Grid Panel
        JPanel gridBorderPanel = new JPanel(new BorderLayout());
        gridBorderPanel.setOpaque(false);
        ZUtil.addStrutsToBorderPanel(gridBorderPanel, new Insets(INSET_VERTICAL, INSET_HORIZONTAL, INSET_VERTICAL, INSET_HORIZONTAL));
        gridBorderPanel.add(gridPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        buttonPanel.add(gridButton, gc);
        gc.gridx++;
        gc.insets.left = 10;
        buttonPanel.add(cancelButton, gc);
        gc.gridx++;
        buttonPanel.add(saveButton, gc);
        gc.gridx++;

        // Content Panel
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(gridBorderPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setBackground(ThemeManager.TRANSPARENT);
        adjustTranslucentBackground();

        // Finalize
        pack();
        setSize(500, 500);
        addListeners();
        ThemeManager.addThemeListener(this);
        ThemeManager.addFontListener(this);
        SaveManager.stashSaveFile.registerSavableContainer(this);
    }

    private void addListeners() {
        gridButton.addActionListener(e -> {
            gridPanel.cycleGrid();
            repaint();
        });
        saveButton.addActionListener(e -> {
            save();
            SaveManager.stashSaveFile.saveToDisk();
            // FIXME : REMOVE
//            if (FrameManager.setupWindow != null)
//                FrameManager.setupWindow.getStashPanel().validateNextButton();
            restorePreviousWindowVisibility();
        });
        cancelButton.addActionListener(e -> {
            load();
            restorePreviousWindowVisibility();
        });
    }

    private void restorePreviousWindowVisibility() {
        AppState previousState = App.isRunningSetup() ? AppState.SETUP : AppState.RUNNING;
        FrameManager.setWindowVisibility(previousState);
    }

    private void setBoundsUsingGrid(Rectangle rect) {
        if (rect == null) return;
        Rectangle fixedRect = new Rectangle(rect);
        // Calculate Position (using getLocationOnScreen would be simpler, but requires the window to be visible)
        Point gridPos = gridPanel.getLocation();
        gridPos.x += BORDER_SIZE + getResizerSize();
        gridPos.y += BORDER_SIZE + getResizerSize() + getTitleBarHeight();
        fixedRect.x -= gridPos.x;
        fixedRect.y -= gridPos.y;
        // Calculate size
        Dimension gridSize = gridPanel.getSize();
        Dimension windowSize = getSize();
        int verticalExcess = windowSize.height - gridSize.height;
        int horizontalExcess = windowSize.width - gridSize.width;
        fixedRect.width += horizontalExcess;
        fixedRect.height += verticalExcess;
        // Apply
        setBounds(fixedRect);
    }

    private void adjustTranslucentBackground() {
        Color c1 = UIManager.getColor("Panel.background");
        Color c2 = UIManager.getColor("Label.foreground");
        Color color = ThemeManager.getLighterColor(c1, c2);
        contentPanel.setBackground(ThemeManager.adjustAlpha(color, 30));
    }

    @Override
    public void onThemeChange() {
        super.onThemeChange();
        adjustTranslucentBackground();
    }

    @Override
    public void save() {
        int gridX = getX() + getResizerSize() + INSET_HORIZONTAL + getBorderSize();
        int gridY = getY() + getResizerSize() + INSET_VERTICAL + getTitleBarHeight() + getBorderSize();
        SaveManager.stashSaveFile.data.gridRect = new Rectangle(gridX, gridY, gridPanel.getWidth(), gridPanel.getHeight());
        FrameManager.stashHelperContainerLegacy.updateLocation();
    }

    @Override
    public void load() {
        setBoundsUsingGrid(SaveManager.stashSaveFile.data.gridRect);
    }

    @Override
    public void onFontChanged() {
        setBoundsUsingGrid(SaveManager.stashSaveFile.data.gridRect);
    }

}
