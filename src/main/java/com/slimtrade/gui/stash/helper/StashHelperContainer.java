package com.slimtrade.gui.stash.helper;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.basic.BasicDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StashHelperContainer extends BasicDialog {
    //TODO : Should probably get spacing X directly from stash overlay left buffer
    private static final long serialVersionUID = 1L;
    public static int height = StashHelper.height;
    private int SPACING_X = 5;
    private final int SMALL_OFFSET_Y = 20;
    private final int LARGE_OFFSET_Y = 55;

    //TODO : Recheck all resizing
    public StashHelperContainer() {
        this.setBackground(ColorManager.CLEAR);
        this.setBounds(0, 0, 0, 0);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, SPACING_X, 0));
        this.pack();
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                repaint();
            }
        });
//        this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    public void updateLocation() {
        int offset = App.saveManager.settingsSaveFile.folderOffset ? LARGE_OFFSET_Y : SMALL_OFFSET_Y;
        this.setLocation(App.saveManager.stashSaveFile.windowX, App.saveManager.stashSaveFile.windowY - offset);
        this.pack();
    }

}
