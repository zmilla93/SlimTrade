package com.slimtrade.gui.overlay;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.basic.CustomSlider;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.enums.ExpandDirection;
import com.slimtrade.gui.messaging.MessagePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class OverlayInfoDialog extends BasicDialog implements IColorable {

    //Labels
    private JLabel title = new CustomLabel("Overlay Manager");
    private JLabel info1 = new CustomLabel("Left click to move panels.");
    private JLabel info2 = new CustomLabel("Right click to toggle panels going off screen.");
    private JLabel menubarLabel = new CustomLabel("Menubar Button");
    private JLabel expandLabel = new CustomLabel("Message Expand Direction");
    private JLabel sizeLabel = new CustomLabel("Message Size");

    public JComboBox<MenubarButtonLocation> menubarCombo = new CustomCombo<>();
    public JComboBox<ExpandDirection> messageCombo = new CustomCombo<>();
    public JSlider messageSizeSlider = new CustomSlider();

    public JButton defaultsButton = new BasicButton("Restore Defaults");
    public JButton cancelButton = new DenyButton("Cancel");
    public JButton saveButton = new ConfirmButton("Apply");

    private Container container = this.getContentPane();

    public OverlayInfoDialog() {

        // Slider
        messageSizeSlider.setMinimum(0);
        messageSizeSlider.setMaximum(10);
        messageSizeSlider.setMajorTickSpacing(5);
        messageSizeSlider.setMinorTickSpacing(1);
        Dimension sliderSize = messageSizeSlider.getPreferredSize();
        sliderSize.height = HEIGHT;
        messageSizeSlider.setSize(sliderSize.width, 10);

        container.setLayout(FrameManager.gridBag);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.top = 20;
        gc.insets.left = 20;
        gc.insets.right = 20;
        gc.insets.bottom = 10;
        // Info
        gc.gridwidth = 3;
        container.add(title, gc);
        gc.insets.top = 0;
        gc.insets.bottom = 0;
        gc.gridy++;
        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        container.add(info2, gc);
        gc.insets.bottom = 0;
        gc.gridy++;

        // Combo Menubar Button
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.insets.bottom = 5;
        gc.fill = GridBagConstraints.BOTH;
        container.add(menubarLabel, gc);
        gc.gridx = 2;
        gc.gridwidth = 1;
        container.add(menubarCombo, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        // Combo Message Expand
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.insets.bottom = 20;
        container.add(expandLabel, gc);
        gc.gridx = 2;
        gc.gridwidth = 1;
        container.add(messageCombo, gc);
        gc.gridx = 0;
        gc.gridy++;

        //TODO : Stuff
        // Message Size Slider
        container.add(sizeLabel, gc);
        gc.gridx++;
        gc.gridwidth = 2;
        container.add(messageSizeSlider, gc);
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy++;
        //
        TradeOffer trade = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "BuyerUsername", "Example Item", 1, "chaos", 20, "STASH_TAB", 0, 0, "", "");
        MessagePanel messagePanel = new MessagePanel(trade, new Dimension(400, 40), false);
        messagePanel.stopTimer();
        gc.gridwidth = 3;
        container.add(messagePanel, gc);
        gc.gridwidth = 1;
        gc.gridy++;


        // Revert/Save buttons

        // Defaults button
        container.add(cancelButton, gc);
        gc.gridx = 1;
        container.add(defaultsButton, gc);
        gc.gridx = 2;
        container.add(saveButton, gc);

        this.pack();
        Dimension size = this.getSize();
        this.setSize(size.width + 100, size.height + 50);
        this.setSize(size.width + 100, size.height + 50);

        messageSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//                System.out.println(messageSizeSlider.getValue());
//                messagePanel.resizeMessage();
            }
        });

    }

    public void updateColor() {
        super.updateColor();
        int i = 4;
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(i, i, i, i, ColorManager.PRIMARY));
    }

}
