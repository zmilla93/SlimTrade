package com.slimtrade.gui.menubar;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.PreloadedImage;

import javax.swing.*;

public class MenubarExpandDialog extends BasicDialog {

    private static final long serialVersionUID = 1L;
    private int size = MenubarButton.HEIGHT;
    private IconButton expandButton;

    public MenubarExpandDialog() {
        this.setBounds(0, TradeUtility.screenSize.height - size, size, size);
//        this.getContentPane().setBackground(Color.RED);
//        expandButton = new IconButton(PreloadedImage.TAG.getImage(), size);
        expandButton = new MenubarExpandButton();
        this.add(expandButton);
        expandButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                FrameManager.menubar.setShow(true);
                FrameManager.menubarToggle.setShow(false);
            }
        });

        expandButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                FrameManager.menubar.setShow(true);
                FrameManager.menubarToggle.setShow(false);
            }
        });
        this.updateColor();
    }

    public void updateLocation() {
        int x = App.saveManager.overlaySaveFile.menubarX;
        int y = App.saveManager.overlaySaveFile.menubarY;
        MenubarButtonLocation loc = App.saveManager.overlaySaveFile.menubarButtonLocation == null ? MenubarButtonLocation.NW : App.saveManager.overlaySaveFile.menubarButtonLocation;
        int modX = 0;
        int modY = 0;
        switch (loc) {
            case NW:
                modX = 0;
                modY = 0;
                break;
            case NE:
                modX = FrameManager.menubar.getWidth() - this.getWidth();
                modY = 0;
                break;
            case SW:
                modX = 0;
                modY = FrameManager.menubar.getHeight()-this.getHeight();
                break;
            case SE:
                modX = FrameManager.menubar.getWidth() - this.getWidth();
                modY = FrameManager.menubar.getHeight() - this.getHeight();
                break;
        }
        this.setLocation(x + modX, y + modY);
        this.revalidate();
        this.repaint();
    }

}
