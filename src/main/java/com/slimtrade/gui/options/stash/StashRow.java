package com.slimtrade.gui.options.stash;

import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.PlaceholderTextField;
import com.slimtrade.modules.theme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;

public class StashRow extends AddRemovePanel {

    private final JButton removeButton = new IconButton(DefaultIcon.CLOSE);
    private final JTextField nameInput = new PlaceholderTextField("Stash Tab Name...", 20);
    private final JComboBox<MatchType> matchTypeCombo = new JComboBox<>();
    private final JComboBox<StashTabType> stashTypeCombo = new JComboBox<>();
    private final JComboBox<Color> colorCombo = new ColorCombo();

    public StashRow(AddRemoveContainer<?> parent) {
        super(parent);
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
            removeFromParent();
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
