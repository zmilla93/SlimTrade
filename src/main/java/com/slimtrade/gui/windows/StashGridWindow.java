package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stash.GridPanel;
import com.slimtrade.modules.saving.ISavable;
import com.slimtrade.modules.theme.IThemeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class StashGridWindow extends CustomDialog implements IThemeListener, ISavable {

    private final JButton gridButton = new JButton("Grid");
    //    private final JButton alignButton = new JButton("Auto Align");
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    private final GridPanel gridPanel = new GridPanel();

    private final int INSET_HORIZONTAL = 8;
    private final int INSET_VERTICAL = 50;

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

        // Finalize
        pack();
        setSize(500, 500);
        addListeners();
        ThemeManager.addThemeListener(this);
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
            if (FrameManager.setupWindow != null)
                FrameManager.setupWindow.getStashPanel().validateNextButton();
            FrameManager.setWindowVisibility(App.getPreviousState());
        });
        cancelButton.addActionListener(e -> {
            load();
            FrameManager.setWindowVisibility(App.getPreviousState());
        });
    }

    public void setBoundsUsingGrid(Rectangle rect) {
        // Calculate Position (using getLocationOnScreen would be simpler, but requires the window to be visible)
        Point gridPos = gridPanel.getLocation();
        gridPos.x += BORDER_SIZE + getResizerSize();
        gridPos.y += BORDER_SIZE + getResizerSize() + getTitleBarHeight();
        rect.x -= gridPos.x;
        rect.y -= gridPos.y;
        // Calculate size
        Dimension gridSize = gridPanel.getSize();
        Dimension windowSize = getSize();
        int verticalExcess = windowSize.height - gridSize.height;
        int horizontalExcess = windowSize.width - gridSize.width;
        rect.width += horizontalExcess;
        rect.height += verticalExcess;
        // Apply
        setBounds(rect);
    }

    @Override
    public void onThemeChange() {
        super.onThemeChange();
        Color c1 = UIManager.getColor("Panel.background");
        Color c2 = UIManager.getColor("Label.foreground");
        Color color = ThemeManager.getDarkerColor(c1, c2);
        contentPanel.setBackground(ThemeManager.adjustAlpha(color, 100));
    }

    @Override
    public void save() {
        int gridX = getX() + getResizerSize() + INSET_HORIZONTAL + getBorderSize();
        int gridY = getY() + getResizerSize() + INSET_VERTICAL + getTitleBarHeight() + getBorderSize();
        SaveManager.stashSaveFile.data.windowRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        SaveManager.stashSaveFile.data.gridRect = new Rectangle(gridX, gridY, gridPanel.getWidth(), gridPanel.getHeight());
        FrameManager.stashHelperContainer.updateLocation();
    }

    @Override
    public void load() {
        if (SaveManager.stashSaveFile.data.windowRect == null) return;
        setBoundsUsingGrid(SaveManager.stashSaveFile.data.gridRect);
    }

}
