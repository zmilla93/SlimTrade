package com.slimtrade.gui.stash;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.saving.StashTab;
import com.slimtrade.enums.StashTabColor;
import com.slimtrade.enums.StashTabType;
import com.slimtrade.gui.basic.ColorPanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.RemovablePanel;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomTextField;
import com.slimtrade.gui.enums.DefaultIcons;

import javax.swing.*;
import java.awt.*;

public class StashTabRow extends RemovablePanel implements IColorable {

    private static final long serialVersionUID = 1L;

    public static final int ROW_HEIGHT = 22;

    private IconButton removeButton;

    CustomTextField stashTabText;
    JComboBox<StashTabType> typeCombo;
    JComboBox<Color> colorCombo;
    private boolean delete;
    private boolean newRow = true;

    public StashTabRow(AddRemovePanel parent) {
        super(parent);
        this.setLayout(new GridBagLayout());
//        this.setPreferredSize(new Dimension(400, ROW_HEIGHT));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        removeButton = new IconButton(DefaultIcons.CLOSE, ROW_HEIGHT);
        this.setRemoveButton(removeButton);

        int stashTextWidth = 250;
            JPanel stashTabTextPanel = new ColorPanel(new GridBagLayout()){

        };
//        stashTabTextPanel.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT));
        stashTabTextPanel.setBackground(Color.RED);

        // Text Field
        stashTabText = new LimitTextField(31);
//		stashTabText.setColumns(20);
        stashTabText.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT));
        stashTabText.setHorizontalAlignment(JTextField.CENTER);
        stashTabTextPanel.add(stashTabText, gc);


        typeCombo = new CustomCombo<>();
        for (StashTabType type : StashTabType.values()) {
            typeCombo.addItem(type);
        }

        colorCombo = new CustomCombo<>();
        colorCombo.setFocusable(false);
        colorCombo.setRenderer(new StashTabCellRenderer());
        for (StashTabColor c : StashTabColor.values()) {
            colorCombo.addItem(c.getBackground());
        }

        this.add(removeButton, gc);
        gc.gridx++;
        gc.insets.left = 5;
        this.add(stashTabTextPanel, gc);
        gc.gridx++;
        this.add(typeCombo, gc);
        gc.gridx++;
        this.add(colorCombo, gc);



    }

    public String getText() {
        return stashTabText.getText();
    }

    public void setText(String text) {
        stashTabText.setText(text);
    }

    public StashTabType getType() {
        return (StashTabType) typeCombo.getSelectedItem();
    }

    public void setType(StashTabType type) {
        typeCombo.setSelectedItem(type);
    }

    public StashTabColor getColor() {
        return StashTabColor.getValueFromColor((Color) colorCombo.getSelectedItem());
    }

    public void setColor(StashTabColor color) {
        colorCombo.setSelectedItem(color.getBackground());
    }

    public StashTab getStashTabData() {
        return new StashTab(getText(), getType(), getColor());
    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBorder(BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_2));
    }

}
