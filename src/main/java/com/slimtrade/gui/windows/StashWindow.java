package com.slimtrade.gui.windows;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.InsetPanel;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stash.GridPanel;
import com.slimtrade.modules.colortheme.IThemeListener;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StashWindow extends CustomDialog implements IThemeListener, ISavable {

    private JButton gridButton = new JButton("Grid");
    private JButton alignButton = new JButton("Auto Align");
    private JButton saveButton = new JButton("Save");
    private JButton cancelButton = new JButton("Cancel");

    private GridPanel gridPanel = new GridPanel();

    private final int INSET_HORIZONTAL = 8;
    private final int INSET_VERTICAL = 20;

    public StashWindow() {
        super("Stash Overlay");
        setFocusable(false);
        setFocusableWindowState(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        InsetPanel insetPanel = new InsetPanel(new Insets(INSET_VERTICAL, INSET_HORIZONTAL, INSET_VERTICAL, INSET_HORIZONTAL));
        insetPanel.contentPanel.setLayout(new BorderLayout());
        insetPanel.contentPanel.add(gridPanel, BorderLayout.CENTER);
        insetPanel.contentPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gc = ZUtil.getGC();
        buttonPanel.add(gridButton, gc);
        gc.gridx++;
        buttonPanel.add(alignButton, gc);
        gc.gridx++;
        buttonPanel.add(cancelButton, gc);
        gc.gridx++;
        buttonPanel.add(saveButton, gc);
        gc.gridx++;

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(insetPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.setBackground(ColorManager.TRANSPARENT);

        ColorManager.addListener(this);

        getCloseButton().setVisible(false);
        addListeners();
        pack();
        setSize(500, 500);
        SaveManager.stashSaveFile.registerSavableContainer(this);
    }

    private void addListeners() {
        gridButton.addActionListener(e -> {
            gridPanel.cycleGrid();
            repaint();
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveManager.stashSaveFile.saveToDisk();
                setVisible(false);
                FrameManager.optionsWindow.setVisible(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
                setVisible(false);
                FrameManager.optionsWindow.setVisible(true);
            }
        });
    }

    @Override
    public void onThemeChange() {
        super.onThemeChange();
        Color c1 = UIManager.getColor("Panel.background");
        Color c2 = UIManager.getColor("Label.foreground");
        Color color = ColorManager.getDarkerColor(c1, c2);
        contentPanel.setBackground(ColorManager.adjustAlpha(color, 100));
    }

    @Override
    public void save() {
        int gridX = getX() + resizerPanelSize + INSET_HORIZONTAL + getBorderSize();
        int gridY = getY() + resizerPanelSize + INSET_VERTICAL + getTitleBarHeight() + getBorderSize();
        SaveManager.stashSaveFile.data.windowRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        SaveManager.stashSaveFile.data.gridRect = new Rectangle(gridX, gridY, gridPanel.getWidth(), gridPanel.getHeight());
        FrameManager.stashHelperContainer.updateLocation();
    }

    @Override
    public void load() {
        setLocation(SaveManager.stashSaveFile.data.windowRect.getLocation());
        setSize(SaveManager.stashSaveFile.data.windowRect.getSize());
    }
}
