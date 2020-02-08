package com.slimtrade.gui.stash;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.stash.helper.ItemHighlighter;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.basic.HideableDialog;

public class StashWindow extends AbstractResizableWindow implements ISaveable {

    private static final long serialVersionUID = 1L;

    private StashGridPanel gridPanel;
    private boolean vis = false;

    public StashWindow() {
        super("Stash Overlay", false);
        this.setMinimumSize(new Dimension(300, 300));
        container.setBackground(ColorManager.CLEAR);
        center.setBackground(ColorManager.CLEAR);
        this.setBackground(ColorManager.CLEAR);

        Logger.getAnonymousLogger().log(Level.ALL, "LOGGER");

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
        JPanel buttonPanel = new JPanel();
        JButton infoButton = new JButton("Grid");
        JButton resetButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");

        buttonPanel.setLayout(new GridBagLayout());

        gc.gridx = 0;
        gc.gridy = 0;
        Insets inset = new Insets(10, 0, 10, 60);
        gc.insets = inset;

        buttonPanel.add(infoButton, gc);
        gc.gridx++;
        buttonPanel.add(resetButton, gc);
        gc.gridx++;
        inset.right = 0;
        buttonPanel.add(saveButton, gc);
        container.add(buttonPanel, BorderLayout.SOUTH);

        HideableDialog local = this;
        infoButton.addActionListener(e ->{
                int count = gridPanel.getGridCellCount();
                if (count == 12) {
                    gridPanel.setGridCellCount(24);
                } else if (count == 24) {
                    gridPanel.setGridCellCount(0);
                } else {
                    gridPanel.setGridCellCount(12);
                }
                // gridPanel.pain
                local.repaint();
                // gridPanel.repaint();
        });

        resetButton.addActionListener(e -> {
                FrameManager.windowState = WindowState.NORMAL;
                vis = true;
                load();
                local.setShow(false);
                FrameManager.showVisibleFrames();
                FrameManager.showOptionsWindow();
        });

        saveButton.addActionListener(e -> {
            FrameManager.windowState = WindowState.NORMAL;
            save();
            FrameManager.stashHelperContainer.updateLocation();
            local.setShow(false);
            App.saveManager.saveToDisk();
            FrameManager.showVisibleFrames();
            FrameManager.showOptionsWindow();
        });

        App.eventManager.addColorListener(this);
        this.updateColor();

    }

    public void save() {
        Point winPos = this.getLocation();
        Dimension winSize = this.getSize();
        App.saveManager.stashSaveFile.windowX = winPos.x;
        App.saveManager.stashSaveFile.windowY = winPos.y;
        App.saveManager.stashSaveFile.windowWidth = winSize.width;
        App.saveManager.stashSaveFile.windowHeight = winSize.height;
        App.saveManager.stashSaveFile.gridX = winPos.x + gridPanel.getX();
        App.saveManager.stashSaveFile.gridY = winPos.y + gridPanel.getY() + this.TITLEBAR_HEIGHT;
        App.saveManager.stashSaveFile.gridWidth = gridPanel.getWidth();
        App.saveManager.stashSaveFile.gridHeight = gridPanel.getHeight();
        App.saveManager.saveStashToDisk();
        ItemHighlighter.setGridInfo(gridPanel.getLocationOnScreen().x, gridPanel.getLocationOnScreen().y, gridPanel.getWidth(), gridPanel.getHeight());
    }

    public void load() {
        ItemHighlighter.setGridInfo(App.saveManager.stashSaveFile.gridX, App.saveManager.stashSaveFile.gridY, App.saveManager.stashSaveFile.gridWidth, App.saveManager.stashSaveFile.gridHeight);
        this.setLocation(App.saveManager.stashSaveFile.windowX, App.saveManager.stashSaveFile.windowY);
        this.setSize(App.saveManager.stashSaveFile.windowWidth, App.saveManager.stashSaveFile.windowHeight);
    }

}
