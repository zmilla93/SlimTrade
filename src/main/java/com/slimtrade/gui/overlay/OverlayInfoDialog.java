package com.slimtrade.gui.overlay;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.enums.ExpandDirection;

import javax.swing.*;
import java.awt.*;

public class OverlayInfoDialog extends BasicDialog implements IColorable {

    //Labels
    private JLabel title = new CustomLabel("Overlay Manager");
    private JLabel info1 = new CustomLabel("Left click to move panels.");
    private JLabel info2 = new CustomLabel("Right click to toggle panels going off screen.");
    private JLabel menubarLabel = new CustomLabel("Menubar Button");
    private JLabel messageLabel = new CustomLabel("Message Expand Direction");

    public JButton defaultsButton = new BasicButton("Restore Defaults");
    public JButton cancelButton = new DenyButton("Cancel");
    public JButton saveButton = new ConfirmButton("Apply");

    public JComboBox<MenubarButtonLocation> menubarCombo = new CustomCombo<>();
    public JComboBox<ExpandDirection> messageCombo = new CustomCombo<>();

    public OverlayInfoDialog() {
        this.setLayout(FrameManager.gridBag);
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
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.insets.bottom = 5;
        gc.fill = GridBagConstraints.BOTH;
        this.add(menubarLabel, gc);
        gc.gridx = 2;
        gc.gridwidth = 1;
        this.add(menubarCombo, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        // Combo Message Expand
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.insets.bottom = 20;
        this.add(messageLabel, gc);
        gc.gridx = 2;
        gc.gridwidth = 1;
        this.add(messageCombo, gc);
        gc.gridy++;

        // Revert/Save buttons
        gc.gridx = 0;
        // Defaults button
        this.add(cancelButton, gc);
        gc.gridx = 1;
        this.add(defaultsButton, gc);
        gc.gridx = 2;
        this.add(saveButton, gc);



    }

    public void updateColor() {
        super.updateColor();
        int i = 4;
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(i, i, i, i, ColorManager.PRIMARY));
    }


}
