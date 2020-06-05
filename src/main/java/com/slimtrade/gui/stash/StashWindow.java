package com.slimtrade.gui.stash;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.stash.helper.ItemHighlighter;

import javax.swing.*;
import java.awt.*;

public class StashWindow extends AbstractResizableWindow implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;

    private StashGridPanel gridPanel;

    private JPanel buttonPanel = new JPanel();


    public StashWindow() {
        super("Stash Overlay", false);
        this.setFocusable(true);
        this.setFocusableWindowState(true);
        this.setMinimumSize(new Dimension(400, 400));
        container.setBackground(ColorManager.CLEAR);
        center.setBackground(ColorManager.CLEAR);
        this.setBackground(ColorManager.CLEAR);

        int buffer = 10;
        JPanel gridOuter = new JPanel();
        gridOuter.setLayout(new BorderLayout());
        gridOuter.setBackground(ColorManager.CLEAR);
        container.setLayout(new BorderLayout());
        gridOuter.add(new BufferPanel(0, 40), BorderLayout.NORTH);
        gridOuter.add(new BufferPanel(buffer, 0), BorderLayout.WEST);
        gridOuter.add(new BufferPanel(0, 40), BorderLayout.SOUTH);
        gridOuter.add(new BufferPanel(buffer, 0), BorderLayout.EAST);

        gridPanel = new StashGridPanel();
        gridOuter.add(gridPanel, BorderLayout.CENTER);

        container.add(gridOuter, BorderLayout.CENTER);

        GridBagConstraints gc = new GridBagConstraints();

        JButton gridResizeButton = new BasicButton("Grid");
        JButton resetButton = new DenyButton("Cancel");
        JButton saveButton = new ConfirmButton("Save");

        buttonPanel.setLayout(new GridBagLayout());

        gc.gridx = 0;
        gc.gridy = 0;
        Insets inset = new Insets(10, 0, 10, 60);
        gc.insets = inset;

        buttonPanel.add(gridResizeButton, gc);
        gc.gridx++;
        buttonPanel.add(resetButton, gc);
        gc.gridx++;
        inset.right = 0;
        buttonPanel.add(saveButton, gc);
        container.add(buttonPanel, BorderLayout.SOUTH);

        gridResizeButton.addActionListener(e -> {
            int count = gridPanel.getGridCellCount();
            if (count == 12) {
                gridPanel.setGridCellCount(24);
            } else if (count == 24) {
                gridPanel.setGridCellCount(0);
            } else {
                gridPanel.setGridCellCount(12);
            }
            repaint();
        });

        resetButton.addActionListener(e -> {
            load();
            closeOverlay();
        });

        saveButton.addActionListener(e -> {
            this.setVisible(true);
            ItemHighlighter.setGridInfo(gridPanel.getLocationOnScreen().x, gridPanel.getLocationOnScreen().y, gridPanel.getWidth(), gridPanel.getHeight());
            save();
            FrameManager.stashHelperContainer.updateLocation();
            closeOverlay();
        });

        this.load();

    }

    public void save() {
        Point winPos = this.getLocation();
        Dimension winSize = this.getSize();
        App.saveManager.stashSaveFile.initialized = true;
        App.saveManager.stashSaveFile.windowX = winPos.x;
        App.saveManager.stashSaveFile.windowY = winPos.y;
        App.saveManager.stashSaveFile.windowWidth = winSize.width;
        App.saveManager.stashSaveFile.windowHeight = winSize.height;
        App.saveManager.stashSaveFile.gridX = winPos.x + gridPanel.getX();
        App.saveManager.stashSaveFile.gridY = winPos.y + gridPanel.getY() + this.TITLEBAR_HEIGHT;
        App.saveManager.stashSaveFile.gridWidth = gridPanel.getWidth();
        App.saveManager.stashSaveFile.gridHeight = gridPanel.getHeight();
        App.saveManager.saveStashToDisk();

    }

    public void load() {
        ItemHighlighter.setGridInfo(App.saveManager.stashSaveFile.gridX, App.saveManager.stashSaveFile.gridY, App.saveManager.stashSaveFile.gridWidth, App.saveManager.stashSaveFile.gridHeight);
        this.setLocation(App.saveManager.stashSaveFile.windowX, App.saveManager.stashSaveFile.windowY);
        this.setSize(App.saveManager.stashSaveFile.windowWidth, App.saveManager.stashSaveFile.windowHeight);
    }

    private void closeOverlay() {
        this.setShow(false);
        if (FrameManager.lastWindowState == WindowState.SETUP) {
            FrameManager.windowState = WindowState.SETUP;
            FrameManager.setupWindow.refreshButtons();
            FrameManager.setupWindow.setVisible(true);
            FrameManager.setupWindow.setAlwaysOnTop(false);
            FrameManager.setupWindow.setAlwaysOnTop(true);
        } else {
            FrameManager.windowState = WindowState.NORMAL;
            FrameManager.showVisibleFrames();
            FrameManager.showOptionsWindow();
        }
        FrameManager.lastWindowState = WindowState.STASH_OVERLAY;
    }

    @Override
    public void updateColor() {
        super.updateColor();
        buttonPanel.setBackground(ColorManager.BACKGROUND);
    }
}
