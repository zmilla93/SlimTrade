package com.slimtrade.gui.overlay;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomSlider;
import com.slimtrade.gui.enums.ExpandDirection;
import com.slimtrade.gui.messaging.MessageDialogManager;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.options.general.LabelComponentPanel;

import javax.swing.*;
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
    private JPanel messageWrapper;

    private JDialog messageDialog;
    private JPanel sliderPanel;

    //TODO : Move this to message panel?
    private static final int MAX_SIZE = 8;

    public OverlayInfoDialog() {

        // Slider
        messageSizeSlider.setMinimum(0);
        messageSizeSlider.setMaximum(MAX_SIZE);
        messageSizeSlider.setMajorTickSpacing(2);
        messageSizeSlider.setMinorTickSpacing(1);
        Dimension sliderSize = messageSizeSlider.getPreferredSize();
        sliderSize.height = HEIGHT;
        messageSizeSlider.setSize(sliderSize.width, 10);

        // Label Components
        LabelComponentPanel menubarPanel = new LabelComponentPanel(menubarLabel, menubarCombo);
        LabelComponentPanel expandPanel = new LabelComponentPanel(expandLabel, messageCombo);
        LabelComponentPanel sizePanel = new LabelComponentPanel(sizeLabel, messageSizeSlider);

        // Init
        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        gc.insets.top = 20;
        gc.insets.left = 20;
        gc.insets.right = 20;
        gc.insets.bottom = 10;

        // Info Labels
        gc.gridwidth = 3;
        container.add(title, gc);
        gc.insets.top = 0;
        gc.insets.bottom = 0;
        gc.gridy++;
        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.top = 5;

        gc.gridwidth = 3;
        // Combo Menubar Button
        gc.fill = GridBagConstraints.BOTH;
        container.add(menubarPanel, gc);
        gc.gridwidth = 0;
        gc.gridy++;

        // Combo Message Expand
        container.add(expandPanel, gc);
        gc.gridwidth = 0;
        gc.gridy++;

        // Message Size Slider
        container.add(sizePanel, gc);
        gc.gridwidth = 0;
        gc.gridy++;

        // Example Message
        messageWrapper = new JPanel(new GridBagLayout());
        messageWrapper.setPreferredSize(new Dimension(400 + 8 + MAX_SIZE * 2, 40 + 8 + MAX_SIZE * 2));
        messageWrapper.setMinimumSize(new Dimension(400 + 8 + MAX_SIZE * 2, 40 + 8 + MAX_SIZE * 2));
        TradeOffer trade = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "BuyerUsername", "Example Item", 1, "chaos", 20, "STASH_TAB", 0, 0, "", "");
        MessagePanel messagePanel = new MessagePanel(trade, MessageDialogManager.getMessageSize(), false);
//        messagePanel.setSize(new Dimension(MessageDialogManager.DEFAULT_SIZE.width + App.saveManager.overlaySaveFile.messageSizeIncrease, MessageDialogManager.DEFAULT_SIZE.height + App.saveManager.overlaySaveFile.messageSizeIncrease));
        messagePanel.stopTimer();
        messageWrapper.add(messagePanel);
        gc.gridwidth = 3;
        container.add(messageWrapper, gc);
        gc.gridwidth = 1;
        gc.gridy++;

        // Buttons
        gc.insets.bottom = 20;
        container.add(cancelButton, gc);
        gc.gridx = 1;
        container.add(defaultsButton, gc);
        gc.gridx = 2;
        container.add(saveButton, gc);


        this.pack();

        messageSizeSlider.addChangeListener(e -> {
            int size = messageSizeSlider.getValue() * 2;
            Dimension dimension = new Dimension(400 + size, 40 + size);
            messagePanel.resizeFrames(dimension, false);
            if (messageDialog != null) {
                messageDialog.setSize(dimension.width + 8, dimension.height + 8);
            }
            revalidate();
            repaint();
        });

    }

    public void registerMessageDialog(JDialog dialog) {
        this.messageDialog = dialog;
    }

    @Override
    public void updateColor() {
        super.updateColor();
        int i = 4;
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(i, i, i, i, ColorManager.PRIMARY));
        messageWrapper.setBackground(ColorManager.BACKGROUND);
    }

}
