package com.slimtrade.gui.overlay;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.enums.ExpandDirection;

import javax.swing.*;
import java.awt.*;

public class OverlayInfoDialog extends BasicDialog {

    //Labels
    private JLabel title = new JLabel("Overlay Manager");
    private JLabel info1 = new JLabel("Left click to drag panels.");
    private JLabel info2 = new JLabel("Right click to prevent panels from going off screen.");
    private JLabel menubarLabel = new JLabel("Menubar Button");
    private JLabel messageLabel = new JLabel("Message Expand Direction");

    public JComboBox<MenubarButtonLocation> menubarCombo = new CustomCombo<>();
    public JComboBox<ExpandDirection> messageCombo = new CustomCombo<>();

    public OverlayInfoDialog() {
        this.setLayout(FrameManager.gridbag);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.top = 20;
        gc.insets.left = 20;
        gc.insets.right = 20;
        gc.insets.bottom = 10;
        // Info
        gc.gridwidth = 3;
        this.add(title, gc);
        gc.insets.top = 0;
        gc.insets.bottom = 0;
        gc.gridy++;
        this.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        this.add(info2, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        // Combo Menubar Button
        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.insets.bottom = 5;
        gc.fill = GridBagConstraints.BOTH;
        this.add(menubarLabel, gc);
        gc.gridx = 2;
        this.add(menubarCombo, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        // Combo Message Expand
        gc.gridx = 1;
        gc.insets.bottom = 20;
        this.add(messageLabel, gc);
        gc.gridx = 2;
        this.add(messageCombo, gc);
        gc.gridy++;

        int i = 4;
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(i, i, i, i, ColorManager.PRIMARY));
    }



}
