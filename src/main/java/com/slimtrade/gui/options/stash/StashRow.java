package com.slimtrade.gui.options.stash;

import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.modules.colortheme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;

public class StashRow extends JPanel {

    private Container parent;
    private JButton removeButton = new IconButton("/icons/default/closex64.png");
    private JTextField nameInput = new JTextField(20);
    private JComboBox<MatchType> matchTypeCombo = new JComboBox<>();
    private JComboBox<StashTabType> stashTypeCombo = new JComboBox<>();
    private JComboBox<Color> colorCombo = new ColorCombo();

    public StashRow(Container parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());

        for (MatchType matchType : MatchType.values()) matchTypeCombo.addItem(matchType);
        for (StashTabType stashType : StashTabType.values()) stashTypeCombo.addItem(stashType);

        GridBagConstraints gc = ZUtil.getGC();
        add(removeButton, gc);
        gc.gridx++;
        add(nameInput, gc);
        gc.gridx++;
        add(matchTypeCombo, gc);
        gc.gridx++;
        add(stashTypeCombo, gc);
        gc.gridx++;
        add(colorCombo, gc);
        gc.gridx++;
        addListeners();
    }

    private void addListeners() {
        JPanel self = this;
        removeButton.addActionListener(e -> {
            parent.remove(self);
            parent.revalidate();
            parent.repaint();
        });
    }

    public StashTabData getData() {
        return new StashTabData(
                nameInput.getText(),
                (MatchType) matchTypeCombo.getSelectedItem(),
                (StashTabType) stashTypeCombo.getSelectedItem(),
                colorCombo.getSelectedIndex());
    }

    public void setData(StashTabData data) {
        nameInput.setText(data.stashTabName);
        matchTypeCombo.setSelectedItem(data.matchType);
        stashTypeCombo.setSelectedItem(data.stashTabType);
        colorCombo.setSelectedIndex(data.stashColorIndex);
//        colorCombo.setSelectedItem(data.stashTabColor.getBackground());
    }

}
